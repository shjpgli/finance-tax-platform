package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.*;
import com.abc12366.cms.mapper.db2.*;
import com.abc12366.cms.model.*;
import com.abc12366.cms.model.bo.*;
import com.abc12366.cms.service.ContentService;
import com.abc12366.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 内容管理模块
 *
 * @author xieyanmao
 * @create 2017-04-27
 * @since 1.0.0
 */
@Service
public class ContentServiceImpl implements ContentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private ContentRoMapper contentRoMapper;

    @Autowired
    private ModelItemRoMapper modelItemRoMapper;

    @Autowired
    private ContentExtMapper contentExtMapper;

    @Autowired
    private ContentExtRoMapper contentExtRoMapper;

    @Autowired
    private ContentTxtMapper contentTxtMapper;

    @Autowired
    private ContentTxtRoMapper contentTxtRoMapper;

    @Autowired
    private ContentAttrMapper contentAttrMapper;

    @Autowired
    private ContentAttrRoMapper contentAttrRoMapper;

    @Autowired
    private ContentPictureMapper contentPictureMapper;

    @Autowired
    private ContentPictureRoMapper contentPictureRoMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileRoMapper fileRoMapper;

    @Autowired
    private ContentTopicMapper topicMapper;

    @Autowired
    private ContentTopicRoMapper topicRoMapper;

    @Autowired
    private ContentGroupViewMapper groupMapper;

    @Autowired
    private ContentGroupViewRoMapper groupRoMapper;

    @Autowired
    private ContentCountMapper contentCountMapper;

    @Override
    public List<ContentListBo> selectList(Map<String,Object> map) {
        //查询内容列表
        List<ContentListBo> contents = contentRoMapper.selectList(map);
        LOGGER.info("{}", contents);
        return contents;
    }

    @Override
    public List<ContentsListBo> selectListByContentType(Map<String,Object> map) {
        //查询内容列表
        List<ContentsListBo> contents = contentRoMapper.selectListByContentType(map);
        LOGGER.info("{}", contents);
        return contents;
    }

    @Override
    public List<ContentsListBo> selectContentType(Map<String,Object> map) {
        //查询内容列表
        List<ContentsListBo> contents = contentRoMapper.selectContentType(map);
        LOGGER.info("{}", contents);
        return contents;
    }

    @Override
    public List<ContentsListBo> selectListByChannelId(Map<String,Object> map) {
        int cnt = contentRoMapper.selectCntByChannelId(map);
        if(cnt > 0){
            throw new ServiceException(4304);
        }
        //查询内容列表
        List<ContentsListBo> contents = contentRoMapper.selectListByChannelId(map);
        LOGGER.info("{}", contents);
        return contents;
    }

    @Override
    public List<ContentsListBo> selectListBytopicId(Map<String,Object> map) {
        //查询内容列表
        List<ContentsListBo> contents = contentRoMapper.selectListBytopicId(map);
        LOGGER.info("{}", contents);
        return contents;
    }

    @Override
    public List<ContentsListBo> selectListByTplContent(Map<String,Object> map) {
        //查询内容列表
        List<ContentsListBo> contents = contentRoMapper.selectListByTplContent(map);
        LOGGER.info("{}", contents);
        return contents;
    }

    @Override
    public List<ModelItemBo> selectModeList(Map<String,Object> map) {
        //查询模型项
        List<ModelItemBo> modelItemBos = modelItemRoMapper.selectList(map);
        LOGGER.info("{}", modelItemBos);
        return modelItemBos;
    }

    @Transactional("db1TxManager")
    @Override
    public ContentSaveBo save(ContentSaveBo contentSaveBo) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //内容
        ContentBo contentBo = contentSaveBo.getContent();
        contentBo.setStatus(2);//审核通过
        contentBo.setContentId(uuid);
        Content content = new Content();
        try {
            BeanUtils.copyProperties(contentBo, content);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        //内容扩展项
        ContentExtBo contentExtBo = contentSaveBo.getContentExt();
        contentExtBo.setContentId(uuid);
        contentExtBo.setReleaseDate(new Date());
        ContentExt contentExt = new ContentExt();
        try {
            BeanUtils.copyProperties(contentExtBo, contentExt);
            contentExt.setNeedRegenerate(0);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
                //内容文本
        ContentTxtBo contentTxtBo = contentSaveBo.getContentTxt();
        contentTxtBo.setContentId(uuid);
        ContentTxt contentTxt = new ContentTxt();
        try {
            if(contentTxtBo != null){
                BeanUtils.copyProperties(contentTxtBo, contentTxt);
            }
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        //内容扩展属性
        List<ContentAttrBo> contentAttrList = contentSaveBo.getContentAttrList();
        //内容图片
        List<ContentPictureBo> contentPictureList = contentSaveBo.getContentPictureList();
        //内容附件
        List<FileBo> fileList = contentSaveBo.getFileList();

        contentMapper.insert(content);
        contentExtMapper.insert(contentExt);
        if(contentTxt != null){
            contentTxtMapper.insert(contentTxt);
        }
        if(contentAttrList != null){
            for(ContentAttrBo contentAttrBo:contentAttrList){
                contentAttrBo.setContentId(uuid);
                ContentAttr contentAttr = new ContentAttr();
                try {
                    BeanUtils.copyProperties(contentAttrBo, contentAttr);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                contentAttrMapper.insert(contentAttr);
            }
        }
        if(contentPictureList != null){
            for(ContentPictureBo contentPictureBo:contentPictureList){
                contentPictureBo.setContentId(uuid);
                ContentPicture contentPicture = new ContentPicture();
                try {
                    BeanUtils.copyProperties(contentPictureBo, contentPicture);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                contentPictureMapper.insert(contentPicture);
            }
        }
        if(fileList != null){
            for(FileBo fileBo:fileList){
                fileBo.setContentId(uuid);
                File file = new File();
                try {
                    BeanUtils.copyProperties(fileBo, file);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                fileMapper.insert(file);
            }
        }

        //用户组
        List<ContentGroupViewBo> groupList = contentSaveBo.getGroupList();
        if(groupList != null){
            for(ContentGroupViewBo groupBo:groupList){
                ContentGroupView group = new ContentGroupView();
                groupBo.setContentId(uuid);
                try {
                    BeanUtils.copyProperties(groupBo, group);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                groupMapper.insert(group);
            }
        }

        //专题组
        List<ContentTopicBo> topicList = contentSaveBo.getTopicList();
        if(topicList != null){
            for(ContentTopicBo topicBo:topicList){
                ContentTopic topic = new ContentTopic();
                topicBo.setContentId(uuid);
                try {
                    BeanUtils.copyProperties(topicBo, topic);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                topicMapper.insert(topic);
            }
        }

        ContentCount cons = new ContentCount();
        cons.setContentId(uuid);
        contentCountMapper.insert(cons);


        LOGGER.info("{}", contentSaveBo);
        return contentSaveBo;
    }

    @Override
    public ContentSaveBo selectContent(String contentId) {
        ContentSaveBo contentSaveBo = new ContentSaveBo();
        //内容
        Content content = contentRoMapper.selectByContentId(contentId);
        if(content == null){
            return contentSaveBo;
        }

        //内容扩展项
        ContentExt contentExt = contentExtRoMapper.selectByContentId(contentId);
        //内容文本
        ContentTxt contentTxt = contentTxtRoMapper.selectByContentId(contentId);
        //内容扩展属性
        List<ContentAttr> contentAttrList = contentAttrRoMapper.selectContentAttrList(contentId);
        //内容图片
        List<ContentPicture> contentPictureList = contentPictureRoMapper.selectContentPictureList(contentId);
        //内容附件
        List<File> fileList = fileRoMapper.selectFileList(contentId);

        ContentBo contentBo = new ContentBo();
        try {
            BeanUtils.copyProperties(content, contentBo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        contentSaveBo.setContent(contentBo);

        ContentExtBo contentExtBo = new ContentExtBo();
        try {
            BeanUtils.copyProperties(contentExt, contentExtBo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        contentSaveBo.setContentExt(contentExtBo);

        ContentTxtBo contentTxtBo = new ContentTxtBo();
        try {
            if(contentTxt != null){
                BeanUtils.copyProperties(contentTxt, contentTxtBo);
            }
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        contentSaveBo.setContentTxt(contentTxtBo);

        List<ContentAttrBo> contentAttrBoList = new ArrayList<ContentAttrBo>();
        for(ContentAttr contentAttr : contentAttrList){
            ContentAttrBo contentAttrBo = new ContentAttrBo();
            try {
                BeanUtils.copyProperties(contentAttr, contentAttrBo);
                contentAttrBoList.add(contentAttrBo);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
        }
        contentSaveBo.setContentAttrList(contentAttrBoList);

        List<ContentPictureBo> contentPictureBoList = new ArrayList<ContentPictureBo>();
        for(ContentPicture contentPicture : contentPictureList){
            ContentPictureBo contentPictureBo = new ContentPictureBo();
            try {
                BeanUtils.copyProperties(contentPicture, contentPictureBo);
                contentPictureBoList.add(contentPictureBo);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
        }
        contentSaveBo.setContentPictureList(contentPictureBoList);

        List<FileBo> fileBoList = new ArrayList<FileBo>();
        for(File file : fileList){
            FileBo fileBo = new FileBo();
            try {
                BeanUtils.copyProperties(file, fileBo);
                fileBoList.add(fileBo);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
        }
        contentSaveBo.setFileList(fileBoList);

        //获取用户组
        List<ContentGroupView> groupList = groupRoMapper.selectList(contentId);
        List<ContentGroupViewBo> groupBoList = new ArrayList<ContentGroupViewBo>();
        if(groupList != null){
            for(ContentGroupView group:groupList){
                ContentGroupViewBo groupBo = new ContentGroupViewBo();
                try {
                    BeanUtils.copyProperties(group, groupBo);
                    groupBoList.add(groupBo);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
            }
        }
        contentSaveBo.setGroupList(groupBoList);

        //获取专题组
        List<ContentTopic> topicList = topicRoMapper.selectByPrimaryKey(contentId);
        List<ContentTopicBo> topicBoList = new ArrayList<ContentTopicBo>();
        if(topicList != null){
            for(ContentTopic topic:topicList){
                ContentTopicBo topicBo = new ContentTopicBo();
                try {
                    BeanUtils.copyProperties(topic, topicBo);
                    topicBoList.add(topicBo);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
            }
        }
        contentSaveBo.setTopicList(topicBoList);

        LOGGER.info("{}", contentSaveBo);
        return contentSaveBo;
    }

    @Transactional("db1TxManager")
    @Override
    public ContentSaveBo update(ContentSaveBo contentSaveBo) {
        //内容
        ContentBo contentBo = contentSaveBo.getContent();
        Content content = new Content();
        try {
            BeanUtils.copyProperties(contentBo, content);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        //内容扩展项
        ContentExtBo contentExtBo = contentSaveBo.getContentExt();
        ContentExt contentExt = new ContentExt();
        try {
            BeanUtils.copyProperties(contentExtBo, contentExt);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        //内容文本
        ContentTxtBo contentTxtBo = contentSaveBo.getContentTxt();
        ContentTxt contentTxt = new ContentTxt();
        try {
            if(contentTxtBo != null){
                BeanUtils.copyProperties(contentTxtBo, contentTxt);
            }
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        //内容扩展属性
        List<ContentAttrBo> contentAttrList = contentSaveBo.getContentAttrList();
        //内容图片
        List<ContentPictureBo> contentPictureList = contentSaveBo.getContentPictureList();
        //内容附件
        List<FileBo> fileList = contentSaveBo.getFileList();
        contentMapper.updateByPrimaryKeySelective(content);
        contentExtMapper.updateByPrimaryKeySelective(contentExt);
        if(contentTxt != null){
            contentTxtMapper.updateByPrimaryKeySelective(contentTxt);
        }
        if(contentAttrList != null){
            for(ContentAttrBo contentAttrBo:contentAttrList){
                ContentAttr contentAttr = new ContentAttr();
                try {
                    BeanUtils.copyProperties(contentAttrBo, contentAttr);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                contentAttrMapper.updateByPrimaryKeySelective(contentAttr);
            }
        }

        int priority = 0;//排序
        //根据内容ID删除内容图片，然后再新增
        contentPictureMapper.deleteByContentId(content.getContentId());
        if(contentPictureList != null){
            for(ContentPictureBo contentPictureBo:contentPictureList){
                contentPictureBo.setPriority(priority);
                ContentPicture contentPicture = new ContentPicture();
                try {
                    BeanUtils.copyProperties(contentPictureBo, contentPicture);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                contentPictureMapper.insert(contentPicture);
                priority++;
            }
        }

        //根据内容ID删除附件信息，然后再新增
        fileMapper.deleteByContentId(content.getContentId());
        if(fileList != null){
            for(FileBo fileBo:fileList){
                File file = new File();
                try {
                    BeanUtils.copyProperties(fileBo, file);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                fileMapper.insert(file);
            }
        }


        //用户组
        groupMapper.deleteByPrimaryKey(content.getContentId());
        List<ContentGroupViewBo> groupList = contentSaveBo.getGroupList();
        if(groupList != null){
            for(ContentGroupViewBo groupBo:groupList){
                ContentGroupView group = new ContentGroupView();
                try {
                    BeanUtils.copyProperties(groupBo, group);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                groupMapper.insert(group);
            }
        }

        //专题组
        topicMapper.deleteByContentId(content.getContentId());
        List<ContentTopicBo> topicList = contentSaveBo.getTopicList();
        if(topicList != null){
            for(ContentTopicBo topicBo:topicList){
                ContentTopic topic = new ContentTopic();
                try {
                    BeanUtils.copyProperties(topicBo, topic);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                topicMapper.insert(topic);
            }
        }

        LOGGER.info("{}", contentSaveBo);
        return contentSaveBo;
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String contentId) {
        //删除内容扩展信息
        contentExtMapper.deleteByPrimaryKey(contentId);
        //删除内容文本信息
        contentTxtMapper.deleteByPrimaryKey(contentId);
        //删除内容扩展属性信息
        contentAttrMapper.deleteByPrimaryKey(contentId);
        //删除内容图片信息
        contentPictureMapper.deleteByPrimaryKey(contentId);
        //删除内容附件信息
        fileMapper.updateByContentId(contentId);
        //用户组
        groupMapper.deleteByPrimaryKey(contentId);
        //专题组
        topicMapper.deleteByContentId(contentId);
        //删除内容信息
        int r = contentMapper.deleteByPrimaryKey(contentId);
        LOGGER.info("{}", r);
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String deleteList(String[] contentIds) {
        for(int i=0;i<contentIds.length;i++){
            this.delete(contentIds[i]);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String updateStatusList(String[] contentIds) {
        contentMapper.updateStatusList(contentIds);
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String updatRegenerateList(String[] contentIds) {
        contentExtMapper.updatRegenerateList(contentIds);
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public ContentUpdateListBo updateList(ContentUpdateListBo contentUpdateListBo) {
        List<ContentBo> contentBoList = contentUpdateListBo.getContentBoList();
        for(ContentBo contentBo : contentBoList){
            Content content = new Content();
            try {
                BeanUtils.copyProperties(contentBo, content);
                contentMapper.updateByPrimaryKeySelective(content);
            } catch (Exception e) {
                LOGGER.error("类转换异常：{}", e);
                throw new RuntimeException("类型转换异常：{}", e);
            }
        }
        return contentUpdateListBo;
    }

    @Transactional("db1TxManager")
    @Override
    public ContentTopicListBo updatetopicList(ContentTopicListBo topicListBo) {
        if(topicListBo != null){
            for(ContentTopicBo topicBo:topicListBo.getTopicBoList()){
                ContentTopic topic = new ContentTopic();
                try {
                    BeanUtils.copyProperties(topicBo, topic);
                } catch (Exception e) {
                    LOGGER.error("类转换异常：{}", e);
                    throw new RuntimeException("类型转换异常：{}", e);
                }
                topicMapper.deleteByPrimaryKey(topic);
                topicMapper.insert(topic);
            }
        }
        return topicListBo;
    }


    @Override
    public List<ContentudBo> selectContentudList(Map<String,Object> map) {
        List<ContentudBo> list = new ArrayList<ContentudBo>();
        //下一篇
        ContentudBo contentudBo1 = contentRoMapper.selectByReleaseDateAsc(map);
        list.add(contentudBo1);
        //上一篇
        ContentudBo contentudBo2 = contentRoMapper.selectByReleaseDateDesc(map);
        list.add(contentudBo2);
        LOGGER.info("{}", list);
        return list;
    }


    @Override
    public String updateViewsDay(String contentId) {
        contentMapper.updateViewsDay(contentId);
        return "";
    }


}
