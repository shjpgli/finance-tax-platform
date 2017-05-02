package com.abc12366.admin.web;

import com.abc12366.admin.model.bo.MenuBO;
import com.abc12366.admin.service.MenuService;
import com.abc12366.common.util.Constant;
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
@RequestMapping(path = "/menu", headers = Constant.VERSION_HEAD + "=1")
public class MenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictController.class);

    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity selectList() {
        List<MenuBO> menuBOs = menuService.selectList();
        LOGGER.info("{}", menuBOs);
        return ResponseEntity.ok(menuBOs);
    }

    @GetMapping(path = "/firstLevel")
    public ResponseEntity selectFirstLevel() {
        List<MenuBO> menuBOs = menuService.selectFirstLevel();
        LOGGER.info("{}", menuBOs);
        return ResponseEntity.ok(menuBOs);
    }

    @GetMapping(path = "/{parentId}")
    public ResponseEntity selectByParentId(@PathVariable String parentId) {
        LOGGER.info("{}", parentId);
        List<MenuBO> menuBOs = menuService.selectByParentId(parentId);
        LOGGER.info("{}", menuBOs);
        return ResponseEntity.ok(menuBOs);
    }

    @GetMapping(path = "/one/{menuId}")
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
        MenuBO menuBO = menuService.delete(menuId);
        LOGGER.info("{}", menuBO);
        return ResponseEntity.ok(menuBO);
    }

    @PutMapping(path = "/{menuId}")
    public ResponseEntity update(@Valid @RequestBody MenuBO menuBO, @PathVariable String menuId) {
        LOGGER.info("{}", menuBO, menuId);
        menuBO.setMenuId(menuId);
        MenuBO menuBO1 = menuService.update(menuBO);
        LOGGER.info("{}", menuBO1);
        return ResponseEntity.ok(menuBO1);
    }
}
