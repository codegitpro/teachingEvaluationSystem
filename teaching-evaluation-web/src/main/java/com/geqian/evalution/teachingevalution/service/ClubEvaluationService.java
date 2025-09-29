package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.ClubEvaluation;

import java.util.Map;

public interface ClubEvaluationService extends IService<ClubEvaluation> {

  /**
   * 分页查询项目评分列表
   */
  ResponseResult<Map<String, Object>> getEvaluationList(PageRequest pageRequest);

  /**
   * 学生提交评分
   */
  ResponseResult<Object> submitEvaluation(ClubEvaluation evaluation);

  /**
   * 获取项目的平均分
   */
  double getProjectAverageScore(Long projectId);

  /**
   * 获取学生已评价的项目列表
   */
  ResponseResult<Map<String, Object>> getEvaluatedProjects();

  /**
   * 获取学生未评价的项目列表
   */
  ResponseResult<Map<String, Object>> getUnevaluatedProjects();
}