package com.abc12366.uc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc12366.gateway.service.AppService;
import com.abc12366.uc.mapper.db2.VipPrivilegeLevelRoMapper;
import com.abc12366.uc.model.Message;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.VipPrivilegeLevelBO;
import com.abc12366.uc.service.IMsgSendService;
import com.abc12366.uc.service.IWxTemplateService;
import com.abc12366.uc.util.MessageConstant;
import com.abc12366.uc.util.MessageSendUtil;

@Service("msgSendService")
public class MsgSendServiceImpl implements IMsgSendService{
    
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgSendServiceImpl.class);
	
	@Autowired
    private AppService appService;
	
	@Autowired
    private MessageSendUtil messageSendUtil;

    @Autowired
    private IWxTemplateService templateService;

    @Autowired
    private VipPrivilegeLevelRoMapper vipPrivilegeLevelRoMapper;
    
    
    public void sendMsg(User user,String sysMsg,String  templateid,Map<String, String> dataList,String dxmsg){
    	
    	 VipPrivilegeLevelBO obj = new VipPrivilegeLevelBO();
         obj.setLevelId(user.getVipLevel());
         obj.setPrivilegeId(MessageConstant.YWTX_CODE);
         VipPrivilegeLevelBO findObj = vipPrivilegeLevelRoMapper.selectLevelIdPrivilegeId(obj);
         
         //获取运营管理系统accessToken
         String accessToken = appService.selectByName("abc12366-admin").getAccessToken();
         LOGGER.info("获取运营管理系统accessToken:" + accessToken);
         
         //查看业务提醒是否启用
         if(findObj != null && findObj.getStatus()){
        	 LOGGER.info("用户:" + user.getId() + ",提醒：" + sysMsg);
        	 
        	 //普通消息
        	 if(sysMsg!=null){
        		 Message message = new Message();
                 message.setBusinessId(user.getId());
                 message.setBusiType(MessageConstant.XTTX);
                 message.setType(MessageConstant.SYS_MESSAGE);
                 message.setContent(sysMsg);
                 message.setUserId(user.getId());
                 messageSendUtil.sendMessage(message, accessToken);
        	 }
             
             
             //微信消息
             if(findObj.getVal2() != null 
            		 && MessageConstant.YWTX_WECHAT.equals(findObj.getVal2())
            		     && StringUtils.isNotEmpty(user.getWxopenid())
            		        && templateid !=null && dataList!=null){
            	 dataList.put("userId", user.getId());
                 dataList.put("openId", user.getWxopenid());
                 templateService.templateSend(templateid, dataList);
             }
             
             //短信消息
             if(findObj.getVal3() != null 
            		 && MessageConstant.YWTX_MESSAGE.equals(findObj.getVal3())
            		    && StringUtils.isNotEmpty(user.getPhone())
            		       && dxmsg!=null){
            	 Map<String,String> maps=new HashMap<String,String>();
                 maps.put("var", dxmsg);
                 List<Map<String,String>> list= new ArrayList<Map<String,String>>();
                 list.add(maps);
                 messageSendUtil.sendPhoneMessage(user.getPhone(),"529", list, accessToken);
             }
         }
    }
    
	 
}
