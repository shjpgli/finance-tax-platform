package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.FollowLecturerMapper;
import com.abc12366.bangbang.mapper.db2.FollowLecturerRoMapper;
import com.abc12366.bangbang.model.FollowLecturer;
import com.abc12366.bangbang.model.bo.FollowLecturerBO;
import com.abc12366.bangbang.service.FollowLecturerService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户关注讲师表服务实现类
 *
 * @author lizhongwei
 * @date 2017-03-12
 * @since 1.0.0
 */
@Service
public class FollowLecturerServiceImpl implements FollowLecturerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FollowLecturerServiceImpl.class);

    @Autowired
    private FollowLecturerRoMapper followLecturerRoMapper;

    @Autowired
    private FollowLecturerMapper followLecturerMapper;


    @Override
    public FollowLecturerBO insert(FollowLecturerBO followLecturerBO) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",followLecturerBO.getUserId());
        map.put("lecturerId", followLecturerBO.getLecturerId());
        FollowLecturer followLecturer = followLecturerRoMapper.selectFollowLecturer(map);
        //查找是否有关注，是：修改，否：新增
        if(followLecturer == null){
            followLecturer = new FollowLecturer();
            followLecturerBO.setId(Utils.uuid());
            Date date = new Date();
            followLecturerBO.setCreateTime(date);
            followLecturerBO.setLastUpdate(date);
            followLecturerBO.setStatus(1);
            BeanUtils.copyProperties(followLecturerBO,followLecturer);
            int count = followLecturerMapper.insert(followLecturer);
            if(count != 1){
                LOGGER.info("新增失败{}"+count);
                throw new ServiceException(4101);
            }
        }else{
            followLecturer.setLastUpdate(new Date());
            int count = followLecturerMapper.update(followLecturer);
            if(count != 1){
                LOGGER.info("修改失败{}"+count);
                throw new ServiceException(4102);
            }
        }
        BeanUtils.copyProperties(followLecturer,followLecturerBO);
        return followLecturerBO;
    }

    @Override
    public List<FollowLecturerBO> selectList(Map<String, Object> map) {
        return followLecturerRoMapper.selectList(map);
    }

    @Override
    public List<String> selectUserIdListByLecturerId(String userId) {
        return followLecturerRoMapper.selectUserIdListByLecturerId(userId);
    }

    @Override
    public FollowLecturerBO selectFollowLecturerBO(String id) {
        return followLecturerRoMapper.selectFollowLecturerBO(id);
    }

    @Override
    public FollowLecturerBO update(FollowLecturerBO followLecturerBO) {
        FollowLecturer followLecturer = new FollowLecturer();
        Map<String,Object> map = new HashMap<>();
        map.put("userId",followLecturerBO.getUserId());
        map.put("lecturerId", followLecturerBO.getLecturerId());
        FollowLecturer data = followLecturerRoMapper.selectFollowLecturer(map);
        //查找是否有关注，是：修改，否：新增
        if(data == null){
            followLecturerBO.setId(Utils.uuid());
            Date date = new Date();
            followLecturerBO.setCreateTime(date);
            followLecturerBO.setLastUpdate(date);
            followLecturerBO.setStatus(1);
            BeanUtils.copyProperties(followLecturerBO,followLecturer);
            int count = followLecturerMapper.insert(followLecturer);
            if(count != 1){
                LOGGER.info("新增失败{}"+count);
                throw new ServiceException(4101);
            }
        }else{
            followLecturerBO.setLastUpdate(new Date());
            BeanUtils.copyProperties(followLecturerBO,followLecturer);
            int count = followLecturerMapper.update(followLecturer);
            if(count != 1){
                LOGGER.info("修改失败{}"+count);
                throw new ServiceException(4102);
            }
        }
        return followLecturerBO;
    }

    @Override
    public void delete(String id, String userId) {
        int count = followLecturerMapper.delete(id, userId);
        if(count != 1){
            LOGGER.info("删除失败{}"+count);
            throw new ServiceException(4103);
        }
    }

    @Override
    public List<FollowLecturerBO> selectBOList(Map<String, Object> map) {
        return followLecturerRoMapper.selectFollowLecturerBOList(map);
    }
}
