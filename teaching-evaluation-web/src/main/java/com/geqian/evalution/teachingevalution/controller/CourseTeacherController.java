package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.vo.TeacherCourseVo;
import com.geqian.evalution.teachingevalution.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author geqian
 * @date 1:39 2025/9/20
 */
@Api(tags = "教师课程管理")
@RestController
@RequestMapping("/teacherCourse")
public class CourseTeacherController {

    @Resource
    private CourseTeacherService courseTeacherService;

    @ApiOperation("获取教师课程列表")
    @GetMapping("/getTeacherCourseList")
    public ResponseResult<Object> getTeacherCourseList(PageRequest pageRequest,@RequestHeader("token")String token){
        return courseTeacherService.getTeacherCourseList(pageRequest,token);
    }


    @ApiOperation("删除学生课程信息")
    @PostMapping("/deleteTeacherCourse")
    public ResponseResult<Object> deleteTeacherCourse(@RequestBody List<TeacherCourseVo> teacherCourseVoList){
        return courseTeacherService.deleteTeacherCourse(teacherCourseVoList);
    }
}
