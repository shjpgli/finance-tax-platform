package com.abc12366.uc.service;


import com.abc12366.cszj.model.weixin.bo.message.News;
import com.abc12366.cszj.model.weixin.bo.message.ReturnMsg;
import com.abc12366.cszj.model.weixin.bo.message.WxNews;
import com.abc12366.cszj.model.weixin.bo.template.FileContent;
import com.abc12366.cszj.model.weixin.bo.template.ImgMaterial;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 
 * @author zhushuai 2017-7-27
 *
 */
public interface IWxMsgService {

	String exec(HttpServletRequest request);
	
	ImgMaterial uploadWxImag(FileContent fileContent);
	
	ImgMaterial add_img(FileContent fileContent);

	WxNews add_news(WxNews news);

	News insertNews(News news);

	News updateNews(News news);

	List<News> getWxnews(News news);

	ReturnMsg insertReNews(ReturnMsg returnMsg);

	void deleteNews(String id);

	ReturnMsg updateReMsg(ReturnMsg returnMsg);

	ReturnMsg getReMsgOneBysetting(String setting);
	
	
	

}
