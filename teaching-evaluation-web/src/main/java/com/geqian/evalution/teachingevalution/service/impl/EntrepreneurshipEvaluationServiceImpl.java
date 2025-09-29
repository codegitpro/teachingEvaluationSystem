package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.AssertUtils;
import com.geqian.evalution.teachingevalution.authentication.LoginUserDetails;
import com.geqian.evalution.teachingevalution.entity.EntrepreneurshipEvaluation;
import com.geqian.evalution.teachingevalution.entity.EntrepreneurshipProject;
import com.geqian.evalution.teachingevalution.mapper.EntrepreneurshipEvaluationMapper;
import com.geqian.evalution.teachingevalution.service.EntrepreneurshipEvaluationService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntrepreneurshipEvaluationServiceImpl
    extends ServiceImpl<EntrepreneurshipEvaluationMapper, EntrepreneurshipEvaluation>
    implements EntrepreneurshipEvaluationService {

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
  public ResponseResult<Object> submitEvaluation(EntrepreneurshipEvaluation evaluation) {
    // Get current authenticated user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
    Long studentId = userDetails.getUser().getUserId();
    String studentName = userDetails.getUser().getUserName();

    // Check if user has already submitted evaluation for this project
    LambdaQueryWrapper<EntrepreneurshipEvaluation> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(EntrepreneurshipEvaluation::getProjectId, evaluation.getProjectId())
        .eq(EntrepreneurshipEvaluation::getStudentId, studentId);

    AssertUtils.isNull(this.getOne(queryWrapper), "您已经评价过该项目");

    // Set evaluation details
    evaluation.setStudentId(studentId);
    evaluation.setStudentName(studentName);
    evaluation.setCreateTime(new Date());

    this.save(evaluation);
    return ResponseResult.success();
  }

  @Override
  public double getProjectAverageScore(Long projectId) {
    LambdaQueryWrapper<EntrepreneurshipEvaluation> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(EntrepreneurshipEvaluation::getProjectId, projectId);
    List<EntrepreneurshipEvaluation> evaluations = this.list(wrapper);

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
    // Get current authenticated user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
    Long studentId = userDetails.getUser().getUserId();

    List<EntrepreneurshipProject> evaluatedProjects = this.baseMapper.selectEvaluatedProjects(studentId);
    Map<String, Object> result = new HashMap<>();
    result.put("total", evaluatedProjects.size());
    result.put("list", evaluatedProjects);

    return ResponseResult.success(result);
  }

  @Override
  public ResponseResult<Map<String, Object>> getUnevaluatedProjects() {
    // Get current authenticated user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
    Long studentId = userDetails.getUser().getUserId();

    List<EntrepreneurshipProject> unevaluatedProjects = this.baseMapper.selectUnevaluatedProjects(studentId);
    Map<String, Object> result = new HashMap<>();
    result.put("total", unevaluatedProjects.size());
    result.put("list", unevaluatedProjects);

    return ResponseResult.success(result);
  }
}