package com.abc12366.admin.web;

import com.abc12366.admin.model.Menu;
import com.abc12366.admin.model.bo.MenuBO;
import com.abc12366.admin.model.bo.MenuUpdateBO;
import com.abc12366.admin.service.MenuService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author liuguiyao
 * @create 2017-04-27 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/menu", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class MenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictController.class);

    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "menuName", required = false) String menuName,
                                     @RequestParam(value = "parentId", required = false) String parentId,
                                     @RequestParam(value = "type", required = false) String type) {
        Menu menu = new Menu();
        menu.setMenuName(menuName);
        menu.setParentId(parentId);
        menu.setType(type);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<MenuBO> menuList = menuService.selectList(menu);
        LOGGER.info("{}", menuList);
        return menuList == null ?
                ResponseEntity.ok(null) :
                ResponseEntity.ok(Utils.kv("menuList", (Page) menuList, "total", ((Page) menuList).getTotal()));
    }

    @GetMapping(path = "/parent/{parentId}")
    public ResponseEntity selectFirstLevel(@PathVariable String parentId) {
        List<MenuBO> menuBOs = menuService.selectFirstLevel();
        LOGGER.info("{}", menuBOs);
        return ResponseEntity.ok(menuBOs);
    }

    @GetMapping(path = "/project/{parentId}")
    public ResponseEntity selectByParentId(@PathVariable String parentId) {
        LOGGER.info("{}", parentId);
        MenuBO menuBOs = menuService.selectByParentId(parentId);
        LOGGER.info("{}", menuBOs);
        return ResponseEntity.ok(menuBOs);
    }

    @GetMapping(path = "/{menuId}")
    public ResponseEntity selectOne(@PathVariable String menuId) {
        LOGGER.info("{}", menuId);
        MenuBO menuBO = menuService.selectByMenuId(menuId);
        LOGGER.info("{}", menuBO);
        return ResponseEntity.ok(menuBO);
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody MenuBO menuBO) {
        LOGGER.info("{}", menuBO);
        MenuBO menuBO1 = menuService.insert(menuBO);
        LOGGER.info("{}", menuBO1);
        return ResponseEntity.ok(menuBO1);
    }

    @DeleteMapping(path = "/{menuId}")
    public ResponseEntity delete(@PathVariable String menuId) {
        LOGGER.info("{}", menuId);
        menuService.delete(menuId);
        return ResponseEntity.ok(null);
    }

    @PutMapping(path = "/{menuId}")
    public ResponseEntity update(@Valid @RequestBody MenuBO menuBO, @PathVariable String menuId) {
        LOGGER.info("{}", menuBO, menuId);
        menuBO.setMenuId(menuId);
        MenuBO menuBO1 = menuService.update(menuBO);
        LOGGER.info("{}", menuBO1);
        return ResponseEntity.ok(menuBO1);
    }

    /**
     * 启用、禁用
     * @return
     */
    @PutMapping(path = "/enable")
    public ResponseEntity enable(@Valid @RequestBody MenuUpdateBO updateBO) {
        LOGGER.info("{}",updateBO);
        menuService.enable(updateBO);
        return ResponseEntity.ok(null);
    }

    /**
     * 启用、禁用
     * @return
     */
    @PutMapping(path = "/disableAll")
    public ResponseEntity disableAll() {
        menuService.disableAll();
        return ResponseEntity.ok(null);
    }
}
