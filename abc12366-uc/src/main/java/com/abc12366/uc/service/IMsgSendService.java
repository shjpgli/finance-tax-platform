package com.abc12366.uc.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.abc12366.uc.model.MessageSendBo;
import com.abc12366.uc.model.User;

/**
 * 消息发送接口
 * @author zhushuai 2017-11-9
 *
 */
public interface IMsgSendService {

	/**
	 * 系统消息发送接口
	 * @param user //用户信息
	 * @param sysMsg //系统消息内容
	 * @param templateid //微信模板消息ID
	 * @param dataList //模板消息内容
	 * @param dxmsg //短信消息
	 */
	public void sendMsg(User user,String sysMsg,String  templateid,Map<String, String> dataList,String dxmsg);
	
	/**
	 * 前段发送消息
	 * @param messageSendBo 消息实体
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ResponseEntity sendMsgByQq(MessageSendBo messageSendBo);

	/**
	 * 前段批量发送消息
	 * @param sendBo 消息实体
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ResponseEntity sendByQdbatch(MessageSendBo sendBo);
}
