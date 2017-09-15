package com.abc12366.bangbang.web.event;

import com.abc12366.bangbang.model.event.*;
import com.abc12366.bangbang.service.EventService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stuy on 2017/9/13.
 */
@RestController
@RequestMapping(path = "/event", headers = Constant.VERSION_HEAD + "=1")
public class EventBangBangController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventBangBangController.class);

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


    @GetMapping(path = "/details/{eventid}")
    public ResponseEntity saveeventrecord( HttpServletRequest request,@PathVariable String eventid,
                                           @RequestParam(value = "userid", defaultValue = "") String userid) {
        EventIdBo data = eventService.saveeventrecord(request, eventid,userid);
        return ResponseEntity.ok(Utils.kv("data", data));
    }

    @PostMapping(path = "/saveeventrecord")
    public ResponseEntity saveeventrecord( HttpServletRequest request,@RequestBody EventRecordBbBo eventRecordBbBo) {
        LOGGER.info("{}", eventRecordBbBo);
        eventRecordBbBo=eventService.addEventRecord(request,eventRecordBbBo);
        return ResponseEntity.ok(Utils.kv("dataList", eventRecordBbBo));
    }

    @PostMapping(path = "/saveEventApply")
    public ResponseEntity saveeventrecord( HttpServletRequest request,@RequestBody EventApplyBbBo eventApplyBbBo) {
//        Map maps = new BeanMap(eventApplyBbBo);
        LOGGER.info("{}", eventApplyBbBo);
        eventApplyBbBo =eventService.saveEventApply(request,eventApplyBbBo);
        return ResponseEntity.ok(Utils.kv("data", eventApplyBbBo));
    }

}
