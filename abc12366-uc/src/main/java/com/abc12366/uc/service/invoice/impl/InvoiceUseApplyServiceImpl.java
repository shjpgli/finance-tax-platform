package com.abc12366.uc.service.invoice.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.InvoiceApprovalLogMapper;
import com.abc12366.uc.mapper.db1.InvoiceDistributeMapper;
import com.abc12366.uc.mapper.db1.InvoiceUseApplyMapper;
import com.abc12366.uc.mapper.db1.InvoiceUseDetailMapper;
import com.abc12366.uc.mapper.db2.*;
import com.abc12366.uc.model.invoice.*;
import com.abc12366.uc.model.invoice.bo.*;
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
public class InvoiceUseApplyServiceImpl implements InvoiceUseApplyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceUseApplyServiceImpl.class);

    @Autowired
    private InvoiceUseApplyRoMapper invoiceUseApplyRoMapper;

    @Autowired
    private InvoiceUseApplyMapper invoiceUseApplyMapper;

    @Autowired
    private InvoiceRepoRoMapper invoiceRepoRoMapper;

    @Autowired
    private InvoiceUseDetailMapper invoiceUseDetailMapper;

    @Autowired
    private InvoiceUseDetailRoMapper invoiceUseDetailRoMapper;

    @Autowired
    private InvoiceDistributeMapper invoiceDistributeMapper;

    @Autowired
    private InvoiceDistributeRoMapper invoiceDistributeRoMapper;

    @Autowired
    private InvoiceApprovalLogMapper invoiceApprovalLogMapper;

    @Autowired
    private InvoiceApprovalLogRoMapper invoiceApprovalLogRoMapper;

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
        invoiceUseDetailMapper.delete(id);
        invoiceApprovalLogMapper.deleteByUseId(id);
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
        List<InvoiceUseDetailBO> invoiceUseDetailBOList = invoiceUseApplyBO.getInvoiceUseDetailBOList();
        if(invoiceUseDetailBOList == null || invoiceUseDetailBOList.size()<1){
            LOGGER.warn("数据错误{}：" + null);
            throw new ServiceException(4906);
        }
        for (InvoiceUseDetailBO detailBO:invoiceUseDetailBOList){
            detailBO.setUseId(id);
            InvoiceUseDetail invoiceUseDetail = new InvoiceUseDetail();
            BeanUtils.copyProperties(detailBO,invoiceUseDetail);
            int dInsert = invoiceUseDetailMapper.insert(invoiceUseDetail);
            if(dInsert != 1){
                LOGGER.warn("新增失败，参数{}：" + invoiceUseDetail);
                throw new ServiceException(4101);
            }
        }

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
        invoiceApprovalLogMapper.insert(log);
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
        List<InvoiceUseDetailBO> invoiceUseDetailBOList = invoiceUseApplyBO.getInvoiceUseDetailBOList();
        if(invoiceUseDetailBOList == null || invoiceUseDetailBOList.size()<1){
            LOGGER.warn("数据错误{}：" + null);
            throw new ServiceException(4906);
        }
        for (InvoiceUseDetailBO detailBO:invoiceUseDetailBOList){
            InvoiceUseDetail invoiceUseDetail = new InvoiceUseDetail();
            BeanUtils.copyProperties(detailBO,invoiceUseDetail);
            int dUpdate = invoiceUseDetailMapper.update(invoiceUseDetail);
            if(dUpdate != 1){
                LOGGER.warn("修改失败，参数{}：" + invoiceUseDetail);
                throw new ServiceException(4102);
            }
        }
        //加入日志
        insertLog(invoiceUseApply.getId(),invoiceUseApply.getRemark(),"已修改");
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
        InvoiceUseApply invoiceUseApply = new InvoiceUseApply();
        BeanUtils.copyProperties(invoiceUseCheckBO,invoiceUseApply);
        int aUpdate = invoiceUseApplyMapper.update(invoiceUseApply);
        if(aUpdate != 1){
            LOGGER.warn("修改失败，参数{}：" + invoiceUseCheckBO);
            throw new ServiceException(4102);
        }
        List<InvoiceUseDetailBO> useDetailBOList = invoiceUseCheckBO.getInvoiceUseDetailBOList();
        for(InvoiceUseDetailBO detailBO:useDetailBOList){
            InvoiceUseDetail invoiceUseDetail = new InvoiceUseDetail();
            BeanUtils.copyProperties(detailBO,invoiceUseDetail);
            int dUpdate = invoiceUseDetailMapper.update(invoiceUseDetail);
            if(dUpdate != 1){
                LOGGER.warn("修改失败，参数{}：" + invoiceUseDetail);
                throw new ServiceException(4102);
            }
            String[] invoiceRepoIds = detailBO.getInvoiceRepoIds();
            for(String repoId : invoiceRepoIds){
                InvoiceRepoBO repoBO = invoiceRepoRoMapper.selectInvoiceRepo(repoId);
                if(repoBO == null){
                    LOGGER.warn("发票不存在{}：" + repoBO);
                    throw new ServiceException(4907);
                }
                InvoiceDistribute invoiceDistribute = new InvoiceDistribute();
                invoiceDistribute.setId(Utils.uuid());
                invoiceDistribute.setInvoiceRepoId(repoBO.getId());
                invoiceDistribute.setInvoiceCode(repoBO.getInvoiceCode());
                invoiceDistribute.setInvoiceNoStart(repoBO.getInvoiceNoStart());
                invoiceDistribute.setInvoiceNoEnd(repoBO.getInvoiceNoEnd());
                invoiceDistribute.setStatus("0");
                invoiceDistribute.setBook(repoBO.getBook());
                invoiceDistribute.setInvoiceTypeCode(repoBO.getInvoiceTypeCode());
                invoiceDistribute.setDistributeUser(invoiceUseCheckBO.getDistributeUser());
                invoiceDistribute.setDistributeTime(new Date());
                invoiceDistribute.setUseId(invoiceUseCheckBO.getId());
                int insert = invoiceDistributeMapper.insert(invoiceDistribute);
                if(insert != 1){
                    LOGGER.warn("新增失败，参数{}：" + invoiceDistribute);
                    throw new ServiceException(4101);
                }
            }
        }
    }

    @Override
    public void signUseApply(InvoiceDistributeBO invoiceDistributeBO) {
        List<InvoiceDistribute> invoiceDistributeList = invoiceDistributeRoMapper.selectInvoiceDistributeList(invoiceDistributeBO.getUseId());
        for(InvoiceDistribute invoiceDistribute:invoiceDistributeList){
            invoiceDistribute.setStatus("1");
            invoiceDistribute.setSignTime(new Date());
            invoiceDistribute.setSignUser(invoiceDistributeBO.getSignUser());
            int update = invoiceDistributeMapper.update(invoiceDistribute);
            if(update != 1){
                LOGGER.warn("修改失败，参数{}：" + invoiceDistribute);
                throw new ServiceException(4102);
            }
        }
    }

    @Override
    public InvoiceUseDetailBO selectInvoiceRepoNum(String code) {
        return invoiceUseDetailRoMapper.selectInvoiceRepoNum(code);
    }


}
