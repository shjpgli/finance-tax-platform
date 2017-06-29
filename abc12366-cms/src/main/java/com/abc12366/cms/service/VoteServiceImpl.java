package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.SubjectMapper;
import com.abc12366.cms.mapper.db1.VoteAdditionMapper;
import com.abc12366.cms.mapper.db1.VoteMapper;
import com.abc12366.cms.mapper.db2.SubjectRoMapper;
import com.abc12366.cms.mapper.db2.VoteAdditionRoMapper;
import com.abc12366.cms.mapper.db2.VoteRoMapper;
import com.abc12366.cms.model.*;
import com.abc12366.cms.model.bo.VoteStatAreaBO;
import com.abc12366.cms.model.bo.VoteStatBrowserBO;
import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

/**
 * 投票功能实现类
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-06-07 4:21 PM
 * @since 1.0.0
 */
@Service
public class VoteServiceImpl implements VoteService {

    // 投票
    @Autowired
    private VoteRoMapper voteRoMapper;

    @Autowired
    private VoteMapper voteMapper;

    // 题目
    @Autowired
    private SubjectRoMapper subjectRoMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private VoteAdditionMapper voteAdditionMapper;

    @Autowired
    private VoteAdditionRoMapper voteAdditionRoMapper;

    @Override
    public List<Vote> selectList(Vote vote, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<Vote> voteList = voteRoMapper.selectList(vote);
        // 根据发票状态、IP查询参与人数
        if (voteList != null && voteList.size() > 0) {
            voteList.stream().filter(v -> v.getStatus()).forEach(v -> {
                VoteResult vr = new VoteResult.Builder().voteId(v.getId()).build();
                v.setNop(voteRoMapper.selectResultCount(vr));
                v.setNov(voteRoMapper.selectHistoryCount(v.getId()));
            });
        }
        return voteList;
    }

    @Transactional("db1TxManager")
    @Override
    public Vote insert(Vote vote) {
        Timestamp now = new Timestamp(new Date().getTime());

        vote.setId(Utils.uuid());
        vote.setCreateTime(now);
        vote.setLastUpdate(now);
        voteMapper.insert(vote);

        // 投票附加信息
        if (vote.getAdditionList() != null && vote.getAdditionList().size() > 0) {
            for (VoteAddition va : vote.getAdditionList()) {
                VoteAddition voteAddition = new VoteAddition.Builder()
                        .id(Utils.uuid())
                        .voteId(vote.getId())
                        .dictId(va.getDictId())
                        .required(va.getRequired())
                        .build();
                voteAdditionMapper.insert(voteAddition);
            }
        }

        // 题目
        if (vote.getSubjectList() != null && vote.getSubjectList().size() > 0) {
            for (Subject s : vote.getSubjectList()) {
                Subject subject = new Subject.Builder()
                        .id(Utils.uuid())
                        .voteId(vote.getId())
                        .subject(s.getSubject())
                        .form(s.getForm())
                        .required(s.getRequired())
                        .sort(s.getSort())
                        .createTime(now)
                        .lastUpdate(now)
                        .build();
                subjectMapper.insertSubject(subject);

                // 选项
                if (s.getItemList() != null && s.getItemList().size() > 0) {
                    for (SubjectItem si : s.getItemList()) {
                        SubjectItem item = new SubjectItem.Builder()
                                .id(Utils.uuid())
                                .subjectId(subject.getId())
                                .type(si.getType())
                                .item(si.getItem())
                                .image(si.getImage())
                                .detail(si.getDetail())
                                .sort(si.getSort())
                                .build();
                        subjectMapper.insertItem(item);
                    }
                }
            }
        }
        return vote;
    }

