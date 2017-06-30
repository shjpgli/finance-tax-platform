package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.ExperienceRuleMapper;
import com.abc12366.uc.mapper.db2.ExperienceRuleRoMapper;
import com.abc12366.uc.model.ExperienceRule;
import com.abc12366.uc.model.bo.ExperienceRuleBO;
import com.abc12366.uc.model.bo.ExperienceRuleInsertBO;
import com.abc12366.uc.model.bo.ExperienceRuleUpdateBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 11:24
 */
@Service
public class ExperienceRuleServiceImpl implements ExperienceRuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceRuleServiceImpl.class);

    @Autowired
    private ExperienceRuleRoMapper experienceRuleRoMapper;

    @Autowired
    private ExperienceRuleMapper experienceRuleMapper;

    @Override
    public List<ExperienceRuleBO> selectList(Map<String, Object> map) {
        LOGGER.info("{}", map);
        return experienceRuleRoMapper.selectList(map);
    }

    @Override
    public ExperienceRuleBO selectOne(String id) {
        LOGGER.info("{}", id);
        return experienceRuleRoMapper.selectOne(id);
    }

    @Override
    public ExperienceRuleBO insert(ExperienceRuleInsertBO experienceRuleInsertBO) {
        if (experienceRuleInsertBO == null) {
            LOGGER.warn("新增失败，参数：" + null);
            throw new ServiceException(4101);
        }
        //经验值规则新增，规则名称、规则代码唯一性校验
        List<ExperienceRuleBO> experienceRuleBOList = experienceRuleRoMapper.selectList(null);
        for (ExperienceRuleBO experieneRuleBO : experienceRuleBOList) {
            if (experieneRuleBO.getName().equals(experienceRuleInsertBO.getName())) {
                LOGGER.warn("新增失败，参数：{}", experienceRuleInsertBO);
                throw new ServiceException(4602);
            }
            if (experieneRuleBO.getCode().equals(experienceRuleInsertBO.getCode())) {
                LOGGER.warn("新增失败，参数：{}", experienceRuleInsertBO);
                throw new ServiceException(4603);
            }
        }

        ExperienceRule experienceRule = new ExperienceRule();
        Date date = new Date();
        experienceRule.setId(Utils.uuid());
        experienceRule.setCreateTime(date);
        experienceRule.setLastUpdate(date);
        BeanUtils.copyProperties(experienceRuleInsertBO, experienceRule);
        int result = experienceRuleMapper.insert(experienceRule);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：" + experienceRule);
            throw new ServiceException(4101);
        }
        ExperienceRuleBO experienceRuleReturn = new ExperienceRuleBO();
        BeanUtils.copyProperties(experienceRule, experienceRuleReturn);
        return experienceRuleReturn;
    }

    @Override
    public ExperienceRuleBO update(ExperienceRuleUpdateBO experienceRuleUpdateBO, String id) {
        ExperienceRuleBO experienceRuleQueryBO = experienceRuleRoMapper.selectOne(id);
        if (experienceRuleQueryBO == null) {
            LOGGER.warn("修改失败，没有可被修改的数据，参数：" + id);
            throw new ServiceException(4102);
        }

        //经验值规则新增，规则名称、规则代码唯一性校验
        List<ExperienceRuleBO> experienceRuleBOList = experienceRuleRoMapper.selectList(null);
        //本身不计入校验数据
        for(int i=0; i<experienceRuleBOList.size(); i++){
            if((experienceRuleBOList.get(i)).getId().equals(id)){
                experienceRuleBOList.remove(i);
            }
        }
        if (experienceRuleUpdateBO.getName() != null) {
            for (ExperienceRuleBO experieneRuleBO : experienceRuleBOList) {
                if (experieneRuleBO.getName().equals(experienceRuleUpdateBO.getName())) {
                    LOGGER.warn("修改失败，参数：{}", experienceRuleUpdateBO);
                    throw new ServiceException(4602);
                }
            }
        }
        if(experienceRuleUpdateBO.getCode()!=null){
            for (ExperienceRuleBO experieneRuleBO : experienceRuleBOList) {
                if (experieneRuleBO.getCode().equals(experienceRuleUpdateBO.getCode())) {
                    LOGGER.warn("修改失败，参数：{}", experienceRuleUpdateBO);
                    throw new ServiceException(4603);
                }
            }
        }

        ExperienceRule experienceRule = new ExperienceRule();
        BeanUtils.copyProperties(experienceRuleUpdateBO, experienceRule);
        experienceRule.setId(id);
        experienceRule.setLastUpdate(new Date());
        if (StringUtils.isEmpty(experienceRuleUpdateBO.getStatus())) {
            experienceRule.setStatus(null);
        }
        int result = experienceRuleMapper.update(experienceRule);
        if (result != 1) {
            LOGGER.warn("修改失败，参数：" + experienceRule);
            throw new ServiceException(4102);
        }
        ExperienceRuleBO experienceRuleReturn = new ExperienceRuleBO();
        BeanUtils.copyProperties(experienceRule, experienceRuleReturn);
        return experienceRuleReturn;
    }

    @Override
    public int delete(String id) {
        LOGGER.info("{}", id);
        int result = experienceRuleMapper.delete(id);
        if (result != 1) {
            LOGGER.warn("删除失败，参数为：id=" + id);
            throw new ServiceException(4103);
        }
        return 1;
    }
}
