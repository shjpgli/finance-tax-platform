package com.abc12366.uc.job.dzsb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.job.DzsbJob;
import com.abc12366.uc.model.job.DzsbTime;
import com.abc12366.uc.model.job.DzsbXxInfo;
import com.abc12366.uc.service.IDzsbTimeService;
import com.abc12366.uc.service.IMsgSendService;
import com.abc12366.uc.service.UserService;
import com.abc12366.uc.util.MessageConstant;
import com.abc12366.uc.webservice.AcceptClient;

/**
 * 申报信息提醒定时任务
 * @author zhushuai 2017-11-9
 *
 */
public class SbxxRemindJob implements Job{
	private static final Logger LOGGER = LoggerFactory.getLogger(SbxxRemindJob.class);
	
	private static String YWLX="NOTIFY_SBXX";
	
	private static AcceptClient client;
	
	private static IDzsbTimeService dzsbTimeService;
	
	private static IMsgSendService msgSendService;
	
    private static UserService userService;
	
	static{
		client=(AcceptClient) SpringCtxHolder.getApplicationContext().getBean("client");
		dzsbTimeService=(IDzsbTimeService) SpringCtxHolder.getApplicationContext().getBean("dzsbTimeService");
		msgSendService=(IMsgSendService) SpringCtxHolder.getApplicationContext().getBean("msgSendService");
		userService = (UserService) SpringCtxHolder.getApplicationContext().getBean("userService");	
	}
	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		
		
		while(true){
			DzsbTime dzsbTime=dzsbTimeService.select(YWLX);
			boolean isFirst=false;
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
	        map.put("maxcount", MessageConstant.DZSBQNUM);
	        DzsbJob job=client.processYw(map);
	        if("00000000".equals(job.getRescode())){//查询失败
	        	List<DzsbXxInfo> dzsbXxInfos= job.getDataList();
	        	if(dzsbXxInfos!=null && dzsbXxInfos.size()>0){//查询到数据
	        		//处理数据
	        		for(int i=0;i<dzsbXxInfos.size();i++){
	        			DzsbXxInfo dzsbXxInfo=dzsbXxInfos.get(i);
	        			List<User> users=userService.findByHngsNsrsbh(dzsbXxInfo.getNsrsbh());
	        			if(users!=null && users.size()>0){
	        				
	        				String sysMsg="您的纳税企业（"+dzsbXxInfo.getNsrsbh().substring(0,6)+"****** "+dzsbXxInfo.getNsrmc()+"）于"+dzsbXxInfo.getWcrq()+"申报税种："+dzsbXxInfo.getSzmc()+"，申报结果：成功，此信息为财税专家电子报税业务提醒信息，不作为实际申报结果凭证，如有疑议请及时查询申报结果。";
	        				
	        				String dxmsg="您的企业（"+dzsbXxInfo.getNsrmc()+"）于"+dzsbXxInfo.getWcrq()+"申报税种："+dzsbXxInfo.getSzmc()+"，申报结果：成功，此信息不作为实际申报结果凭证";
	        				
	        				Map<String, String> dataList = new HashMap<String, String>();
	        				dataList.put("first", "财税专家会员提醒，您的纳税企业（"+dzsbXxInfo.getNsrmc()+"）申报结果信息如下：");
	                        dataList.put("remark", "此信息为财税专家会员用户电子申报事项提醒信息，不作为实际申报结果凭证，如有疑议请及时查询申报结果。");
	                        dataList.put("keyword1", dzsbXxInfo.getNsrsbh().substring(0,6)+"****** ");
	                        dataList.put("keyword2", dzsbXxInfo.getSzmc());
	                        dataList.put("keyword3", dzsbXxInfo.getSkssqq()+"至"+dzsbXxInfo.getSkssqz());
	                        dataList.put("keyword4", "申报成功");
	                        dataList.put("keyword4Color", "#00DB00");
	                        dataList.put("keyword5", dzsbXxInfo.getWcrq());
	        				                                             
	        				msgSendService.sendMsg(users.get(i), sysMsg, "YeYkFYIhmbSKdlCTmY8XF5qrR6o6ykYYPLN41DC3mOQ", dataList, dxmsg);
	        			}else{
	        				LOGGER.info("查询当前录入日期["+dzsbTime.getLasttime()+"]申报信息，未查到相关用户信息,纳税人名称:"+dzsbXxInfo.getNsrmc());
	        			}
	        		
	        		}
	        		LOGGER.info("查询当前录入日期["+dzsbTime.getLasttime()+"]申报信息，最后一笔日期:"+dzsbXxInfos.get(dzsbXxInfos.size()-1).getLrrq());
	        		dzsbTime.setLasttime(dzsbXxInfos.get(dzsbXxInfos.size()-1).getLrrq());
                    if(isFirst){//第一次插入数据
                    	dzsbTimeService.insert(dzsbTime);
	        		}else{//非第一次更新数据
	        			dzsbTimeService.update(dzsbTime);
	        		}
	        		if(!job.getIsExistData() 
	        				|| dzsbXxInfos.size()<Integer.valueOf(MessageConstant.DZSBQNUM)){//没有数据了
	        			LOGGER.info("操作当前录入日期申报信息:全部处理完毕");
	        			break;
	        		}
	        	}else{
	        		dzsbTimeService.insert(dzsbTime);
	        		break;
	        	}
	        }else{
	        	LOGGER.info("查询当前录入日期["+dzsbTime.getLasttime()+"]申报信息异常:"+job.getMessage());
	        }
		}	
	}
	
	public static void main(String[] args) {
		
        System.out.println("123456789".substring(0,6));
	}

}
