package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.ModelMapper;
import com.abc12366.cms.mapper.db2.ModelRoMapper;
import com.abc12366.cms.model.Model;
import com.abc12366.cms.model.bo.ModelBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by xieyanmao on 2017/5/8.
 */
@Service
public class ModelServiceImpl implements ModelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ModelRoMapper modelRoMapper;

    @Override
    public List<ModelBo> selectList() {
        //查询模型列表
        List<Model> modelList =  modelRoMapper.selectList();
        List<ModelBo> modelBoList = new ArrayList<>();
        for(Model model : modelList){
            ModelBo modelBo = new ModelBo();
            try {
                BeanUtils.copyProperties(model, modelBo);
                modelBoList.add(modelBo);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
        }
        return modelBoList;
    }

    @Override
    public ModelBo save(ModelBo modelBo) {
        //保存模型信息
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Model model = new Model();
        modelBo.setModelId(uuid);
        try {
            BeanUtils.copyProperties(modelBo, model);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        modelMapper.insert(model);
        return modelBo;
    }

    @Override
    public ModelBo selectModel(String modelId) {
        //查询模型信息
        Model model = modelRoMapper.selectByPrimaryKey(modelId);
        ModelBo modelBo = new ModelBo();
        try {
            BeanUtils.copyProperties(model, modelBo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        return modelBo;
    }

    @Override
    public ModelBo update(ModelBo modelBo) {
        //更新模型信息
        Model model = new Model();
        try {
            BeanUtils.copyProperties(modelBo, model);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        modelMapper.updateByPrimaryKey(model);
        return modelBo;
    }

    @Override
    public String delete(String modelId) {
        int r = modelMapper.deleteByPrimaryKey(modelId);
        LOGGER.info("{}", r);
        return "";
    }
}
