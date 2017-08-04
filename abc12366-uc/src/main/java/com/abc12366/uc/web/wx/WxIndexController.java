package com.abc12366.uc.web.wx;

import com.abc12366.uc.service.IWxMsgService;
import com.abc12366.uc.util.wx.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信主接入口
 * @author zhushuai 2017-7-31
 *
 */
@Controller
public class WxIndexController {
	@Autowired
    private IWxMsgService iWxMsgService;

    @RequestMapping("/abc/wechat")
    public @ResponseBody String wechatVlidate(Model model, HttpServletRequest request,HttpServletResponse response){
    	 boolean isGet = request.getMethod().toLowerCase().equals("get");
    	 if(isGet){
    		 String signature = request.getParameter("signature");
             // 时间戳
             String timestamp = request.getParameter("timestamp");
             // 随机数
             String nonce = request.getParameter("nonce");
             // 随机字符串
             String echostr = request.getParameter("echostr");
             if(signature != null && SignUtil.checkSignature(signature, timestamp, nonce)){
            	 return echostr;
             }else{
            	 return null;
             }
    	 }else{
    		 return iWxMsgService.exec(request);
    	 }
    }
}
