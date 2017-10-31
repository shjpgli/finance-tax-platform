package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.TodoTask;
import com.abc12366.uc.model.TodoTaskFront;
import com.abc12366.uc.service.TodoTaskService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待办任务接口控制器
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-12
 * Time: 14:36
 */
@RestController
@RequestMapping(path = "/todo/task", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class TodoTaskController {
    private static Logger LOGGER = LoggerFactory.getLogger(TodoTaskController.class);

    @Autowired
    private TodoTaskService todoTaskService;

    /**
     * 根据系统任务类型查询用户待办任务列表
     * @param type 系统任务类型
     * @param userId 用户ID
     * @return ResponseEntity {@linkplain com.abc12366.uc.model.TodoTask}
     */
    @GetMapping(path = "/{type}/{userId}")
    public ResponseEntity selectList(@PathVariable("type") String type, @PathVariable("userId") String userId) {
        LOGGER.info("{}:{}", type, userId);
        List<TodoTask> taskList = todoTaskService.selectList(type, userId);
        return ResponseEntity.ok(Utils.kv("dataList", taskList));
    }

    /**
     * 查询用户日常任务列表
     * @param userId 用户ID
     * @param page 页数
     * @param size 每页数据条数
     * @return ResponseEntity {@linkplain com.abc12366.uc.model.TodoTask}
     */
    @GetMapping(path = "/normal")
    public ResponseEntity selectNormalTaskList(@RequestParam(value = "userId") String userId,
                                               @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                               @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}", userId, page, size);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<TodoTaskFront> taskList = todoTaskService.selectNormalTaskList(userId);
        return (taskList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) taskList, "total", ((Page) taskList).getTotal()));
    }

    /**
     * 查询用户一次性（多为成长任务）任务列表
     * @param userId 用户ID
     * @param page 页数
     * @param size 每页数据条数
     * @return ResponseEntity {@linkplain com.abc12366.uc.model.TodoTask}
     */
    @GetMapping(path = "/onetime")
    public ResponseEntity selectOnetimeTaskList(@RequestParam(value = "userId") String userId,
                                                @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                                @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}", userId, page, size);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<TodoTaskFront> taskList = todoTaskService.selectOnetimeTaskList(userId);
        return (taskList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) taskList, "total", ((Page) taskList).getTotal()));
    }

    /**
     * 查询用户日常任务列表
     * @param userId 用户ID
     * @param page 页数
     * @param size 每页数据条数
     * @return ResponseEntity {@linkplain com.abc12366.uc.model.TodoTask}
     */
    @GetMapping(path = "/special")
    public ResponseEntity selectSpecialTaskList(@RequestParam(value = "userId") String userId,
                                                @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                                @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}", userId, page, size);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<TodoTaskFront> taskList = todoTaskService.selectSpecialTaskList(userId);
        return (taskList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) taskList, "total", ((Page) taskList).getTotal()));
    }

    /**
     * 查询用户帮帮任务列表
     * @param userId 用户ID
     * @param page 页数
     * @param size 每页数据条数
     * @return ResponseEntity {@linkplain com.abc12366.uc.model.TodoTask}
     */
    @GetMapping(path = "/bangbang")
    public ResponseEntity selectBangbangTaskList(@RequestParam(value = "userId") String userId,
                                                @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                                @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}", userId, page, size);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<TodoTaskFront> taskList = todoTaskService.selectBangbangTaskList(userId);
        return (taskList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) taskList, "total", ((Page) taskList).getTotal()));
    }

    /**
     * 用户做任务接口：做任务并且计算奖励，用于用户业务操作任务埋点，
     * @param userId 用户ID
     * @param taskCode 系统任务编码
     */
    /**系统任务编码
    //1.首次修改登录密码
    public final static String SYS_TASK_FIRST_UPDATE_PASSWROD_CODE = "T-scxgdl";
    //2.首次上传用户头像图片
    public final static String SYS_TASK_FIRST_UPLOAD_PICTURE_CODE = "T-scsctx";
    //3.首次手机认证
    public final static String SYS_TASK_FIRST_PHONE_VALIDATE_CODE = "T-scsjrz";
    //4.首次申报缴税
    public final static String SYS_TASK_SCSBJS_CODE = "T-scsbjs";
    //5.首次消费
    public final static String SYS_TASK_FIRST_CONSUME_CODE = "T-scxf";
    //6.首次下载安装ABC4000
    public final static String SYS_TASK_SCXZAZ_CODE = "T-scxzaz";
    //7.网上零申报
    public final static String SYS_TASK_WSLSB_CODE = "T-wslsb";
    //8.网上缴税
    public final static String SYS_TASK_WSJS_CODE = "T-wsjs";
    //9.固定资产折旧管理
    public final static String SYS_TASK_GDZCZJGL_CODE = "T-gdzxcj";
    //10.海关完税凭证采集
    public final static String SYS_TASK_HGWSPZCJ_CODE = "T-hgwspz";
    //11.查询服务器数据
    public final static String SYS_TASK_CXFWQSJ_CODE = "T-cxwfqsj";
    //12.获取申报结果
    public final static String SYS_TASK_HQSBJG_CODE = "T-hqsbjg";
    //13.分支机构设置
    public final static String SYS_TASK_FZJGSZ_CODE = "T-fzjgsz";
    //14.预缴税款
    public final static String SYS_TASK_YJSK_CODE = "T-yjsk";
    //15.每日回答问题
    public final static String SYS_TASK_MRHDWT_CODE = "T-mrhdwt";
    //16.每日疑难提问
    public final static String SYS_TASK_MRYNTW_CODE = "T-mryntw";
    //17.每日问答收藏
    public final static String SYS_TASK_MRWDSC_CODE = "T-mrwdsc";
    //18.介质申报
    public final static String SYS_TASK_JZSB_CODE = "T-jzsb";
    //19.系统修复
    public final static String SYS_TASK_XTXF_CODE = "T-xtxf";
    //20.每日评论任务
    public final static String SYS_TASK_MRPL_CODE = "T-mrpl";
    //21.每日签到
    public final static String SYS_TASK_CHECK_CODE = "T-mrqd";
    //22.每日收藏课程
    public final static String SYS_TASK_COURSE_COLLECT_CODE = "T-mrsckc";
    //23.每日评论问题
    public final static String SYS_TASK_ASK_COMMENT_CODE = "T-mrplwd";
    //24.每日浏览资讯
    public final static String SYS_TASK_BROSE_NEWS_CODE = "T-mrllzx";
    //25.每日评论课程
    public final static String SYS_TASK_COURSE_COMMENT_CODE = "T-mrplkc";
    //26.消费超过1000人民币
    public final static String SYS_TASK_CONSUME_BEYOND_1000_CODE = "T-ycxxf";
    //27.消费超过3000人民币
    public final static String SYS_TASK_CONSUME_BEYOND_3000_CODE = "ycxxf3k";
    //28.消费超过5000人民币
    public final static String SYS_TASK_CONSUME_BEYOND_5000_CODE = "T-ycxxf5k";
    //29.打印完税凭证
    public final static String SYS_TASK_DYWSPZ_CODE = "T-dywspz";
    //30.作废报表
    public final static String SYS_TASK_ZFBB_CODE = "T-zfbb";
    //31.每日分享课程
    public final static String SYS_TASK_COURSE_SHARE_CODE = "T-mrfxkc";
    //32.绑定税号
    public final static String SYS_TASK_COURSE_BDSH_CODE = "T-mrfxkc";
    //33.每日登录
    public final static String SYS_TASK_LOGIN_CODE = "T-mrdl";
    //34.网上申报
    public final static String SYS_TASK_WSSB_CODE = "T-wssb";
    //35.首次实名认证
    public final static String SYS_TASK_FIRST_REALNAME_VALIDATE_CODE = "T-scsmrz";
    //36.关注财税专家公众号
    public final static String SYS_TASK_GZCSZJGZH_CODE = "T-gzcs";
    //37.首次邮箱认证(暂不做)
    public final static String SYS_TASK_FIRST_MAIL_VALIDATE_CODE = "T-scyxrz";
    //38.下载企业信息
    public final static String SYS_TASK_XZQYXX_CODE = "T-xzqyxx";
    //40.ABC4000实名认证
    public final static String SYS_TASK_ABC4000_SMRZ_CODE = "T-smrz";
    //41.每日课程学习
    public final static String SYS_TASK_COURSE_LEARNING_CODE = "T-mrkcxx";*/
    @PostMapping(path = "/do/award/{userId}/{taskCode}")
    public ResponseEntity doTaskAward(@PathVariable("userId") String userId, @PathVariable("taskCode") String taskCode) {
        LOGGER.info("用户做任务,用户ID：{}，任务编码：{}", userId, taskCode);
        todoTaskService.doTask(userId, taskCode);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 用户做任务接口：做任务不计算奖励，用于用户业务操作任务埋点，多用于奖励规则比较复杂需要单做的业务
     * @param userId 用户ID
     * @param taskCode 系统任务编码
     */
    @PostMapping(path = "/do/noaward/{userId}/{taskCode}")
    public ResponseEntity doTaskNoAward(@PathVariable("userId") String userId, @PathVariable("taskCode") String taskCode) {
        todoTaskService.doTaskWithouComputeAward(userId, taskCode);
        return ResponseEntity.ok(Utils.kv());
    }
}
