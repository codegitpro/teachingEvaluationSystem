package com.geqian.evalution.teachingevalution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.common.utils.AssertUtils;
import com.geqian.common.utils.JwtUtils;
import com.geqian.evalution.teachingevalution.entity.EntrepreneurshipProject;
import com.geqian.evalution.teachingevalution.entity.User;
import com.geqian.evalution.teachingevalution.mapper.EntrepreneurshipProjectMapper;
import com.geqian.evalution.teachingevalution.service.EntrepreneurshipProjectService;
import com.geqian.evalution.teachingevalution.service.UserService;
import com.geqian.evalution.teachingevalution.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class EntrepreneurshipProjectServiceImpl extends
    ServiceImpl<EntrepreneurshipProjectMapper, EntrepreneurshipProject> implements EntrepreneurshipProjectService {

  @Resource
  private UserService userService;

  @Override
  public ResponseResult<PageResult<EntrepreneurshipProject>> getProjectList(PageRequest pageRequest) {
    LambdaQueryWrapper<EntrepreneurshipProject> wrapper = new LambdaQueryWrapper<>();
    String key = pageRequest.getKey();
    if (AssertUtils.isNotEmpty(key)) {
      wrapper.like(EntrepreneurshipProject::getProjectName, key);
    }
    PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
    List<EntrepreneurshipProject> projectList = this.baseMapper.selectList(wrapper);
    PageInfo<EntrepreneurshipProject> pageInfo = new PageInfo<>(projectList);
    PageResult<EntrepreneurshipProject> pageResult = PageUtils.getPageResult(pageInfo);
    return ResponseResult.success(pageResult);
  }

  @Override
  public ResponseResult<Object> addProject(EntrepreneurshipProject project) {
    if (Objects.nonNull(project)) {
      // 获取当前用户信息
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
          .getRequest();
      String token = request.getHeader("token");
      String userId = JwtUtils.parseToken(token);
      User user = userService.info(token);

      // 设置创建者信息
      project.setCreatorId(Long.valueOf(userId));
      project.setCreatorName(user.getNickName());

      // 设置时间
      Date now = new Date();
      project.setCreateTime(now);
      project.setUpdateTime(now);

      // 设置默认状态
      if (project.getStatus() == null) {
        project.setStatus(0);
      }

      this.save(project);
      return ResponseResult.success();
    }
    return ResponseResult.fail("项目信息不能为空");
  }

  @Override
  public ResponseResult<Object> updateProject(EntrepreneurshipProject project) {
    if (Objects.nonNull(project)) {
      project.setUpdateTime(new Date());
      this.updateById(project);
      return ResponseResult.success();
    }
    return ResponseResult.fail("项目信息不能为空");
  }

  @Override
  public ResponseResult<Object> deleteProject(String projectId) {
    if (Objects.nonNull(projectId)) {
      this.baseMapper.deleteById(projectId);
      return ResponseResult.success();
    }
    return ResponseResult.fail("项目ID不能为空");
  }
}