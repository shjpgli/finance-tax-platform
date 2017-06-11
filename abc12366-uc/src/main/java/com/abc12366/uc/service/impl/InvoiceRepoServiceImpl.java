package com.abc12366.uc.service.impl;

import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.InvoiceRepoMapper;
import com.abc12366.uc.mapper.db2.InvoiceRepoRoMapper;
import com.abc12366.uc.model.InvoiceRepo;
import com.abc12366.uc.model.bo.InvoiceRepoBO;
import com.abc12366.uc.service.InvoiceRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @create 2017-06-08 10:17 AM
 * @since 1.0.0
 */
@Service
public class InvoiceRepoServiceImpl implements InvoiceRepoService {

    @Autowired
    private InvoiceRepoRoMapper invoiceRepoRoMapper;
    @Autowired
    private InvoiceRepoMapper invoiceRepoMapper;

    @Override
    public List<InvoiceRepo> selectInvoiceRepoList(InvoiceRepo invoiceRepo) {
        return invoiceRepoRoMapper.selectInvoiceRepoList(invoiceRepo);
    }

    @Override
    public InvoiceRepoBO addInvoiceRepo(InvoiceRepoBO invoiceRepoBO) {
        int start = Integer.valueOf(invoiceRepoBO.getStartNo());
        int end = Integer.valueOf(invoiceRepoBO.getStartNo());
        InvoiceRepo invoiceRepo = null;
        Date date = null;
        int insert;
        InvoiceRepoBO bo = new InvoiceRepoBO();
        List<InvoiceRepo> list = new ArrayList<InvoiceRepo>();
        int successNum = 0;
        for (int i = start; i <= end; i++) {
            insert = 0;
            invoiceRepo = new InvoiceRepo();
            date = new Date();
            invoiceRepo.setId(Utils.uuid());
            invoiceRepo.setInvoiceNo(String.valueOf(i));
            invoiceRepo.setInvoiceCode(invoiceRepoBO.getInvoiceCode());
            invoiceRepo.setProperty(invoiceRepoBO.getProperty());
            invoiceRepo.setInvoiceName(invoiceRepoBO.getInvoiceName());
            invoiceRepo.setCreateTime(date);
            invoiceRepo.setLastUpdate(date);
            //查询发票号码是否已存在
            InvoiceRepo temp = invoiceRepoRoMapper.selectByInvoiceNo(String.valueOf(i));
            if (temp != null){
                list.add(invoiceRepo);
            }else{
                insert = invoiceRepoMapper.insert(invoiceRepo);
                if(insert != 1){
                    list.add(invoiceRepo);
                }else{
                    successNum++;
                }
            }
        }
        bo.setSuccessNum(successNum);
        bo.setFailList(list);
        return bo;
    }

    @Override
    public void deleteInvoiceRepo(String id) {
        invoiceRepoMapper.deleteByPrimaryKey(id);
    }

}
