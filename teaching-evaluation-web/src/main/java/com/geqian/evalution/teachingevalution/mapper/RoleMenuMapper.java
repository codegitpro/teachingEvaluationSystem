package com.geqian.evalution.teachingevalution.mapper;

import com.geqian.evalution.teachingevalution.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_role_menu(角色权限关联表)】的数据库操作Mapper
* @createDate 2025-09-09 17:19:22
* @Entity com.geqian.evalution.teachingevalution.entity.RoleMenu
*/
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<Long> selectPermissions(Long roleId);
}




