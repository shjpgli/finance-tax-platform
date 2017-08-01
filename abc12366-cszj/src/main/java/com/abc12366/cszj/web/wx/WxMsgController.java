package com.abc12366.cszj.web.wx;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abc12366.common.util.Utils;
import com.abc12366.cszj.model.weixin.bo.message.News;
import com.abc12366.cszj.model.weixin.bo.template.FileContent;
import com.abc12366.cszj.model.weixin.bo.template.ImgMaterial;
import com.abc12366.cszj.service.IWxMsgService;


/**
 * 微信消息管理
 * @author zhushuai 2017-7-31
 *
 */
@Controller
public class WxMsgController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WxMsgController.class);
	
	@Autowired
	private IWxMsgService iWxMsgService;
    
	//微信图片素材接口
	@PostMapping("/uploadWxImag")
	public @ResponseBody ImgMaterial uploadWxImag(@Valid @RequestBody FileContent fileContent){
		return iWxMsgService.uploadWxImag(fileContent);
	}
	
	//图文消息创建
	@SuppressWarnings("rawtypes")
	@PostMapping("/wxnews/creat")
	public ResponseEntity wxnewsCreat(@Valid @RequestBody News news){
		 LOGGER.info("{}", news);

		 News v = iWxMsgService.insertNews(news);

	     ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));
	     LOGGER.info("{}", responseEntity);
	     return responseEntity;
	}
	
	//图文消息修改
	@SuppressWarnings("rawtypes")
	@PutMapping("/wxnews/{id}")
	public ResponseEntity  wxnewsEdit(@PathVariable("id")String id,@Valid @RequestBody News news ){
		LOGGER.info("{},{}", id, news);

		news.setId(id);
		News v = iWxMsgService.updateNews(news);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
	}
	
	//图文消息
	@SuppressWarnings("rawtypes")
	@GetMapping("/wxnews/get")
	public ResponseEntity getWxnews(News news){
		List<News> selectNews = iWxMsgService.getWxnews(news);
		ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", selectNews));
		LOGGER.info("{}", responseEntity);
		return responseEntity;
	}
	
}
