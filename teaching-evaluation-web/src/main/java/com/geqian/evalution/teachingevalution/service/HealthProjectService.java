package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.HealthProject;

public interface HealthProjectService extends IService<HealthProject> {

  ResponseResult<PageResult<HealthProject>> getProjectList(PageRequest pageRequest);

  ResponseResult<Object> addProject(HealthProject project);

  ResponseResult<Object> updateProject(HealthProject project);

  ResponseResult<Object> deleteProject(String projectId);
}