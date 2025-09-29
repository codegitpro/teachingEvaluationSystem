package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.MoralEvaluation;
import com.geqian.evalution.teachingevalution.service.MoralEvaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "品德与社会化水平评分管理")
@RestController
@RequestMapping("/moral/evaluation")
public class MoralEvaluationController {

  @Resource
  private MoralEvaluationService moralEvaluationService;

  @ApiOperation("分页查询项目评分列表")
  @GetMapping("/list")
  public ResponseResult<Map<String, Object>> getEvaluationList(PageRequest pageRequest) {
    return moralEvaluationService.getEvaluationList(pageRequest);
  }

  @ApiOperation("提交项目评分")
  @PostMapping("/submit")
  public ResponseResult<Object> submitEvaluation(@RequestBody MoralEvaluation evaluation) {
    return moralEvaluationService.submitEvaluation(evaluation);
  }

  @ApiOperation("获取项目平均分")
  @GetMapping("/average/{projectId}")
  public ResponseResult<Double> getProjectAverageScore(@PathVariable Long projectId) {
    return ResponseResult.success(moralEvaluationService.getProjectAverageScore(projectId));
  }

  @ApiOperation("获取已评价项目列表")
  @GetMapping("/evaluated")
  public ResponseResult<Map<String, Object>> getEvaluatedProjects() {
    return moralEvaluationService.getEvaluatedProjects();
  }

  @ApiOperation("获取未评价项目列表")
  @GetMapping("/unevaluated")
  public ResponseResult<Map<String, Object>> getUnevaluatedProjects() {
    return moralEvaluationService.getUnevaluatedProjects();
  }
}