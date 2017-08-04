package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.bo.TagBO;
import com.abc12366.uc.model.bo.TagSelectParamBO;

import java.util.List;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-06-15
 * Time: 11:03
 */
public interface TagRoMapper {

    TagBO selectOne(String id);

    List<TagBO> selectList(TagSelectParamBO tagSelectParamBO);

    List<TagBO> selectListByUserId(String userId);
}
