package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.MapUtil;
import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.CurriculumCollectMapper;
import com.abc12366.bangbang.mapper.db2.CurriculumCollectRoMapper;
import com.abc12366.bangbang.model.Collect;
import com.abc12366.bangbang.model.bo.CollectBO;
import com.abc12366.bangbang.model.bo.CollectListBO;
import com.abc12366.bangbang.model.curriculum.CurriculumCollect;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumCollectBo;
import com.abc12366.bangbang.service.CurrCollectService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xieyanmao on 2017/8/11.
 */
@Service
public class CurrCollectServiceImpl implements CurrCollectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrCollectServiceImpl.class);

    @Autowired
    private CurriculumCollectMapper collectMapper;

    @Autowired
    private CurriculumCollectRoMapper collectRoMapper;

    @Override
    public CurriculumCollectBo insert(String curriculumId, HttpServletRequest request) {
        LOGGER.info("{}:{}", curriculumId, request);
        String userId = UcUserCommon.getUserId(request);


        CurriculumCollect collect = new CurriculumCollect();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        collect.setCollectId(uuid);
        collect.setCurriculumId(curriculumId);
        collect.setUserId(userId);
        collect.setCollectTime(new Date());

        Map map = MapUtil.kv("curriculumId", curriculumId, "userId", userId);
        int cnt =  collectRoMapper.selectExist(map);

        if(cnt >0){
            throw new ServiceException(4325);
        }

        int result = collectMapper.insert(collect);

        CurriculumCollectBo collectBO = new CurriculumCollectBo();
        BeanUtils.copyProperties(collect, collectBO);
        return collectBO;
    }

    @Override
    public void delete(String curriculumId, HttpServletRequest request) {
        LOGGER.info("{}:{}", curriculumId, request);
        String userId = UcUserCommon.getUserId(request);
        Map map = MapUtil.kv("curriculumId", curriculumId, "userId", userId);
        collectMapper.delete(map);
    }

    @Override
    public List<CurriculumCollectBo> selectList(String curriculumId) {
        LOGGER.info("{}", curriculumId);
        return collectRoMapper.selectList(curriculumId);
    }

    @Override
    public String selectExist(String curriculumId, HttpServletRequest request) {
        LOGGER.info("{}", curriculumId);
        String userId = UcUserCommon.getUserId(request);
        Map map = MapUtil.kv("curriculumId", curriculumId, "userId", userId);
        String cnt = collectRoMapper.selectExist(map)+"";
        return cnt;
    }

}
