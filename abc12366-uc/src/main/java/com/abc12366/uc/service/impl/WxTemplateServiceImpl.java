package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.uc.job.wx.WxUserTokenJob;
import com.abc12366.uc.mapper.db1.TemplateMapper;
import com.abc12366.uc.mapper.db2.TemplateRoMapper;
import com.abc12366.uc.mapper.db2.WxGzhRoMapper;
import com.abc12366.uc.model.weixin.BaseWxRespon;
import com.abc12366.uc.model.weixin.bo.template.Template;
import com.abc12366.uc.model.weixin.bo.template.WxTemplates;
import com.abc12366.uc.service.IWxTemplateService;
import com.abc12366.uc.util.wx.WechatUrl;
import com.abc12366.uc.util.wx.WxConnectFactory;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxTemplateServiceImpl implements IWxTemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxTemplateServiceImpl.class);
    @Autowired
    private TemplateRoMapper templateRoMapper;
    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private WxGzhRoMapper gzhRoMapper;

    @Transactional("db1TxManager")
    public boolean synchroTemplate() {
        LOGGER.info("开始同步微信模板消息数据.......");
        Map<String, String> headparamters = new HashMap<String, String>();
        headparamters.put("access_token", gzhRoMapper.selectUserToken(WxUserTokenJob.gzhInfo.getAppid()));
        WxTemplates listRs = WxConnectFactory.get(WechatUrl.TEMPLATEMSG_LIST, headparamters, null, WxTemplates.class);
        templateMapper.deleteAll();
        if (listRs.getErrcode() != 0) {
            return false;
        } else {
            for (int i = 0; i < listRs.getTemplate_list().size(); i++) {
                Template template = listRs.getTemplate_list().get(i);
                template.setLastupdate(new Timestamp(new Date().getTime()));
                templateMapper.insert(template);
            }
            return true;
        }

    }

    @Transactional("db1TxManager")
    public void delete(String id) {
        templateMapper.delete(id);
        Map<String, String> headparamters = new HashMap<String, String>();
        headparamters.put("access_token", gzhRoMapper.selectUserToken(WxUserTokenJob.gzhInfo.getAppid()));
        BaseWxRespon baseWxRespon = WxConnectFactory.get(WechatUrl.TEMPLATEMSG_LIST, headparamters, null,
                BaseWxRespon.class);
        if (baseWxRespon.getErrcode() != 0) {
            throw new ServiceException(4012);
        }
    }

    @Override
    public Template selectOne(String id) {
        Template info = new Template();
        try {
            LOGGER.info("查询单个模板消息:{}", id);
            info = templateRoMapper.selectOne(id);
        } catch (Exception e) {
            LOGGER.error("查询单个模板消息异常：{}", e);
            throw new ServiceException(4234);
        }
        return info;
    }

    @Override
    public List<Template> wxTemplateList(Template template, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<Template> templates = templateRoMapper.selectList(template);
        return templates;
    }

}
