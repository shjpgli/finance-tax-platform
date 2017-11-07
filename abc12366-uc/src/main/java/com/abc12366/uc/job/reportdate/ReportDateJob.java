package com.abc12366.uc.job.reportdate;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.service.AppService;
import com.abc12366.uc.model.Message;
import com.abc12366.uc.model.bo.UserBO;
import com.abc12366.uc.model.weixin.bo.template.Template;
import com.abc12366.uc.service.IWxTemplateService;
import com.abc12366.uc.service.UserService;
import com.abc12366.uc.util.MessageConstant;
import com.abc12366.uc.util.MessageSendUtil;
import com.abc12366.uc.wsbssoa.response.HngsAppLoginResponse;
import com.abc12366.uc.wsbssoa.utils.soaUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private MessageSendUtil messageSendUtil;

    @Autowired
    private IWxTemplateService templateService;

    @Autowired
    private AppService appService;

    private String shenqqix = "";//申报期限

    private String pmonthF = "";//上个月第一天

    private String pmonthL = "";//上个月最后一天

    private String accessToken = "";//accessToken

    private static final int SPLIT_NUM = 5000;//每个线程处理数量

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {

        userService = (UserService) SpringCtxHolder.getApplicationContext().getBean("userService");
        messageSendUtil = (MessageSendUtil) SpringCtxHolder.getApplicationContext().getBean("messageSendUtil");
        templateService = (IWxTemplateService) SpringCtxHolder.getApplicationContext().getBean("templateService");
        appService = (AppService) SpringCtxHolder.getApplicationContext().getBean("appService");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        pmonthF = format.format(cal_1.getTime());

        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);
        pmonthL = format.format(cale.getTime());

        LOGGER.info("电子税局获取办税期限..............");
        HttpHeaders headers = new HttpHeaders();
        String url = SpringCtxHolder.getProperty("wsbssoa.hngs.url") + "/app/login";
        Map<String, Object> map = new HashMap<>();
        map.put("appId", SpringCtxHolder.getProperty("APPID"));
        map.put("secret", SpringCtxHolder.getProperty("SECRET"));
        HttpEntity requestEntity = new HttpEntity(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (soaUtil.isExchangeSuccessful(responseEntity)) {
            HngsAppLoginResponse hngsAppLoginResponse = JSON.parseObject(String.valueOf(responseEntity.getBody()),
                    HngsAppLoginResponse.class);
            String dzsjtoken = hngsAppLoginResponse.getAccessToken();

            HttpHeaders headers2 = new HttpHeaders();
            headers2.add("platform", SpringCtxHolder.getProperty("APPID"));
            headers2.add("accessToken", dzsjtoken);
            HttpEntity httpEntity = new HttpEntity(headers2);
            ResponseEntity responseEntity2 = restTemplate.exchange(SpringCtxHolder.getProperty("wsbssoa.hngs.url") +
                            "/ggfw/bsrl/getsbrq?sbnf=" + new SimpleDateFormat("yyyy").format(new Date()), HttpMethod.GET,
					httpEntity, String.class);

            JSONObject json = JSONObject.parseObject(String.valueOf(responseEntity2.getBody()));
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
        } else {
            LOGGER.info("电子税局登录异常..............");
        }

        //获取运营管理系统accessToken
        accessToken = appService.selectByName("abc12366-admin").getAccessToken();
        LOGGER.info("获取运营管理系统accessToken:" + accessToken);

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
                LOGGER.info("用户:" + JSONObject.toJSONString(userBO));
                //1.会员是否快到期
                if (!"VIP0".equalsIgnoreCase(userBO.getVipLevel())
                        && chargeDate(userBO.getVipExpireDate())) {

                    //普通消息
                    LOGGER.info("用户:" + userBO.getId() + ",会员过期提醒：" + userBO.getVipLevel());
                    Message message = new Message();
                    message.setBusinessId(userBO.getId());
                    message.setBusiType(MessageConstant.HYDQTX);
                    message.setType(MessageConstant.SYS_MESSAGE);
                    message.setContent(MessageConstant.HYDQMSG.replaceAll("\\{#DATA.LEVEL\\}", userBO.getVipLevelName
							()).replaceAll("\\{#DATA.DATE\\}", getFormat(userBO.getVipExpireDate())));
                    message.setUserId(userBO.getId());
                    messageSendUtil.sendMessage(message, accessToken);

                    //微信消息
                    if (StringUtils.isNotEmpty(userBO.getWxopenid())) {
                        Map<String, String> dataList = new HashMap<String, String>();
                        dataList.put("userId", userBO.getId());
                        dataList.put("openId", userBO.getWxopenid());
                        dataList.put("first", "您的会员即将过期");
                        dataList.put("remark", "您的财税专家会员即将过期，为不影响您正常使用请及时续费。");
                        dataList.put("keyword1", userBO.getVipLevelName());
                        dataList.put("keyword1Color", "#00DB00");
                        dataList.put("keyword2", getFormat(userBO.getVipExpireDate()));
                        dataList.put("keyword2Color", "#00DB00");
                        dataList.put("url", SpringCtxHolder.getProperty("mbxx.hygq.url")+new BASE64Encoder().encode(userBO.getWxopenid().getBytes()));
                        templateService.templateSend("tG9RgeqS3RNgx7lc0oQkBXf3xZ-WiDYk6rxE0WwPuA8", dataList);
                    }

                    //短信消息
                    if (("VIP3".equalsIgnoreCase(userBO.getVipLevel())
                            || "VIP4".equalsIgnoreCase(userBO.getVipLevel()))
                            && StringUtils.isNotEmpty(userBO.getPhone())) {
                        String vdxMsg = MessageConstant.HYDQMSG.replaceAll("\\{#DATA.LEVEL\\}", userBO
								.getVipLevelName()).replaceAll("\\{#DATA.DATE\\}", getFormat(userBO.getVipExpireDate
								()));
                        messageSendUtil.sendPhoneMessage(userBO.getPhone(), vdxMsg, accessToken);
                    }
                }

                //2.申报期信息发送
                LOGGER.info("用户:" + userBO.getId() + ",申报期信息提醒：" + userBO.getVipLevel());
                Message message = new Message();
                message.setBusinessId(userBO.getId());
                message.setBusiType(MessageConstant.SBXQTX);
                message.setType(MessageConstant.SYS_MESSAGE);
                message.setContent(MessageConstant.SBQXXTMSG.replaceAll("\\{#DATA.DATE\\}", shenqqix));
                message.setUserId(userBO.getId());
                messageSendUtil.sendMessage(message, accessToken);

                if (!"VIP0".equalsIgnoreCase(userBO.getVipLevel())
                        && StringUtils.isNotEmpty(userBO.getWxopenid())) {
                    Map<String, String> dataList = new HashMap<String, String>();
                    dataList.put("userId", userBO.getId());
                    dataList.put("openId", userBO.getWxopenid());
                    dataList.put("first", "财税专家会员提醒，本月纳税申报可申报的报表种类如下：");
                    dataList.put("remark", "实际申报种类以税务局核定信息为准，请您在申报期限内及时进行申报缴税！");
                    dataList.put("keyword1", "增值税、消费税、所得税、财务报表");
                    dataList.put("keyword1Color", "#00DB00");
                    dataList.put("keyword2", pmonthF + "至" + pmonthL);
                    dataList.put("keyword2Color", "#00DB00");
                    dataList.put("keyword3", shenqqix);
                    dataList.put("keyword3Color", "#00DB00");
                    dataList.put("url", SpringCtxHolder.getProperty("mbxx.sbqx.url"));
                    templateService.templateSend("eltMyMTpahpHEqH0uV_xVw-FuMAwdDlq_kLUkDynM2g", dataList);
                }

                //短信消息
                if (("VIP3".equalsIgnoreCase(userBO.getVipLevel())
                        || "VIP4".equalsIgnoreCase(userBO.getVipLevel()))
                        && StringUtils.isNotEmpty(userBO.getPhone())) {
                    String vdxMsg = MessageConstant.SBQXSJMSG.replaceAll("\\{#DATA.DATE\\}", shenqqix);
                    messageSendUtil.sendPhoneMessage(userBO.getPhone(), vdxMsg, accessToken);
                }
            }
            return 1;
        }
    }

    /**
     * 判断会员是否会在三个月之内到期
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
     * @param date
     * @return
     */
    public String getFormat(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
    }
}
