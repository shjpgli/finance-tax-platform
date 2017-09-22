package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.uc.service.PrivilegeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-22
 * Time: 10:18
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class PrivilegeItemController {

    @Autowired
    private PrivilegeItemService privilegeItemService;
}
