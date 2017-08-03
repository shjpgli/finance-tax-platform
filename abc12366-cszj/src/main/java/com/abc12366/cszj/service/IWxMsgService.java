package com.abc12366.cszj.service;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.abc12366.cszj.model.weixin.bo.message.FileContent;
import com.abc12366.cszj.model.weixin.bo.message.ImgMaterial;
import com.abc12366.cszj.model.weixin.bo.message.News;
import com.abc12366.cszj.model.weixin.bo.message.ReturnMsg;
import com.abc12366.cszj.model.weixin.bo.message.WxNews;
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

	List<ReturnMsg> selectkeyList(ReturnMsg returnMsg, int page, int size);

	void deleteWxremsg(String id);

	ReturnMsg selectOneWxremsg(String id);

	News selectOne(String id);
	
	
	

}
