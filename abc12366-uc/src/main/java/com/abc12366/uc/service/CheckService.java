package com.abc12366.uc.service;

import com.abc12366.uc.model.Check;
import com.abc12366.uc.model.CheckRank;
import com.abc12366.uc.model.ReCheck;
import com.abc12366.uc.model.bo.CheckListBO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-21
 * Time: 14:28
 */
public interface CheckService {
    int check(Check check);

    void reCheck(ReCheck check);

    List<CheckRank> rank(String year);

    List<CheckListBO> checklist(String yearMonth);
}
