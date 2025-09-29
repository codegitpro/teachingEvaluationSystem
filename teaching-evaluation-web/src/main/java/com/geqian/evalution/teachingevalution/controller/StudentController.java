package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.dto.CourseStudentDto;
import com.geqian.evalution.teachingevalution.entity.Student;
import com.geqian.evalution.teachingevalution.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author geqian
 * @date 11:36 2025/9/12
 */
@Api(tags = "学生管理")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;


    @ApiOperation("查询学生列表")
    @GetMapping("/getStudentList")
    public ResponseResult<PageResult<Student>> getStudentList(PageRequest pageRequest) {
        return studentService.getStudentList(pageRequest);
    }


    @ApiOperation("更新学生信息")
    @PostMapping("/updateStudent")
    public ResponseResult<Object> updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }


    @ApiOperation("新增学生")
    @PostMapping("/addStudent")
    public ResponseResult<Object> addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }


    @ApiOperation("删除学生")
    @GetMapping("/deleteStudent")
    public ResponseResult<Object> deleteStudent(@RequestParam("studentId") String studentId) {

        return studentService.deleteStudent(studentId);
    }


    @ApiOperation("学生分配课程")
    @PostMapping("/courseAllocation")
    public ResponseResult<Object> courseAllocation(@RequestBody CourseStudentDto dto) {
        return studentService.courseAllocation(dto);
    }
}
