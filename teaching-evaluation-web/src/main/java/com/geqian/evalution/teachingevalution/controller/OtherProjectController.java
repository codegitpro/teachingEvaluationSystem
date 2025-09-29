package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.OtherProject;
import com.geqian.evalution.teachingevalution.service.OtherProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "其他能力与素养项目管理")
@RestController
@RequestMapping("/other/project")
public class OtherProjectController {

  @Resource
  private OtherProjectService otherProjectService;

  @ApiOperation("获取项目列表")
  @GetMapping("/list")
  public ResponseResult<PageResult<OtherProject>> getProjectList(PageRequest pageRequest) {
    return otherProjectService.getProjectList(pageRequest);
  }

  @ApiOperation("添加项目")
  @PostMapping("/add")
  public ResponseResult<Object> addProject(@RequestBody OtherProject project) {
    return otherProjectService.addProject(project);
  }

  @ApiOperation("更新项目")
  @PutMapping("/update")
  public ResponseResult<Object> updateProject(@RequestBody OtherProject project) {
    return otherProjectService.updateProject(project);
  }

  @ApiOperation("删除项目")
  @DeleteMapping("/delete/{projectId}")
  public ResponseResult<Object> deleteProject(@PathVariable("projectId") String projectId) {
    return otherProjectService.deleteProject(projectId);
  }
}