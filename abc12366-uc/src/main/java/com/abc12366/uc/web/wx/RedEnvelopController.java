package com.abc12366.uc.web.wx;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.weixin.WxRedEnvelop;
import com.abc12366.uc.model.weixin.bo.Id;
import com.abc12366.uc.model.weixin.bo.redpack.WxLotteryBO;
import com.abc12366.uc.model.weixin.bo.redpack.WxRedEnvelopBO;
import com.abc12366.uc.model.weixin.bo.redpack.WxRedEnvelopUpdateBO;
import com.abc12366.uc.service.IActivityService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 微信红包口令
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-14 10:30 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/wx/redpack", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class RedEnvelopController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedEnvelopController.class);

    @Autowired
    private IActivityService iActivityService;

    /**
     * 抽奖记录列表
     *
     * @param activityId    活动ID
     * @param sendStatus    发送状态
     * @param receiveStatus 接收状态
     * @param openId        微信OPENID
     * @param secret        红包口令
     * @param page          当前页
     * @param size          每页大小
     * @return ResponseEntity
     */
    @GetMapping()
    public ResponseEntity selectList(@RequestParam(value = "activityId", required = false) String activityId,
                                     @RequestParam(value = "sendStatus", required = false) String sendStatus,
                                     @RequestParam(value = "receiveStatus", required = false) String receiveStatus,
                                     @RequestParam(value = "openId", required = false) String openId,
                                     @RequestParam(value = "secret", required = false) String secret,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {

        WxRedEnvelop redEnvelop = new WxRedEnvelop.Builder()
                .activityId(activityId)
                .sendStatus(sendStatus)
                .receiveStatus(receiveStatus)
                .openId(openId)
                .secret(secret)
                .build();
        LOGGER.info("{},{},{}", redEnvelop, page, size);
        List<WxRedEnvelop> dataList = iActivityService.selectRedEnvelopList(redEnvelop, page, size);

        PageInfo<WxRedEnvelop> pageInfo = new PageInfo<>(dataList);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(),
                "total", pageInfo.getTotal()));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 根据活动ID生成口令
     *
     * @param activityId 活动ID
     * @return ResponseEntity
     */
    @GetMapping("/{activityId}")
    public ResponseEntity insert(@PathVariable("activityId") String activityId) {
        LOGGER.info("{}", activityId);
        WxRedEnvelopBO data = iActivityService.generateSecret(activityId);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 根据口令ID修改口令记录
     *
     * @param id 口令ID
     * @param bo 更新口令对象
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String id,
                                 @Valid @RequestBody WxRedEnvelopUpdateBO bo) {
        bo.setId(id);
        LOGGER.info("{}", bo);

        WxRedEnvelop data = iActivityService.updateSecret(bo);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 抽奖
     *
     * @param lotteryBO WxLotteryBO
     * @return ResponseEntity
     */
    @PostMapping()
    public ResponseEntity lottery(@Valid @RequestBody WxLotteryBO lotteryBO) {

        LOGGER.info("{}", lotteryBO);
        WxRedEnvelop data = iActivityService.lottery(lotteryBO);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 查询微信红包信息
     *
     * @param id 红包口令表主键
     * @return ResponseEntity
     */
    @GetMapping("/hbinfo/{id}")
    public ResponseEntity gethbinfo(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        WxRedEnvelop data = iActivityService.gethbinfo(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 导入红包数据
     *
     * @param redEnvelopList List<WxRedEnvelop>
     * @return ResponseEntity
     */
    @PostMapping("/import")
    public ResponseEntity importJSON(@Valid @RequestBody List<WxRedEnvelopBO> redEnvelopList) {
        LOGGER.info("{}", redEnvelopList);
        iActivityService.importJSON(redEnvelopList);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 对于发送失败的红包，重新发送
     *
     * @param id 红包口令表主键
     * @return ResponseEntity
     */
    @PutMapping("/resend/{id}")
    public ResponseEntity resend(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        WxRedEnvelop data = iActivityService.resend(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 删除未抽奖的口令
     *
     * @param id 红包口令表主键
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        iActivityService.deleteSecret(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 批量删除未抽奖的口令
     *
     * @param ids List<Id>
     * @return ResponseEntity
     */
    @PostMapping("/batchdelete")
    public ResponseEntity batchDelete(@Valid @RequestBody List<Id> ids) {
        LOGGER.info("{}", ids);
        iActivityService.batchDeleteSecret(ids);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }
}
