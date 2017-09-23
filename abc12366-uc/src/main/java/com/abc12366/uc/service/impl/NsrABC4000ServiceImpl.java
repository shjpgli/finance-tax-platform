package com.abc12366.uc.service.impl;

import com.abc12366.uc.mapper.db1.UserBindMapper;
import com.abc12366.uc.mapper.db2.UserBindRoMapper;
import com.abc12366.uc.model.UserDzsb;
import com.abc12366.uc.model.abc4000.*;
import com.abc12366.uc.service.NsrABC4000Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-10
 * Time: 9:39
 */
@Service
public class NsrABC4000ServiceImpl implements NsrABC4000Service {
    private static final Logger LOGGER = LoggerFactory.getLogger(NsrABC4000ServiceImpl.class);

    @Autowired
    private UserBindRoMapper userBindRoMapper;

    @Autowired
    private UserBindMapper userBindMapper;

    @Override
    public ResponseForAbc4000 selectList(String userId) {
        LOGGER.info("{}", userId);
        List<NSRXX> nsrxxList = userBindRoMapper.selectListByUserId(userId);
        Date date = new Date();
        for (int i = 0; i < nsrxxList.size(); i++) {
            nsrxxList.get(i).setDqxtsj(date);
        }
        ResponseForAbc4000 response = new ResponseForAbc4000();
        response.setT_nsrxx(nsrxxList);
        response.setSuccess(true);
        response.setCode("2000");
        response.setMessage("成功");
        return response;
    }

    @Transactional("db1TxManager")
    @Override
    public ResponseForAbc4000Simple update(ABC4000CallbackBO data) {
        LOGGER.info("{}", data);
        int result = 0;
        for (NSRXXBO nsrxxbo : data.getT_nsrxx()) {
            UserDzsb userDzsb = new UserDzsb();
            userDzsb.setStatus(true);
            userDzsb.setUserId(data.getUserid());
            userDzsb.setNsrsbh(nsrxxbo.getY_nsrsbh());
            userDzsb.setShxydm(nsrxxbo.getShxydm());
            userDzsb.setDjxh(nsrxxbo.getDjxh());
            userDzsb.setSwjgMc(nsrxxbo.getSwjgmc());
            userDzsb.setSwjgDm(nsrxxbo.getSwjgdm());
            userDzsb.setLastUpdate(new Date());
            userDzsb.setExpireTime(nsrxxbo.getRjdqr());
            userDzsb.setExpandExpireTime(nsrxxbo.getYqdqr());
            userDzsb.setFrmc(nsrxxbo.getFrmc());
            userDzsb.setFrzjh(nsrxxbo.getFrzjh());
            result += userBindMapper.update(userDzsb);
        }

        ResponseForAbc4000Simple response = new ResponseForAbc4000Simple();
        if (result < 1) {
            response.setSuccess(false);
            response.setCode("5000");
            response.setMessage("回调财税专家接口未更新数据！");
        } else {
            response.setSuccess(true);
            response.setCode("2000");
            response.setMessage("回调财税专家接口成功！");
        }
        return response;
    }
}
