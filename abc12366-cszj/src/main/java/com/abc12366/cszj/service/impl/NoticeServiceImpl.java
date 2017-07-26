package com.abc12366.cszj.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.cszj.mapper.db1.NoticeMapper;
import com.abc12366.cszj.mapper.db2.NoticeRoMapper;
import com.abc12366.cszj.model.bo.NoticeBO;
import com.abc12366.cszj.service.NoticeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 通知公告管理实现类
 *
 * @author yuanluo <ljun51@outlook.com>
 * @create 2017-07-26 4:21 PM
 * @since 1.0.0
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    // 通知公告
    @Autowired
    private NoticeRoMapper noticeRoMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<NoticeBO> selectList(NoticeBO notice, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<NoticeBO> noticeList = noticeRoMapper.selectList(notice);
        return noticeList;
    }

    @Transactional("db1TxManager")
    @Override
    public NoticeBO insert(NoticeBO notice) {
        Timestamp now = new Timestamp(new Date().getTime());
        notice.setId(Utils.uuid());
        notice.setCreateTime(now);
        notice.setLastUpdate(now);
        notice.setCount(0);
        noticeMapper.insert(notice);
        return notice;
    }

    @Override
    public NoticeBO selectOne(String id) {
        NoticeBO notice = noticeRoMapper.selectOne(id);
        NoticeBO n = new NoticeBO();
        if (notice != null) {
        int num = notice.getCount();
        num += 1;
            n.setCount(num);
            n.setId(notice.getId());
            updateCount(n);
        return  notice;
        } else {
            throw new ServiceException(4012);
        }
    }
    public void updateCount(NoticeBO notice) {
        noticeMapper.updatecount(notice);
    }
    @Transactional("db1TxManager")
    @Override
    public NoticeBO update(NoticeBO notice) {
        Timestamp now = new Timestamp(new Date().getTime());
        NoticeBO v = selectOne(notice.getId());
        if (v != null) {
            notice.setLastUpdate(now);
            noticeMapper.update(notice);
            return selectOne(notice.getId());
        } else {
            throw new ServiceException(4012);
        }
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(String id) {
        NoticeBO v = selectOne(id);
        if (v != null) {
            // 删除投票信息
            noticeMapper.deleteByPrimaryKey(id);
        } else {
            throw new ServiceException(4012);
        }
    }

}
