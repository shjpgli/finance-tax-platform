package com.abc12366.cms.service;

import com.abc12366.cms.model.ModelItem;
import com.abc12366.cms.model.bo.ContentQueryBo;
import com.abc12366.cms.model.bo.ContentSaveBo;
import com.abc12366.cms.model.bo.ContentListBo;

import java.util.List;
import java.util.Map;

/**
 * 内容管理模块
 *
 * @author xieyanmao
 * @create 2017-04-27
 * @since 1.0.0
 */
public interface ContentService {
    List<ContentListBo> selectList(Map<String,Object> map);

    List<ModelItem> selectModeList(String modelId);

    ContentSaveBo save(ContentSaveBo contentSaveBo);

    ContentQueryBo selectContent(String contentId);

    ContentSaveBo update(ContentSaveBo contentSaveBo);

    String delete(String contentId);

}
