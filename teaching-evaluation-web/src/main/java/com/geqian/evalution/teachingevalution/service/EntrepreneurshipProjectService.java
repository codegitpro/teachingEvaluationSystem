package com.geqian.evalution.teachingevalution.service;

import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.EntrepreneurshipProject;
import com.baomidou.mybatisplus.extension.service.IService;

public interface EntrepreneurshipProjectService extends IService<EntrepreneurshipProject> {

  ResponseResult<PageResult<EntrepreneurshipProject>> getProjectList(PageRequest pageRequest);

  ResponseResult<Object> addProject(EntrepreneurshipProject project);

  ResponseResult<Object> updateProject(EntrepreneurshipProject project);

  ResponseResult<Object> deleteProject(String projectId);
}