package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.Role;
import com.geqian.evalution.teachingevalution.entity.Student;
import com.geqian.evalution.teachingevalution.entity.User;
import com.geqian.evalution.teachingevalution.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author geqian
 * @date 9:18 2025/9/14
 */

@Api(tags = "角色管理")
@RequestMapping("/role")
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;


    @ApiOperation("分页查询角色列表")
    @GetMapping("/getRoleList")
    public ResponseResult<PageResult<Role>> getRoleList(PageRequest pageRequest) {
        return roleService.getRoleList(pageRequest);
    }


    @ApiOperation("更新角色信息")
    @PostMapping("/updateRole")
    public ResponseResult<Object> updateRole(@RequestBody Role role, @RequestHeader("token") String token) {
        return roleService.updateRole(role, token);
    }


    @ApiOperation("新增角色")
    @PostMapping("/addRole")
    public ResponseResult<Object> addRole(@RequestBody Role role, @RequestHeader("token") String token) {
        return roleService.addRole(role, token);
    }


    @ApiOperation("删除角色")
    @GetMapping("/deleteRole")
    public ResponseResult<Object> deleteStudent(@RequestParam("roleId") Long roleId) {

        return roleService.deleteRole(roleId);
    }

    @ApiOperation("分配权限")
    @PostMapping("/grantPermissions")
    public ResponseResult<Object> grantPermissions(@RequestBody Role role, @RequestHeader("token") String token) {
        return roleService.grantPermissions(role,token);
    }

    @ApiOperation("所有角色列表")
    @GetMapping("/getRoleAllList")
    public ResponseResult<Object> getRoleAllList() {
        return roleService.getRoleAllList();
    }

}
