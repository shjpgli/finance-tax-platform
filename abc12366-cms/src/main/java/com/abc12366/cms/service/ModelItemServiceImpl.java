package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.ModelItemMapper;
import com.abc12366.cms.mapper.db2.ModelItemRoMapper;
import com.abc12366.cms.model.ModelItem;
import com.abc12366.cms.model.bo.ModelItemBo;
import com.abc12366.cms.model.bo.ModelItemListBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xieyanmao on 2017/5/8.
 */
@Service
public class ModelItemServiceImpl implements ModelItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelItemServiceImpl.class);

    @Autowired
    private ModelItemMapper modelItemMapper;

    @Autowired
    private ModelItemRoMapper modelItemRoMapper;

    @Override
    public List<ModelItemBo> selectList(Map<String, Object> map) {
        //查询模型项列表
        List<ModelItemBo> modelItemBos = modelItemRoMapper.selectList(map);
//        List<ModelItemBo> modelItemBos = new ArrayList<>();
//        for(ModelItem modelItem : modelItems){
//            ModelItemBo modelItemBo = new ModelItemBo();
//            try {
//                BeanUtils.copyProperties(modelItem, modelItemBo);
//                modelItemBos.add(modelItemBo);
//            } catch (Exception e) {
//                LOGGER.error("类转换异常：{}", e);
//                throw new RuntimeException("类型转换异常：{}", e);
//            }
//        }
        return modelItemBos;
    }

    @Override
    public ModelItemListBo updateList(ModelItemListBo modelItemListBo) {
        //保存模型项列表
        List<ModelItemBo> list = modelItemListBo.getModelItemBoList();
        for(ModelItemBo modelItemBo:list){
            ModelItem modelItem = new ModelItem();
            try {
                BeanUtils.copyProperties(modelItemBo, modelItem);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
            modelItemMapper.updateByPrimaryKey(modelItem);
        }
        return modelItemListBo;
    }

    @Override
    public ModelItemListBo saveList(ModelItemListBo modelItemListBo) {
        //保存模型项列表
        List<ModelItemBo> list = modelItemListBo.getModelItemBoList();
        for(ModelItemBo modelItemBo:list){
            ModelItem modelItem = new ModelItem();
            try {
                BeanUtils.copyProperties(modelItemBo, modelItem);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
            modelItemMapper.insert(modelItem);
        }
        return modelItemListBo;
    }

    @Override
    public ModelItemBo save(ModelItemBo modelItemBo) {
        //保存模型项信息
        String uuid = UUID.randomUUID().toString().replace("-", "");
        ModelItem modelItem = new ModelItem();
        modelItemBo.setModelitemId(uuid);
        try {
            BeanUtils.copyProperties(modelItemBo, modelItem);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        modelItemMapper.insert(modelItem);
        return modelItemBo;
    }

    @Override
    public ModelItemBo selectModel(String modelitemId) {
        ModelItem modelItem = modelItemRoMapper.selectByPrimaryKey(modelitemId);
        ModelItemBo modelItemBo = new ModelItemBo();
        try {
            BeanUtils.copyProperties(modelItem, modelItemBo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        return modelItemBo;
    }

    @Override
    public ModelItemBo update(ModelItemBo modelItemBo) {
        //保存模型项信息
        ModelItem modelItem = new ModelItem();
        try {
            BeanUtils.copyProperties(modelItemBo, modelItem);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        modelItemMapper.updateByPrimaryKey(modelItem);
        return modelItemBo;
    }

    @Override
    public String delete(String modelItemId) {
        int r = modelItemMapper.deleteByPrimaryKey(modelItemId);
        LOGGER.info("{}", r);
        return "";
    }
}
