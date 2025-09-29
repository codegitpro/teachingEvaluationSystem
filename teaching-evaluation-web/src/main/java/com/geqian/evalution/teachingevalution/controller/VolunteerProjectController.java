package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.VolunteerProject;
import com.geqian.evalution.teachingevalution.service.VolunteerProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "社会实践与志愿服务项目管理")
@RestController
@RequestMapping("/volunteer/project")
public class VolunteerProjectController {

  @Resource
  private VolunteerProjectService volunteerProjectService;

  @ApiOperation("获取项目列表")
  @GetMapping("/list")
  public ResponseResult<PageResult<VolunteerProject>> getProjectList(PageRequest pageRequest) {
    return volunteerProjectService.getProjectList(pageRequest);
  }

  @ApiOperation("添加项目")
  @PostMapping("/add")
  public ResponseResult<Object> addProject(@RequestBody VolunteerProject project) {
    return volunteerProjectService.addProject(project);
  }

  @ApiOperation("更新项目")
  @PutMapping("/update")
  public ResponseResult<Object> updateProject(@RequestBody VolunteerProject project) {
    return volunteerProjectService.updateProject(project);
  }

  @ApiOperation("删除项目")
  @DeleteMapping("/delete/{projectId}")
  public ResponseResult<Object> deleteProject(@PathVariable("projectId") String projectId) {
    return volunteerProjectService.deleteProject(projectId);
  }
}