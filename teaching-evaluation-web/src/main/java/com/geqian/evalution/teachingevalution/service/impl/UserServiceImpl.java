package com.geqian.evalution.teachingevalution.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.cache.cache.SimpleCacheTemplate;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.AssertUtils;
import com.geqian.common.utils.JwtUtils;
import com.geqian.evalution.teachingevalution.annotation.CustomTransaction;
import com.geqian.evalution.teachingevalution.authentication.LoginUserDetails;
import com.geqian.evalution.teachingevalution.common.dto.RoleDto;
import com.geqian.evalution.teachingevalution.entity.*;
import com.geqian.evalution.teachingevalution.mapper.RoleMapper;
import com.geqian.evalution.teachingevalution.mapper.UserMapper;
import com.geqian.evalution.teachingevalution.mapper.UserRoleMapper;
import com.geqian.evalution.teachingevalution.service.StudentService;
import com.geqian.evalution.teachingevalution.service.TeacherService;
import com.geqian.evalution.teachingevalution.service.UserService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.geqian.file.storage.FileStorageTemplate;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private SimpleCacheTemplate<String, Object> cacheTemplate;


    @Resource
    private FileStorageTemplate fileStorageTemplate;


    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMapper roleMapper;


    @Resource
    private PasswordEncoder passwordEncoder;


    @Resource
    private StudentService studentService;


    @Resource
    private TeacherService teacherService;







    /**
     * 登录
     *
     * @param user
     * @return
     */
    @Override
    public ResponseResult<Object> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //进行用户验证，调用UserDetailsServiceImpl中的loadUserByUsername方法验证用户信息
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // authenticate 不为 null 认证通过，为 null 认证没通过
        if (Objects.isNull(authenticate)) {
            return ResponseResult.fail("用户名或密码错误");
        }
        //验证通过，从authenticate中获取用户信息
        LoginUserDetails loginUserDetails = (LoginUserDetails) authenticate.getPrincipal();
        //获取用户id
        String userId = loginUserDetails.getUser().getUserId().toString();
        String jwt = JwtUtils.generateToken(userId);
        Map<String, String> data = new HashMap<>();
        data.put("token", jwt);
        cacheTemplate.set("user:" + userId, loginUserDetails);
        return ResponseResult.success(data);
    }


    /**
     * 退出登录
     */
    @Override
    public void logout() {
        //获取 SecurityContextHolder 中的用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
        String userId = loginUserDetails.getUser().getUserId().toString();
        //删除redis用户缓存
        cacheTemplate.delete("user:" + userId);
    }

    @Override
    public PageResult<User> pageSearch(PageRequest pageRequest) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        String key = pageRequest.getKey();
        if (AssertUtils.isNotEmpty(key)) {
            wrapper.like(User::getNickName, key);
        }
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<User> userList = this.baseMapper.selectList(wrapper);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        for (User user : pageInfo.getList()) {
            List<Role> roleList = roleMapper.selectUserRole(user.getUserId());
            if (!CollectionUtils.isEmpty(roleList)){
                user.setRoleIdList(roleList.stream().map(Role::getRoleId).collect(Collectors.toList()));
                user.setRoleNameList(roleList.stream().map(Role::getName).collect(Collectors.toList()));
            }
        }
        return PageUtils.getPageResult(pageInfo);
    }

    @Override
    public User info(String token) {
        String userId = JwtUtils.parseToken(token);
        User user = baseMapper.selectById(userId);
        List<Role> roleList = roleMapper.selectUserRole(Long.valueOf(userId));
        user.setRoleNameList(roleList.stream().map(Role::getName).collect(Collectors.toList()));
        return user;
    }

    @CustomTransaction
    @Override
    public ResponseResult<Object> delete(Long userId,Integer userType) {
        User user = this.getById(userId);
        if (Objects.equals(user.getUserName(),"admin")){
            return ResponseResult.fail("无法删除超级管理员");
        }
        if (Objects.equals(userType,0)){
            studentService.removeById(userId);
        }
        if (Objects.equals(userType,1)){
            teacherService.removeById(userId);
        }
        this.baseMapper.deleteById(userId);
        return ResponseResult.success();
    }

    @SneakyThrows
    @Override
    public ResponseResult<Object> update(MultipartHttpServletRequest request) {
        String userId = JwtUtils.parseToken(request.getHeader("token"));
        User user = JSON.parseObject(request.getParameter("user"), User.class);
        if (Objects.equals(user.getUserName(),"admin")){
            return ResponseResult.fail("无法更改超级管理员");
        }
        MultipartFile file = request.getFile("file");
        if (Objects.nonNull(file)) {
            if (StringUtils.hasText(user.getAvatar())) {
                try {
                    fileStorageTemplate.deleteFile(user.getAvatar());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String url = fileStorageTemplate.uploadFile(file.getBytes(), file.getOriginalFilename());
            user.setAvatar(url);
        }
        if (StringUtils.hasText(user.getPassword())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setUpdateBy(Long.valueOf(userId));
        user.setUpdateTime(new Date());
        this.baseMapper.updateById(user);
        return ResponseResult.success();
    }


    @SneakyThrows
    @Override
    @CustomTransaction("用户新增失败")
    public ResponseResult<Object> add(MultipartHttpServletRequest request) {
        String userId = JwtUtils.parseToken(request.getHeader("token"));
        User user = JSON.parseObject(request.getParameter("user"), User.class);
        MultipartFile file = request.getFile("file");
        if (Objects.nonNull(file)){
            String url = fileStorageTemplate.uploadFile(file.getBytes(), file.getOriginalFilename());user.setAvatar(url);
            user.setAvatar(url);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateBy(Long.valueOf(userId));
        user.setUpdateBy(Long.valueOf(userId));
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        this.baseMapper.insertReturnUserId(user);

        if (Objects.equals(user.getUserType(), 0)) {
            Student student = new Student();
            student.setStdId(user.getUserId());
            student.setStdSex(Integer.valueOf(user.getSex()));
            student.setStdTel(user.getPhoneNumber());
            student.setStdName(user.getNickName());
            studentService.save(student);
            LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Role::getRoleKey,"system:role:student");
            Role role = roleMapper.selectOne(wrapper);
            if (Objects.nonNull(role)){
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(role.getRoleId());
                userRoleMapper.insert(userRole);
            }
        }
        if (Objects.equals(user.getUserType(), 1)) {
            Teacher teacher = new Teacher();
            teacher.setThId(user.getUserId());
            teacher.setThName(user.getNickName());
            teacher.setThSex(Integer.valueOf(user.getSex()));
            teacher.setThTel(user.getPhoneNumber());
            teacherService.save(teacher);
            LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Role::getRoleKey,"system:role:teacher");
            Role role = roleMapper.selectOne(wrapper);
            if (Objects.nonNull(role)){
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(role.getRoleId());
                userRoleMapper.insert(userRole);
            }
        }
        return ResponseResult.success();
    }
    @Override
    public ResponseResult<Object> grantRole(RoleDto roleDto,String token) {
        User user = this.getById(roleDto.getUserId());
        if (Objects.equals(user.getUserName(),"admin")){
            return ResponseResult.fail("无法更改超级管理员角色");
        }
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,roleDto.getUserId());
        this.userRoleMapper.delete(wrapper);
        List<Long> roleIdList = roleDto.getRoleIdList();
        for (Long roleId : roleIdList) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(roleDto.getUserId());
            this.userRoleMapper.insert(userRole);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> updatePassword(String srcPassword, String newPassword, String token) {
        String userId = JwtUtils.parseToken(token);
        User user = this.getById(userId);
        if (passwordEncoder.matches(srcPassword,user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            this.updateById(user);
            return ResponseResult.success("修改成功");
        }
        return ResponseResult.fail("原密码错误，请重新输入");
    }

}
