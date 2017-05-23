package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.ChannelAttrMapper;
import com.abc12366.cms.mapper.db1.ChannelExtMapper;
import com.abc12366.cms.mapper.db1.ChannelMapper;
import com.abc12366.cms.mapper.db1.ContentMapper;
import com.abc12366.cms.mapper.db2.ChannelAttrRoMapper;
import com.abc12366.cms.mapper.db2.ChannelExtRoMapper;
import com.abc12366.cms.mapper.db2.ChannelRoMapper;
import com.abc12366.cms.mapper.db2.ModelItemRoMapper;
import com.abc12366.cms.model.Channel;
import com.abc12366.cms.model.ChannelAttr;
import com.abc12366.cms.model.ChannelExt;
import com.abc12366.cms.model.ModelItem;
import com.abc12366.cms.model.bo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xieyanmao on 2017/5/9.
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelServiceImpl.class);

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private ChannelRoMapper channelRoMapper;

    @Autowired
    private ChannelExtMapper channelExtMapper;

    @Autowired
    private ChannelExtRoMapper channelExtRoMapper;

    @Autowired
    private ChannelAttrMapper channelAttrMapper;

    @Autowired
    private ChannelAttrRoMapper channelAttrRoMapper;

    @Autowired
    private ModelItemRoMapper modelItemRoMapper;

    @Override
    public List<ChannelBo> selectList() {
        List<Channel> channelList = channelRoMapper.selectList();
        List<ChannelBo> channelBoList = new ArrayList<>();
        for(Channel channel : channelList){
            try {
                ChannelBo channelBo = new ChannelBo();
                BeanUtils.copyProperties(channel,channelBo);
                channelBoList.add(channelBo);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
        }
        LOGGER.info("{}", channelBoList);
        return channelBoList;
    }

    @Override
    public List<ModelItemBo> selectModeList(Map<String,Object> map) {
        //查询模型项
        List<ModelItem> modelItems = modelItemRoMapper.selectList(map);
        List<ModelItemBo> modelItemBos = new ArrayList<>();
        for(ModelItem modelItem : modelItems){
            ModelItemBo modelItemBo = new ModelItemBo();
            try {
                BeanUtils.copyProperties(modelItem, modelItemBo);
                modelItemBos.add(modelItemBo);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
        }
        LOGGER.info("{}", modelItemBos);
        return modelItemBos;
    }

    @Override
    public ChannelSaveBo save(ChannelSaveBo channelSaveBo) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //栏目
        ChannelBo channelBo = channelSaveBo.getChannel();
        channelBo.setChannelId(uuid);
        Channel channel = new Channel();
        try {
            BeanUtils.copyProperties(channelBo, channel);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        channelMapper.insert(channel);
        //栏目扩展信息
        ChannelExtBo channelExtBo = channelSaveBo.getChannelExt();
        channelExtBo.setChannelId(uuid);
        ChannelExt channelExt = new ChannelExt();
        try {
            BeanUtils.copyProperties(channelExtBo, channelExt);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        channelExtMapper.insert(channelExt);
        //栏目扩展项信息
        List<ChannelAttrBo> channelAttrBoList = channelSaveBo.getChannelAttrList();
        for(ChannelAttrBo channelAttrBo:channelAttrBoList){
            channelAttrBo.setChannelId(uuid);
            ChannelAttr channelAttr = new ChannelAttr();
            try {
                BeanUtils.copyProperties(channelAttrBo, channelAttr);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
            channelAttrMapper.insert(channelAttr);
        }
        LOGGER.info("{}", channelSaveBo);
        return channelSaveBo;
    }

    @Override
    public ChannelQueryBo selectChannel(String channelId) {
        ChannelQueryBo channelQueryBo = new ChannelQueryBo();
        //查询栏目信息
        Channel channel = channelRoMapper.selectByPrimaryKey(channelId);
        ChannelBo channelBo = new ChannelBo();
        try {
            BeanUtils.copyProperties(channel, channelBo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        //查询栏目扩展信息
        ChannelExt channelExt = channelExtRoMapper.selectByPrimaryKey(channelId);
        ChannelExtBo channelExtBo = new ChannelExtBo();
        try {
            BeanUtils.copyProperties(channelExt, channelExtBo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        //查询栏目扩展项信息
        List<ChannelAttr> channelAttrList = channelAttrRoMapper.selectByChannelId(channelId);
        List<ChannelAttrBo> channelAttrBoList = new ArrayList<>();
        for(ChannelAttr channelAttr:channelAttrList){
            ChannelAttrBo channelAttrBo = new ChannelAttrBo();
            try {
                BeanUtils.copyProperties(channelAttr, channelAttrBo);
                channelAttrBoList.add(channelAttrBo);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
        }
        channelQueryBo.setChannel(channelBo);
        channelQueryBo.setChannelExt(channelExtBo);
        channelQueryBo.setChannelAttrList(channelAttrBoList);
        return channelQueryBo;
    }

    @Override
    public ChannelSaveBo update(ChannelSaveBo channelSaveBo) {
        //栏目
        ChannelBo channelBo = channelSaveBo.getChannel();
        Channel channel = new Channel();
        try {
            BeanUtils.copyProperties(channelBo, channel);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        channelMapper.updateByPrimaryKey(channel);
        //栏目扩展信息
        ChannelExtBo channelExtBo = channelSaveBo.getChannelExt();
        ChannelExt channelExt = new ChannelExt();
        try {
            BeanUtils.copyProperties(channelExtBo, channelExt);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        channelExtMapper.updateByPrimaryKey(channelExt);
        //栏目扩展项信息
        List<ChannelAttrBo> channelAttrBoList = channelSaveBo.getChannelAttrList();
        for(ChannelAttrBo channelAttrBo:channelAttrBoList){
            ChannelAttr channelAttr = new ChannelAttr();
            try {
                BeanUtils.copyProperties(channelAttrBo, channelAttr);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
            channelAttrMapper.updateByPrimaryKey(channelAttr);
        }
        LOGGER.info("{}", channelSaveBo);
        return channelSaveBo;
    }

    @Override
    public String delete(String channelId) {
        //删除栏目扩展信息
        channelExtMapper.deleteByPrimaryKey(channelId);
        //删除栏目扩展项信息
        channelAttrMapper.deleteByPrimaryKey(channelId);
        //删除栏目信息
        int r = channelMapper.deleteByPrimaryKey(channelId);
        LOGGER.info("{}", r);
        return "";
    }
}
