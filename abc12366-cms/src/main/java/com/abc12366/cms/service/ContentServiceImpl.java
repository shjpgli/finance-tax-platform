package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.*;
import com.abc12366.cms.mapper.db2.*;
import com.abc12366.cms.model.*;
import com.abc12366.cms.model.bo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 内容管理模块
 *
 * @author xieyanmao
 * @create 2017-04-27
 * @since 1.0.0
 */
@Service
public class ContentServiceImpl implements ContentService{
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
    public List<ContentsListBo> selectListByChannelId(Map<String,Object> map) {
        //查询内容列表
        List<ContentsListBo> contents = contentRoMapper.selectListByChannelId(map);
        LOGGER.info("{}", contents);
        return contents;
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

    @Override
    public ContentSaveBo save(ContentSaveBo contentSaveBo) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //内容
        ContentBo contentBo = contentSaveBo.getContent();
        contentBo.setStatus(2);
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
        ContentCount cons = new ContentCount();
        cons.setContentId(uuid);
        contentCountMapper.insert(cons);


        LOGGER.info("{}", contentSaveBo);
        return contentSaveBo;
    }

    @Override
    public ContentQueryBo selectContent(String contentId) {
        ContentQueryBo contentQueryBo = new ContentQueryBo();
        //内容
        Content content = contentRoMapper.selectByContentId(contentId);
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
        contentQueryBo.setContent(contentBo);

        ContentExtBo contentExtBo = new ContentExtBo();
        try {
            BeanUtils.copyProperties(contentExt, contentExtBo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        contentQueryBo.setContentExt(contentExtBo);

        ContentTxtBo contentTxtBo = new ContentTxtBo();
        try {
            if(contentTxt != null){
                BeanUtils.copyProperties(contentTxt, contentTxtBo);
            }
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        contentQueryBo.setContentTxt(contentTxtBo);

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
        contentQueryBo.setContentAttrList(contentAttrBoList);

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
        contentQueryBo.setContentPictureList(contentPictureBoList);

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
        contentQueryBo.setFileList(fileBoList);

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
        contentQueryBo.setGroupList(groupBoList);
        LOGGER.info("{}", contentQueryBo);
        return contentQueryBo;
    }

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

        LOGGER.info("{}", contentSaveBo);
        return contentSaveBo;
    }

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
        //删除内容信息
        int r = contentMapper.deleteByPrimaryKey(contentId);
        LOGGER.info("{}", r);
        return "";
    }

    @Override
    public String deleteList(String[] contentIds) {
        for(int i=0;i<contentIds.length;i++){
            this.delete(contentIds[i]);
        }
        return "";
    }

    @Override
    public String updateStatusList(String[] contentIds) {
        contentMapper.updateStatusList(contentIds);
        return "";
    }

    @Override
    public String updatRegenerateList(String[] contentIds) {
        contentExtMapper.updatRegenerateList(contentIds);
        return "";
    }

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


}
