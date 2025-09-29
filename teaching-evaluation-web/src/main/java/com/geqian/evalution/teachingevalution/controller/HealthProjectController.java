package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.HealthProject;
import com.geqian.evalution.teachingevalution.service.HealthProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "身心健康项目管理")
@RestController
@RequestMapping("/health/project")
public class HealthProjectController {

  @Resource
  private HealthProjectService healthProjectService;

  @ApiOperation("获取项目列表")
  @GetMapping("/list")
  public ResponseResult<PageResult<HealthProject>> getProjectList(PageRequest pageRequest) {
    return healthProjectService.getProjectList(pageRequest);
  }

  @ApiOperation("添加项目")
  @PostMapping("/add")
  public ResponseResult<Object> addProject(@RequestBody HealthProject project) {
    return healthProjectService.addProject(project);
  }

  @ApiOperation("更新项目")
  @PutMapping("/update")
  public ResponseResult<Object> updateProject(@RequestBody HealthProject project) {
    return healthProjectService.updateProject(project);
  }

  @ApiOperation("删除项目")
  @DeleteMapping("/delete/{projectId}")
  public ResponseResult<Object> deleteProject(@PathVariable("projectId") String projectId) {
    return healthProjectService.deleteProject(projectId);
  }
}