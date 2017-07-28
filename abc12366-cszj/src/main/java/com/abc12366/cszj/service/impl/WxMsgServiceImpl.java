package com.abc12366.cszj.service.impl;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.abc12366.cszj.service.IWxMsgService;
import com.abc12366.cszj.util.wx.MsgMap;
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
public class WxMsgServiceImpl implements IWxMsgService{
	private static final Logger LOGGER = LoggerFactory.getLogger(WxMsgServiceImpl.class);

	public String exec(HttpServletRequest request) {
		try {
			Map<String, String> map=parseXml(request);
			int msgCode = MsgMap.getMsgType(map.get("MsgType"));
			switch(msgCode){
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
		        	int eventCode=MsgMap.getEventType(map.get("Event"));
		        	switch(eventCode){
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
	private  Map<String, String> parseXml(HttpServletRequest request)
            throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList){
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

}
