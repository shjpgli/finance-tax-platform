package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.InvoiceMapper;
import com.abc12366.uc.mapper.db1.OrderInvoiceMapper;
import com.abc12366.uc.mapper.db2.GoodsCategoryRoMapper;
import com.abc12366.uc.mapper.db2.InvoiceRoMapper;
import com.abc12366.uc.mapper.db2.OrderInvoiceRoMapper;
import com.abc12366.uc.model.GoodsCategory;
import com.abc12366.uc.model.Invoice;
import com.abc12366.uc.model.OrderInvoice;
import com.abc12366.uc.model.bo.GoodsCategoryBO;
import com.abc12366.uc.model.bo.InvoiceBO;
import com.abc12366.uc.service.GoodsCategoryService;
import com.abc12366.uc.service.InvoiceService;
import com.abc12366.uc.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lizhongwei
 * @create 2017-06-01
 * @since 1.0.0
 */
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    @Autowired
    private GoodsCategoryRoMapper goodsCategoryRoMapper;

    @Override
    public List<GoodsCategoryBO> selectList(GoodsCategory goodsCategory) {
        //查询所有父节点
        List<GoodsCategoryBO> boList = goodsCategoryRoMapper.selectList(goodsCategory);
        for (GoodsCategoryBO bo:boList){

        }
        return null;
    }

    /**
     * 递归算法解析成树形结构
     *
     * @param id
     */
    public GoodsCategoryBO recursiveTree(String id) {
        GoodsCategoryBO node = goodsCategoryRoMapper.selectById(id);
        List<GoodsCategoryBO> treeNodes = goodsCategoryRoMapper.selectByParentId(id);
        //遍历子节点
        for(GoodsCategoryBO child : treeNodes){
            GoodsCategoryBO n = recursiveTree(child.getId()); //递归
            node.getNodes().add(n);
        }
        return node;
    }

    @Override
    public GoodsCategory add(GoodsCategoryBO goodsCategoryBO) {
        return null;
    }

    @Override
    public GoodsCategoryBO update(GoodsCategoryBO goodsCategoryBO) {
        return null;
    }

    @Override
    public GoodsCategory selectGategory(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
