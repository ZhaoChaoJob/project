package com.geotmt.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.geotmt.admin.dao.SysUserRepository;
import com.geotmt.admin.model.jpa.SysMenu;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by geo on 2018/10/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:application-dev.properties"})
@SpringBootTest
public class SysUserServiceImpTest {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Test
    public void getSysUserByName() throws Exception {
//        SysUser sysUser = sysUserService.getSysUserByName("admin","123");
        List<Map<String,Object>> menuListResult = sysUserRepository.getMenu(1001L);
        List<SysMenu> menuList = new ArrayList<>();
        for (Map<String,Object> map :menuListResult) {
            SysMenu sysMenu = new SysMenu();
            BeanUtils.populate(sysMenu,map);
            menuList.add(sysMenu);
        }

        // 最后的结果
        List<SysMenu> menuList2 = new ArrayList<>();
        // 先找到所有的一级菜单
        for (int i = 0; i < menuList.size(); i++) {
            // 一级菜单没有parentId
            if (StringUtils.isBlank(menuList.get(i).getParentId())) {
                menuList2.add(menuList.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysMenu menu : menuList2) {
            menu.setChildren(getChild(menu.getMenuId(), menuList));
        }

        String json = JSONObject.toJSONString(menuList2 );

//        SysMenu sysMenu = JSONObject.toJavaObject((JSON) JSONObject.toJSON(o),SysMenu.class);
        System.out.println("000000"+json);
    }

    /**
     * 递归查找子菜单
     *
     * @param id
     *            当前菜单id
     * @param rootMenu
     *            要查找的列表
     * @return
     */
    private List<SysMenu> getChild(String id, List<SysMenu> rootMenu) {
        // 子菜单
        List<SysMenu> childList = new ArrayList<>();
        for (SysMenu menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(menu.getParentId())) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (SysMenu menu : childList) {// 没有url子菜单还有子菜单
//            if (StringUtils.isBlank(menu.getUrl())) {
                // 递归
                menu.setChildren(getChild(menu.getMenuId(), rootMenu));
//            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

}