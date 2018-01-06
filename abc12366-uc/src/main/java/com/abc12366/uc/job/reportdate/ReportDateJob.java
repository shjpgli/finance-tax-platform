package com.abc12366.uc.job.reportdate;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.model.bo.AppBO;
import com.abc12366.gateway.service.AppService;
import com.abc12366.gateway.util.RemindConstant;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.UserBO;
import com.abc12366.uc.service.IMsgSendService;
import com.abc12366.uc.service.UserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * 本月申报期限以及会员到期提醒
 *
 * @author zhushuai 2017-10-23
 * @since 1.0.0
 */
public class ReportDateJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportDateJob.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AppService appService;

    @Autowired
    private IMsgSendService msgSendService;

    private String shenqqix = "";//申报期限

    private String pmonthF = "";//上个月第一天

    private String pmonthL = "";//上个月最后一天

    private String accessToken = "";//accessToken

    private static final int SPLIT_NUM = 5000;//每个线程处理数量

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void execute(JobExecutionContext arg0) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        pmonthF = format.format(cal_1.getTime());

        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);
        pmonthL = format.format(cale.getTime());

        AppBO appBO = appService.selectByName("abc12366-admin");
        Date lastRest = appBO.getLastResetTokenTime();
        if (lastRest.before(new Date())) {
            appBO.setLastResetTokenTime(DateUtils.addHours(new Date(), 2));
            appService.update(appBO);
        }
        accessToken = appBO.getAccessToken();
        LOGGER.info("获取运营管理系统accessToken:" + accessToken);

        LOGGER.info("开始电子税局获取办税期限..............");
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Access-Token", accessToken);
        headers2.add("Version", "1");
        HttpEntity httpEntity = new HttpEntity(headers2);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity2 = restTemplate.exchange(SpringCtxHolder.getProperty("abc12366.message.url") +
                        "/hngs/get?api="
                        + new BASE64Encoder().encode(("/ggfw/bsrl/getsbrq?sbnf=" + new SimpleDateFormat("yyyy")
                        .format(new Date())).getBytes()), HttpMethod.GET,
                httpEntity, String.class);

        JSONObject json = JSONObject.parseObject(String.valueOf(responseEntity2.getBody()));
        LOGGER.info("结束电子税局获取办税期限:" + json.toJSONString());

        if ("000".equals(json.getString("code"))) {
            JSONArray array = json.getJSONArray("dataList");
            String dateM = new SimpleDateFormat("yyyy-MM").format(new Date());
            for (Object obj : array) {
                JSONObject object = (JSONObject) obj;
                if (dateM.equalsIgnoreCase(object.getString("sbyf"))) {
                    shenqqix = object.getString("sbyf") + "-" + object.getString("sbrq").split(",")[1];
                    LOGGER.info("获取办税日历本月办税期限:" + shenqqix);
                    break;
                }
            }
        } else {
            LOGGER.info("获取办税日历异常:" + json.getString("msg"));
        }

        int userTotal = userService.getAllNomalCont();
        int threadNum = (int) Math.ceil((float) userTotal / SPLIT_NUM);
        List<Future<Integer>> futureList = new ArrayList<>();

        LOGGER.info("开始创建线程池.......");
        long time = System.currentTimeMillis();

        //创建线程池
        ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.SECONDS, new
                ArrayBlockingQueue<Runnable>(threadNum), new ThreadFactory() {

            @Override
            public Thread newThread(Runnable runnable) {
                Thread t = new Thread(runnable, "ReportDateThread_" + System.currentTimeMillis());
                return t;
            }
        });


        for (int i = 1; i <= threadNum; i++) {
            int begin = (i - 1) * SPLIT_NUM;
            Future<Integer> future = executorService.submit(new ReportDateThread(begin));
            futureList.add(future);
        }

        //记录线程结果  
        int handleCount = 0;
        for (int i = 0; i < futureList.size(); i++) {
            try {
                Future<Integer> future = futureList.get(i);
                Integer result = future.get();
                handleCount += ((result == null) ? 0 : result);
            } catch (Exception e) {
                LOGGER.error("线程结果记录异常:", e);
            }
        }
        long time2 = System.currentTimeMillis();
        LOGGER.info("线程池处理完毕：" + handleCount + ",耗时：" + (time2 - time));

        //关闭线程池
        executorService.shutdown();
    }

    public class ReportDateThread implements Callable<Integer> {
        private int begin;

        public ReportDateThread(Integer begin) {
            this.begin = begin;
        }

        @Override
        public Integer call() {
            Map<String, Object> map = new HashMap<>();
            map.put("status", true);
            map.put("begin", begin);
            map.put("size", SPLIT_NUM);
            List<UserBO> userList = userService.getNomalList(map);

            //业务处理
            for (UserBO userBO : userList) {
                User user = new User();
                BeanUtils.copyProperties(userBO, user);
                LOGGER.info("用户:" + JSONObject.toJSONString(user));
                //1.会员是否快到期
                if (!"VIP0".equalsIgnoreCase(userBO.getVipLevel())
                        && chargeDate(userBO.getVipExpireDate())) {
                    LOGGER.info("用户:" + userBO.getId() + ",会员过期信息提醒：" + userBO.getVipLevel() + ";过期时间:" + userBO
                            .getVipExpireDate());
                    String sysMsg = RemindConstant.HYDQMSG.replaceAll("\\{#DATA.LEVEL\\}", userBO.getVipLevelName())
                            .replaceAll("\\{#DATA.DATE\\}", getFormat(userBO.getVipExpireDate()));

                    Map<String, String> dataList = new HashMap<String, String>();
                    dataList.put("first", "您的会员即将过期");
                    dataList.put("remark", "您的财税专家会员即将过期，为不影响您正常使用请及时续费。");
                    dataList.put("keyword1", userBO.getVipLevelName());
                    dataList.put("keyword1Color", "#00DB00");
                    dataList.put("keyword2", getFormat(userBO.getVipExpireDate()));
                    dataList.put("keyword2Color", "#00DB00");
                    dataList.put("url", SpringCtxHolder.getProperty("mbxx.hygq.url") + new BASE64Encoder().encode
                            (userBO.getWxopenid().getBytes()));

                    String vdxMsg = RemindConstant.HYDQMSG.replaceAll("\\{#DATA.LEVEL\\}", userBO
                            .getVipLevelName()).replaceAll("\\{#DATA.DATE\\}", getFormat(userBO.getVipExpireDate
                            ()));

                    msgSendService.sendMsg(user, sysMsg, "", "tG9RgeqS3RNgx7lc0oQkBXf3xZ-WiDYk6rxE0WwPuA8", dataList,
                            vdxMsg);
                }

                //2.申报期信息发送
                LOGGER.info("用户:" + userBO.getId() + ",申报期信息提醒：" + userBO.getVipLevel());
                String sysMsg = RemindConstant.SBQXXTMSG.replaceAll("\\{#DATA.DATE\\}", shenqqix);

                Map<String, String> dataList = new HashMap<String, String>();
                dataList.put("first", "财税专家会员提醒，本月纳税申报可申报的报表种类如下：");
                dataList.put("remark", "实际申报种类以税务局核定信息为准，请您在申报期限内及时进行申报缴税！");
                dataList.put("keyword1", "增值税、消费税、所得税、财务报表");
                dataList.put("keyword1Color", "#00DB00");
                dataList.put("keyword2", pmonthF + "至" + pmonthL);
                dataList.put("keyword2Color", "#00DB00");
                dataList.put("keyword3", shenqqix);
                dataList.put("keyword3Color", "#00DB00");
                dataList.put("url", SpringCtxHolder.getProperty("mbxx.sbqx.url"));

                String vdxMsg = RemindConstant.SBQXSJMSG.replaceAll("\\{#DATA.DATE\\}", shenqqix);

                msgSendService.sendMsg(user, sysMsg, "", "eltMyMTpahpHEqH0uV_xVw-FuMAwdDlq_kLUkDynM2g", dataList,
                        vdxMsg);
            }
            return 1;
        }
    }

    /**
     * 判断会员是否会在三个月之内到期
     *
     * @param dates Date
     * @return boolean
     */
    public boolean chargeDate(Date dates) {
        if (dates == null || dates.before(new Date())) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 3);
        return dates.before(calendar.getTime());
    }

    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    public String getFormat(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar
                .DAY_OF_MONTH) + "日";
    }
}
