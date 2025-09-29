package com.geqian.evalution.teachingevalution.service;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.vo.StudentCourseVo;
import com.geqian.evalution.teachingevalution.entity.CourseStudent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【course_student】的数据库操作Service
* @createDate 2025-09-19 11:53:51
*/
public interface CourseStudentService extends IService<CourseStudent> {

    ResponseResult<Object> getStudentCourseList(PageRequest pageRequest,String token);

    ResponseResult<Object> deleteStudentCourse(List<StudentCourseVo> studentCourseVoList);
}
