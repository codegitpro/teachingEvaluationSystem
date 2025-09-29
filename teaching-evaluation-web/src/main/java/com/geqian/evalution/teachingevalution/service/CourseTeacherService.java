package com.geqian.evalution.teachingevalution.service;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.vo.TeacherCourseVo;
import com.geqian.evalution.teachingevalution.entity.CourseTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【course_teacher(课程教师关联表)】的数据库操作Service
* @createDate 2025-09-19 11:53:51
*/
public interface CourseTeacherService extends IService<CourseTeacher> {

    ResponseResult<Object> getTeacherCourseList(PageRequest pageRequest,String token);

    ResponseResult<Object> deleteTeacherCourse(List<TeacherCourseVo> teacherCourseVoList);
}
