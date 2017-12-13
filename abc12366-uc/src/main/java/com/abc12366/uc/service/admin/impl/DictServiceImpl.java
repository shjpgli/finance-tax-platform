package com.abc12366.uc.service.admin.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.RedisConstant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.DictMapper;
import com.abc12366.uc.mapper.db2.DictRoMapper;
import com.abc12366.uc.model.Dict;
import com.abc12366.uc.model.bo.DictBO;
import com.abc12366.uc.model.bo.DictUpdateBO;
import com.abc12366.uc.service.admin.DictService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @create 2017-04-26 5:01 PM
 * @since 1.0.0
 */
@Service
public class DictServiceImpl implements DictService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictServiceImpl.class);
    @Autowired
    private DictRoMapper dictRoMapper;

    @Autowired
    private DictMapper dictMapper;
    
    @Autowired
	private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Dict> selectList(Dict dict) {
        return dictRoMapper.selectList(dict);
    }

    @Override
    public List<Dict> selectList(String dictId) {
        return dictRoMapper.selectByDictId(dictId);
    }

    @Override
    public List<DictBO> selectFirstLevel() {
        List<Dict> dicts = dictRoMapper.selectFirstLevel();
        if (dicts == null || dicts.size() == 0) {
            throw new ServiceException(4104);
        }
        List<DictBO> dictBOs = new ArrayList<>();
        for (Dict dict : dicts) {
            DictBO dictBO = new DictBO();
            BeanUtils.copyProperties(dict, dictBO);
            dictBOs.add(dictBO);
        }
        return dictBOs;
    }

    @Override
    public DictBO selectOne(Dict dict) {
        Dict dict1 = dictRoMapper.selectOne(dict);
        DictBO dictBO = new DictBO();
        if (dict1 == null) {
            return null;
        }
        BeanUtils.copyProperties(dict1, dictBO);
        return dictBO;
    }

    @Transactional("db1TxManager")
    @Override
    public DictBO insert(DictBO dictBO) {
        //dictId，fieldKey确定数据唯一性
        Dict dict = dictRoMapper.selectDict(dictBO);
        if (dict != null) {
            LOGGER.info("dictId，fieldKey确定数据唯一性{}", dict);
            throw new ServiceException(4165);
        }
        dict = new Dict();
        BeanUtils.copyProperties(dictBO, dict);

        dict.setId(Utils.uuid());
        dict.setCreateTime(new Date());
        dict.setLastUpdate(new Date());

        dictMapper.insert(dict);
        // 删除redis
        redisTemplate.delete(dict.getDictId() + "_Dict");
        
        BeanUtils.copyProperties(dict, dictBO);
        return dictBO;
    }

    @Transactional("db1TxManager")
    @Override
    public DictBO update(DictUpdateBO dictUpdateBO) {
        Dict dict = new Dict();
        dict.setLastUpdate(new Date());
        BeanUtils.copyProperties(dictUpdateBO, dict);
        int upd = dictMapper.update(dict);
        if (upd != 1) {
            throw new ServiceException(4102);
        }
        
        // 删除redis
        redisTemplate.delete(dictUpdateBO.getDictId() + "_Dict");
        
        DictBO dictBO = new DictBO();
        BeanUtils.copyProperties(dict, dictBO);
        return dictBO;
    }

    @Transactional("db1TxManager")
    @Override
    public int delete(String id) {
    	Dict dict=dictRoMapper.selectById(id);
        int del = dictMapper.delete(id);
        if (del != 1) {
            throw new ServiceException(4103);
        }
        // 删除redis
        redisTemplate.delete(dict.getDictId() + "_Dict");
        return del;
    }

    @Override
    public Dict selectById(String id) {
        return dictRoMapper.selectById(id);
    }

    @Override
    public List<DictBO> selectDictList(Dict dict) {
    	List<DictBO> dictBOs=null;
    	if(redisTemplate.hasKey(dict.getDictId() + "_Dict")){
    		dictBOs=JSONArray.parseArray(redisTemplate.opsForValue().get(dict.getDictId() + "_Dict"),DictBO.class);
    		LOGGER.info("从redis获取数据字典信息:{}", JSONArray.toJSONString(dictBOs));
    	}else{
    		dictBOs=dictRoMapper.selectDictList(dict);
    		redisTemplate.opsForValue().set(dict.getDictId() + "_Dict",
    				JSONArray.toJSONString(dictBOs),
					RedisConstant.DICT_TIME_ODFAY, TimeUnit.DAYS);
    	}
        return dictBOs;
    }

    @Transactional("db1TxManager")
    @Override
    public void batchDelete(Dict bo) {
        String id = bo.getId();
        if (id == null || "".equals(id)) {
            LOGGER.info("id不能为空{}", bo);
            throw new ServiceException(4150);
        }
        String[] ids = id.split(",");
        for (String dId : ids) {
            int del = dictMapper.delete(dId);
            if (del != 1) {
                throw new ServiceException(4103);
            }
        }
    }

    @Override
    public List<Dict> selectListByDictName(Dict dict) {
        return dictRoMapper.selectListByDictName(dict);
    }

}
