package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.dto.RoleDto;
import com.geqian.evalution.teachingevalution.entity.User;
import com.geqian.evalution.teachingevalution.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录管理
 *
 * @author geqian
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Resource
    private UserService userService;


    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseResult<Object> login(@RequestBody User user) {
        return userService.login(user);
    }

    @ApiOperation("返回用户信息")
    @GetMapping("/getUserInfo")
    public ResponseResult<User> info(@RequestParam("token") String token) {
        User user = userService.info(token);
        return ResponseResult.success(user);
    }


    @ApiOperation("注销")
    @GetMapping("/logout")
    public ResponseResult<String> logout() {
        userService.logout();
        return ResponseResult.success("注销成功");
    }


    //@PreAuthorize("hasAuthority('system:admin')")
    @ApiOperation("分页查询")
    @GetMapping("/pageSearch")
    public ResponseResult<PageResult<User>> pageSearch(PageRequest pageRequest) {
        PageResult<User> pageResult = userService.pageSearch(pageRequest);
        return ResponseResult.success(pageResult);
    }


    @ApiOperation("删除用户")
    @GetMapping("/delete")
    public ResponseResult<Object> delete(@RequestParam("userId") Long userId, @RequestParam("userType") Integer userType) {
        return userService.delete(userId, userType);
    }

    @ApiOperation("更新用户")
    @PostMapping("/update")
    public ResponseResult<Object> update(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        return userService.update(multipartRequest);
    }

    @ApiOperation("新增用户")
    @PostMapping("/add")
    public ResponseResult<Object> add(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        return userService.add(multipartRequest);
    }


    @ApiOperation("分配角色")
    @PostMapping("/grantRole")
    public ResponseResult<Object> grantRole(@RequestBody RoleDto roleDto, @RequestHeader("token") String token) {
        return userService.grantRole(roleDto, token);
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePassword")
    public ResponseResult<Object> updatePassword(@RequestParam("srcPassword") String srcPassword,
                                                 @RequestParam("newPassword") String newPassword,
                                                 @RequestHeader("token") String token) {
        return userService.updatePassword(srcPassword,newPassword, token);
    }

}