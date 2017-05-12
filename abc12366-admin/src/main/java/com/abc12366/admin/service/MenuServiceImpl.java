package com.abc12366.admin.service;

import com.abc12366.admin.mapper.db1.MenuMapper;
import com.abc12366.admin.mapper.db2.MenuRoMapper;
import com.abc12366.admin.model.Menu;
import com.abc12366.admin.model.bo.MenuBO;
import com.abc12366.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liuguiyao
 * @create 2017-04-27 10:08 AM
 * @since 1.0.0
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuRoMapper menuRoMapper;
    @Override
    public List<Menu> selectList(Menu menu) {
        List<Menu> menus = menuRoMapper.selectList(menu);
        /*if(menus == null||menus.size()==0){
            return null;
        }
        List<MenuBO> menuBOs = new ArrayList<>();
        for(Menu menu:menus){
            MenuBO menuBO = new MenuBO();
            BeanUtils.copyProperties(menu,menuBO);
            menuBOs.add(menuBO);
        }*/
        return menus;
    }

    @Override
    public List<MenuBO> selectByParentId(String parentId) {
        List<Menu> menus = menuRoMapper.selectByParentId(parentId);
        List<Menu> childrenTemp = new ArrayList<>();
        childrenTemp.addAll(menus);
        if(menus==null||menus.size()==0){
            return null;
        }
        for(Menu menu : childrenTemp){
            boolean hasChildren = hasChildren(menu);
            if(hasChildren){
                getChildren(menus, menu);
            }
        }
        List<MenuBO> menuBOs = new ArrayList<>();
        for(Menu menu : menus){
            MenuBO menuBO = new MenuBO();
            BeanUtils.copyProperties(menu ,menuBO);
            menuBOs.add(menuBO);
        }
        return menuBOs;
    }

    @Override
    public MenuBO selectByMenuId(String menuId) {
        Menu menu = menuRoMapper.selectByMenuId(menuId);
        if(menu == null){
            return null;
        }
        MenuBO menuBO = new MenuBO();
        BeanUtils.copyProperties(menu, menuBO);
        return menuBO;
    }

    @Transactional("db1TxManager")
    @Override
    public MenuBO insert(MenuBO menuBO) {
        if(menuBO.getParentId()==null||menuBO.getParentId().trim().equals("")){
            menuBO.setParentId(null);
        }
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuBO, menu);
        menu.setMenuId(Utils.uuid());
        menuMapper.insert(menu);
        BeanUtils.copyProperties(menu, menuBO);
        return menuBO;
    }

    @Transactional("db1TxManager")
    @Override
    public MenuBO delete(String menuId) {
        Menu menu = menuRoMapper.selectByMenuId(menuId);
        if(menu == null){
            return null;
        }
        boolean hasChildren = hasChildren(menu);
        if(hasChildren){
            deleteChildren(menuRoMapper.selectByParentId(menu.getMenuId()));
        }
        menuMapper.delete(menu.getMenuId());
        MenuBO menuBO = new MenuBO();
        BeanUtils.copyProperties(menu, menuBO);
        return menuBO;
    }

    @Transactional("db1TxManager")
    @Override
    public MenuBO update(MenuBO menuBO) {
        Menu menu = menuRoMapper.selectByMenuId(menuBO.getMenuId());
        if(menu == null){
            return null;
        }
        menu.setMenuName(menuBO.getMenuName());
        menu.setMenuUrl(menuBO.getMenuUrl());
        menu.setParentId(menuBO.getParentId());
        menu.setPerms(menuBO.getPerms());
        menu.setType(menuBO.getType());
        menu.setIcon(menuBO.getIcon());
        menu.setSort(menuBO.getSort());
        menu.setStatus(menuBO.isStatus());
        menu.setRemark(menuBO.getRemark());
        menuMapper.update(menu);
        BeanUtils.copyProperties(menu, menuBO);
        return menuBO;
    }

    @Override
    public List<MenuBO> selectFirstLevel() {
        List<Menu> menus = menuRoMapper.selectFirstLevel();
        if(menus==null||menus.size()==0){
            return null;
        }
        List<MenuBO> menuBOs = new ArrayList<>();
        for(Menu menu : menus){
            MenuBO menuBO = new MenuBO();
            BeanUtils.copyProperties(menu, menuBO);
            menuBOs.add(menuBO);
        }
        return menuBOs;
    }

    @Override
    public boolean hasChildren(Menu menu) {
        List<Menu> menus = menuRoMapper.selectByParentId(menu.getMenuId());
        if(menus != null && menus.size()!=0){
            return true;
        }
        return false;
    }

    @Override
    public void deleteChildren(List<Menu> children) {
        if(children!=null && children.size()!=0){
            for(Menu menu : children){
                if(hasChildren(menu)){
                    deleteChildren(menuRoMapper.selectByParentId(menu.getMenuId()));
                }
                menuMapper.delete(menu.getMenuId());
            }
        }
    }

    @Override
    public void getChildren(List<Menu> menus,Menu menu) {
        List<Menu> children = menuRoMapper.selectByParentId(menu.getMenuId());
        menus.addAll(children);
        for(Menu m:children){
            if(hasChildren(m)){
                getChildren(menus,m);
            }
        }
    }

}
