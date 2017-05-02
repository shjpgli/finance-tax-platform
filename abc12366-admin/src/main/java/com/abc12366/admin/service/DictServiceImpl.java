package com.abc12366.admin.service;

import com.abc12366.admin.mapper.db1.DictMapper;
import com.abc12366.admin.mapper.db2.DictRoMapper;
import com.abc12366.admin.model.Dict;
import com.abc12366.admin.model.bo.DictBO;
import com.abc12366.admin.model.bo.DictUpdateBO;
import com.abc12366.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 3:10 PM
 * @since 1.0.0
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private DictRoMapper dictRoMapper;

    @Override
    public List<DictBO> selectList() {
        List<Dict> dicts = dictRoMapper.selectList();
        if(dicts == null || dicts.size()==0){
            return null;
        }
        List<DictBO> dictBOs = new ArrayList<>();
        for (Dict dict:dicts){
            DictBO dictBO = new DictBO();
            BeanUtils.copyProperties(dict,dictBO);
            dictBOs.add(dictBO);
        }
        return dictBOs;
    }

    @Override
    public List<DictBO> selectFirstLevel(){
        List<Dict> dicts = dictRoMapper.selectFirstLevel();
        if(dicts == null || dicts.size()==0){
            return null;
        }
        List<DictBO> dictBOs = new ArrayList<>();
        for(Dict dict : dicts){
            DictBO dictBO = new DictBO();
            BeanUtils.copyProperties(dict,dictBO);
            dictBOs.add(dictBO);
        }
        return dictBOs;
    }

    @Override
    public DictBO selectOne(String id){
        Dict dict = dictRoMapper.selectOne(id);
        DictBO dictBO = new DictBO();
        if(dict == null){
            return null;
        }
        BeanUtils.copyProperties(dict,dictBO);
        return dictBO;
    }

    @Transactional("db2TxManager")
    @Override
    public DictBO insert(DictBO dictBO) {
        Dict dict = dictRoMapper.selectByDictName(dictBO.getDictName());
        if(dict != null){
            return null;
        }
        dict = new Dict();
        BeanUtils.copyProperties(dictBO, dict);

        dict.setId(Utils.uuid());
        dict.setCreateTime(new Date());
        dict.setLastUpdate(new Date());

        //TODO 暂时无法获取用户数据
        //dict.setCreateUser("");
        //dict.setLastUser("");
        dictMapper.insert(dict);
        BeanUtils.copyProperties(dict,dictBO);
        return dictBO;
    }

    @Transactional("db2TxManager")
    @Override
    public DictBO update(DictUpdateBO dictUpdateBO) {
        Dict dict = dictRoMapper.selectOne(dictUpdateBO.getId());
        if(dict == null){
            return null;
        }
        dict.setDictId(dictUpdateBO.getDictId());
        dict.setDictName(dictUpdateBO.getDictName());
        dict.setFieldKey(dictUpdateBO.getFieldKey());
        dict.setFieldValue(dictUpdateBO.getFieldValue());
        dict.setStatus(dictUpdateBO.isStatus());
        dict.setLastUpdate(new Date());
        DictBO dictBO = new DictBO();
        dictMapper.update(dict);
        BeanUtils.copyProperties(dict, dictBO);
        return dictBO;
    }

    @Transactional("db2TxManager")
    @Override
    public DictBO delete(String id) {
        Dict dict = dictRoMapper.selectOne(id);
        DictBO dictBO = new DictBO();
        if(dict != null){
            BeanUtils.copyProperties(dict,dictBO);
            dictMapper.delete(id);
        }
        return dictBO;
    }

}
