package com.abc12366.uc.service.invoice.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.InvoiceDetailMapper;
import com.abc12366.uc.mapper.db1.InvoiceRepoMapper;
import com.abc12366.uc.mapper.db2.InvoiceDetailRoMapper;
import com.abc12366.uc.mapper.db2.InvoiceRepoRoMapper;
import com.abc12366.uc.model.invoice.InvoiceDetail;
import com.abc12366.uc.model.invoice.InvoiceRepo;
import com.abc12366.uc.model.invoice.bo.InvoiceRepoBO;
import com.abc12366.uc.service.invoice.InvoiceRepoService;
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
public class InvoiceRepoServiceImpl implements InvoiceRepoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepoServiceImpl.class);

    @Autowired
    private InvoiceRepoRoMapper invoiceRepoRoMapper;

    @Autowired
    private InvoiceRepoMapper invoiceRepoMapper;

    @Autowired
    private InvoiceDetailRoMapper invoiceDetailRoMapper;


    @Autowired
    private InvoiceDetailMapper invoiceDetailMapper;

    @Override
    public List<InvoiceRepoBO> selectList(InvoiceRepoBO invoiceRepoBO) {
        return invoiceRepoRoMapper.selectList(invoiceRepoBO);
    }

    @Override
    public InvoiceRepoBO selectInvoiceRepo(InvoiceRepo invoiceRepo) {
        return invoiceRepoRoMapper.selectInvoiceRepo(invoiceRepo.getId());
    }

    @Override
    public void delete(String id) {
        int delete = invoiceRepoMapper.delete(id);
        if(delete != 1){
            LOGGER.warn("删除失败，参数{}：" + id);
            throw new ServiceException(4103);
        }
        invoiceDetailMapper.delete(id);
    }

    @Override
    public InvoiceRepoBO add(InvoiceRepoBO invoiceRepoBO) {
        if(invoiceRepoBO == null){
            LOGGER.warn("数据错误{}：" + null);
            throw new ServiceException(4906);
        }

        Date date = new Date();
        invoiceRepoBO.setCreateTime(date);
        int length = invoiceRepoBO.getInvoiceNoStart().length();
        int start = Integer.parseInt(invoiceRepoBO.getInvoiceNoStart());
        int end = Integer.parseInt(invoiceRepoBO.getInvoiceNoEnd());
        int count = invoiceRepoBO.getShare();
        String ids[] = invoiceRepoBO.getId().split("-");
        int repoId = Integer.parseInt(ids[1]);
        int num = 0;
        for(int i = start;i < end; i ++ ){
            if(i % count == 0){
                System.out.println("号码起："+i+"   号码止："+(i + count - 1)+"  Id = "+(ids[0]+repoId));
                invoiceRepoBO.setId(ids[0]+repoId);

                invoiceRepoBO.setInvoiceNoStart(autoGenericCode(i,length));
                num = (i + count - 1);
                InvoiceRepo invoiceRepo = new InvoiceRepo();
                BeanUtils.copyProperties(invoiceRepoBO,invoiceRepo);

                int insert = invoiceRepoMapper.insert(invoiceRepo);
                if(insert != 1){
                    LOGGER.warn("新增失败，参数{}：" + invoiceRepoBO);
                    throw new ServiceException(4101);
                }
                invoiceRepoBO.setInvoiceNoEnd(autoGenericCode((num),length));
                for(int j = i;j <= num;j++){
                    InvoiceDetail invoiceDetail = new InvoiceDetail();
                    invoiceDetail.setId(Utils.uuid());
                    invoiceDetail.setCreateTime(date);
                    invoiceDetail.setLastUpdate(date);
                    invoiceDetail.setInvoiceNo(autoGenericCode(j,length));
                    invoiceDetail.setInvoiceCode(invoiceRepoBO.getInvoiceCode());
                    invoiceDetail.setInvoiceRepoId(ids[0]+repoId);
                    int dInsert = invoiceDetailMapper.insert(invoiceDetail);
                    if(dInsert != 1){
                        LOGGER.warn("新增失败，参数{}：" + invoiceRepoBO);
                        throw new ServiceException(4101);
                    }
                }
                repoId ++;
            }
        }

        return invoiceRepoBO;
    }

    /**
     * 不够位数的在前面补0，保留num的长度位数字
     * @param code 内容
     * @param num  长度
     * @return
     */
    private static String autoGenericCode(int code, int num) {
        return String.format("%0" + num + "d", code + 1);
    }

    @Override
    public InvoiceRepoBO update(InvoiceRepoBO invoiceRepoBO) {
        return null;
    }

    @Override
    public String selectRepoId(String invoiceTypeCode) {
        String repoId;
        InvoiceRepo invoiceRepo = invoiceRepoRoMapper.selectRepoId(invoiceTypeCode);
        if(invoiceRepo == null){
            repoId = invoiceTypeCode;
        }else{
            repoId = invoiceRepo.getInvoiceTypeCode();
        }
        return repoId;
    }

    @Override
    public List<InvoiceDetail> selectInvoiceDetailList(InvoiceDetail invoiceDetail) {
        return invoiceDetailRoMapper.selectInvoiceDetailList(invoiceDetail);
    }

    @Override
    public void deleteInvoiceDetail(String id) {
        InvoiceDetail invoiceDetail = invoiceDetailRoMapper.selectByPrimaryKey(id);
        if (invoiceDetail != null) {
            if ("1".equals(invoiceDetail.getStatus()) || "2".equals(invoiceDetail.getStatus())) {
                LOGGER.info("{发票在分配中或已使用，不能删除}：{}", id);
                throw new ServiceException(4174);
            }
            int rDel = invoiceDetailMapper.delete(id);
            if (rDel != 1) {
                LOGGER.info("{发票详情信息删除失败}：{}", id);
                throw new ServiceException(4172);
            }
        } else {
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
        if (update != 1) {
            LOGGER.info("{发票详情信息作废失败}：{}", id);
            throw new ServiceException(4176);
        }
    }

    @Override
    public InvoiceRepoBO selectInvoiceRepoNum(String code) {
        InvoiceRepoBO invoiceRepoBO = invoiceRepoRoMapper.selectInvoiceRepoNum(code);
        return invoiceRepoBO;
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
