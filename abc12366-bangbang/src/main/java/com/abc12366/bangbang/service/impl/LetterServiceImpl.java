package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.LetterMapper;
import com.abc12366.bangbang.mapper.db2.LetterRoMapper;
import com.abc12366.bangbang.model.Letter;
import com.abc12366.bangbang.model.bo.LetterBO;
import com.abc12366.bangbang.model.bo.LetterInsertBO;
import com.abc12366.bangbang.model.bo.LetterListBO;
import com.abc12366.bangbang.service.LetterService;
import com.abc12366.bangbang.util.BangbangRestTemplateUtil;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Properties;
import com.abc12366.gateway.util.Utils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-24
 * Time: 17:41
 */
@Service
public class LetterServiceImpl implements LetterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LetterServiceImpl.class);

    private static Properties properties = new Properties("application.properties");

    @Autowired
    private LetterMapper letterMapper;

    @Autowired
    private LetterRoMapper letterRoMapper;

    @Autowired
    private BangbangRestTemplateUtil bangbangRestTemplateUtil;

    @Override
    public LetterBO send(String fromId, String toId, LetterInsertBO letterInsertBO) {
        LOGGER.info("{}:{}", fromId, toId);
        Letter letter = new Letter();
        Date date = new Date();
        letter.setId(Utils.uuid());
        letter.setFromId(fromId);
        letter.setToId(toId);
        letter.setStatus("1");
        letter.setContent(letterInsertBO.getContent());
        letter.setCreateTime(date);
        letter.setLastUpdate(date);

        int result = letterMapper.insert(letter);
        if (result < 1) {
            throw new ServiceException(4820);
        }
        LetterBO letterBO = new LetterBO();
        BeanUtils.copyProperties(letter, letterBO);
        return letterBO;
    }

    @Override
    public LetterListBO selectList(HttpServletRequest request) throws IOException {
        LOGGER.info("{}", request);
        String toId = (String) request.getAttribute(Constant.USER_ID);
        if (toId == null || toId.trim().equals("")) {
            throw new ServiceException(4193);
        }
        //老套路：查库
        //return letterRoMapper.selectList(toId);

        //新套路，调message接口
        String url = properties.getValue("chabc.soa.url") + "/message/user";
        if (!StringUtils.isEmpty(request.getAttribute("page"))) {
            url += "?page=" + request.getAttribute("page");
            if (!StringUtils.isEmpty(request.getAttribute("size"))) {
                url += "&size=" + request.getAttribute("size");
            }
        }
        String responseStr;
        try {
            responseStr = bangbangRestTemplateUtil.send(url, HttpMethod.GET, request);
        } catch (Exception e) {
            throw new ServiceException(4821);
        }
        LetterListBO response = null;
        if (!StringUtils.isEmpty(responseStr)) {
            response = JSON.parseObject(responseStr, LetterListBO.class);
        }
        return response;
    }

    @Override
    public void read(String id) {
        LOGGER.info("{}", id);
        int result = letterMapper.read(id);
        if (result < 1) {
            throw new ServiceException(4102);
        }
    }

    @Override
    public void delete(String id) {
        LOGGER.info("{}", id);
        int result = letterMapper.delete(id);
        if (result < 1) {
            throw new ServiceException(4103);
        }
    }
}
