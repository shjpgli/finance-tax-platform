package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.InvoiceDetailMapper;
import com.abc12366.uc.mapper.db1.InvoiceRepoMapper;
import com.abc12366.uc.mapper.db2.InvoiceDetailRoMapper;
import com.abc12366.uc.mapper.db2.InvoiceRepoRoMapper;
import com.abc12366.uc.model.InvoiceDetail;
import com.abc12366.uc.model.InvoiceRepo;
import com.abc12366.uc.model.bo.InvoiceRepoBO;
import com.abc12366.uc.service.InvoiceRepoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @create 2017-06-08 10:17 AM
 * @since 1.0.0
 */
@Service
public class InvoiceRepoServiceImpl implements InvoiceRepoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepoServiceImpl.class);

    @Autowired
    private InvoiceRepoRoMapper invoiceRepoRoMapper;
    @Autowired
    private InvoiceRepoMapper invoiceRepoMapper;

    @Autowired
    private InvoiceDetailMapper invoiceDetailMapper;

    @Autowired
    private InvoiceDetailRoMapper invoiceDetailRoMapper;

    @Override
    public List<InvoiceRepo> selectInvoiceRepoList(InvoiceRepo invoiceRepo) {
        return invoiceRepoRoMapper.selectInvoiceRepoList(invoiceRepo);
    }

    @Transactional("db1TxManager")
    @Override
    public InvoiceRepoBO addInvoiceRepo(InvoiceRepoBO invoiceRepoBO) {
        String iRepoId = Utils.uuid();
        invoiceRepoBO.setId(iRepoId);
        Date date = new Date();
        invoiceRepoBO.setCreateTime(date);
        invoiceRepoBO.setLastUpdate(date);
        InvoiceRepo invoiceRepo = new InvoiceRepo();
        BeanUtils.copyProperties(invoiceRepoBO,invoiceRepo);
        int rInsert = invoiceRepoMapper.insert(invoiceRepo);
        if (rInsert != 1){
            LOGGER.info("发票仓库新增信息失败：{}", invoiceRepo);
            throw new ServiceException(4169);
        }
        //拆分发票段
        String invoiceSection = invoiceRepoBO.getInvoiceSection();
        boolean isSection = invoiceSection.contains("-");
        if (isSection == false){
            LOGGER.info("{发票段信息错误}", invoiceSection);
            throw new ServiceException(4170);
        }
        String[]  sections = invoiceSection.split("-");
        //入库发票详情表
        int start = Integer.valueOf(sections[0]);
        int end = Integer.valueOf(sections[1]);
        List<InvoiceDetail> list = new ArrayList<>();
        int successNum = 0;
        for (int i = start; i <= end; i++) {
            int insert;
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            date = new Date();
            invoiceDetail.setId(Utils.uuid());
            invoiceDetail.setInvoiceRepoId(iRepoId);
            invoiceDetail.setInvoiceNo(String.valueOf(i));
            invoiceDetail.setInvoiceCode(invoiceRepoBO.getInvoiceCode());
            invoiceDetail.setProperty(invoiceRepoBO.getProperty());
            invoiceDetail.setInvoiceName(invoiceRepoBO.getInvoiceName());
            invoiceDetail.setStatus("0");
            invoiceDetail.setCreateTime(date);
            invoiceDetail.setLastUpdate(date);
            //查询发票号码是否已存在
            InvoiceRepo temp = invoiceDetailRoMapper.selectByInvoiceNo(String.valueOf(i));
            if (temp != null){
                list.add(invoiceDetail);
            }else{
                insert = invoiceDetailMapper.insert(invoiceDetail);
                if(insert != 1){
                    list.add(invoiceDetail);
                }else{
                    successNum++;
                }
            }
        }
        invoiceRepoBO.setSuccessNum(successNum);
        return invoiceRepoBO;
    }

    @Transactional("db1TxManager")
    @Override
    public void deleteInvoiceRepo(String id) {
        //查找发票详情信息，是否有已使用或分配中的发票
        List<InvoiceDetail> invoiceDetails = invoiceDetailRoMapper.selectByIdAndStatus(id);
        if (invoiceDetails != null && invoiceDetails.size() != 0){
            LOGGER.info("{该发票仓库信息中有已使用或分配中的发票}：{}", id);
            throw new ServiceException(4177);
        }
        int rDel = invoiceRepoMapper.deleteByPrimaryKey(id);
        if (rDel != 1){
            LOGGER.info("{发票仓库信息删除失败}：{}", id);
            throw new ServiceException(4171);
        }
        //同时删除发票详情信息
        invoiceDetailMapper.deleteByInvoiceRepoId(id);
    }

    @Override
    public List<InvoiceDetail> selectInvoiceDetailList(InvoiceDetail invoiceDetail) {
        return invoiceDetailRoMapper.selectInvoiceDetailList(invoiceDetail);
    }

    @Override
    public void deleteInvoiceDetail(String id) {
        InvoiceDetail invoiceDetail = invoiceDetailRoMapper.selectByPrimaryKey(id);
        if (invoiceDetail != null){
            if ("1".equals(invoiceDetail.getStatus()) || "2".equals(invoiceDetail.getStatus())){
                LOGGER.info("{发票在分配中或已使用，不能删除}：{}", id);
                throw new ServiceException(4174);
            }
            int rDel = invoiceDetailMapper.delete(id);
            if (rDel != 1){
                LOGGER.info("{发票详情信息删除失败}：{}", id);
                throw new ServiceException(4172);
            }
        }else {
            LOGGER.info("{发票信息不存在}：{}", id);
            throw new ServiceException(4175);
        }
    }

    @Override
    public void invalidInvoiceDetail(String id) {
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setId(id);
        invoiceDetail.setStatus("3");
        int update = invoiceDetailMapper.update(invoiceDetail);
        if (update != 1){
            LOGGER.info("{发票详情信息作废失败}：{}", id);
            throw new ServiceException(4176);
        }
    }

    @Override
    public InvoiceDetail selectInvoiceDetail() {
        return invoiceDetailRoMapper.selectInvoiceDetail();
    }

    @Override
    public List<InvoiceDetail> selectInvoiceDetailListByInvoice(InvoiceDetail invoiceDetail) {
        return invoiceDetailRoMapper.selectInvoiceDetailListByInvoice(invoiceDetail);
    }

}
