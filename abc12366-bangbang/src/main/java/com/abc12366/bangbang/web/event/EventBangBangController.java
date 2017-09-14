package com.abc12366.bangbang.web.event;

import com.abc12366.bangbang.model.bo.FollowUserBO;
import com.abc12366.bangbang.model.event.SingleEventBo;
import com.abc12366.bangbang.model.event.SingleEventListBo;
import com.abc12366.bangbang.model.event.SingleEventOneBo;
import com.abc12366.bangbang.service.EventService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.gateway.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by stuy on 2017/9/13.
 */
@RestController
@RequestMapping(path = "/event", headers = Constant.VERSION_HEAD + "=1")
public class EventBangBangController {

    @Autowired
    private EventService eventService;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 查询最新的一个活动
     * @param request
     * @return
     */
    @GetMapping(path = "/singleevent")
    public ResponseEntity singleevent( HttpServletRequest request) {
        SingleEventBo singleEvent = eventService.singleEvent(request);
        return ResponseEntity.ok(Utils.kv("data", singleEvent));
    }


    /**
     * 查询活动列表，不需要分页
     * @param request
     * @return
     */
    @GetMapping(path = "/singleeventlist")
    public ResponseEntity singleeventlist( HttpServletRequest request) {
        List<SingleEventBo> dataList = eventService.singleEventList(request);
        return ResponseEntity.ok(Utils.kv("dataList", dataList));
    }


}
