package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.bo.FollowLecturerBO;

import java.util.List;
import java.util.Map;

/**
 * 用户关注讲师表服务接口
 *
 * @author lizhongwei
 * @date 2017-03-12
 * @since 1.0.0
 */
public interface FollowLecturerService {

    /**
     * 新增
     */
    FollowLecturerBO insert(FollowLecturerBO followLecturerBO);

    /**
     * 查询列表
     */
    List<FollowLecturerBO> selectList(Map<String, Object> map);

    /**
     * 根据讲师UserId查询所有用户ID
     * @param userId 讲师UserId
     * @return 所有用户ID
     */
    List<String> selectUserIdListByLecturerId(String userId);

    /**
     * 单个查询
     */
    FollowLecturerBO selectFollowLecturerBO(String id);

    /**
     * 修改
     */
    FollowLecturerBO update(FollowLecturerBO followLecturerBO);

    /**
     * 删除
     */
    void delete(String id, String userId);
}
