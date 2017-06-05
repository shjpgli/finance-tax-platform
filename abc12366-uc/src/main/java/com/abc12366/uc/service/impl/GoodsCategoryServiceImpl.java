package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.GoodsCategoryMapper;
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

import java.util.ArrayList;
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

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public List<GoodsCategoryBO> selectList(GoodsCategory goodsCategory) {
        //查询所有父节点
        List<GoodsCategoryBO> boList = goodsCategoryRoMapper.selectList(goodsCategory);
        List<GoodsCategoryBO> list = new ArrayList<>();
        GoodsCategoryBO categoryBO = null;
        for (GoodsCategoryBO bo:boList){
            categoryBO = new GoodsCategoryBO();
            if(bo.getParentId() == null){
                categoryBO = recursiveTree(bo.getId());
                list.add(categoryBO);
            }
        }
        return list;
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
   /* private List<GoodsCategoryBO> getChildren(GoodsCategoryBO bo,List<GoodsCategoryBO> listBo){
        List<GoodsCategoryBO> children = new ArrayList<GoodsCategoryBO>();
        String id = bo.getId();
        for (GoodsCategoryBO child : listBo) {
            if (id.equals(child.getParentId())) {
                children.add(child);
            }
        }
        return children;
    }*/

    @Override
    public GoodsCategory add(GoodsCategoryBO goodsCategoryBO) {
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtils.copyProperties(goodsCategoryBO,goodsCategory);
        goodsCategory.setId(Utils.uuid());
        Date date = new Date();
        goodsCategory.setCreateTime(date);
        goodsCategory.setLastUpdate(date);
        int insert = goodsCategoryMapper.insert(goodsCategory);
        if(insert != 1){
            throw new ServiceException(4101);
        }
        return goodsCategory;
    }

    @Override
    public GoodsCategoryBO update(GoodsCategoryBO goodsCategoryBO) {
        String id = goodsCategoryBO.getId();
        String parentId = goodsCategoryBO.getParentId();
        //判断节点是否是自己本身
        if(parentId != null && !"".equals(parentId)){
            if(id.equals(parentId)){
                throw new ServiceException(4118);
            }
        }
        GoodsCategory category = new GoodsCategory();
        BeanUtils.copyProperties(goodsCategoryBO,category);
        category.setLastUpdate(new Date());
        int upd = goodsCategoryMapper.update(category);
        if(upd != 1){
            throw new ServiceException(4102);
        }
        GoodsCategoryBO bo = new GoodsCategoryBO();
        BeanUtils.copyProperties(category,bo);
        return bo;
    }

    @Override
    public GoodsCategory selectGategory(String id) {
        return null;
    }

    @Override
    public void delete(String id) {
        deleteTree(id);
    }

    /**
     * 递归算法删除树节点
     *
     * @param id
     */
    public void deleteTree(String id) {
        List<GoodsCategoryBO> treeNodes = new ArrayList<GoodsCategoryBO>();
        treeNodes = goodsCategoryRoMapper.selectByParentId(id);
        //遍历子节点
        for(GoodsCategoryBO child : treeNodes){
            deleteTree(child.getId()); //递归
        }
        goodsCategoryMapper.deleteByPrimaryKey(id);
    }
}
