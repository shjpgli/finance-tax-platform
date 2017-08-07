package com.abc12366.uc.service.admin;

import com.abc12366.uc.model.admin.TaskInfo;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-07 9:48 AM
 * @since 1.0.0
 */
public interface TaskService {


    List<TaskInfo> list();

    void addJob(TaskInfo info);

    void edit(TaskInfo info);

    void delete(String jobName, String jobGroup);

    void pause(String jobName, String jobGroup);

    void resume(String jobName, String jobGroup);
}
