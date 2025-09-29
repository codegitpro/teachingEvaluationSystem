package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.EntrepreneurshipEvaluation;

import java.util.Map;

public interface EntrepreneurshipEvaluationService extends IService<EntrepreneurshipEvaluation> {

  /**
   * 分页查询项目评分列表
   */
  ResponseResult<Map<String, Object>> getEvaluationList(PageRequest pageRequest);

  /**
   * 提交项目评分
   */
  ResponseResult<Object> submitEvaluation(EntrepreneurshipEvaluation evaluation);

  /**
   * 获取项目的平均分
   */
  double getProjectAverageScore(Long projectId);

  /**
   * 获取已评价项目列表
   */
  ResponseResult<Map<String, Object>> getEvaluatedProjects();

  /**
   * 获取未评价项目列表
   */
  ResponseResult<Map<String, Object>> getUnevaluatedProjects();
}