package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.dto.CourseStudentDto;
import com.geqian.evalution.teachingevalution.entity.Student;

/**
* @author Administrator
* @description 针对表【tb_student(学生表)】的数据库操作Service
* @createDate 2025-09-12 11:34:26
*/
public interface StudentService extends IService<Student> {

    ResponseResult<Object> deleteStudent(String studentId);

    ResponseResult<Object> addStudent(Student student);

    ResponseResult<Object> updateStudent(Student student);

    ResponseResult<PageResult<Student>> getStudentList(PageRequest pageRequest);

    ResponseResult<Object> courseAllocation(CourseStudentDto dao);
}
