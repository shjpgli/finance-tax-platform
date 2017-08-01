package com.abc12366.cszj.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.abc12366.cszj.model.weixin.bo.message.News;
import com.abc12366.cszj.model.weixin.bo.template.FileContent;
import com.abc12366.cszj.model.weixin.bo.template.ImgMaterial;
/**
 * 
 * @author zhushuai 2017-7-27
 *
 */
public interface IWxMsgService {

	String exec(HttpServletRequest request);
	
	ImgMaterial uploadWxImag(FileContent fileContent);

	News insertNews(News news);

	News updateNews(News news);

	List<News> getWxnews(News news);
	
	
	

}
