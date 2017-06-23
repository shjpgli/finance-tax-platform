package com.abc12366.uc.service.impl;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.IpMapper;
import com.abc12366.uc.mapper.db2.IpRoMapper;
import com.abc12366.uc.model.Ip;
import com.abc12366.uc.model.bo.IpQueryResult;
import com.abc12366.uc.service.IpService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-06-22 11:08 AM
 * @since 1.0.0
 */
@Service
public class IpServiceImpl implements IpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpServiceImpl.class);

    // IP地址更新周期为7天
    private final static int UPDATE_CYCLE = 7;

    @Autowired
    private IpMapper ipMapper;

    @Autowired
    private IpRoMapper ipRoMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void merge(String ip) {
        Timestamp now = new Timestamp(new Date().getTime());
        Ip o = ipRoMapper.selectOne(ip);

        if (o != null) {
            if (new Date().after(DateUtils.addDays(o.getUpdateTime(), UPDATE_CYCLE))) {
                Ip updateObj = getIpObj(ip);

                if (updateObj != null) {
                    BeanUtils.copyProperties(updateObj, o, "id");
                    o.setUpdateTime(now);
                    ipMapper.update(o);
                }
            }
        } else {
            Ip insertObj = getIpObj(ip);

            if (insertObj != null) {
                insertObj.setId(Utils.uuid());
                insertObj.setCreateTime(now);
                insertObj.setUpdateTime(now);
                ipMapper.insert(insertObj);
            }
        }
    }

    private Ip getIpObj(String ip) {
        LOGGER.info("{}, {}", Constant.IP_QUERY_URL, ip);
        String jsonStr = restTemplate.getForObject(Constant.IP_QUERY_URL, String.class, ip);
        LOGGER.info("{}", jsonStr);
        IpQueryResult ipQueryResult = JSON.parseObject(jsonStr, IpQueryResult.class);
        return ipQueryResult.getCode() == 0 ? ipQueryResult.getData() : null;
    }
}
