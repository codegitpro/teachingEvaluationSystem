package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.EntrepreneurshipProject;
import com.geqian.evalution.teachingevalution.service.EntrepreneurshipProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "创业项目管理")
@RestController
@RequestMapping("/entrepreneurship/project")
public class EntrepreneurshipProjectController {

  @Resource
  private EntrepreneurshipProjectService projectService;

  @ApiOperation("获取项目列表")
  @GetMapping("/list")
  public ResponseResult<PageResult<EntrepreneurshipProject>> getProjectList(PageRequest pageRequest) {
    return projectService.getProjectList(pageRequest);
  }

  @ApiOperation("添加项目")
  @PostMapping("/add")
  public ResponseResult<Object> addProject(@RequestBody EntrepreneurshipProject project) {
    return projectService.addProject(project);
  }

  @ApiOperation("更新项目")
  @PutMapping("/update")
  public ResponseResult<Object> updateProject(@RequestBody EntrepreneurshipProject project) {
    return projectService.updateProject(project);
  }

  @ApiOperation("删除项目")
  @DeleteMapping("/delete/{projectId}")
  public ResponseResult<Object> deleteProject(@PathVariable("projectId") String projectId) {
    return projectService.deleteProject(projectId);
  }
}