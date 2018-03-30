package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.MyTaskSurvey;
import com.abc12366.uc.model.bo.MyTaskBO;
import com.abc12366.uc.model.bo.TaskRangeBO;
import com.abc12366.uc.model.bo.UserTaskBO;

import java.util.List;
import java.util.Map;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-24
 * Time: 11:54
 */
public interface UserTaskRoMapper {
    

    MyTaskBO selectMyTask(String userId);

    //我的任务统计：UC我的任务模块顶排统计列表
    List<TaskRangeBO> selectTaskRangeList();

    //我的任务概况：UC、模块顶排用户任务概况展示，包括本月完成任务获取的经验值、积分，以及本月完成任务数量
    MyTaskSurvey selectMyTaskSurvey(String userId);
}
