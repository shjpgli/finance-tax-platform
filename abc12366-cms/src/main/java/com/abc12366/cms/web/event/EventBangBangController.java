package com.abc12366.cms.web.event;

import com.abc12366.cms.model.event.SingleEventBo;
import com.abc12366.cms.service.EventBangBangService;
import com.abc12366.cms.service.EventService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by stuy on 2017/9/13.
 */
@RestController
@RequestMapping(path = "/bangbang/event", headers = Constant.VERSION_HEAD + "=1")
public class EventBangBangController {

    @Autowired
    private EventBangBangService eventService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(path = "/singleevent")
    public ResponseEntity singleevent( HttpServletRequest request) {
        SingleEventBo singleevent = eventService.singleEvent();
        return ResponseEntity.ok(Utils.kv("data", singleevent));
    }


    @GetMapping(path = "/singleeventlist")
    public ResponseEntity singleeventlist( HttpServletRequest request) {
        List<SingleEventBo> dataList = eventService.singleEventList();
        return ResponseEntity.ok(Utils.kv("dataList", dataList));
    }
}
