package com.abc12366.cszj.service.impl;

import java.io.InputStream;
import java.io.Writer;
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

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.cszj.config.Scheduler;
import com.abc12366.cszj.mapper.db1.WxMsgMapper;
import com.abc12366.cszj.mapper.db2.WxMsgRoMapper;
import com.abc12366.cszj.model.weixin.bo.message.Article;
import com.abc12366.cszj.model.weixin.bo.message.News;
import com.abc12366.cszj.model.weixin.bo.template.FileContent;
import com.abc12366.cszj.model.weixin.bo.template.ImgMaterial;
import com.abc12366.cszj.service.IWxMsgService;
import com.abc12366.cszj.util.wx.MsgMap;
import com.abc12366.cszj.util.wx.WechatUrl;
import com.abc12366.cszj.util.wx.WxConnectFactory;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

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
		tks.put("type", "image");
		return WxConnectFactory.postFile(WechatUrl.MATERIAL_ADDMATE, tks, null,
				ImgMaterial.class, fileContent);
	}

	public String exec(HttpServletRequest request) {
		try {
			Map<String, String> map = parseXml(request);
			int msgCode = MsgMap.getMsgType(map.get("MsgType"));
			switch (msgCode) {
			case 0:

			case 1:
			case 2:
			case 3:
			case 4:
				break;
			case 5:
				break;
			case 6:
			case 7:
				int eventCode = MsgMap.getEventType(map.get("Event"));
				switch (eventCode) {
				case 0:

				case 1:
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

	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	@Override
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
            if (update != 1){
                LOGGER.info("{修改图文消息失败}", update);
                throw new ServiceException(4421);
            }
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

}
