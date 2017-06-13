package com.abc12366.admin.service.impl;

import com.abc12366.admin.mapper.db1.DictMapper;
import com.abc12366.admin.mapper.db2.DictRoMapper;
import com.abc12366.admin.model.Dict;
import com.abc12366.admin.model.bo.DictBO;
import com.abc12366.admin.model.bo.DictUpdateBO;
import com.abc12366.admin.service.DictService;
import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @create 2017-04-26 5:01 PM
 * @since 1.0.0
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictRoMapper dictRoMapper;

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Dict> selectList(Dict dict) {
        List<Dict> dicts = dictRoMapper.selectList(dict);
        return dicts;
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
    public DictBO selectOne(Dict dict){
        Dict dict1 = dictRoMapper.selectOne(dict);
        DictBO dictBO = new DictBO();
        if(dict1 == null){
            return null;
        }
        BeanUtils.copyProperties(dict1,dictBO);
        return dictBO;
    }

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

        dictMapper.insert(dict);
        BeanUtils.copyProperties(dict,dictBO);
        return dictBO;
    }

    @Override
    public DictBO update(DictUpdateBO dictUpdateBO) {
        Dict dict = new Dict();
        dict.setLastUpdate(new Date());
        BeanUtils.copyProperties(dictUpdateBO, dict);
        int upd = dictMapper.update(dict);
        if(upd != 1){
            throw new ServiceException(4102);
        }
        DictBO dictBO = new DictBO();
        BeanUtils.copyProperties(dict, dictBO);
        return dictBO;
    }

    @Override
    public int delete(String id) {

        int del = dictMapper.delete(id);
        if(del != 1){
            throw new ServiceException(4103);
        }
        return del;
    }

    @Override
    public Dict selectById(String id) {
        return dictRoMapper.selectById(id);
    }

    @Override
    public List<DictBO> selectDictList(Dict dict) {
        return dictRoMapper.selectDictList(dict);
    }

}
