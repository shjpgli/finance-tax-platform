package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.QuestionnaireParamMapper;
import com.abc12366.cms.mapper.db2.QuestionnaireParamRoMapper;
import com.abc12366.cms.model.questionnaire.QuestionnaireParam;
import com.abc12366.cms.model.questionnaire.bo.QuestionnaireParamBO;
import com.abc12366.cms.service.QuestionnaireParamService;
import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author lizhongwei
 * @create 2017-06-16 4:21 PM
 * @since 1.0.0
 */
@Service
public class QuestionnaireParamServiceImpl implements QuestionnaireParamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireParamServiceImpl.class);
    @Autowired
    private QuestionnaireParamRoMapper questionnaireRoMapper;

    @Autowired
    private QuestionnaireParamMapper questionnaireMapper;

}
