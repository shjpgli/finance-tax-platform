package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.admin.bo.OperateMessageBO;
import com.abc12366.uc.model.admin.bo.YyxxLogBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-12-19
 * Time: 14:45
 */
public interface OperateMessageRoMapper {
    List<OperateMessageBO> selectList();

    List<OperateMessageBO> selectValidList(Date date);

    List<String> selectTagIdList(String userId);

    List<YyxxLogBO> selectWebLogList(@Param("userId")String userId, @Param("messageId")String messageId, @Param("type")String type, @Param("start")Date start, @Param("end")Date end);
}
