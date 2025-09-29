package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.JwtUtils;
import com.geqian.evalution.teachingevalution.authentication.LoginUserDetails;
import com.geqian.evalution.teachingevalution.entity.ClubEvaluation;
import com.geqian.evalution.teachingevalution.entity.ClubProject;
import com.geqian.evalution.teachingevalution.entity.User;
import com.geqian.evalution.teachingevalution.mapper.ClubEvaluationMapper;
import com.geqian.evalution.teachingevalution.service.ClubEvaluationService;
import com.geqian.evalution.teachingevalution.service.UserService;
import com.geqian.evalution.teachingevalution.service.ClubProjectService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;

@Service
public class ClubEvaluationServiceImpl extends ServiceImpl<ClubEvaluationMapper, ClubEvaluation>
    implements ClubEvaluationService {

  @Resource
  private UserService userService;

  @Resource
  private ClubProjectService projectService;

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
  public ResponseResult<Object> submitEvaluation(ClubEvaluation evaluation) {
    // 获取当前用户信息
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
    Integer userId = Integer.valueOf(loginUserDetails.getUser().getUserId().toString());
    String userName = loginUserDetails.getUser().getNickName();

    // 检查是否已评分
    LambdaQueryWrapper<ClubEvaluation> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(ClubEvaluation::getProjectId, evaluation.getProjectId())
        .eq(ClubEvaluation::getStudentId, userId);
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
    LambdaQueryWrapper<ClubEvaluation> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(ClubEvaluation::getProjectId, projectId);
    List<ClubEvaluation> evaluations = this.list(wrapper);

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

    List<ClubProject> evaluatedProjects = this.baseMapper.selectEvaluatedProjects(userId);
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

    List<ClubProject> unevaluatedProjects = this.baseMapper.selectUnevaluatedProjects(userId);
    Map<String, Object> result = new HashMap<>();
    result.put("unevaluatedProjects", unevaluatedProjects);
    return ResponseResult.success(result);
  }
}