package com.abc12366.cszj.mapper.db2;

import com.abc12366.cszj.model.bo.NoticeBO;

import java.util.List;

/**
 * @author yuanluo <ljun51@outlook.com>
 * @create 2017-07-26 4:18 PM
 * @since 1.0.0
 */
public interface NoticeRoMapper {

    List<NoticeBO> selectList(NoticeBO notice);

    List<NoticeBO> selectListForqt(NoticeBO notice);

    NoticeBO selectOne(String id);
}
