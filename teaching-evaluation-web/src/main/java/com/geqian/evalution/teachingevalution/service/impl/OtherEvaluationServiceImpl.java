package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.authentication.LoginUserDetails;
import com.geqian.evalution.teachingevalution.entity.OtherEvaluation;
import com.geqian.evalution.teachingevalution.entity.OtherProject;
import com.geqian.evalution.teachingevalution.mapper.OtherEvaluationMapper;
import com.geqian.evalution.teachingevalution.service.OtherEvaluationService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OtherEvaluationServiceImpl extends ServiceImpl<OtherEvaluationMapper, OtherEvaluation>
    implements OtherEvaluationService {

  @Override
  public ResponseResult<Map<String, Object>> getEvaluationList(PageRequest pageRequest) {
    PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
    List<Map<String, Object>> evaluationList = this.baseMapper.selectEvaluationListWithProject();
    PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(evaluationList);
    PageResult<Map<String, Object>> pageResult = PageUtils.getPageResult(pageInfo);

    Map<String, Object> result = new HashMap<>();
    result.put("total", pageResult.getTotal());
    result.put("totalPage", pageResult.getTotalPage());
    result.put("pageNum", pageResult.getpageNum());
    result.put("pageSize", pageResult.getPageSize());
    result.put("size", pageResult.getSize());
    result.put("dataList", pageResult.getDataList());

    return ResponseResult.success(result);
  }

  @Override
  public ResponseResult<Object> submitEvaluation(OtherEvaluation evaluation) {
    // 获取当前用户信息
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
    Integer userId = Integer.valueOf(loginUserDetails.getUser().getUserId().toString());
    String userName = loginUserDetails.getUser().getNickName();

    // 检查是否已评分
    LambdaQueryWrapper<OtherEvaluation> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(OtherEvaluation::getProjectId, evaluation.getProjectId())
        .eq(OtherEvaluation::getStudentId, userId);
    if (this.baseMapper.selectCount(wrapper) > 0) {
      return ResponseResult.fail("您已经评分过该项目");
    }

    // 设置评分信息
    evaluation.setStudentId(userId);
    evaluation.setStudentName(userName);
    evaluation.setCreateTime(LocalDateTime.now());
    this.save(evaluation);
    return ResponseResult.success();
  }

  @Override
  public double getProjectAverageScore(Long projectId) {
    LambdaQueryWrapper<OtherEvaluation> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(OtherEvaluation::getProjectId, projectId);
    List<OtherEvaluation> evaluations = this.list(wrapper);

    if (evaluations.isEmpty()) {
      return 0.0;
    }

    double totalScore = evaluations.stream()
        .mapToDouble(e -> e.getScore().doubleValue())
        .sum();
    return totalScore / evaluations.size();
  }

  @Override
  public ResponseResult<Map<String, Object>> getEvaluatedProjects() {
    // 获取当前用户信息
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
    Integer userId = Integer.valueOf(loginUserDetails.getUser().getUserId().toString());

    List<OtherProject> evaluatedProjects = this.baseMapper.selectEvaluatedProjects(userId);
    Map<String, Object> result = new HashMap<>();
    result.put("evaluatedProjects", evaluatedProjects);
    return ResponseResult.success(result);
  }

  @Override
  public ResponseResult<Map<String, Object>> getUnevaluatedProjects() {
    // 获取当前用户信息
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
    Integer userId = Integer.valueOf(loginUserDetails.getUser().getUserId().toString());

    List<OtherProject> unevaluatedProjects = this.baseMapper.selectUnevaluatedProjects(userId);
    Map<String, Object> result = new HashMap<>();
    result.put("unevaluatedProjects", unevaluatedProjects);
    return ResponseResult.success(result);
  }
}