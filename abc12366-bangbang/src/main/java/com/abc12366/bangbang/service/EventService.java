package com.abc12366.bangbang.service;

import com.abc12366.bangbang.model.event.SingleEventBo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by stuy on 2017/9/13.
 */
public interface EventService {

    SingleEventBo singleEvent(HttpServletRequest request);

    List<SingleEventBo> singleEventList(HttpServletRequest request);
}
