package com.abc12366.uc.web.wx;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.weixin.WxActivity;
import com.abc12366.uc.model.weixin.WxRedEnvelop;
import com.abc12366.uc.model.weixin.bo.redpack.WxLotteryBO;
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

    @GetMapping()
    public ResponseEntity selectList(@RequestParam(value = "activityId", required = false) String activityId,
                                     @RequestParam(value = "sendStatus", required = false) String sendStatus,
                                     @RequestParam(value = "receiveStatus", required = false) String receiveStatus,
                                     @RequestParam(value = "openId", required = false) String openId,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        WxRedEnvelop redEnvelop = new WxRedEnvelop.Builder()
                .activityId(activityId)
                .sendStatus(sendStatus)
                .receiveStatus(receiveStatus)
                .openId(openId)
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
     */
    @GetMapping("/{activityId}")
    public ResponseEntity insert(@PathVariable("activityId") String activityId) {
        LOGGER.info("{}", activityId);
        WxRedEnvelop data = iActivityService.generateSecret(activityId);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 抽奖
     */
    @PostMapping()
    public ResponseEntity lottery(@Valid @RequestBody WxLotteryBO lotteryBO) {

        LOGGER.info("{}", lotteryBO);
        WxRedEnvelop data = iActivityService.lottery(lotteryBO);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }
}
