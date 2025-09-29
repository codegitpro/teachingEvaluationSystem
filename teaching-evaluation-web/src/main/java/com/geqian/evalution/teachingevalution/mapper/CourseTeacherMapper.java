package com.geqian.evalution.teachingevalution.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.geqian.evalution.teachingevalution.common.vo.TeacherCourseVo;
import com.geqian.evalution.teachingevalution.entity.CourseTeacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【course_teacher(课程教师关联表)】的数据库操作Mapper
* @createDate 2025-09-19 11:53:51
* @Entity com.geqian.evalution.teachingevalution.entity.CourseTeacher
*/
public interface CourseTeacherMapper extends BaseMapper<CourseTeacher> {

    CourseTeacher selectByCourseId(Long courseId);

    List<TeacherCourseVo> selectTeacherCourseList(@Param("teacherName") String teacherName, @Param("teacherId") String teacherId);

    int deleteByCourseId(Long courseId);

}




