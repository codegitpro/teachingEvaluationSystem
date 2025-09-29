package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.Course;
import com.geqian.evalution.teachingevalution.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author geqian
 * @date 11:36 2025/9/12
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;


    @ApiOperation("查询课程列表")
    @GetMapping("/getCourseList")
    public ResponseResult<PageResult<Course>> getCourseList(PageRequest pageRequest) {
        return courseService.getCourseList(pageRequest);
    }


    @ApiOperation("查询所有课程列表")
    @GetMapping("/getCourseAllList")
    public ResponseResult<List<Course>> getCourseAllList() {
        return courseService.getCourseAllList();
    }

    @ApiOperation("查询指定教师课程列表")
    @GetMapping("/getCourseInfoList")
    public ResponseResult<List<Course>> getCourseExistList(Long teacherId) {
        return courseService.getCourseExistList(teacherId);
    }

    @ApiOperation("查询指定教师已有课程编号")
    @GetMapping("/getExistsCourseList")
    public ResponseResult<List<Long>> getExistsCourseList(@RequestParam("teacherId")String teacherId) {
        return courseService.getExistsCourseList(teacherId);
    }

    @ApiOperation("更新课程信息")
    @PostMapping("/updateCourse")
    public ResponseResult<Object> updateCourse(@RequestBody Course courseId) {
        return courseService.updateCourse(courseId);
    }


    @ApiOperation("新增课程")
    @PostMapping("/addCourse")
    public ResponseResult<Object> addCourse(@RequestBody Course courseId) {
        return courseService.addCourse(courseId);
    }


    @ApiOperation("删除课程")
    @GetMapping("/deleteCourse")
    public ResponseResult<Object> deleteCourse(@RequestParam("courseId") String courseId) {

        return courseService.deleteCourse(courseId);
    }
}
