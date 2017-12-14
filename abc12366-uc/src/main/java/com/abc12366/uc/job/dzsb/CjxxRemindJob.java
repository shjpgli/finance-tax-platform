package com.abc12366.uc.job.dzsb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abc12366.gateway.util.Constant;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.job.DzsbJob;
import com.abc12366.uc.model.job.DzsbTime;
import com.abc12366.uc.model.job.DzsbXxInfo;
import com.abc12366.uc.service.IDzsbTimeService;
import com.abc12366.uc.service.IMsgSendService;
import com.abc12366.uc.service.UserService;
import com.abc12366.uc.webservice.AcceptClient;
import com.alibaba.fastjson.JSONObject;

/**
 * 催缴信息提醒
 * @author zhushuai 2017-11-9
 *
 */
@SuppressWarnings("deprecation")
public class CjxxRemindJob implements StatefulJob{
	private static final Logger LOGGER = LoggerFactory.getLogger(CjxxRemindJob.class);
	
	private static String YWLX="NOTIFY_CJXX";
	
	@Autowired
	private AcceptClient client;
	
	@Autowired
    private IDzsbTimeService dzsbTimeService;
	
	@Autowired
	private IMsgSendService msgSendService;
	
	@Autowired
    private UserService userService;
	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		LOGGER.info("--------开始执行[催缴信息提醒]定时任务----------");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		//查询申报期限
		Map<String, String> mapA = new HashMap<>();
		mapA.put("serviceid", "GY01");
		mapA.put("ywid", "NOTIFY_SBQX");
        DzsbJob jobA=client.processYw(mapA);
        if("00000000".equals(jobA.getRescode())){
        	List<DzsbXxInfo> dzsbXxInfosA= jobA.getDataList();
        	if(dzsbXxInfosA!=null && dzsbXxInfosA.size()>0){
        		String sbxq=dzsbXxInfosA.get(0).getSbqx();
        		if(isTwoDayBefore(sbxq)){
        			DzsbTime dzsbTime=dzsbTimeService.select(YWLX);
    				boolean isFirst=false;
        			while(true){
        				if(dzsbTime==null){//查询不到数据默认设置当月第一天
        					dzsbTime=new DzsbTime();
        					Calendar c = Calendar.getInstance();    
        			        c.set(Calendar.DAY_OF_MONTH,1);
        			        dzsbTime.setYwlx(YWLX);
        			        dzsbTime.setLasttime(format.format(c.getTime())+" 00:00:00");
        			        isFirst=true;
        				}
        				
        				LOGGER.info("当前录入日期:"+dzsbTime.getLasttime());
        				
        				Map<String, String> map = new HashMap<>();
        		        map.put("serviceid", "GY01");
        		        map.put("ywid", YWLX);
        		        map.put("lrrq", dzsbTime.getLasttime());
        		        map.put("maxcount", Constant.DZSBQNUM);
        		        long d1=System.currentTimeMillis();
        		        DzsbJob job=client.processYw(map);
        		        long d2=System.currentTimeMillis();
        		        LOGGER.info("当前录入日期:"+dzsbTime.getLasttime()+",请求耗时:"+(d2-d1));
        		        if("00000000".equals(job.getRescode())){//查询成功
        		        	List<DzsbXxInfo> dzsbXxInfos= job.getDataList();
        		        	if(dzsbXxInfos!=null && dzsbXxInfos.size()>0){//查询到数据
        		        		LOGGER.info("获取[催缴信息提醒]数据:"+JSONObject.toJSONString(job.getDataList()));
        		        		//处理数据
        		        		for(int i=0;i<dzsbXxInfos.size();i++){
        		        			
        		        			DzsbXxInfo dzsbXxInfo=dzsbXxInfos.get(i);
        		        			
        		        			List<User> users=userService.findByDzsbNsrsbh(dzsbXxInfo.getNsrsbh());
        		        			
        		        			if(users!=null && users.size()>0){
        		        				
        		        				String sysMsg="财税专家用户提醒，您的企业（"+dzsbXxInfo.getNsrsbh().substring(0,6)+"****** "+dzsbXxInfo.getNsrmc()+"）本月还有未申报的报表种类："+dzsbXxInfo.getSzmc()+"，申报期限："+sbxq+"，实际申报种类以税务局核定信息为准，如已申报请忽略！";
        		        				
        		        				String dxmsg="您的企业（"+dzsbXxInfo.getNsrmc()+"）本月还有未申报的报表种类："+dzsbXxInfo.getSzmc()+"，申报期限："+sbxq+"，实际申报种类以税务局核定信息为准，如已申报请忽略";
        		        				
        		        				Map<String, String> dataList = new HashMap<String, String>();
        		        				dataList.put("first", "财税专家会员提醒，您的企业（"+dzsbXxInfo.getNsrsbh().substring(0,6)+"****** "+dzsbXxInfo.getNsrmc()+"）本月还有未申报的报表种类如下");
        		                        dataList.put("remark", "此信息仅为提醒，实际申报种类以税务局核定信息为准，如已申报请忽略！");
        		                        dataList.put("keyword1", dzsbXxInfo.getSzmc());
        		                        dataList.put("keyword2", dzsbXxInfo.getSkssqq()+"至"+dzsbXxInfo.getSkssqz());
        		                        dataList.put("keyword3", sbxq);
        		                        dataList.put("keyword3Color", "#00DB00");
        		                        
        		                        for(int j=0;j<users.size();j++){
        		                        	msgSendService.sendMsg(users.get(j), sysMsg,"", "eltMyMTpahpHEqH0uV_xVw-FuMAwdDlq_kLUkDynM2g", dataList, dxmsg);
        		                        }
        		        			}else{
        		        				LOGGER.info("查询当前录入日期["+dzsbTime.getLasttime()+"]催缴信息，未查到相关用户信息,纳税人名称:"+dzsbXxInfo.getNsrmc());
        		        			}
        		        		
        		        		}
        		        		LOGGER.info("查询当前录入日期["+dzsbTime.getLasttime()+"]催缴信息，最后一笔日期:"+dzsbXxInfos.get(dzsbXxInfos.size()-1).getLrrq());
        		        		dzsbTime.setLasttime(dzsbXxInfos.get(dzsbXxInfos.size()-1).getLrrq());
        	                    
        		        		if(!job.getIsExistData() 
        		        				|| dzsbXxInfos.size()<Integer.valueOf(Constant.DZSBQNUM)){//没有数据了
        		        			LOGGER.info("操作当前录入日期催缴信息:全部处理完毕");
        		        			break;
        		        		}
        		        	}else{
        		        		//dzsbTimeService.insert(dzsbTime);
        		        		break;
        		        	}
        		        }else{//查询失败
        		        	LOGGER.info("查询当前录入日期["+dzsbTime.getLasttime()+"]催缴信息异常:"+job.getMessage());
        		        	break;
        		        }
        			}	
        			if(isFirst){//第一次插入数据
                    	dzsbTimeService.insert(dzsbTime);
	        		}else{//非第一次更新数据
	        			dzsbTimeService.update(dzsbTime);
	        		}
        		}
        	}
        }else{
        	LOGGER.info("查询本月申报期限信息异常:"+jobA.getMessage());
        }
        LOGGER.info("--------结束执行[催缴信息提醒]定时任务----------");
	} 
	
	/**
	 * 判断今天是否是申报期限结束前前两天
	 * @param yearStr
	 * @return
	 */
    public static boolean isTwoDayBefore(String yearStr) {
    	try {
			Date date=new SimpleDateFormat("yyyy-MM-dd").parse(yearStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			String day2=new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			String day1=new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			String today=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			return today.equals(day2) || today.equals(day1);
		} catch (ParseException e) {
			LOGGER.error("格式化申报期限异常:",e);
			return false;
		}
	}

}
