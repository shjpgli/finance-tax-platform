package com.abc12366.uc.service.invoice.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.InvoiceApprovalLogMapper;
import com.abc12366.uc.mapper.db1.InvoiceUseApplyMapper;
import com.abc12366.uc.mapper.db1.InvoiceUseDetailMapper;
import com.abc12366.uc.mapper.db2.InvoiceUseApplyRoMapper;
import com.abc12366.uc.mapper.db2.InvoiceUseDetailRoMapper;
import com.abc12366.uc.model.invoice.InvoiceApprovalLog;
import com.abc12366.uc.model.invoice.InvoiceUseApply;
import com.abc12366.uc.model.invoice.InvoiceUseDetail;
import com.abc12366.uc.model.invoice.bo.InvoiceUseApplyBO;
import com.abc12366.uc.model.invoice.bo.InvoiceUseCheckBO;
import com.abc12366.uc.model.invoice.bo.InvoiceUseDetailBO;
import com.abc12366.uc.service.invoice.InvoiceUseApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @create 2017-05-15 10:17 AM
 * @since 1.0.0
 */
@Service
public class InvoiceuseApplyServiceImpl implements InvoiceUseApplyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceuseApplyServiceImpl.class);

    @Autowired
    private InvoiceUseApplyRoMapper invoiceUseApplyRoMapper;

    @Autowired
    private InvoiceUseApplyMapper invoiceUseApplyMapper;

    @Autowired
    private InvoiceUseDetailRoMapper invoiceUseDetailRoMapper;

    @Autowired
    private InvoiceApprovalLogMapper invoiceApprovalLogMapper;

    @Autowired
    private InvoiceUseDetailMapper invoiceUseDetailMapper;

    @Override
    public List<InvoiceUseApplyBO> selectList(InvoiceUseApplyBO applyBO) {
        return invoiceUseApplyRoMapper.selectList(applyBO);
    }

    @Override
    public InvoiceUseApplyBO selectInvoiceUseApply(InvoiceUseApply invoiceUseApply) {
        return invoiceUseApplyRoMapper.selectInvoiceUseApply(invoiceUseApply.getId());
    }

    @Override
    public void delete(String id) {
        int delete = invoiceUseApplyMapper.delete(id);
        if(delete != 1){
            LOGGER.warn("删除失败，参数{}：" + id);
            throw new ServiceException(4103);
        }
        int dDelete = invoiceUseDetailMapper.delete(id);
        if(dDelete != 1){
            LOGGER.warn("删除失败，参数{}：" + id);
            throw new ServiceException(4103);
        }
    }

    @Override
    public InvoiceUseApplyBO add(InvoiceUseApplyBO invoiceUseApplyBO) {
        if(invoiceUseApplyBO == null){
            LOGGER.warn("数据错误{}：" + null);
            throw new ServiceException(4906);
        }
        String id = Utils.uuid();
        invoiceUseApplyBO.setId(id);
        Date date = new Date();
        invoiceUseApplyBO.setApplyTime(date);
        InvoiceUseApply invoiceUseApply = new InvoiceUseApply();
        BeanUtils.copyProperties(invoiceUseApplyBO,invoiceUseApply);
        int insert = invoiceUseApplyMapper.insert(invoiceUseApply);
        if(insert != 1){
            LOGGER.warn("新增失败，参数{}：" + invoiceUseApplyBO);
            throw new ServiceException(4101);
        }
        InvoiceUseDetailBO invoiceUseDetailBO = invoiceUseApplyBO.getInvoiceUseDetailBO();
        if(invoiceUseApplyBO == null){
            LOGGER.warn("数据错误{}：" + null);
            throw new ServiceException(4906);
        }
        invoiceUseDetailBO.setUseId(id);
        InvoiceUseDetail invoiceUseDetail = new InvoiceUseDetail();
        BeanUtils.copyProperties(invoiceUseDetailBO,invoiceUseDetail);
        int dInsert = invoiceUseDetailMapper.insert(invoiceUseDetail);
        if(dInsert != 1){
            LOGGER.warn("新增失败，参数{}：" + invoiceUseDetail);
            throw new ServiceException(4101);
        }
        invoiceUseApplyBO.setInvoiceUseDetailBO(invoiceUseDetailBO);

        //加入日志
        insertLog(id,"","已提交");
        return invoiceUseApplyBO;
    }

    private void insertLog(String id,String opinions,String result) {
        InvoiceApprovalLog log = new InvoiceApprovalLog();
        log.setId(Utils.uuid());
        log.setUseId(id);
        log.setApprovalOpinions(opinions);
        log.setApprovalResult(result);
    }

    @Override
    public InvoiceUseApplyBO update(InvoiceUseApplyBO invoiceUseApplyBO) {
        if(invoiceUseApplyBO == null){
            LOGGER.warn("数据错误{}：" + null);
            throw new ServiceException(4906);
        }
        Date date = new Date();
        invoiceUseApplyBO.setApplyTime(date);
        InvoiceUseApply invoiceUseApply = new InvoiceUseApply();
        BeanUtils.copyProperties(invoiceUseApplyBO,invoiceUseApply);
        int update = invoiceUseApplyMapper.update(invoiceUseApply);
        if(update != 1){
            LOGGER.warn("修改失败，参数{}：" + invoiceUseApplyBO);
            throw new ServiceException(4102);
        }
        InvoiceUseDetailBO invoiceUseDetailBO = invoiceUseApplyBO.getInvoiceUseDetailBO();
        if(invoiceUseApplyBO == null){
            LOGGER.warn("数据错误{}：" + null);
            throw new ServiceException(4906);
        }
        InvoiceUseDetail invoiceUseDetail = new InvoiceUseDetail();
        BeanUtils.copyProperties(invoiceUseDetailBO,invoiceUseDetail);
        int dInsert = invoiceUseDetailMapper.insert(invoiceUseDetail);
        if(dInsert != 1){
            LOGGER.warn("修改失败，参数{}：" + invoiceUseDetail);
            throw new ServiceException(4102);
        }
        invoiceUseApplyBO.setInvoiceUseDetailBO(invoiceUseDetailBO);
        //加入日志
        insertLog(invoiceUseApply.getId(),invoiceUseDetailBO.getRemark(),"已修改");
        return invoiceUseApplyBO;
    }

    @Override
    public void checkUseApplay(InvoiceUseCheckBO invoiceUseCheckBO) {
        if(invoiceUseCheckBO == null){
            LOGGER.warn("数据错误{}：" + null);
            throw new ServiceException(4906);
        }
        Date date = new Date();
        invoiceUseCheckBO.setApplyTime(date);
        InvoiceUseApply invoiceUseApply = new InvoiceUseApply();
        BeanUtils.copyProperties(invoiceUseCheckBO, invoiceUseApply);
        int update = invoiceUseApplyMapper.update(invoiceUseApply);
        if(update != 1){
            LOGGER.warn("修改失败，参数{}：" + invoiceUseCheckBO);
            throw new ServiceException(4102);
        }
        String check = invoiceUseCheckBO.getExamineStatus();
        /**审批状态，0：待审核，1：审核通过，2：审核不通过，3：草稿**/
        if(check.equals("1")){
            insertLog(invoiceUseApply.getId(),invoiceUseCheckBO.getRemark(),"审核通过");
        }else if(check.equals("2")){
            insertLog(invoiceUseApply.getId(),invoiceUseCheckBO.getRemark(),"审核不通过");
        }
        //加入日志
    }

    @Override
    public void distributeUseApply(InvoiceUseCheckBO invoiceUseCheckBO) {

    }
}
