package com.geqian.evalution.teachingevalution.service;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.evalution.teachingevalution.entity.Student;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_menu(菜单表)】的数据库操作Service
* @createDate 2025-09-09 17:19:22
*/
public interface MenuService extends IService<Menu> {

    ResponseResult<List<Menu>> getMenuList(String token);

    ResponseResult<PageResult<Menu>> getMenuPageList(String token, PageRequest pageRequest);

    ResponseResult<Object> updateMenu(Menu menu,String token);

    ResponseResult<Object> addMenu(Menu menu,String token);

    ResponseResult<Object> getMenuDirList(String token);

    ResponseResult<Object> deleteMenu(Integer menuId);

    ResponseResult<List<Menu>> getMenuAllList();

}
