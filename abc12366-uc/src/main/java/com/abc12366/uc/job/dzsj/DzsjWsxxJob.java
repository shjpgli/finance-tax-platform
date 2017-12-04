package com.abc12366.uc.job.dzsj;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import sun.misc.BASE64Decoder;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.service.AppService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.job.DzsjWsxx;
import com.abc12366.uc.model.job.WsxxInfo;
import com.abc12366.uc.service.IMsgSendService;
import com.abc12366.uc.service.UserService;
import com.alibaba.fastjson.JSONObject;

/**
 * 电子税局文书信息消息提醒
 * @author zhushuai 2017-11-10
 *
 */
@SuppressWarnings("deprecation")
public class DzsjWsxxJob implements StatefulJob{
	private static final Logger LOGGER = LoggerFactory.getLogger(DzsjWsxxJob.class);
	
	private final String QCOUNT="200";//查询数量
	
	@Autowired
	private  AppService appService;
	
	@Autowired
    private  UserService userService;
    
    @Autowired
    private  IMsgSendService msgSendService;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		LOGGER.info("--------开始执行[电子税局文书信息提醒]定时任务----------");
		while(true){
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.add(Constant.APP_TOKEN_HEAD,  appService.selectByName("abc12366-admin").getAccessToken());
				headers.add(Constant.VERSION_HEAD,Constant.VERSION_1);
				String url = SpringCtxHolder.getProperty("abc12366.message.url")+"/hngs/get?api="+Base64.getEncoder().encodeToString(("/fw/xxtx/ws/list?sl="+QCOUNT).getBytes());
				HttpEntity requestEntity = new HttpEntity(null, headers);
				RestTemplate restTemplate=new RestTemplate();
				ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
				
				DzsjWsxx dzsjWsxx=JSONObject.parseObject(responseEntity.getBody().toString(), DzsjWsxx.class);
				LOGGER.info("获取[电子税局文书信息提醒]数据:"+JSONObject.toJSONString(dzsjWsxx));
				if("000".equals(dzsjWsxx.getCode())){//获取数据成功
					List<WsxxInfo> list=dzsjWsxx.getList();
					if(list!=null && list.size()>0){
						for(int i=0;i<list.size();i++){
							WsxxInfo wsxxInfo=list.get(i);
							User user=userService.selectUser(new String(new BASE64Decoder().decodeBuffer(wsxxInfo.getCszjUserid())));
							if(user!=null){
								String sysMsg="财税专家用户提醒，您的企业（"+wsxxInfo.getNsrmc()+"）涉税业务办理："+wsxxInfo.getWszlmc()+"，办理结果："+wsxxInfo.getWsztmc()+"，办理时间："+wsxxInfo.getLrrq()+"，此信息为财税专家涉税业务提醒信息，不作为实际业务办理结果凭证，如有疑议请及时登录系统查询业务办理结果。";
								
								String dxmsg="您的企业（"+wsxxInfo.getNsrmc()+"）涉税业务办理："+wsxxInfo.getWszlmc()+" "+wsxxInfo.getWsztmc()+"，办理时间："+wsxxInfo.getLrrq()+"，此信息不作为实际业务办理结果凭证";
								
								Map<String, String> dataList = new HashMap<String, String>();
								dataList.put("first", "财税专家会员提醒，您的企业（"+wsxxInfo.getNsrmc()+"）涉税业务办理进展情况如下：");
				                dataList.put("remark", "此信息为财税专家涉税业务提醒信息，不作为实际业务办理结果凭证，如有疑议请及时登录系统查询业务办理结果。");
				                dataList.put("keyword1", wsxxInfo.getWszlmc()+"  "+wsxxInfo.getWsztmc());
				                dataList.put("keyword1Color", "#00DB00");
				                dataList.put("keyword2", wsxxInfo.getLrrq());
				                
				                msgSendService.sendMsg(user, sysMsg,"", "x0BXoANGCPnCb4GoA_Lm2hEPTJrdmW0QCUUvtjK5QRQ", dataList, dxmsg);
							}
							
						}
						if(dzsjWsxx.getSl()==0){//没有剩余消息，退出循环
							break;
						}
					}else{//没有查询到信息 退出循环
						break;
					}
				}else{//获取数据异常
					LOGGER.info("电子税局获取文书申请信息异常："+dzsjWsxx.getMsg());
					break;
				}
			}  catch (Exception e) {
				LOGGER.error("电子税局获取文书申请信息异常：",e);
				break;
			}   
		}
		LOGGER.info("--------结束执行[电子税局文书信息提醒]定时任务----------");
	}

}
