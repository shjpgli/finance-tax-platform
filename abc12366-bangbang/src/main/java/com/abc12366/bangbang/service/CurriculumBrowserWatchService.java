package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.curriculum.CurriculumBrowserWatch;

/**
 * @Author liuQi
 * @Date 2018/1/30 16:31
 */
public interface CurriculumBrowserWatchService {

    /*今日浏览观看数*/
    CurriculumBrowserWatch selectTodayNum();

    /*本月浏览观看数*/
    CurriculumBrowserWatch selectCurrentMonthNum();

    /*修改*/
    void updateBrowserNum();

    /*修改*/
    void updateWatchNum();
}
