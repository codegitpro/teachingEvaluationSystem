package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.ClubProject;

public interface ClubProjectService extends IService<ClubProject> {

  ResponseResult<PageResult<ClubProject>> getProjectList(PageRequest pageRequest);

  ResponseResult<Object> addProject(ClubProject project);

  ResponseResult<Object> updateProject(ClubProject project);

  ResponseResult<Object> deleteProject(String projectId);
}