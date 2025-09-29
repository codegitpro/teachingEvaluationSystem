package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.AssertUtils;
import com.geqian.evalution.teachingevalution.authentication.LoginUserDetails;
import com.geqian.evalution.teachingevalution.entity.ClubProject;
import com.geqian.evalution.teachingevalution.mapper.ClubProjectMapper;
import com.geqian.evalution.teachingevalution.service.ClubProjectService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ClubProjectServiceImpl extends ServiceImpl<ClubProjectMapper, ClubProject>
    implements ClubProjectService {

  @Override
  public ResponseResult<PageResult<ClubProject>> getProjectList(PageRequest pageRequest) {
    LambdaQueryWrapper<ClubProject> wrapper = new LambdaQueryWrapper<>();
    String key = pageRequest.getKey();
    if (AssertUtils.isNotEmpty(key)) {
      wrapper.like(ClubProject::getProjectName, key);
    }
    wrapper.orderByDesc(ClubProject::getCreateTime);

    PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
    List<ClubProject> projectList = this.baseMapper.selectList(wrapper);
    PageInfo<ClubProject> pageInfo = new PageInfo<>(projectList);
    PageResult<ClubProject> pageResult = PageUtils.getPageResult(pageInfo);
    return ResponseResult.success(pageResult);
  }

  @Override
  public ResponseResult<Object> addProject(ClubProject project) {
    if (Objects.nonNull(project)) {
      // 获取当前用户信息
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
      String userId = loginUserDetails.getUser().getUserId().toString();
      String userName = loginUserDetails.getUser().getNickName();

      // 设置创建者信息
      project.setCreatorId(Integer.valueOf(userId));
      project.setCreatorName(userName);

      // 设置时间
      LocalDateTime now = LocalDateTime.now();
      project.setCreateTime(now);
      project.setUpdateTime(now);

      // 设置默认状态
      if (project.getStatus() == null) {
        project.setStatus(1);
      }

      this.save(project);
      return ResponseResult.success();
    }
    return ResponseResult.fail("项目信息不能为空");
  }

  @Override
  public ResponseResult<Object> updateProject(ClubProject project) {
    if (Objects.nonNull(project)) {
      project.setUpdateTime(LocalDateTime.now());
      this.updateById(project);
      return ResponseResult.success();
    }
    return ResponseResult.fail("项目信息不能为空");
  }

  @Override
  public ResponseResult<Object> deleteProject(String projectId) {
    if (Objects.nonNull(projectId)) {
      this.removeById(projectId);
      return ResponseResult.success();
    }
    return ResponseResult.fail("项目ID不能为空");
  }
}