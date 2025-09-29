package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.MoralProject;

public interface MoralProjectService extends IService<MoralProject> {

  ResponseResult<PageResult<MoralProject>> getProjectList(PageRequest pageRequest);

  ResponseResult<Object> addProject(MoralProject project);

  ResponseResult<Object> updateProject(MoralProject project);

  ResponseResult<Object> deleteProject(String projectId);
}