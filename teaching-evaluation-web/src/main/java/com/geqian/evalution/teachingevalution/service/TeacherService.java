package com.geqian.evalution.teachingevalution.service;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.dto.CourseTeacherDto;
import com.geqian.evalution.teachingevalution.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【tb_teacher(教师表)】的数据库操作Service
* @createDate 2025-09-12 11:34:26
*/
public interface TeacherService extends IService<Teacher> {

    ResponseResult<Object> addTeacher(Teacher teacher);

    ResponseResult<Object> deleteTeacher(String teacherId);

    ResponseResult<Object> updateTeacher(Teacher teacher);

    ResponseResult<PageResult<Teacher>> getTeacherList(PageRequest pageRequest);

    ResponseResult<Object> distributeCourses(CourseTeacherDto courseTeacherDto);

    ResponseResult<Object> getTeacherAllList(String token);

}
