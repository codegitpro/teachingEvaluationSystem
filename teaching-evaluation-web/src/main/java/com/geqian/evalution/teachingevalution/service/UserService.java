package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.dto.RoleDto;
import com.geqian.evalution.teachingevalution.entity.User;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
* @author Administrator
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-01-12 14:25:06
*/
public interface UserService extends IService<User> {

    ResponseResult<Object> login(User user);

    void logout();

    PageResult<User> pageSearch(PageRequest pageRequest);

    User info(String token);

    ResponseResult<Object> delete(Long userId,Integer userType);

    ResponseResult<Object> update(MultipartHttpServletRequest request);

    ResponseResult<Object> add(MultipartHttpServletRequest request);

    ResponseResult<Object> grantRole(RoleDto roleDto,String token);

    ResponseResult<Object> updatePassword(String srcPassword, String newPassword, String token);
}
