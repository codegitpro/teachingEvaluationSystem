package com.geqian.evalution.teachingevalution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.entity.User;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-01-12 14:25:06
* @Entity com.geqian.evalution.teachingevalution.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    //查询用户权限
    List<String> selectPermissions(Long userId);

    //查询用户权限
    int insertReturnUserId(User user);

}




