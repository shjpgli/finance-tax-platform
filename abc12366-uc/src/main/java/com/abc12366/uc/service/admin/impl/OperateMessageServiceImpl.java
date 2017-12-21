package com.abc12366.uc.service.admin.impl;

import com.abc12366.gateway.model.bo.AppBO;
import com.abc12366.gateway.service.AppService;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.MessageConstant;
import com.abc12366.gateway.util.RemindConstant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.OperateMessageMapper;
import com.abc12366.uc.mapper.db2.OperateMessageRoMapper;
import com.abc12366.uc.mapper.db2.UserExtendRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.Message;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.admin.OperateMessage;
import com.abc12366.uc.model.admin.bo.OperateMessageBO;
import com.abc12366.uc.model.admin.bo.YyxxLogBO;
import com.abc12366.uc.service.IWxTemplateService;
import com.abc12366.uc.service.MessageSendUtil;
import com.abc12366.uc.service.admin.OperateMessageService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-12-18
 * Time: 11:03
 */
@Service
public class OperateMessageServiceImpl implements OperateMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperateMessageServiceImpl.class);

    @Autowired
    private OperateMessageMapper operateMessageMapper;

    @Autowired
    private OperateMessageRoMapper operateMessageRoMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private MessageSendUtil messageSendUtil;

    @Autowired
    private AppService appService;

    @Autowired
    private IWxTemplateService templateService;

    @Autowired
    private UserExtendRoMapper userExtendRoMapper;

    @Override
    public OperateMessageBO insert(OperateMessageBO operateMessageBO) {
        LOGGER.info("发送运营消息：{}", operateMessageBO);
        OperateMessage operateMessage = new OperateMessage();
        BeanUtils.copyProperties(operateMessageBO, operateMessage);
        Date date = new Date();
        operateMessage.setId(Utils.uuid());
        operateMessage.setCreateTime(date);
        operateMessage.setLastUpdate(date);
        if (operateMessageBO.getSendTime().equals("1")) {
            operateMessage.setStartTime(date);
        }
        operateMessageMapper.insert(operateMessage);
        BeanUtils.copyProperties(operateMessage, operateMessageBO);
        return operateMessageBO;
    }

    @Override
    public List<OperateMessageBO> selectList(int page, int size) {
        LOGGER.info("查询运营消息列表");
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<OperateMessageBO> tempList = operateMessageRoMapper.selectList();
        if (tempList == null || tempList.size() < 1) {
            return null;
        }
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getStatus().equals("1")) {
                Date now = new Date();
                if (now.after(tempList.get(i).getEndTime())) {
                    tempList.get(i).setStatus("2");
                }
            }
        }
        return tempList;
    }

    @Override
    public OperateMessageBO update(OperateMessageBO operateMessageBO) {
        if (operateMessageBO == null || StringUtils.isEmpty(operateMessageBO.getId())) {
            return null;
        }
        operateMessageBO.setLastUpdate(new Date());
        operateMessageMapper.update(operateMessageBO);
        return operateMessageBO;
    }

    @Override
    public void send(String userId) {
        User user = userRoMapper.selectOne(userId);
        UserExtend userExtend = userExtendRoMapper.selectOne(userId);
        List<String> tagIdList = operateMessageRoMapper.selectTagIdList(userId);
        if (user == null) {
            return;
        }
        List<OperateMessageBO> operateMessageBOList = operateMessageRoMapper.selectValidList(new Date());
        for (OperateMessageBO o : operateMessageBOList) {
            //判断运营消息任务的有效性
            if (StringUtils.isEmpty(o.getStatus()) || !o.getStatus().equals("1") ||
                    o.getStartTime() == null || o.getEndTime() == null ||
                    o.getStartTime().after(new Date()) || o.getEndTime().before(new Date())) {
                continue;
            }
            //判断用户是否在消息发送范围内
            //目标人群：1-全部用户，2-部分用户，3-特定用户
            if (!StringUtils.isEmpty(o.getTarget())) {
                switch (o.getTarget()) {
                    case "1":
                        //根据运营消息频率查看是否已发送
                        sendYyxx(o, user);
                        break;
                    case "2":
                        sendPart(o, user, userExtend, tagIdList);
                        break;
                    case "3":
                        if (StringUtils.isEmpty(o.getUserIds())) {
                            break;
                        }
                        if (o.getUserIds().contains(userId)) {
                            sendYyxx(o, user);
                            break;
                        }
                }
            }
        }
    }

    @Override
    public void sendYyxx(OperateMessageBO o, User user) {
        //获取运营管理系统accessToken
        AppBO appBO = appService.selectByName("abc12366-admin");
        Date lastRest = appBO.getLastResetTokenTime();
        if (lastRest.before(new Date())) {
            appBO.setLastResetTokenTime(org.apache.commons.lang3.time.DateUtils.addHours(new Date(), 2));
            appService.update(appBO);
        }
        String accessToken = appBO.getAccessToken();
        //系统消息
        //系统消息日志

        if (o.getWeb()) {
            if ((o.getRate().equals("O") && (!sendAready(user.getId(), o.getId(), MessageConstant.YYXX_WEB, null, null)))
                    || ((o.getRate().equals("D")) && (!sendAready(user.getId(), o.getId(), MessageConstant.YYXX_WEB, DateUtils.getFirstHourOfDay(), DateUtils.getFirstHourOfLastDay())))) {
                Message message = new Message();
                message.setBusinessId(o.getId());
                message.setBusiType(MessageConstant.YYXX_BUSITYPE);
                message.setType(MessageConstant.SYS_MESSAGE);
                message.setContent(o.getContent());
                message.setUserId(user.getId());
                message.setUrl(o.getUrl());
                messageSendUtil.sendMessage(message, accessToken);
                operateMessageMapper.yyxxLog(new YyxxLogBO(Utils.uuid(), user.getId(), o.getId(), MessageConstant.YYXX_WEB, new Date()));
            }
        }
        if (o.getWechat() && !StringUtils.isEmpty(user.getWxopenid())) {
            if ((o.getRate().equals("O") && (!sendAready(user.getId(), o.getId(), MessageConstant.YYXX_WECHAT, null, null)))
                    || ((o.getRate().equals("D")) && (!sendAready(user.getId(), o.getId(), MessageConstant.YYXX_WECHAT, DateUtils.getFirstHourOfDay(), DateUtils.getFirstHourOfLastDay())))) {
                Map<String, String> dataList = new HashMap<>();
                dataList.put("first", RemindConstant.YYXX_WX_FIRST);
                dataList.put("keyword1", DateUtils.dateToStr(new Date()));
                dataList.put("keyword2", o.getContent());
                dataList.put("remark", RemindConstant.YYXX_WX_REMARK);
                dataList.put("userId", user.getId());
                dataList.put("openId", user.getWxopenid());
                templateService.templateSend("jfQJ8Oh_8KRs6t6KqFrOag5p89kgOUXKHo-Z6rmo3wM", dataList);
                operateMessageMapper.yyxxLog(new YyxxLogBO(Utils.uuid(), user.getId(), o.getId(), MessageConstant.YYXX_WECHAT, new Date()));
            }
        }
        if (o.getMessage() && !StringUtils.isEmpty(user.getPhone())) {
            if ((o.getRate().equals("O") && (!sendAready(user.getId(), o.getId(), MessageConstant.YYXX_MESSAGE, null, null)))
                    || ((o.getRate().equals("D")) && (!sendAready(user.getId(), o.getId(), MessageConstant.YYXX_MESSAGE, DateUtils.getFirstHourOfDay(), DateUtils.getFirstHourOfLastDay())))) {
                Map<String, String> maps = new HashMap<>();
                maps.put("var", o.getContent());
                List<Map<String, String>> list = new ArrayList<>();
                list.add(maps);
                messageSendUtil.sendPhoneMessage(user.getPhone(), MessageConstant.MESSAGE_UPYUN_TEMPLATE_615,
                        list, accessToken);
                operateMessageMapper.yyxxLog(new YyxxLogBO(Utils.uuid(), user.getId(), o.getId(), MessageConstant.YYXX_MESSAGE, new Date()));
            }
        }
    }

    private boolean sendAready(String userId, String messageId, String type, Date start, Date end) {
        List<YyxxLogBO> yyxxLogBOList = operateMessageRoMapper.selectWebLogList(userId, messageId, type, start, end);
        return (yyxxLogBOList != null && yyxxLogBOList.size() > 0);
    }

    @Override
    public boolean tagIdContains(List<String> tagIdList, String tagIds) {
        if (tagIds == null || tagIds.split(",").length < 1) {
            return true;
        }
        if (tagIdList == null || tagIdList.size() < 1) {
            return false;

        }
        String[] tagArrays = tagIds.split(",");
        for (int i = 0; i < tagIdList.size(); i++) {
            for (int j = 0; j < tagArrays.length; j++) {
                if (tagIdList.get(i).trim().equals(tagArrays[j].trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void sendPart(OperateMessageBO o, User user, UserExtend userExtend, List<String> tagIdList) {
        boolean sendArea = false;
        boolean sendTag = false;
        boolean sendRegTime = false;
        //地域
        if (!StringUtils.isEmpty(o.getAreaOper()) && !StringUtils.isEmpty(o.getAreaIds())) {
            //地域限制
            if (o.getAreaOper().trim().equals("equals") && userExtend != null) {
                if ((!StringUtils.isEmpty(userExtend.getProvince()) && o.getAreaIds().contains(userExtend.getProvince()))
                        || (!StringUtils.isEmpty(userExtend.getCity()) && o.getAreaIds().contains(userExtend.getCity()))
                        || (!StringUtils.isEmpty(userExtend.getArea()) && o.getAreaIds().contains(userExtend.getArea()))) {
                    sendArea = true;
                }
                //地域排除
            } else if (o.getAreaOper().trim().equals("ne")) {
                if (userExtend == null ||
                        ((StringUtils.isEmpty(userExtend.getProvince()) || (!StringUtils.isEmpty(userExtend.getProvince()))) && !o.getAreaIds().contains(userExtend.getProvince()))
                                && ((StringUtils.isEmpty(userExtend.getCity()) || (!StringUtils.isEmpty(userExtend.getCity()))) && !o.getAreaIds().contains(userExtend.getProvince()))
                                && ((StringUtils.isEmpty(userExtend.getArea()) || (!StringUtils.isEmpty(userExtend.getArea()))) && !o.getAreaIds().contains(userExtend.getProvince()))) {
                    sendArea = true;
                }
            }
        } else {
            sendArea = true;
        }
        //标签
        if (!StringUtils.isEmpty(o.getTagOper()) && !StringUtils.isEmpty(o.getTagIds())) {
            //标签限制
            if (o.getTagOper().trim().equals("equals")) {
                if (tagIdContains(tagIdList, o.getTagIds())) {
                    sendTag = true;
                }
                //标签排除
            } else if (o.getTagOper().trim().equals("ne")) {
                if (!tagIdContains(tagIdList, o.getTagIds())) {
                    sendTag = true;
                }
            }
        } else {
            sendTag = true;
        }
        //注册时间
        if (!StringUtils.isEmpty(o.getRegTimeOper()) && o.getRegStartTime() != null && o.getRegEndTime() != null) {
            if (user.getCreateTime() != null && user.getCreateTime().after(o.getRegStartTime()) && user.getCreateTime().before(o.getRegEndTime())) {
                sendRegTime = true;
            }
        } else {
            sendRegTime = true;
        }

        if (sendArea && sendTag && sendRegTime) {
            sendYyxx(o, user);
        }
    }
}
