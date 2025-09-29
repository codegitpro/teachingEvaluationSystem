package com.geqian.evalution.teachingevalution.controller;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.ClubProject;
import com.geqian.evalution.teachingevalution.service.ClubProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "社团活动与社会工作项目管理")
@RestController
@RequestMapping("/club/project")
public class ClubProjectController {

  @Resource
  private ClubProjectService clubProjectService;

  @ApiOperation("获取项目列表")
  @GetMapping("/list")
  public ResponseResult<PageResult<ClubProject>> getProjectList(PageRequest pageRequest) {
    return clubProjectService.getProjectList(pageRequest);
  }

  @ApiOperation("添加项目")
  @PostMapping("/add")
  public ResponseResult<Object> addProject(@RequestBody ClubProject project) {
    return clubProjectService.addProject(project);
  }

  @ApiOperation("更新项目")
  @PutMapping("/update")
  public ResponseResult<Object> updateProject(@RequestBody ClubProject project) {
    return clubProjectService.updateProject(project);
  }

  @ApiOperation("删除项目")
  @DeleteMapping("/delete/{projectId}")
  public ResponseResult<Object> deleteProject(@PathVariable("projectId") String projectId) {
    return clubProjectService.deleteProject(projectId);
  }
}