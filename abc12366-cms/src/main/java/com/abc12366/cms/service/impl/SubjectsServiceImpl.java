package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.OptionMapper;
import com.abc12366.cms.mapper.db1.SubjectsMapper;
import com.abc12366.cms.mapper.db2.OptionRoMapper;
import com.abc12366.cms.mapper.db2.SubjectsRoMapper;
import com.abc12366.cms.mapper.db2.VoteRoMapper;
import com.abc12366.cms.model.Subject;
import com.abc12366.cms.model.questionnaire.Option;
import com.abc12366.cms.model.questionnaire.Subjects;
import com.abc12366.cms.model.questionnaire.bo.SubjectsBO;
import com.abc12366.cms.service.SubjectsService;
import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lizhongwei
 * @create 2017-06-07 4:21 PM
 * @since 1.0.0
 */
@Service
public class SubjectsServiceImpl implements SubjectsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectsServiceImpl.class);
    @Autowired
    private SubjectsRoMapper subjectsRoMapper;

    @Autowired
    private SubjectsMapper subjectsMapper;

    @Autowired
    private OptionRoMapper optionRoMapper;

    @Autowired
    private OptionMapper optionMapper;


    @Override
    public List<SubjectsBO> selectList(SubjectsBO subjectsBO) {
        Subjects subjects = new Subjects();
        BeanUtils.copyProperties(subjectsBO,subjects);

        return subjectsRoMapper.selectList(subjects);
    }

    @Override
    public SubjectsBO selectOne(String id) {
        return subjectsRoMapper.selectOne(id);
    }


    @Transactional("db1TxManager")
    @Override
    public SubjectsBO insert(SubjectsBO subjectsBO) {
        Subjects subjects = new Subjects();
        BeanUtils.copyProperties(subjectsBO,subjects);
        String subjectsId = Utils.uuid();
        subjects.setId(subjectsId);
        int insert = subjectsMapper.insert(subjects);
        if(insert != 1){
            LOGGER.info("{新增题目失败}", subjects);
            throw new ServiceException(4399);
        }
        List<Option> options = new ArrayList<>();
        List<Option> optionList = subjectsBO.getOptionList();
        int oInsert = 0;
        for (Option option : optionList){
            option.setId(Utils.uuid());
            option.setSubjectsId(subjectsId);
            option.setStatus(true);
            oInsert = optionMapper.insert(option);
            if (oInsert != 1){
                LOGGER.info("{新增选项失败}", option);
                throw new ServiceException(4396);
            }
            options.add(option);
        }
        SubjectsBO bo = new SubjectsBO();
        BeanUtils.copyProperties(subjects,bo);
        bo.setOptionList(options);
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public SubjectsBO update(SubjectsBO subjectsBO) {
        Subjects subjects = new Subjects();
        BeanUtils.copyProperties(subjectsBO,subjects);
        int update = subjectsMapper.update(subjects);
        if(update != 1){
            LOGGER.info("{修改题目失败}", subjects);
            throw new ServiceException(4398);
        }
        List<Option> options = new ArrayList<>();
        List<Option> optionList = subjectsBO.getOptionList();
        int oUpdate = 0;
        //先删除所有的选项，再新增
        optionMapper.deleteBySubjectsId(subjectsBO.getId());
        for (Option option : optionList){
            //查询选项是否已存在
//            Option temp = optionRoMapper.selectByPrimaryKey(option.getId());
//            if(temp == null){
                option.setId(Utils.uuid());
                option.setSubjectsId(subjects.getId());
                option.setStatus(true);
                oUpdate = optionMapper.insert(option);
                if (oUpdate != 1){
                    LOGGER.info("{新增选项失败}", option);
                    throw new ServiceException(4396);
                }
//            }else {
//                oUpdate = optionMapper.update(option);
//                if (oUpdate != 1){
//                    LOGGER.info("{修改选项失败}", option);
//                    throw new ServiceException(4395);
//                }
//            }
            options.add(option);
        }
        SubjectsBO bo = new SubjectsBO();
        BeanUtils.copyProperties(subjects,bo);
        bo.setOptionList(options);
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public void delete(SubjectsBO subjectsBO) {
        Subjects subjects = new Subjects();
        BeanUtils.copyProperties(subjectsBO,subjects);
        int del = subjectsMapper.delete(subjects);
        if (del != 1){
            LOGGER.info("{删除题目失败}", subjects);
            throw new ServiceException(4397);
        }
        optionMapper.deleteBySubjectsId(subjectsBO.getId());
    }
}
