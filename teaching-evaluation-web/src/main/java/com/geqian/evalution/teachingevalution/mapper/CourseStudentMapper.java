package com.geqian.evalution.teachingevalution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.common.vo.StudentCourseVo;
import com.geqian.evalution.teachingevalution.entity.CourseStudent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【course_student】的数据库操作Mapper
* @createDate 2025-09-19 11:53:51
* @Entity com.geqian.evalution.teachingevalution.entity.CourseStudent
*/
public interface CourseStudentMapper extends BaseMapper<CourseStudent> {

    int insertIfNotExist(CourseStudent courseStudent);

    List<StudentCourseVo> selectStudentCourseList(@Param("studentName") String studentName, @Param("teacherId") String teacherId);
}




