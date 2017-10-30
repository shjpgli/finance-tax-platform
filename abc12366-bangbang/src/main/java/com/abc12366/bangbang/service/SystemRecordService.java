package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.SystemRecord;
import com.abc12366.bangbang.model.bo.SystemRecordBO;
import com.abc12366.bangbang.model.bo.SystemRecordInsertBO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 用户日志服务
 *
 * @author lingsuzhi <554600654@qq.com.com>
 * @create 2017-08-16
 */
public interface SystemRecordService {

    /**
     * 查询用户日志列表
     *
     * @param map  Map appName,location,startTime,endTime
     * @param page 当前页
     * @param size 页大小
     * @return List<SystemRecordBO>
     * @see com.abc12366.bangbang.model.bo.SystemRecordBO
     */
    List<SystemRecordBO> selectList(Map<String, String> map, int page, int size);

    /**
     * 查看用户日志
     *
     * @param systemRecord SystemRecord
     * @return SystemRecordBO
     * @see com.abc12366.bangbang.model.bo.SystemRecordBO
     */
    SystemRecordBO selectOne(SystemRecord systemRecord);

    /**
     * 异步新增日志；如果规则代码有效，则新增用户经验值
     *
     * @param systemRecordInsertBO 日志BO
     * @return CompletableFuture<SystemRecordBO>
     * @see com.abc12366.bangbang.model.bo.SystemRecordInsertBO
     * @see com.abc12366.bangbang.model.bo.SystemRecordBO
     */
    CompletableFuture<SystemRecordBO> insert(SystemRecordInsertBO systemRecordInsertBO);
}
