package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.LetterMapper;
import com.abc12366.bangbang.mapper.db2.LetterRoMapper;
import com.abc12366.bangbang.model.Letter;
import com.abc12366.bangbang.model.bo.LetterBO;
import com.abc12366.bangbang.model.bo.LetterInsertBO;
import com.abc12366.bangbang.model.bo.LetterListBO;
import com.abc12366.bangbang.service.LetterService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private LetterMapper letterMapper;

    @Autowired
    private LetterRoMapper letterRoMapper;

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
    public List<LetterListBO> selectList(HttpServletRequest request) {
        LOGGER.info("{}", request);
        String toId = (String) request.getAttribute(Constant.USER_ID);
        if (toId == null || toId.trim().equals("")) {
            throw new ServiceException(4193);
        }
        return letterRoMapper.selectList(toId);
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
