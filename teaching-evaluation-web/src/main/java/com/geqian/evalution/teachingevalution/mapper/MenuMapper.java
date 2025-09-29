package com.geqian.evalution.teachingevalution.mapper;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;

import com.geqian.evalution.teachingevalution.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
 * @createDate 2025-09-09 17:19:22
 * @Entity com.geqian.evalution.teachingevalution.entity.Menu
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectMenuList(@Param("userId") String userId, @Param("parentId") Integer parentId, @Param("label") String label);

    Long insertReturnMenuId(Menu menu);

}




