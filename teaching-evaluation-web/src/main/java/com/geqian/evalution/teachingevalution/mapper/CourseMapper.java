package com.geqian.evalution.teachingevalution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.common.vo.CourseTeacherVo;
import com.geqian.evalution.teachingevalution.entity.Course;

import java.util.List;

/**
* @author Administrator
* @description 针对表【tb_course(课程表)】的数据库操作Mapper
* @createDate 2025-09-12 22:39:04
* @Entity com.geqian.evalution.teachingevalution.entity.Course
*/
public interface CourseMapper extends BaseMapper<Course> {


    List<CourseTeacherVo> selectTeacherCourseInfo(String teacherId);

    List<Course> getCourseExistList(Long teacherId);
}




