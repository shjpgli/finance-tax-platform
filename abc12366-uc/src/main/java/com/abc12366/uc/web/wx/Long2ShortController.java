package com.abc12366.uc.web.wx;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.gateway.web.BaseController;
import com.abc12366.uc.model.weixin.ShortUrl;
import com.abc12366.uc.service.Long2ShortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 长链接转短链接接口
 *
 * @author lijun <ljun51@outlook.com>
 * @date 2018-01-05 1:52 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class Long2ShortController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(WxUserController.class);

    @Autowired
    private Long2ShortService long2ShortService;

    public Long2ShortController(RestTemplate restTemplate) {
        super(restTemplate);
    }

    /**
     * 微信长链接转短链接接口
     *
     * @param longUrl 长url地址
     * @return 短url地址
     */
    @PostMapping("/long2short")
    public ResponseEntity long2short(@RequestBody String longUrl) {
        LOGGER.info("{}", longUrl);
        ShortUrl data = long2ShortService.long2short(longUrl);
        if (data.getErrcode().equals(0) && "ok".equalsIgnoreCase(data.getErrmsg())) {
            return ResponseEntity.ok(Utils.kv("data", data.getShort_url()));
        } else {
            throw new ServiceException(data.getErrcode(), data.getErrmsg());
        }
    }
}
