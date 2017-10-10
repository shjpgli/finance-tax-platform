package com.abc12366.uc.web.admin;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.admin.Menu;
import com.abc12366.uc.model.admin.bo.MenuBO;
import com.abc12366.uc.model.admin.bo.MenuUpdateBO;
import com.abc12366.uc.service.admin.MenuService;
import com.abc12366.uc.web.DictController;
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
@RequestMapping(path = "/admin/menu", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class MenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictController.class);

    @Autowired
    private MenuService menuService;

    /**
     * 列表查询
     * @param pageNum
     * @param pageSize
     * @param menuName  菜单名称
     * @param parentId  父节点ID
     * @param type  类型
     * @return
     */
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
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) menuList, "total", ((Page) menuList).getTotal()));
    }

    /**
     * 根据父节点查询所有
     * @param parentId
     * @return
     */
    @GetMapping(path = "/parent/{parentId}")
    public ResponseEntity selectFirstLevel(@PathVariable String parentId) {
        List<MenuBO> menuBOs = menuService.selectFirstLevel();
        LOGGER.info("{}", menuBOs);
        return ResponseEntity.ok(Utils.kv("dataList", menuBOs));
    }

    /**
     * 根据父节点查询树形机构
     * @param parentId
     * @return
     */
    @GetMapping(path = "/project/{parentId}")
    public ResponseEntity selectByParentId(@PathVariable String parentId) {
        LOGGER.info("{}", parentId);
        MenuBO menuBO = menuService.selectByParentId(parentId);
        LOGGER.info("{}", menuBO);
        return ResponseEntity.ok(Utils.kv("data", menuBO));
    }

    /**
     * 根据ID查询
     * @param menuId
     * @return
     */
    @GetMapping(path = "/{menuId}")
    public ResponseEntity selectOne(@PathVariable String menuId) {
        LOGGER.info("{}", menuId);
        MenuBO menuBO = menuService.selectByMenuId(menuId);
        LOGGER.info("{}", menuBO);
        return ResponseEntity.ok(Utils.kv("data", menuBO));
    }

    /**
     * 新增
     * @param mBO
     * @return
     */
    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody MenuBO mBO) {
        LOGGER.info("{}", mBO);
        MenuBO menuBO = menuService.insert(mBO);
        LOGGER.info("{}", menuBO);
        return ResponseEntity.ok(Utils.kv("data", menuBO));
    }

    /**
     * 删除
     * @param menuId
     * @return
     */
    @DeleteMapping(path = "/{menuId}")
    public ResponseEntity delete(@PathVariable String menuId) {
        LOGGER.info("{}", menuId);
        menuService.delete(menuId);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 修改
     * @param bo
     * @param menuId
     * @return
     */
    @PutMapping(path = "/{menuId}")
    public ResponseEntity update(@Valid @RequestBody MenuBO bo, @PathVariable String menuId) {
        LOGGER.info("{}", bo, menuId);
        bo.setMenuId(menuId);
        MenuBO menuBO = menuService.update(bo);
        LOGGER.info("{}", menuBO);
        return ResponseEntity.ok(Utils.kv("data", menuBO));
    }

    /**
     * 启用、禁用
     *
     * @return
     */
    @PutMapping(path = "/enable")
    public ResponseEntity enable(@Valid @RequestBody MenuUpdateBO updateBO) {
        LOGGER.info("{}", updateBO);
        menuService.enable(updateBO);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 启用、禁用
     *
     * @return
     */
    @PutMapping(path = "/disableAll")
    public ResponseEntity disableAll() {
        menuService.disableAll();
        return ResponseEntity.ok(Utils.kv());
    }
}
