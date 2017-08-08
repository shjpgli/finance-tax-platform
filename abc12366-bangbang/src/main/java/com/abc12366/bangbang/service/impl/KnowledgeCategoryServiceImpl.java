package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.StringUtil;
import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.KnowledgeCategoryMapper;
import com.abc12366.bangbang.model.KnowledgeCategory;
import com.abc12366.bangbang.model.bo.SortBO;
import com.abc12366.bangbang.service.KnowledgeCategoryService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author liuqi
 * @Date 2017/8/2 21:01
 */
@Service
public class KnowledgeCategoryServiceImpl implements KnowledgeCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseServiceImpl.class);

    @Autowired
    private KnowledgeCategoryMapper knowledgeCategoryMapper;

    @Override
    public List<KnowledgeCategory> listAll() {
        return knowledgeCategoryMapper.selectAll();
    }

    @Override
    public KnowledgeCategory add(KnowledgeCategory record) {
        try{
            record.setId(Utils.uuid());
            record.setCreateUser(UcUserCommon.getAdminId());
            record.setUpdateUser(UcUserCommon.getAdminId());
            String parentCode = StringUtil.nullToString(record.getParentCode());
            String code = parentCode + genCodes(6);
            for (; ; ) {
                KnowledgeCategory rs = knowledgeCategoryMapper.selectByCode(code);
                if (rs == null) {
                    break;
                } else {
                    code = parentCode + genCodes(6);
                }
            }
            record.setCode(code);
            knowledgeCategoryMapper.insert(record);
            return record;
        }catch (Exception e){
            LOGGER.error("KnowledgeCategoryServiceImpl.add()", e);
            throw new ServiceException(4511);
        }
    }

    @Override
    public KnowledgeCategory modify(KnowledgeCategory knowledgeCategory) {
        try{
            knowledgeCategory.setUpdateUser(UcUserCommon.getAdminId());
            knowledgeCategory.setUpdateTime(new Date());
            knowledgeCategoryMapper.updateByPrimaryKey(knowledgeCategory);
            return knowledgeCategory;
        }catch (Exception e){
            LOGGER.error("KnowledgeCategoryServiceImpl.modify()", e);
            throw new ServiceException(4513);
        }
    }

    @Override
    public void modifyNameById(String id, String name) {
        try{
            KnowledgeCategory knowledgeCategory = new KnowledgeCategory();
            knowledgeCategory.setId(id);
            knowledgeCategory.setName(name);
            knowledgeCategory.setUpdateUser(UcUserCommon.getAdminId());
            knowledgeCategory.setUpdateTime(new Date());
            knowledgeCategoryMapper.updateByPrimaryKeySelective(knowledgeCategory);
        }catch (Exception e){
            LOGGER.error("KnowledgeCategoryServiceImpl.modifyNameById()", e);
            throw new ServiceException(4513);
        }
    }

    @Override
    public void modifySort(List<SortBO> list) {
        try {
            if (list != null && !list.isEmpty()) {
                knowledgeCategoryMapper.batchUpdateSort(list);
            }
        }catch (Exception e){
            LOGGER.error("KnowledgeCategoryServiceImpl.modifySort()", e);
            throw new ServiceException(4513);
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            knowledgeCategoryMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            LOGGER.error("KnowledgeCategoryServiceImpl.modifySort()", e);
            throw new ServiceException(4512);
        }
    }


    public String genCodes(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        val = val.toLowerCase();
        return val;
    }
}
