package com.abc12366.uc.service.impl;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.model.bo.UCUserBO;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db2.UcUserLoginLogRoMapper;
import com.abc12366.uc.model.TodoTaskFront;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.UcUserLoginLog;
import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.service.IMsgSendService;
import com.abc12366.uc.service.TodoTaskService;
import com.abc12366.uc.service.UserExtendService;
import com.abc12366.uc.service.UserFeedbackMsgService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-11-10
 * Time: 15:11
 */
@Service
public class UserFeedbackMsgServiceImpl implements UserFeedbackMsgService {

    @Autowired
    private IMsgSendService msgSendService;

    @Autowired
    private UserExtendService userExtendService;

    @Autowired
    private UcUserLoginLogRoMapper loginLogRoMapper;

    @Autowired
    private TodoTaskService todoTaskService;

    @Override
    public void updatePasswordSuccessNotice() {
        User user = getUser();

        //发信息
        //1.系统消息
        String sysMsg = "您的密码于" + DateUtils.dateToStr(new Date()) + "被修改，请确认为本人操作，如有疑问请联系客服咨询4008873133。";
        //2.微信消息
        Map<String, String> dataList = new HashMap<>();
        dataList.put("first", "您好,您的密码已通过，");
        dataList.put("keyword1", user.getUsername());
        dataList.put("keyword2", DateUtils.dateToStr(new Date()));
        dataList.put("remark", "完成修改，感谢您的使用！");
        //3.短信消息
        String dxmsg = sysMsg;
        msgSendService.sendMsg(user, sysMsg, "AYi8h8g7_bKN8Yr9wVDh4ZQ_CIOwsoIzX1A6tx1E5WE", dataList, dxmsg);
    }

    @Override
    public void unrealname() {
        if (!isFirstLoginToday()) {
            return;
        }
        UserExtendBO userExtendBO = getUserExtend();
        if (userExtendBO == null || userExtendBO.getValidStatus() == null || !StringUtils.isEmpty(userExtendBO.getValidStatus()) || userExtendBO.getValidStatus().equals("2")) {
            return;
        }
        User user = getUser();

        //发信息
        //1.系统消息
        String sysMsg = "根据国家网信办相关规定，互联网平台的注册用户必须实名认证，您还未进行实名认证，" +
                "请尽快完成实名认证，否则部分功能将无法使用；" +
                "<a href='" + SpringCtxHolder.getProperty("abc12366.api.url.uc") + "'>马上去实名认证。</a>";
        //2.微信消息
        Map<String, String> dataList = new HashMap<>();
        dataList.put("first", "根据国家网信办相关规定，互联网平台的注册用户必须实名认证,");
        dataList.put("keyword1", "未认证");
        dataList.put("keyword2", DateUtils.dateToStr(new Date()));
        dataList.put("remark", "请尽快完成实名认证，否则部分功能将无法使用。");
        //3.短信消息
        String dxmsg = "根据国家网信办相关规定，互联网平台的注册用户必须实名认证，您还未进行实名认证，" +
                "请尽快完成实名认证，否则部分功能将无法使用。";
        msgSendService.sendMsg(user, sysMsg, "JQUa0hyi-oKyG-hhuboC_4IKAeBTRn26w2ippsLUS-U", dataList, dxmsg);
    }

    @Override
    public void undotask() {
        if (!isFirstLoginToday()) {
            return;
        }
        int undoTaskCount = 0;
        List<TodoTaskFront> taskList = new ArrayList<>();
        taskList.addAll(todoTaskService.selectNormalTaskList(Utils.getUserId()));
        taskList.addAll(todoTaskService.selectOnetimeTaskList(Utils.getUserId()));
        taskList.addAll(todoTaskService.selectSpecialTaskList(Utils.getUserId()));
        taskList.addAll(todoTaskService.selectBangbangTaskList(Utils.getUserId()));
        for (TodoTaskFront taskFront : taskList) {
            if (taskFront != null && taskFront.getStatus() != null && taskFront.getStatus().equals("1")) {
                undoTaskCount++;
            }
        }

        //发信息
        //1.系统消息
        String sysMsg = "您还有" + undoTaskCount + "个任务没有完成，快去做任务赢取积分和经验值吧!" +
                "<a href='" + SpringCtxHolder.getProperty("abc12366.api.url.uc") + "/userinfo/task.php'>马上做任务。</a>";
        //2.微信消息,不做
        //3.短信消息，不做

        msgSendService.sendMsg(getUser(), sysMsg, null, null, null);
    }

    @Override
    public void check() {
        if (!isFirstLoginToday()) {
            return;
        }

        //发信息
        //1.系统消息
        String sysMsg = "签到赢积分换好礼，您今天的签到打卡任务未完成，请及时签到；" +
                "<a href='" + SpringCtxHolder.getProperty("abc12366.api.url.uc") + "/member/checkIn.php'>马上签到。</a>";
        //2.微信消息,不做
        //3.短信消息，不做
        msgSendService.sendMsg(getUser(), sysMsg, null, null, null);
    }

    @Override
    public void expLevelUp() {
        //发信息
        //1.系统消息
        String sysMsg = "恭喜您！您的经验值等级提升了，详情请查看经验值明细；" +
                "<a href='" + SpringCtxHolder.getProperty("abc12366.api.url.uc") + "/userinfo/expLog.php'>查看详情</a>";
        //2.微信消息,不做
        //3.短信消息，不做
        msgSendService.sendMsg(getUser(), sysMsg, null, null, null);
    }

    private User getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        UCUserBO ucUserBO = (UCUserBO) request.getAttribute(Constant.USER_INFO);
        User user = new User();
        BeanUtils.copyProperties(ucUserBO, user);
        return user;
    }

    private UserExtendBO getUserExtend() {
        return userExtendService.selectOne(Utils.getUserId());
    }

    private boolean isFirstLoginToday() {
        Map<String, Object> map = new HashMap<>();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MINUTE, 0);

        calendar2.add(Calendar.DAY_OF_MONTH, 1);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MINUTE, 0);
        map.put("userId", Utils.getUserId());
        map.put("startTime", calendar1.getTime());
        map.put("endTime", calendar2.getTime());
        List<UcUserLoginLog> logList = loginLogRoMapper.selectLoginLogList(map);
        boolean isFirstLoginToday = false;
        if (logList == null || logList.size() <= 1) {
            isFirstLoginToday = true;
        }
        return isFirstLoginToday;
    }

}
