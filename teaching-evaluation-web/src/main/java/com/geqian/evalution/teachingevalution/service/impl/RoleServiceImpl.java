package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.AssertUtils;
import com.geqian.common.utils.JwtUtils;
import com.geqian.evalution.teachingevalution.common.vo.RoleVo;
import com.geqian.evalution.teachingevalution.entity.Role;
import com.geqian.evalution.teachingevalution.entity.RoleMenu;
import com.geqian.evalution.teachingevalution.entity.UserRole;
import com.geqian.evalution.teachingevalution.mapper.RoleMapper;
import com.geqian.evalution.teachingevalution.mapper.RoleMenuMapper;
import com.geqian.evalution.teachingevalution.service.RoleMenuService;
import com.geqian.evalution.teachingevalution.service.RoleService;
import com.geqian.evalution.teachingevalution.service.UserRoleService;
import com.geqian.evalution.teachingevalution.service.UserService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_role(角色表)】的数据库操作Service实现
 * @createDate 2025-09-09 17:19:22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private UserService userService;

    @Override
    public ResponseResult<PageResult<Role>> getRoleList(PageRequest pageRequest) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        String key = pageRequest.getKey();
        if (AssertUtils.isNotEmpty(key)) {
            wrapper.like(Role::getName, key);
        }
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<Role> roleList = this.baseMapper.selectList(wrapper);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        PageResult<Role> pageResult = PageUtils.getPageResult(pageInfo);
        for (Role role : pageInfo.getList()) {
            List<Long> permissions = roleMenuMapper.selectPermissions(role.getRoleId());
            role.setPermissionList(permissions);
        }
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> updateRole(Role role, String token) {
        if (Objects.nonNull(role)) {
            if (Objects.equals(role.getRoleKey(),"system:role:admin")){
                return ResponseResult.fail("无法更改超级管理员角色");
            }
            String userId = JwtUtils.parseToken(token);
            role.setUpdateBy(Long.valueOf(userId));
            role.setUpdateTime(new Date());
            this.updateById(role);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> addRole(Role role, String token) {
        if (Objects.nonNull(role)) {
            String userId = JwtUtils.parseToken(token);
            role.setUpdateBy(Long.valueOf(userId));
            role.setCreateBy(Long.valueOf(userId));
            Date date = new Date();
            role.setUpdateTime(date);
            role.setCreateTime(date);
            this.save(role);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> deleteRole(Long roleId) {
        if (Objects.nonNull(roleId)) {
            Role role = this.getById(roleId);
            if (Objects.equals(role.getRoleKey(),"system:role:admin")){
                return ResponseResult.fail("无法删除超级管理员角色");
            }
            this.removeById(roleId);
            LambdaQueryWrapper<RoleMenu> roleMenuWrapper = new LambdaQueryWrapper<>();
            roleMenuWrapper.eq(RoleMenu::getRoleId, roleId);
            roleMenuService.remove(roleMenuWrapper);
            LambdaQueryWrapper<UserRole> userRoleWrapper = new LambdaQueryWrapper<>();
            userRoleWrapper.eq(UserRole::getRoleId, roleId);
            userRoleService.remove(userRoleWrapper);
            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }

    @Override
    public ResponseResult<Object> grantPermissions(Role role, String token) {
        if (Objects.equals(role.getRoleKey(),"system:role:admin")){
            return ResponseResult.fail("无法更改管理员角色权限");
        }
        LambdaQueryWrapper<RoleMenu> roleMenuWrapper = new LambdaQueryWrapper<>();
        roleMenuWrapper.eq(RoleMenu::getRoleId, role.getRoleId());
        roleMenuService.remove(roleMenuWrapper);
        List<Long> permissionList = role.getPermissionList();
        if (!CollectionUtils.isEmpty(permissionList)) {
            List<RoleMenu> roleMenuList = permissionList.stream().map(p -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(role.getRoleId());
                roleMenu.setMenuId(p);
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> getRoleAllList() {
        List<Role> roleList = this.list();
        if (!CollectionUtils.isEmpty(roleList)) {
            List<RoleVo> roleVoList = roleList.stream().map(role -> {
                RoleVo roleVo = new RoleVo();
                roleVo.setRoleName(role.getName());
                roleVo.setRoleId(role.getRoleId());
                return roleVo;
            }).collect(Collectors.toList());
            return ResponseResult.success(roleVoList);
        }
        return ResponseResult.success(new ArrayList<RoleVo>());
    }
}




