package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.JwtUtils;
import com.geqian.evalution.teachingevalution.entity.Menu;
import com.geqian.evalution.teachingevalution.entity.RoleMenu;
import com.geqian.evalution.teachingevalution.mapper.MenuMapper;
import com.geqian.evalution.teachingevalution.mapper.RoleMapper;
import com.geqian.evalution.teachingevalution.mapper.RoleMenuMapper;
import com.geqian.evalution.teachingevalution.service.MenuService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
 * @createDate 2025-09-09 17:19:22
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {


    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RoleMapper roleMapper;


    @Override
    public ResponseResult<List<Menu>> getMenuList(String token) {
        String userId = JwtUtils.parseToken(token);
        List<Menu> menuList = this.baseMapper.selectMenuList(userId, null,null);
        if (!CollectionUtils.isEmpty(menuList)){
            menuList = menuList.stream().sorted((a,b)->a.getOrderBy()-b.getOrderBy()).collect(Collectors.toList());
            List<Menu> topLeveMenuList = menuList.stream().filter(menu -> Objects.equals(menu.getParentId(), 0)).collect(Collectors.toList());
            for (Menu topLeveMenu : topLeveMenuList) {
                topLeveMenu.setChildren(getChildrenMenu(menuList, topLeveMenu.getMenuId()));
            }
            return ResponseResult.success(topLeveMenuList);
        }
        return ResponseResult.success(new ArrayList<>());

    }

    @Override
    public ResponseResult<PageResult<Menu>> getMenuPageList(String token, PageRequest pageRequest) {
        String userId = JwtUtils.parseToken(token);
        List<Menu> menuList;
        List<Menu> topLeveMenuList;
        if (StringUtils.hasText(pageRequest.getKey())){
            menuList = this.baseMapper.selectMenuList(userId, null,null).stream().sorted((a,b)->a.getOrderBy()-b.getOrderBy()).collect(Collectors.toList());
            PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
            topLeveMenuList = this.baseMapper.selectMenuList(userId, 0,pageRequest.getKey());
        } else {
            menuList = this.baseMapper.selectMenuList(userId, null,null).stream().sorted((a,b)->a.getOrderBy()-b.getOrderBy()).collect(Collectors.toList());
            PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
            topLeveMenuList = this.baseMapper.selectMenuList(userId, 0,null);
        }
        topLeveMenuList = topLeveMenuList.stream().sorted((a,b)->a.getOrderBy()-b.getOrderBy()).collect(Collectors.toList());
        PageInfo<Menu> pageInfo = new PageInfo<>(topLeveMenuList);
        PageResult<Menu> pageResult = PageUtils.getPageResult(pageInfo);
        for (Menu topLeveMenu : pageResult.getDataList()) {
            topLeveMenu.setChildren(getChildrenMenu(menuList, topLeveMenu.getMenuId()));
        }

        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> updateMenu(Menu menu,String token) {
        try {
            if (Objects.nonNull(menu)) {
                String userId = JwtUtils.parseToken(token);
                menu.setUpdateTime(new Date());
                menu.setUpdateBy(Long.valueOf(userId));
                this.baseMapper.updateById(menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail();
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> addMenu(Menu menu, String token) {
        try {
            if (StringUtils.hasText(token)) {
                String userId = JwtUtils.parseToken(token);
                if (Objects.nonNull(menu)) {
                    menu.setCreateBy(Long.valueOf(userId));
                    menu.setUpdateBy(Long.valueOf(userId));
                    Date date = new Date();
                    menu.setUpdateTime(date);
                    menu.setCreateTime(date);
                    List<Long> roleIdList = roleMapper.selectRoleIdList(Long.valueOf(userId));
                    this.baseMapper.insertReturnMenuId(menu);
                    for (Long roleId : roleIdList) {
                        RoleMenu roleMenu = new RoleMenu();
                        roleMenu.setRoleId(roleId);
                        roleMenu.setMenuId(menu.getMenuId());
                        roleMenuMapper.insert(roleMenu);
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseResult.fail();
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> getMenuDirList(String token) {
        String userId = JwtUtils.parseToken(token);
        List<Menu> menuList = this.baseMapper.selectMenuList(userId, null,null);

        if (!CollectionUtils.isEmpty(menuList)) {
            List<Menu> menuDirList = menuList.stream().filter(menu -> !StringUtils.hasText(menu.getComponent())).collect(Collectors.toList());
            List<Menu> topLeveMenuDirList = menuDirList.stream().filter(menu -> Objects.equals(menu.getParentId(), 0)).collect(Collectors.toList());
            for (Menu topLeveMenuDir : topLeveMenuDirList) {
                topLeveMenuDir.setChildren(getChildrenMenu(menuDirList, topLeveMenuDir.getMenuId()));
            }
            return ResponseResult.success(topLeveMenuDirList);
        }
        return ResponseResult.success(new ArrayList<>());
    }

    @Override
    public ResponseResult<Object> deleteMenu(Integer menuId) {
        if (Objects.nonNull(menuId)) {
            this.baseMapper.deleteById(menuId);
            LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RoleMenu::getMenuId, menuId);
            roleMenuMapper.delete(wrapper);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<List<Menu>> getMenuAllList() {
        List<Menu> menuList = this.baseMapper.selectList(null);
        if (!CollectionUtils.isEmpty(menuList)){
            menuList = menuList.stream().sorted((a,b)->a.getOrderBy()-b.getOrderBy()).collect(Collectors.toList());
            List<Menu> topLeveMenuList = menuList.stream().filter(menu -> Objects.equals(menu.getParentId(), 0)).collect(Collectors.toList());
            for (Menu topLeveMenu : topLeveMenuList) {
                topLeveMenu.setChildren(getChildrenMenu(menuList, topLeveMenu.getMenuId()));
            }
            return ResponseResult.success(topLeveMenuList);
        }
        return ResponseResult.success(new ArrayList<>());
    }


    private List<Menu> getChildrenMenu(List<Menu> menuList, long menuId) {
        System.out.println(menuId);
        List<Menu> nextLevelMenuList = menuList.stream().filter(menu -> menu.getParentId() == menuId).collect(Collectors.toList());
        for (Menu nextLevelMenu : nextLevelMenuList) {
            nextLevelMenu.setChildren(getChildrenMenu(menuList, nextLevelMenu.getMenuId()));
        }
        return nextLevelMenuList;
    }
}




