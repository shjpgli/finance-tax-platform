package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.ModelItemMapper;
import com.abc12366.cms.mapper.db1.ModelMapper;
import com.abc12366.cms.mapper.db2.ModelItemRoMapper;
import com.abc12366.cms.mapper.db2.ModelRoMapper;
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
        List<ModelItemBo> modelItemBoList = modelItemRoMapper.selectList(map);
        return null;
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
    public ModelItemBo save(ModelItemBo modelItemBo) {
        //保存模型项信息
        String uuid = UUID.randomUUID().toString().replace("-", "");
        ModelItem modelItem = new ModelItem();
        modelItemBo.setModelId(uuid);
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
    public ModelItemBo selectModel(String modelItemId) {
        ModelItemBo modelItemBo = modelItemRoMapper.selectByPrimaryKey(modelItemId);
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
