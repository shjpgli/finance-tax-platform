package com.abc12366.uc.service;

import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.NsrMapper;
import com.abc12366.uc.mapper.db2.NsrRoMapper;
import com.abc12366.uc.model.Nsr;
import com.abc12366.uc.model.bo.NsrBO;
import com.abc12366.uc.model.bo.NsrSelectBO;
import com.abc12366.uc.model.bo.NsrUpdateBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuguiyao
 * @create 2017-05-05 10:08 AM
 * @since 1.0.0
 */
@Service
public class NsrServiceImpl implements NsrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NsrServiceImpl.class);

    @Autowired
    private NsrRoMapper nsrRoMapper;

    @Autowired
    private NsrMapper nsrMapper;

    @Override
    public NsrBO selectOne(String id) {
        LOGGER.info("{}", id);
        Nsr nsr = nsrRoMapper.selectOne(id);
        NsrBO nsrBO = new NsrBO();
        if (nsr != null)
            BeanUtils.copyProperties(nsr, nsrBO);
        LOGGER.info("{}", nsrBO);
        return nsrBO;
    }

    @Override
    public List<NsrBO> selectList(NsrSelectBO nsrSelectBO) {
        LOGGER.info("{}", nsrSelectBO);
        if (nsrSelectBO == null) {
            return null;
        }
        List<Nsr> nsrs = nsrRoMapper.selectList(nsrSelectBO);
        List<NsrBO> nsrBOs = new ArrayList<>();
        if (nsrs != null && nsrs.size() > 0) {
            for (Nsr nsr : nsrs) {
                NsrBO nsrBO = new NsrBO();
                BeanUtils.copyProperties(nsr, nsrBO);
                nsrBOs.add(nsrBO);
            }
        }
        LOGGER.info("{}", nsrBOs);
        return nsrBOs;
    }

    @Override
    public NsrBO update(NsrUpdateBO nsrUpdateBO) {
        LOGGER.info("{}", nsrUpdateBO);
        if (nsrUpdateBO == null) {
            return null;
        }
        List<Nsr> nsrs = new ArrayList<>();
        if (!StringUtils.isEmpty(nsrUpdateBO.getId())) {
            Nsr nsrTemp = nsrRoMapper.selectOne(nsrUpdateBO.getId());
            nsrs.add(nsrTemp);
        } else {
            NsrSelectBO nsrSelectBO = new NsrSelectBO();
            nsrs = nsrRoMapper.selectList(nsrSelectBO);
        }
        if (nsrs.size() != 1) {
            return null;
        }
        Nsr nsr = nsrs.get(0);
        BeanUtils.copyProperties(nsrUpdateBO, nsr);
        nsr.setLastUpdate(new Date());
        int result = nsrMapper.update(nsr);
        NsrBO nsrBO = new NsrBO();
        if (result > 0) {
            BeanUtils.copyProperties(nsr, nsrBO);
        }
        LOGGER.info("{}", nsrBO);
        return nsrBO;
    }

    @Override
    public NsrBO insert(NsrBO nsrBO) {
        LOGGER.info("{}", nsrBO);
        if (nsrBO == null) {
            return null;
        }
        NsrSelectBO nsrSelectBO = new NsrSelectBO();
        BeanUtils.copyProperties(nsrBO, nsrSelectBO);
        List<Nsr> nsrs = nsrRoMapper.selectList(nsrSelectBO);
        if (nsrs.size() > 0) {
            return null;
        }
        Nsr nsr = new Nsr();
        BeanUtils.copyProperties(nsrBO, nsr);
        nsr.setId(Utils.uuid());
        nsr.setStatus(true);
        nsr.setCreateTime(new Date());
        nsr.setLastUpdate(new Date());
        int result = nsrMapper.insert(nsr);
        NsrBO nsrBO1 = new NsrBO();
        if (result > 0) {
            BeanUtils.copyProperties(nsr, nsrBO1);
        }
        LOGGER.info("{}", nsrBO1);
        return nsrBO1;
    }

    @Override
    public NsrBO delete(String id) {
        LOGGER.info("{}", id);
        Nsr nsr = nsrRoMapper.selectOne(id);
        if (nsr == null) {
            return null;
        }
        int result = nsrMapper.delete(id);
        NsrBO nsrBO = new NsrBO();
        if (result > 0) {
            BeanUtils.copyProperties(nsr, nsrBO);
        }
        LOGGER.info("{}", nsrBO);
        return nsrBO;
    }
}
