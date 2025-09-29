package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.dto.CourseTeacherDto;
import com.geqian.evalution.teachingevalution.entity.Teacher;
import com.geqian.evalution.teachingevalution.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author geqian
 * @date 11:36 2025/9/12
 */
@Api(tags = "教师管理")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;


    @ApiOperation("查询教师列表")
    @GetMapping("/getTeacherList")
    public ResponseResult<PageResult<Teacher>> getTeacherList(PageRequest pageRequest) {
        return teacherService.getTeacherList(pageRequest);
    }

    @ApiOperation("获取图表显示教师列表")
    @GetMapping("/getTeacherAllList")
    public ResponseResult<Object> getTeacherAllList(@RequestHeader("token") String token) {
        return teacherService.getTeacherAllList(token);
    }


    @ApiOperation("更新教师信息")
    @PostMapping("/updateTeacher")
    public ResponseResult<Object> updateTeacher(@RequestBody Teacher teacher) {
        return teacherService.updateTeacher(teacher);
    }


    @ApiOperation("新增教师")
    @PostMapping("/addTeacher")
    public ResponseResult<Object> addTeacher(@RequestBody Teacher teacher) {
        return teacherService.addTeacher(teacher);
    }


    @ApiOperation("删除教师")
    @GetMapping("/deleteTeacher")
    public ResponseResult<Object> deleteTeacher(@RequestParam("teacherId") String teacherId) {

        return teacherService.deleteTeacher(teacherId);
    }

    @ApiOperation("教师分配课程")
    @PostMapping("/distributeCourses")
    public ResponseResult<Object> distributeCourses(@RequestBody CourseTeacherDto courseTeacherDto) {
        return teacherService.distributeCourses(courseTeacherDto);
    }


}
