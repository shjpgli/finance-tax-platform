package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.*;
import com.abc12366.cms.mapper.db2.*;
import com.abc12366.cms.model.Channel;
import com.abc12366.cms.model.ChannelAttr;
import com.abc12366.cms.model.ChannelExt;
import com.abc12366.cms.model.ChnlGroupView;
import com.abc12366.cms.model.bo.*;
import com.abc12366.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ChnlGroupViewRoMapper groupRoMapper;

    @Autowired
    private ChnlGroupViewMapper groupMapper;

    @Autowired
    private ContentRoMapper contentRoMapper;

    @Autowired
    private ChannelCountMapper channelCountMapper;


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
    public List<ChannelBo> selectLists(Map<String,Object> map) {
        List<ChannelBo> channelBoList = channelRoMapper.selectLists(map);
        LOGGER.info("{}", channelBoList);
        return channelBoList;
    }

    @Override
    public List<ModelItemBo> selectModeList(Map<String,Object> map) {
        //查询模型项
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
        LOGGER.info("{}", modelItemBos);
        return modelItemBos;
    }

    @Transactional("db1TxManager")
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
        if(channelAttrBoList != null){
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
        }

        //用户组
        List<ChnlGroupViewBo> groupBoList = channelSaveBo.getGroupList();
        if(groupBoList != null){
            for(ChnlGroupViewBo groupBo:groupBoList){
                ChnlGroupView group = new ChnlGroupView();
                groupBo.setChannelId(uuid);
                try {
                    BeanUtils.copyProperties(groupBo, group);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                groupMapper.insert(group);
            }
        }

        LOGGER.info("{}", channelSaveBo);
        return channelSaveBo;
    }

    @Override
    public ChannelSaveBo selectChannel(String channelId) {
        ChannelSaveBo channelSaveBo = new ChannelSaveBo();
        //查询栏目信息
        Channel channel = channelRoMapper.selectByPrimaryKey(channelId);
        ChannelBo channelBo = new ChannelBo();
        try {
            if(channel != null){
                BeanUtils.copyProperties(channel, channelBo);
            }

        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        //查询栏目扩展信息
        ChannelExt channelExt = channelExtRoMapper.selectByPrimaryKey(channelId);
        ChannelExtBo channelExtBo = new ChannelExtBo();
        try {
            if(channelExt != null){
                BeanUtils.copyProperties(channelExt, channelExtBo);
            }
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        //查询栏目扩展项信息
        List<ChannelAttr> channelAttrList = channelAttrRoMapper.selectByChannelId(channelId);
        List<ChannelAttrBo> channelAttrBoList = new ArrayList<ChannelAttrBo>();
        if(channelAttrList != null){
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
        }

        List<ChnlGroupView> groupList = groupRoMapper.selectList(channelId);
        List<ChnlGroupViewBo> groupBoList = new ArrayList<ChnlGroupViewBo>();
        if(groupBoList != null){
            for(ChnlGroupView group:groupList){
                ChnlGroupViewBo groupBo = new ChnlGroupViewBo();
                try {
                    BeanUtils.copyProperties(group, groupBo);
                    groupBoList.add(groupBo);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
            }
        }

        channelSaveBo.setChannel(channelBo);
        channelSaveBo.setChannelExt(channelExtBo);
        channelSaveBo.setChannelAttrList(channelAttrBoList);
        channelSaveBo.setGroupList(groupBoList);
        return channelSaveBo;
    }

    @Transactional("db1TxManager")
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
        channelMapper.updateByPrimaryKeySelective(channel);
        //栏目扩展信息
        ChannelExtBo channelExtBo = channelSaveBo.getChannelExt();
        ChannelExt channelExt = new ChannelExt();
        try {
            BeanUtils.copyProperties(channelExtBo, channelExt);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        channelExtMapper.updateByPrimaryKeySelective(channelExt);
        //栏目扩展项信息
        List<ChannelAttrBo> channelAttrBoList = channelSaveBo.getChannelAttrList();
        if(channelAttrBoList != null){
            for(ChannelAttrBo channelAttrBo:channelAttrBoList){
                ChannelAttr channelAttr = new ChannelAttr();
                try {
                    BeanUtils.copyProperties(channelAttrBo, channelAttr);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                channelAttrMapper.updateByPrimaryKeySelective(channelAttr);
            }
        }

        //用户组
        groupMapper.deleteByPrimaryKey(channelBo.getChannelId());
        List<ChnlGroupViewBo> groupBoList = channelSaveBo.getGroupList();
        if(groupBoList != null){
            for(ChnlGroupViewBo groupBo:groupBoList){
                ChnlGroupView group = new ChnlGroupView();
                try {
                    BeanUtils.copyProperties(groupBo, group);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                groupMapper.insert(group);
            }
        }

        LOGGER.info("{}", channelSaveBo);
        return channelSaveBo;
    }

    @Transactional("db1TxManager")
    @Override
    public ChannelBo updateChannelByparentId(ChannelBo channelBo) {
        Channel channel = new Channel();
        try {
            BeanUtils.copyProperties(channelBo, channel);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        channelMapper.updateByPrimaryKeySelective(channel);

        List<Channel> channelList = channelRoMapper.selectListByparentId(channelBo.getChannelId());
        if(channelList != null){
            for(Channel channel1 : channelList){
                ChannelBo channelBo1 = new ChannelBo();
                try {
                    BeanUtils.copyProperties(channel1,channelBo1);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                channelBo1.setIsDisplay(channelBo.getIsDisplay());
                this.updateChannelByparentId(channelBo1);
            }
        }

        return channelBo;
    }

    @Override
    public List<ChannelBo> selectListByparentId(String parentId) {
        List<Channel> channelList = channelRoMapper.selectListByparentId(parentId);
        List<ChannelBo> channelBoList = new ArrayList<>();
        if(channelList != null){
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
        }

        LOGGER.info("{}", channelBoList);
        return channelBoList;
    }

    @Override
    public List<ChannelExtBo> selectListBytplChannel(String tplChannel) {
        List<ChannelExt> channelExtList = channelExtRoMapper.selectListBytplChannel(tplChannel);
        List<ChannelExtBo> channelExtBoList = new ArrayList<>();
        if(channelExtList != null){
            for(ChannelExt channelExt : channelExtList){
                try {
                    ChannelExtBo channelExtBo = new ChannelExtBo();
                    BeanUtils.copyProperties(channelExt,channelExtBo);
                    channelExtBoList.add(channelExtBo);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
            }
        }

        LOGGER.info("{}", channelExtBoList);
        return channelExtBoList;
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String channelId) {

        int cnt = contentRoMapper.selectByChannelId(channelId).intValue();

        if(cnt != 0){
//            LOGGER.warn("改栏目下存在内容信息,不能删除：{}", menu.toString());
            throw new ServiceException(4301);
        }

        //删除栏目扩展信息
        channelExtMapper.deleteByPrimaryKey(channelId);
        //删除栏目扩展项信息
        channelAttrMapper.deleteByPrimaryKey(channelId);
        //删除用户组
        groupMapper.deleteByPrimaryKey(channelId);
        //删除统计数
        channelCountMapper.deleteByPrimaryKey(channelId);
        //删除栏目信息
        int r = channelMapper.deleteByPrimaryKey(channelId);

        List<Channel> channelList = channelRoMapper.selectListByparentId(channelId);
        for(Channel channel : channelList){
            this.delete(channel.getChannelId());
        }
        return "";
    }
}
