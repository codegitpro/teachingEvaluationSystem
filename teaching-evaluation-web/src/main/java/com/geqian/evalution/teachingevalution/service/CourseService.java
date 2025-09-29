package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.Course;

import java.util.List;

/**
* @author Administrator
* @description 针对表【tb_course(课程表)】的数据库操作Service
* @createDate 2025-09-12 22:39:04
*/
public interface CourseService extends IService<Course> {

    ResponseResult<PageResult<Course>> getCourseList(PageRequest pageRequest);

    ResponseResult<Object> updateCourse(Course courseId);

    ResponseResult<Object> addCourse(Course courseId);

    ResponseResult<Object> deleteCourse(String courseId);

    ResponseResult<List<Course>> getCourseAllList();

    ResponseResult<List<Long>> getExistsCourseList(String teacherId);

    ResponseResult<List<Course>> getCourseExistList(Long teacherId);
}
