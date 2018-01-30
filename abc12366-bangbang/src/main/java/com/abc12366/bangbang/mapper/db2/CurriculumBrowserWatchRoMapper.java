package com.abc12366.bangbang.mapper.db2;

import com.abc12366.bangbang.model.curriculum.CurriculumBrowserWatch;

/**
 *
 * CurriculumBrowserWatchRoMapper数据库操作接口类
 */
public interface CurriculumBrowserWatchRoMapper {

    int selectCntToday();

    /*今日浏览观看数*/
    CurriculumBrowserWatch selectTodayNum();

    /*本月浏览观看数*/
    CurriculumBrowserWatch selectCurrentMonthNum();

}
