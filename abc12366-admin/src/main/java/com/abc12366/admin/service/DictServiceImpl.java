package com.abc12366.admin.service;

import com.abc12366.admin.mapper.db1.DictMapper;
import com.abc12366.admin.mapper.db2.DictRoMapper;
import com.abc12366.admin.model.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 3:10 PM
 * @since 1.0.0
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private DictRoMapper dictRoMapper;

    @Override
    public List<Dict> selectList() {
        return dictRoMapper.selectList();
    }
}
