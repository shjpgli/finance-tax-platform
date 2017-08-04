package com.abc12366.uc.service;

import com.abc12366.uc.model.weixin.bo.gzh.GzhInfo;

import java.util.List;

public interface IWxGzhService {

    List<GzhInfo> wxgzhList(GzhInfo gzhInfo, int page, int size);

    GzhInfo selectOne(String id);

    GzhInfo insert(GzhInfo gzhInfo);

    GzhInfo update(GzhInfo gzhInfo);

    void delete(String id);

}
