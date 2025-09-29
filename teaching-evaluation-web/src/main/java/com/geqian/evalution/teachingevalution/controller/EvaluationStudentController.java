package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.common.vo.EvaluationStudentVo;
import com.geqian.evalution.teachingevalution.entity.Course;
import com.geqian.evalution.teachingevalution.entity.EvaluationStudent;
import com.geqian.evalution.teachingevalution.service.CourseService;
import com.geqian.evalution.teachingevalution.service.EvaluationStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author geqian
 * @date 11:36 2025/9/12
 */
@Api(tags = "学生评分管理")
@RestController
@RequestMapping("/evaluationStudent")
public class EvaluationStudentController {

    @Resource
    private EvaluationStudentService evaluationStudentService;


    @ApiOperation("查询学生评分列表")
    @GetMapping("/getEvaluationStudentList")
    public ResponseResult<Object> getEvaluationStudentList(PageRequest pageRequest, @RequestHeader("token")String token) {
        return evaluationStudentService.getEvaluationStudentList(pageRequest,token);
    }

    @ApiOperation("更新学生评分信息")
    @PostMapping("/updateEvaluationStudent")
    public ResponseResult<Object> updateEvaluationStudent(@RequestBody EvaluationStudent evaluationStudent) {
        return evaluationStudentService.updateEvaluationStudent(evaluationStudent);
    }



}