    @Override
    public Vote selectOne(String id) {
        Vote vote = voteRoMapper.selectOne(id);

        if (vote != null) {
            // 查询附加信息
            vote.setAdditionList(voteAdditionRoMapper.selectList(vote.getId()));

            // 如果为已发布状态，统计参与总人数
            if (vote.getStatus()) {
                VoteResult vr = new VoteResult.Builder().voteId(vote.getId()).build();
                vote.setNop(voteRoMapper.selectResultCount(vr));
                vote.setNov(voteRoMapper.selectHistoryCount(vote.getId()));
            }
            List<Subject> subjectList = subjectRoMapper.selectSubjectList(vote.getId());
            if (subjectList != null && subjectList.size() > 0) {
                for (Subject subject : subjectList) {
                    List<SubjectItem> itemList = subjectRoMapper.selectItemList(subject.getId());
                    // 如果为已发布状态，统计得票数
                    if (vote.getStatus()) {
                        for (SubjectItem item : itemList) {
                            VoteResult vr = new VoteResult.Builder()
                                    .voteId(vote.getId())
                                    .subjectId(subject.getId())
                                    .itemId(item.getId())
                                    .build();
                            item.setNop(voteRoMapper.selectResultCount(vr));
                        }
                    }
                    subject.setItemList(itemList);
                }
                vote.setSubjectList(subjectList);
            }
            return vote;
        } else {
            throw new ServiceException(4012);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public Vote update(Vote vote) {
        Timestamp now = new Timestamp(new Date().getTime());
        Vote v = selectOne(vote.getId());
        if (v != null) {
            v.setName(vote.getName());
            v.setEndTime(vote.getEndTime());
            v.setLogin(vote.getLogin());
            v.setStatus(vote.getStatus());
            v.setLastUpdate(now);
            voteMapper.update(v);

            // 先删除附加信息
            List<VoteAddition> additionList = voteAdditionRoMapper.selectList(v.getId());
            if (additionList != null && additionList.size() > 0) {
                for (VoteAddition va : additionList) {
                    voteAdditionMapper.delete(va.getVoteId());
                }
            }

            // 再新增附加信息
            if (vote.getAdditionList() != null && vote.getAdditionList().size() > 0) {
                for (VoteAddition va : vote.getAdditionList()) {
                    VoteAddition voteAddition = new VoteAddition.Builder()
                            .id(Utils.uuid())
                            .voteId(vote.getId())
                            .dictId(va.getDictId())
                            .required(va.getRequired())
                            .build();
                    voteAdditionMapper.insert(voteAddition);
                }
            }

            // 先删除题目
            List<Subject> subjectList = subjectRoMapper.selectSubjectList(v.getId());
            if (subjectList != null && subjectList.size() > 0) {
                for (Subject subject : subjectList) {
                    subjectMapper.deleteItem(subject.getId());
                }
                subjectMapper.deleteSubject(v.getId());
            }
            // 再新增题目
            if (vote.getSubjectList() != null && vote.getSubjectList().size() > 0) {
                for (Subject s : vote.getSubjectList()) {
                    Subject subject = new Subject.Builder()
                            .id(Utils.uuid())
                            .voteId(vote.getId())
                            .subject(s.getSubject())
                            .form(s.getForm())
                            .required(s.getRequired())
                            .sort(s.getSort())
                            .createTime(now)
                            .lastUpdate(now)
                            .build();
                    subjectMapper.insertSubject(subject);

                    // 选项
                    if (s.getItemList() != null && s.getItemList().size() > 0) {
                        for (SubjectItem si : s.getItemList()) {
                            SubjectItem item = new SubjectItem.Builder()
                                    .id(Utils.uuid())
                                    .subjectId(subject.getId())
                                    .type(si.getType())
                                    .item(si.getItem())
                                    .image(si.getImage())
                                    .detail(si.getDetail())
                                    .sort(si.getSort())
                                    .build();
                            subjectMapper.insertItem(item);
                        }
                    }
                }
            }
            return selectOne(v.getId());
        } else {
            throw new ServiceException(4012);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(String id) {
        Vote v = selectOne(id);
        if (v != null) {
            List<Subject> subjectList = subjectRoMapper.selectSubjectList(v.getId());
            if (subjectList != null && subjectList.size() > 0) {
                for (Subject subject : subjectList) {
                    // 删除选项
                    subjectMapper.deleteItem(subject.getId());
                }
                // 删除题目
                subjectMapper.deleteSubject(v.getId());
            }
            // 删除附加信息
            List<VoteAddition> additionList = voteAdditionRoMapper.selectList(v.getId());
            if (additionList != null && additionList.size() > 0) {
                for (VoteAddition va : additionList) {
                    voteAdditionMapper.delete(va.getVoteId());
                }
            }
            // 删除投票信息
            voteMapper.delete(id);
        } else {
            throw new ServiceException(4012);
        }
    }

    @Override
    public List<VoteResult> vote(String voteId, List<VoteResult> resultList, HttpServletRequest request) {
        Vote v = selectOne(voteId);
        if (v != null) {
            // 判断是否在活动起止之间内
            Timestamp now = new Timestamp(new Date().getTime());
            if (now.before(v.getCreateTime()) || now.after(v.getEndTime())) {
                throw new ServiceException(4014);
            }

            String userId = (String) request.getAttribute(Constant.USER_ID);
            if (v.getLogin() && StringUtils.isEmpty(userId)) {
                throw new ServiceException(4013);
            }
            if (resultList.size() > 0) {
                List<VoteResult> dataList = new ArrayList<>();
                String addr = Utils.getAddr(request);
                String userAgent = Utils.getUserAgent(request);
                for (VoteResult result : resultList) {
                    result.setId(Utils.uuid());
                    result.setVoteId(voteId);
                    result.setSubjectId(result.getSubjectId());
                    result.setItemId(result.getItemId());
                    result.setUserId(userId);
                    result.setIp(addr);
                    result.setUserAgent(userAgent);
                    result.setCreateTime(now);
                    voteMapper.insertResult(result);
                    dataList.add(result);
                }
                return dataList;
            }
        }
        throw new ServiceException(4012);
    }

    @Override
    public VoteResult vote(VoteResult result, HttpServletRequest request) {
        Vote v = selectOne(result.getVoteId());
        if (v != null) {
            // 判断是否在活动起止之间内
            Timestamp now = new Timestamp(new Date().getTime());
            if (now.before(v.getCreateTime()) || now.after(v.getEndTime())) {
                throw new ServiceException(4014);
            }

            String userId = (String) request.getAttribute(Constant.USER_ID);
            if (v.getLogin() && StringUtils.isEmpty(userId)) {
                throw new ServiceException(4013);
            }
            String addr = Utils.getAddr(request);
            String userAgent = Utils.getUserAgent(request);
            result.setId(Utils.uuid());
            result.setUserId(userId);
            result.setIp(addr);
            result.setUserAgent(userAgent);
            result.setCreateTime(now);
            result.setCreateTime(now);
            voteMapper.insertResult(result);
            return result;
        }
        throw new ServiceException(4012);
    }

    @Override
    public VoteHistory insertHistory(String voteId, HttpServletRequest request) {
        String addr = Utils.getAddr(request);
        String userAgent = Utils.getUserAgent(request);
        VoteHistory vh = new VoteHistory.Builder()
                .id(Utils.uuid())
                .voteId(voteId)
                .ip(addr)
                .userAgent(userAgent)
                .createTime(new Timestamp(new Date().getTime()))
                .build();
                voteMapper.insertHistory(vh);
        return vh;
    }

    @Override
    public List<VoteStatBrowserBO> statBrowser(String voteId) {
        return voteRoMapper.statBrowser(voteId);
    }

    @Override
    public List<VoteStatAreaBO> statIpArea(String voteId) {
        return voteRoMapper.statIpArea(voteId);
    }

    @Override
    public Map<String, Integer> statViews(String voteId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("nop", voteRoMapper.selectResultCount(new VoteResult.Builder().voteId(voteId).build()));
        map.put("nov", voteRoMapper.selectHistoryCount(voteId));
        return map;
    }
}
