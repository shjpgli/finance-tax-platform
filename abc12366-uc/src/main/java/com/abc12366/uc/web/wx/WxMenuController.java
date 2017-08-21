package com.abc12366.uc.web.wx;

import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.weixin.BaseWxRespon;
import com.abc12366.uc.model.weixin.bo.menu.Button;
import com.abc12366.uc.model.weixin.bo.menu.WxMenu;
import com.abc12366.uc.service.IWxMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 微信自定义菜单
 *
 * @author zhushuai 2017-7-28
 */
@Controller
public class WxMenuController {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(WxMenuController.class);

    @Autowired
    IWxMenuService iWxMenuService;

    //从数据库获取菜单信息
    @SuppressWarnings("rawtypes")
    @GetMapping("/wxmenu/db/list")
    public ResponseEntity wxmenudbList() {
        WxMenu wxMenu = iWxMenuService.getWxMenuDb();
        ResponseEntity responseEntity = ResponseEntity.ok(Utils
                .kv("data", wxMenu));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }


    //从数据库获取单个菜单信息
    @SuppressWarnings("rawtypes")
    @GetMapping("/wxmenu/db/{id}")
    public ResponseEntity wxmenudbInfo(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        Button button = iWxMenuService.selectOne(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", button));

        LOGGER.info("{}", responseEntity);
        return responseEntity;

    }

    //添加单个菜单信息
    @SuppressWarnings("rawtypes")
    @PostMapping("/wxmenu/db/creat")
    public ResponseEntity wxmenudbcreat(@Valid @RequestBody Button button) {
        LOGGER.info("{}", button);

        Button v = iWxMenuService.insert(button);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @SuppressWarnings("rawtypes")
    @PutMapping("/wxmenu/db/{id}")
    public ResponseEntity wxmenudbEdit(@PathVariable("id") String id, @Valid @RequestBody Button button) {
        LOGGER.info("{},{}", id, button);

        button.setId(id);
        Button v = iWxMenuService.update(button);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/wxmenu/db/{id}")
    public ResponseEntity wxmenudbDel(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        iWxMenuService.delete(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }


    //从微信服务器获取菜单信息
    @SuppressWarnings("rawtypes")
    @GetMapping("/wxmenu/list")
    public ResponseEntity wxmenuList() {
        WxMenu wxMenu = iWxMenuService.getWxMenu();
        ResponseEntity responseEntity = ResponseEntity.ok(Utils
                .kv("data", wxMenu));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    //创建菜单到微信服务器
    @SuppressWarnings("rawtypes")
    @PostMapping("/wxmenu/create")
    public ResponseEntity wxmenuCreate() {
        WxMenu wxMenu = iWxMenuService.getWxMenuDb();
        BaseWxRespon result = iWxMenuService.creatWxMenu(wxMenu);
        if (0 == result.getErrcode()) {
            return ResponseEntity.ok(Utils.kv());
        } else {
        	return ResponseEntity.ok(Utils.bodyStatus(9999, result.getErrmsg()));
        }
    }

    //删除微信服务器菜单
    @SuppressWarnings("rawtypes")
    @DeleteMapping("/wxmenu/del")
    public ResponseEntity wxmenuDel() {
        BaseWxRespon result = iWxMenuService.delWxMenu();
        if (0 == result.getErrcode()) {
            return ResponseEntity.ok(Utils.kv());
        } else {
        	return ResponseEntity.ok(Utils.bodyStatus(9999, result.getErrmsg()));
        }
    }
}
