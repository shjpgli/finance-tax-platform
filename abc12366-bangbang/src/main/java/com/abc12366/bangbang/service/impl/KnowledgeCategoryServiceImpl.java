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
        record.setId(Utils.uuid());
        record.setCreateUser(UcUserCommon.getUserId());
        record.setUpdateUser(UcUserCommon.getUserId());
        String parentCode = StringUtil.nullToString(record.getParentCode());
        String code = parentCode + genCodes(6);
        for (; ; ) {
            KnowledgeCategory rs = knowledgeCategoryMapper.selectByCode(code);
            if (rs != null) {
                code = parentCode + genCodes(6);
            } else {
                break;
            }
        }
        record.setCode(code);
        int rs = knowledgeCategoryMapper.insert(record);
        if (rs != 1) {
            LOGGER.error("知识库分类新增失败：{}", record);
            throw new ServiceException(4501);
        }
        return record;
    }

    @Override
    public KnowledgeCategory modify(KnowledgeCategory knowledgeCategory) {
        knowledgeCategoryMapper.updateByPrimaryKey(knowledgeCategory);
        return knowledgeCategory;
    }

    @Override
    public void modifyNameById(String id, String name) {
        KnowledgeCategory record = new KnowledgeCategory();
        record.setId(id);
        record.setName(name);
        int rs = knowledgeCategoryMapper.updateByPrimaryKeySelective(record);
        if (rs != 1) {
            LOGGER.error("知识库分类修改名称失败：{}", id);
            throw new ServiceException(4502);
        }
    }

    @Override
    public void modifySort(List<SortBO> list) {
        if (list != null && !list.isEmpty()) {
            knowledgeCategoryMapper.batchUpdateSort(list);
        }
    }

    @Override
    public void deleteById(String id) {
        int rs = knowledgeCategoryMapper.deleteByPrimaryKey(id);
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
