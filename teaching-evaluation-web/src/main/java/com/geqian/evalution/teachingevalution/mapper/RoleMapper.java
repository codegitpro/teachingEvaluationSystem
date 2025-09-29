package com.geqian.evalution.teachingevalution.mapper;

import com.geqian.evalution.teachingevalution.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2025-09-09 17:19:22
* @Entity com.geqian.evalution.teachingevalution.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {


    List<Long> selectRoleIdList(Long userId);

    List<Role> selectUserRole(Long userId);
}




