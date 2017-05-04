package com.abc12366.cms.service;

import com.abc12366.cms.mapper.db1.*;
import com.abc12366.cms.mapper.db2.*;
import com.abc12366.cms.model.*;
import com.abc12366.cms.model.bo.ContentSaveBo;
import com.abc12366.cms.model.bo.ContentListBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @Override
    public List<ContentListBo> selectList(Map<String,Object> map) {
        List<ContentListBo> contents = contentRoMapper.selectList(map);
        LOGGER.info("{}", contents);
        return contents;
    }

    @Override
    public List<ModelItem> selectModeList(String modelId) {
        List<ModelItem> modelItems = modelItemRoMapper.selectModeList(modelId);
        LOGGER.info("{}", modelItems);
        return modelItems;
    }

    @Override
    public String save(ContentSaveBo contentSaveDto) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Content content = contentSaveDto.getContent();
        content.setContentId(uuid);
        ContentExt contentExt = contentSaveDto.getContentExt();
        contentExt.setContentId(uuid);
        ContentTxt contentTxt = contentSaveDto.getContentTxt();
        contentTxt.setContentId(uuid);
        List<ContentAttr> contentAttrList = contentSaveDto.getContentAttrList();
        List<ContentPicture> contentPictureList = contentSaveDto.getContentPictureList();
        List<File> fileList = contentSaveDto.getFileList();
        contentMapper.insert(content);
        contentExtMapper.insert(contentExt);
        contentTxtMapper.insert(contentTxt);
        for(ContentAttr contentAttr:contentAttrList){
            contentAttr.setContentId(uuid);
            contentAttrMapper.insert(contentAttr);
        }
        for(ContentPicture contentPicture:contentPictureList){
            contentPicture.setContentId(uuid);
            contentPictureMapper.insert(contentPicture);
        }
        for(File file:fileList){
            file.setContentId(uuid);
            fileMapper.insert(file);
        }

        LOGGER.info("{}", "111");
        return "1111";
    }

    @Override
    public ContentSaveBo selectContent(String contentId) {
        ContentSaveBo contentSaveBo = new ContentSaveBo();
        Content content = contentRoMapper.selectByPrimaryKey(contentId);
        ContentExt contentExt = contentExtRoMapper.selectByPrimaryKey(contentId);
        ContentTxt contentTxt = contentTxtRoMapper.selectByPrimaryKey(contentId);
        List<ContentAttr> contentAttrList = contentAttrRoMapper.selectContentAttrList(contentId);
        List<ContentPicture> contentPictureList = contentPictureRoMapper.selectContentPictureList(contentId);
        List<File> fileList = fileRoMapper.selectFileList(contentId);
        contentSaveBo.setContent(content);
        contentSaveBo.setContentExt(contentExt);
        contentSaveBo.setContentTxt(contentTxt);
        contentSaveBo.setContentAttrList(contentAttrList);
        contentSaveBo.setContentPictureList(contentPictureList);
        contentSaveBo.setFileList(fileList);
        LOGGER.info("{}", contentSaveBo);
        return contentSaveBo;
    }

    @Override
    public String update(ContentSaveBo contentSaveDto) {
        Content content = contentSaveDto.getContent();
        ContentExt contentExt = contentSaveDto.getContentExt();
        ContentTxt contentTxt = contentSaveDto.getContentTxt();
        List<ContentAttr> contentAttrList = contentSaveDto.getContentAttrList();
        List<ContentPicture> contentPictureList = contentSaveDto.getContentPictureList();
        List<File> fileList = contentSaveDto.getFileList();
        contentMapper.updateByPrimaryKey(content);
        contentExtMapper.updateByPrimaryKey(contentExt);
        contentTxtMapper.updateByPrimaryKey(contentTxt);
        for(ContentAttr contentAttr:contentAttrList){
            contentAttrMapper.updateByPrimaryKey(contentAttr);
        }
        int priority = 0;
        contentPictureMapper.deleteByContentId(content.getContentId());
        for(ContentPicture contentPicture:contentPictureList){
            contentPicture.setPriority(priority);
            contentPictureMapper.insert(contentPicture);
            priority++;
        }
        fileMapper.deleteByContentId(content.getContentId());
        for(File file:fileList){
            fileMapper.insert(file);
        }

        LOGGER.info("{}", "111");
        return "1111";
    }

    @Override
    public String delete(String contentId) {
        contentExtMapper.deleteByPrimaryKey(contentId);
        contentTxtMapper.deleteByPrimaryKey(contentId);
        contentAttrMapper.deleteByPrimaryKey(contentId);
        contentPictureMapper.deleteByPrimaryKey(contentId);
        fileMapper.updateByContentId(contentId);
        int r = contentMapper.deleteByPrimaryKey(contentId);
        LOGGER.info("{}", r);
        return "r";
    }


}
