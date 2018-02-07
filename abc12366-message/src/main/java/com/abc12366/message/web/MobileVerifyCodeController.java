package com.abc12366.message.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.message.model.bo.GetCodeParam;
import com.abc12366.message.model.bo.VerifyParam;
import com.abc12366.message.service.MobileVerifyCodeService;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * 验证码发送校验控制器
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-20
 * Time: 0:08
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class MobileVerifyCodeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobileVerifyCodeController.class);

    @Autowired
    private MobileVerifyCodeService moboleVerifyCodeService;

    /**
     * 获取验证码接口
     *
     * @param getCodeParam 获取验证码参数
     * @return ResponseEntity
     * @throws IOException
     */
    @PostMapping(path = "/getcode")
    public ResponseEntity getCode(@Valid @RequestBody GetCodeParam getCodeParam) throws IOException {
        LOGGER.info("{}", getCodeParam);
        moboleVerifyCodeService.getCode(getCodeParam.getType().trim(), getCodeParam.getPhone().trim());
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 验证码校验接口
     *
     * @param verifyParam 验证码校验参数
     * @return ResponseEntity
     * @throws IOException
     */
    @PostMapping(path = "/verify")
    public ResponseEntity verify(@Valid @RequestBody VerifyParam verifyParam) throws IOException {
        LOGGER.info("{}", verifyParam);
        moboleVerifyCodeService.verify(verifyParam);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 获取注册验证码接口（校验号码是否已被注册）
     *
     * @param getCodeParam 获取注册验证码参数
     * @return ResponseEntity
     * @throws IOException
     */
    @PostMapping(path = "/regis/code")
    public ResponseEntity getRegisCode(@Valid @RequestBody GetCodeParam getCodeParam) throws IOException {
        LOGGER.info("{}", getCodeParam);
        moboleVerifyCodeService.getRegisCode(getCodeParam.getType().trim(), getCodeParam.getPhone().trim());
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 调用阿里接口查询手机短信发送情况
     * @param phone 手机号码
     * @param bizId 短信流水
     * @param logId 日志id
     * @param sendDate 发送日期
     * @return ResponseEntity
     */
    @GetMapping(path = "/ali/detail")
    public ResponseEntity querySendDetails(@RequestParam String phone,@RequestParam String bizId,@RequestParam String logId,@RequestParam String sendDate){
        LOGGER.info("调用阿里短信发送情况查询接口：{}:{}:{}:{}", phone,bizId,logId,sendDate);
        QuerySendDetailsResponse.SmsSendDetailDTO detail =  moboleVerifyCodeService.querySendDetails(phone,bizId,logId,sendDate);
        return ResponseEntity.ok(Utils.kv("data",detail));
    }
}
