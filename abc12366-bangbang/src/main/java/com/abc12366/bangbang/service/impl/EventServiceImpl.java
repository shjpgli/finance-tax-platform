package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.EventMapper;
import com.abc12366.bangbang.model.event.SingleEventBo;
import com.abc12366.bangbang.model.event.SingleEventListBo;
import com.abc12366.bangbang.model.event.SingleEventOneBo;
import com.abc12366.bangbang.service.EventService;
import com.abc12366.bangbang.util.BangbangRestTemplateUtil;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Properties;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by stuy on 2017/9/13.
 */
@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    private static Properties properties = new Properties("application.properties");

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private BangbangRestTemplateUtil bangbangRestTemplateUtil;

    @Override
    public SingleEventBo singleEvent(HttpServletRequest request) {
        SingleEventOneBo dataList= null;
        try {
            String url = properties.getValue("chabc.soa.url") + "/cms/bangbang/event/singleevent";
            //String url = "http://118.118.116.132:9400/cms/bangbang/event/singleevent";
            String str = bangbangRestTemplateUtil.send(url, HttpMethod.GET, request);
            dataList = JSON.parseObject(str,SingleEventOneBo.class);
        } catch (IOException e) {
            throw new ServiceException(4821);
        }
        return dataList.getData();
    }

    @Override
    public List<SingleEventBo> singleEventList(HttpServletRequest request) {
        //String url = "http://118.118.116.132:9400/cms/bangbang/event/singleeventlist";
        SingleEventListBo dataList= null;
        try {
            String url = properties.getValue("chabc.soa.url") + "/cms/bangbang/event/singleeventlist";
            String str = bangbangRestTemplateUtil.send(url, HttpMethod.GET, request);
            dataList = JSON.parseObject(str,SingleEventListBo.class);
        } catch (IOException e) {
            throw new ServiceException(4821);
        }
        return dataList.getDataList();
    }
}
