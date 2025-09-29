package com.geqian.evalution.teachingevalution.service;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.evalution.teachingevalution.entity.Student;

/**
* @author Administrator
* @description 针对表【sys_role(角色表)】的数据库操作Service
* @createDate 2025-09-09 17:19:22
*/
public interface RoleService extends IService<Role> {

    ResponseResult<PageResult<Role>> getRoleList(PageRequest pageRequest);

    ResponseResult<Object> updateRole(Role role,String token);

    ResponseResult<Object> addRole(Role role,String token);

    ResponseResult<Object> deleteRole(Long roleId);

    ResponseResult<Object> grantPermissions(Role role,String token);

    ResponseResult<Object> getRoleAllList();

}
