package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.Menu;
import com.geqian.evalution.teachingevalution.entity.User;
import com.geqian.evalution.teachingevalution.service.MenuService;
import com.geqian.evalution.teachingevalution.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户登录管理
 *
 * @author geqian
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单管理")
public class MenuController {

    @Resource
    private MenuService menuService;


    @ApiOperation("当前用户所有菜单列表")
    @GetMapping("/getMenuList")
    public ResponseResult<List<Menu>> getMenuList(@RequestHeader("token") String token) {
        return menuService.getMenuList(token);
    }

    @ApiOperation("所有菜单列表")
    @GetMapping("/getMenuAllList")
    public ResponseResult<List<Menu>> getMenuAllList() {
        return menuService.getMenuAllList();
    }

    @ApiOperation("分页菜单列表")
    @GetMapping("/getMenuPageList")
    public ResponseResult<PageResult<Menu>> getMenuPageList(@RequestHeader("token") String token, PageRequest pageRequest) {
        return menuService.getMenuPageList(token, pageRequest);
    }


    @ApiOperation("更新菜单")
    @PostMapping("/updateMenu")
    public ResponseResult<Object> updateMenu(@RequestBody Menu menu,@RequestHeader("token") String token) {
        return menuService.updateMenu(menu,token);
    }


    @ApiOperation("新增菜单")
    @PostMapping("/addMenu")
    public ResponseResult<Object> addMenu(@RequestBody Menu menu, @RequestHeader("token") String token) {
        return menuService.addMenu(menu,token);
    }

    @ApiOperation("获取菜单目录")
    @GetMapping("/getMenuDirList")
    public ResponseResult<Object> getMenuDirList(@RequestHeader("token") String token) {
        return menuService.getMenuDirList(token);
    }


    @ApiOperation("删除菜单")
    @GetMapping("/deleteMenu")
    public ResponseResult<Object> deleteMenu(@RequestParam("menuId") Integer menuId) {
        return menuService.deleteMenu(menuId);
    }
}