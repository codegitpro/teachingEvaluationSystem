package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.vo.StudentCourseVo;
import com.geqian.evalution.teachingevalution.service.CourseStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author geqian
 * @date 1:39 2025/9/20
 */
@Api(tags = "学生课程管理")
@RestController
@RequestMapping("/studentCourse")
public class CourseStudentController {

    @Resource
    private CourseStudentService courseStudentService;

    @ApiOperation("获取学生课程列表")
    @GetMapping("/getStudentCourseList")
    public ResponseResult<Object> getStudentCourseList(PageRequest pageRequest,@RequestHeader("token")String token){
        return courseStudentService.getStudentCourseList(pageRequest,token);
    }

    @ApiOperation("删除学生课程信息")
    @PostMapping("/deleteStudentCourse")
    public ResponseResult<Object> deleteStudentCourse(@RequestBody List<StudentCourseVo> studentCourseVoList){
        return courseStudentService.deleteStudentCourse(studentCourseVoList);
    }
}
