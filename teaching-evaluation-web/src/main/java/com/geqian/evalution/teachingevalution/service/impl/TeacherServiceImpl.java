package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.AssertUtils;
import com.geqian.common.utils.JwtUtils;
import com.geqian.evalution.teachingevalution.common.dto.CourseTeacherDto;
import com.geqian.evalution.teachingevalution.entity.CourseTeacher;
import com.geqian.evalution.teachingevalution.entity.Role;
import com.geqian.evalution.teachingevalution.entity.Teacher;
import com.geqian.evalution.teachingevalution.mapper.CourseTeacherMapper;
import com.geqian.evalution.teachingevalution.mapper.RoleMapper;
import com.geqian.evalution.teachingevalution.mapper.TeacherMapper;
import com.geqian.evalution.teachingevalution.service.CourseTeacherService;
import com.geqian.evalution.teachingevalution.service.TeacherService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @author Administrator
* @description 针对表【tb_teacher(教师表)】的数据库操作Service实现
* @createDate 2025-09-12 11:34:26
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

    @Resource
    private CourseTeacherService courseTeacherService;

    @Resource
    private CourseTeacherMapper courseTeacherMapper;


    @Resource
    private RoleMapper roleMapper;

    @Override
    public ResponseResult<Object> addTeacher(Teacher teacher) {
        if (Objects.nonNull(teacher)){
            this.save(teacher);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> deleteTeacher(String teacherId) {
        if (Objects.nonNull(teacherId)){
            this.removeById(teacherId);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> updateTeacher(Teacher teacher) {
        if (Objects.nonNull(teacher)){
            this.updateById(teacher);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<PageResult<Teacher>> getTeacherList(PageRequest pageRequest) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        String key = pageRequest.getKey();
        if (AssertUtils.isNotEmpty(key)) {
            wrapper.like(Teacher::getThName, key);
        }
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<Teacher> teacherList = this.baseMapper.selectList(wrapper);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        PageResult<Teacher> pageResult = PageUtils.getPageResult(pageInfo);
        return ResponseResult.success(pageResult);
    }

    @Override
    public ResponseResult<Object> distributeCourses(CourseTeacherDto courseTeacherDto) {
        if (Objects.nonNull(courseTeacherDto) && Objects.nonNull(courseTeacherDto.getCourseIdList())){
            LambdaQueryWrapper<CourseTeacher> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CourseTeacher::getTeacherId,courseTeacherDto.getTeacherId());
            courseTeacherService.remove(wrapper);
            courseTeacherDto.getCourseIdList().stream().map(courseId->{
                //移除已分配课程
                courseTeacherMapper.deleteByCourseId(courseId);
                CourseTeacher courseTeacher = new CourseTeacher();
                courseTeacher.setTeacherId(courseTeacherDto.getTeacherId());
                courseTeacher.setCourseId(courseId);
                return courseTeacher;
            }).forEach(courseTeacher -> courseTeacherService.save(courseTeacher));
            return ResponseResult.success();
        }
        return ResponseResult.fail();
    }

    @Override
    public ResponseResult<Object> getTeacherAllList(String token) {
        String userId = JwtUtils.parseToken(token);
        List<Role> roleList = roleMapper.selectUserRole(Long.valueOf(userId));
        List<String> roleRoleKey = roleList.stream().map(Role::getRoleKey).collect(Collectors.toList());
        if (!roleRoleKey.contains("system:role:admin")){
            List<Map<String, Object>> teacherInfoList = Stream.of(this.getById(userId)).map(teacher->{
                Map<String, Object> teacherInfo = new HashMap<>();
                teacherInfo.put("teacherId", teacher.getThId());
                teacherInfo.put("teacherName", teacher.getThName());
                return teacherInfo;
            }).collect(Collectors.toList());
            return ResponseResult.success(teacherInfoList);
        }
        List<Teacher> teacherList = this.list();
        List<Map<String, Object>> teacherInfoList = teacherList.stream().map(teacher -> {
            Map<String, Object> teacherInfo = new HashMap<>();
            teacherInfo.put("teacherId", teacher.getThId());
            teacherInfo.put("teacherName", teacher.getThName());
            return teacherInfo;
        }).collect(Collectors.toList());
        return ResponseResult.success(teacherInfoList);
    }

}




