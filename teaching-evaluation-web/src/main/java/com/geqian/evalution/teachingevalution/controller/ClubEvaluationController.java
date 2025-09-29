package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.ClubEvaluation;
import com.geqian.evalution.teachingevalution.service.ClubEvaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "社团活动与社会工作评分管理")
@RestController
@RequestMapping("/club/evaluation")
public class ClubEvaluationController {

  @Resource
  private ClubEvaluationService clubEvaluationService;

  @ApiOperation("分页查询项目评分列表")
  @GetMapping("/list")
  public ResponseResult<Map<String, Object>> getEvaluationList(PageRequest pageRequest) {
    return clubEvaluationService.getEvaluationList(pageRequest);
  }

  @ApiOperation("提交项目评分")
  @PostMapping("/submit")
  public ResponseResult<Object> submitEvaluation(@RequestBody ClubEvaluation evaluation) {
    return clubEvaluationService.submitEvaluation(evaluation);
  }

  @ApiOperation("获取项目平均分")
  @GetMapping("/average/{projectId}")
  public ResponseResult<Double> getProjectAverageScore(@PathVariable Long projectId) {
    return ResponseResult.success(clubEvaluationService.getProjectAverageScore(projectId));
  }

  @ApiOperation("获取已评价项目列表")
  @GetMapping("/evaluated")
  public ResponseResult<Map<String, Object>> getEvaluatedProjects() {
    return clubEvaluationService.getEvaluatedProjects();
  }

  @ApiOperation("获取未评价项目列表")
  @GetMapping("/unevaluated")
  public ResponseResult<Map<String, Object>> getUnevaluatedProjects() {
    return clubEvaluationService.getUnevaluatedProjects();
  }
}