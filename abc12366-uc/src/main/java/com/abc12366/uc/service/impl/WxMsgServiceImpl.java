package com.abc12366.uc.service.impl;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.cszj.config.Scheduler;
import com.abc12366.cszj.mapper.db1.WxMsgMapper;
import com.abc12366.cszj.mapper.db2.WxMsgRoMapper;
import com.abc12366.cszj.model.weixin.bo.message.Article;
import com.abc12366.cszj.model.weixin.bo.message.News;
import com.abc12366.cszj.model.weixin.bo.message.ReturnMsg;
import com.abc12366.cszj.model.weixin.bo.message.WxNews;
import com.abc12366.cszj.model.weixin.bo.template.FileContent;
import com.abc12366.cszj.model.weixin.bo.template.ImgMaterial;
import com.abc12366.cszj.service.IWxMsgService;
import com.abc12366.cszj.util.wx.MsgMap;
import com.abc12366.cszj.util.wx.WechatUrl;
import com.abc12366.cszj.util.wx.WxConnectFactory;


/**
 * 
 * @author zhushuai 2017-7-27
 * 
 */
@Service
public class WxMsgServiceImpl implements IWxMsgService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WxMsgServiceImpl.class);
	@Autowired
	private WxMsgMapper msgMapper;
	
	@Autowired
	private WxMsgRoMapper msgRoMapper;

	@Override
	public ImgMaterial uploadWxImag(FileContent fileContent) {
		Map<String, String> tks = new HashMap<String, String>();
		tks.put("access_token", Scheduler.token.getAccess_token());
		return WxConnectFactory.postFile(WechatUrl.MATERIAL_NEWSIMG, tks, null,
				ImgMaterial.class, fileContent);
	}

	public String exec(HttpServletRequest request) {
		try {
			Map<String, String> map = parseXml(request);
			int msgCode = MsgMap.getMsgType(map.get("MsgType"));
			switch (msgCode) {
			case 0://文本
				ReturnMsg keymsg =getReMsgOneBykeyString(map.get("Content"));
				if(keymsg!=null){
					return keymsg.toWxXml(map.get("ToUserName"), map.get("FromUserName"), System.currentTimeMillis()); 
				}
			case 1://图片消息
			case 2://语音
			case 3://视频
			case 4://小视频				
			case 5://位置	
			case 6://链接
				ReturnMsg remsg =getReMsgOneBysetting("1");
				return remsg.toWxXml(map.get("ToUserName"), map.get("FromUserName"), System.currentTimeMillis());
			case 7:
				int eventCode = MsgMap.getEventType(map.get("Event"));
				switch (eventCode) {
				case 0://关注
					ReturnMsg newmsg =getReMsgOneBysetting("0");
					return newmsg.toWxXml(map.get("ToUserName"), map.get("FromUserName"), System.currentTimeMillis());
				case 1://取消关注
					
				case 2:
				case 3:
				case 4:
					break;
				case 5:
					break;
				}
				break;
			}
		} catch (Exception e) {
			LOGGER.error("解析微信消息失败:", e);
		}
		return null;
	}

	private ReturnMsg getReMsgOneBykeyString(String key) {
		try {
            return msgRoMapper.getReMsgOneBysetting(key);
        } catch (Exception e) {
            LOGGER.error("查询单个关键字类型信息失败：{}", e);
            throw null;
        }
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		inputStream.close();
		inputStream = null;

		return map;
	}

	@Override
	public WxNews add_news(WxNews news) {
		Map<String, String> tks = new HashMap<String, String>();
		tks.put("access_token", Scheduler.token.getAccess_token());
		return WxConnectFactory.post(WechatUrl.MATERIAL_ADDNEWS, tks, news, WxNews.class);
	}

	@Override
	public ImgMaterial add_img(FileContent fileContent) {
		Map<String, String> tks = new HashMap<String, String>();
		tks.put("access_token", Scheduler.token.getAccess_token());
		tks.put("type", "image");
		return WxConnectFactory.postFile(WechatUrl.MATERIAL_ADDMATE, tks, null,
				ImgMaterial.class, fileContent);
	}

	
	@Transactional("db1TxManager")
	public News insertNews(News news) {
		Timestamp now = new Timestamp(new Date().getTime());
		String msgId = Utils.uuid();
		news.setId(msgId);
		news.setCreatDate(now);
		msgMapper.insertNews(news);

		for (Article article : news.getArticles()) {
			article.setNewsId(msgId);
			msgMapper.insertArticle(article);
		}
		return news;
	}

	@Override
	@Transactional("db1TxManager")
	public News updateNews(News news) {
		Timestamp now = new Timestamp(new Date().getTime());
		news.setLastUpdate(now);
		int update=msgMapper.updateNews(news);
        if(update != 1){
            LOGGER.info("{修改图文消息失败}", update);
            throw new ServiceException(4421);
        }else{
        	msgMapper.deleteArticle(news.getId());
    		for (Article article : news.getArticles()) {
    			article.setNewsId(news.getId());
    			msgMapper.insertArticle(article);
    		}
        }
		return news;
	}

	@Override
	public List<News> getWxnews(News news) {
		return msgRoMapper.getNews(news);
	}

	@Override
	public ReturnMsg insertReNews(ReturnMsg returnMsg) {
		if(!"2".equals(returnMsg.getSetting())){
			ReturnMsg	newmsg = msgRoMapper.getReMsgOneBysetting(returnMsg.getSetting());
			if(newmsg!=null){
				LOGGER.info("{修改自动回复消息失败:被添加回复或者自动回复只能存在一条记录}", returnMsg.getSetting());
	            throw new ServiceException(4421);
			}
		}
		Timestamp now = new Timestamp(new Date().getTime());
		String msgId = Utils.uuid();
		returnMsg.setId(msgId);
		returnMsg.setCreateDate(now);
		msgMapper.insertRemsg(returnMsg);
		return returnMsg;
	}

	@Override
	@Transactional("db1TxManager")
	public void deleteNews(String id) {
		msgMapper.deleteArticle(id);
		msgMapper.deleteNews(id);
	}

	@Override
	@Transactional("db1TxManager")
	public ReturnMsg updateReMsg(ReturnMsg returnMsg) {
		Timestamp now = new Timestamp(new Date().getTime());
		returnMsg.setLastUpdate(now);
		int update=msgMapper.updateRemsg(returnMsg);
        if(update != 1){
            LOGGER.info("{修改自动回复消息失败}", update);
            throw new ServiceException(4421);
        }
		return returnMsg;
	}

	@Override
	public ReturnMsg getReMsgOneBysetting(String setting) {
		ReturnMsg newmsg = new ReturnMsg();
        try {
            LOGGER.info("查询单个类型自动回复信息:{}", setting);
            newmsg = msgRoMapper.getReMsgOneBysetting(setting);
        } catch (Exception e) {
            LOGGER.error("查询单个类型自动回复信息：{}", e);
            throw new ServiceException(4234);
        }
        return newmsg;
	}

}
