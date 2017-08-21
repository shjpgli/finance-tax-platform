package com.abc12366.uc.web.wx;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.weixin.bo.template.Template;
import com.abc12366.uc.service.IWxTemplateService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 微信模板消息
 *
 * @author zhushuai 2017-8-3
 */
@Controller
public class WxTemplateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxTemplateController.class);

    @Autowired
    private IWxTemplateService templateService;

    //同步微信模板消息到数据库
    @SuppressWarnings("rawtypes")
    @RequestMapping("/wxTemplate/synchro")
    public ResponseEntity synchroTemplate(HttpServletRequest request) {
        if (templateService.synchroTemplate()) {
            return ResponseEntity.ok(Utils.kv());
        } else {
            throw new ServiceException(4117);
        }
    }

    //从数据库获取模板消息
    @SuppressWarnings("rawtypes")
    @GetMapping("/wxTemplate/list")
    public ResponseEntity wxTemplateList(Template template,
                                         @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                         @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{},{},{}", template, page, size);
        List<Template> dataList = templateService.wxTemplateList(template, page, size);

        PageInfo<Template> pageInfo = new PageInfo<Template>(dataList);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(),
                "total", pageInfo.getTotal()));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    //从数据库获取单个菜单信息
    @SuppressWarnings("rawtypes")
    @GetMapping("/wxTemplate/{id}")
    public ResponseEntity wxTemplateInfo(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        Template template = templateService.selectOne(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", template));

        LOGGER.info("{}", responseEntity);
        return responseEntity;

    }

    // 删除数据库以及微信端模板消息
    @SuppressWarnings("rawtypes")
    @DeleteMapping("/wxTemplate/{id}")
    public ResponseEntity templateDel(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        templateService.delete(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    //模板消息发送
    @SuppressWarnings("rawtypes")
    @PostMapping("/wxTemplate/send/{temp_id}")
    public ResponseEntity templateSend(@PathVariable("temp_id") String temp_id,
    		@RequestBody Map<String,String> dataList) {
        return templateService.templateSend(temp_id,dataList);
    }
   
}
