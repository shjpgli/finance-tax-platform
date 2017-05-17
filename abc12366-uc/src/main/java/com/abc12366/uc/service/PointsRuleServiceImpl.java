package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.PointsRuleMapper;
import com.abc12366.uc.mapper.db2.PointsRuleRoMapper;
import com.abc12366.uc.model.PointsRule;
import com.abc12366.uc.model.bo.PointsRuleBO;
import com.abc12366.uc.model.bo.PointsRuleUpdateBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-16 10:18 PM
 * @since 2.0.0
 */
@Service
public class PointsRuleServiceImpl implements PointsRuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointsLogServiceImpl.class);

    @Autowired
    private PointsRuleMapper uPointRuleMapper;

    @Autowired
    private PointsRuleRoMapper uPointRuleRoMapper;

    @Override
    public List<PointsRuleBO> selectList(Map map) {
        List<PointsRuleBO> pointsRuleBOs = uPointRuleRoMapper.selectList(map);
        return pointsRuleBOs;
    }

    @Override
    public PointsRuleBO selectOne(String id) {
        PointsRuleBO pointsRuleBO = uPointRuleRoMapper.selectOne(id);
        if (pointsRuleBO == null) {
            LOGGER.warn("查询失败，参数：{}" + pointsRuleBO.toString());
            throw new ServiceException(4104);
        }
        return pointsRuleBO;
    }

    @Transactional("db1TxManager")
    @Override
    public PointsRuleBO insert(PointsRuleBO pointsRuleBO) {
        if (pointsRuleBO == null) {
            LOGGER.warn("新增失败，参数：{}" + pointsRuleBO.toString());
            throw new ServiceException(4101);
        }
        PointsRule pointsRuleQuery = uPointRuleRoMapper.selectByCode(pointsRuleBO.getCode());
        if (pointsRuleQuery != null) {
            LOGGER.warn("新增失败，参数：{}" + pointsRuleBO.toString());
            throw new ServiceException(4101);
        }
        PointsRule pointsRule = new PointsRule();
        BeanUtils.copyProperties(pointsRuleBO, pointsRule);
        Date date = new Date();
        pointsRule.setId(Utils.uuid());
        pointsRule.setCreateTime(date);
        pointsRule.setLastUpdate(date);
        int result = uPointRuleMapper.insert(pointsRule);
        if (result < 1) {
            LOGGER.warn("新增失败，参数：{}" + pointsRuleBO.toString());
            throw new ServiceException(4101);
        }
        PointsRuleBO pointsRuleBO1 = new PointsRuleBO();
        BeanUtils.copyProperties(pointsRule, pointsRuleBO1);
        return pointsRuleBO1;
    }

    @Transactional("db1TxManager")
    @Override
    public PointsRuleBO update(PointsRuleUpdateBO pointsRuleUpdateBO, String id) {
        if (pointsRuleUpdateBO == null) {
            LOGGER.warn("更新失败，参数：{}：{}" + pointsRuleUpdateBO.toString(), id);
            throw new ServiceException(4102);
        }
        PointsRuleBO uPointsRuleQuery = uPointRuleRoMapper.selectOne(id);
        if (uPointsRuleQuery == null) {
            LOGGER.warn("更新失败，参数：{}：{}" + pointsRuleUpdateBO.toString(), id);
            throw new ServiceException(4102);
        }
        PointsRule pointsRuleQuery = uPointRuleRoMapper.selectByCode(pointsRuleUpdateBO.getCode());
        if (pointsRuleQuery != null) {
            LOGGER.warn("更新失败，参数：{}" + pointsRuleUpdateBO.getCode());
            throw new ServiceException(4102);
        }
        PointsRule pointsRule = new PointsRule();
        BeanUtils.copyProperties(pointsRuleUpdateBO, pointsRule);
        pointsRule.setId(id);
        pointsRule.setCreateTime(null);
        pointsRule.setLastUpdate(new Date());
        int result = uPointRuleMapper.update(pointsRule);
        if (result != 1) {
            LOGGER.warn("更新失败，参数：{}：{}" + pointsRuleUpdateBO.toString(), id);
            throw new ServiceException(4102);
        }
        PointsRuleBO pointsRuleBOReturn = new PointsRuleBO();
        BeanUtils.copyProperties(pointsRule, pointsRuleBOReturn);
        return pointsRuleBOReturn;
    }
}
