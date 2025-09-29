package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.OtherProject;

public interface OtherProjectService extends IService<OtherProject> {

  ResponseResult<PageResult<OtherProject>> getProjectList(PageRequest pageRequest);

  ResponseResult<Object> addProject(OtherProject project);

  ResponseResult<Object> updateProject(OtherProject project);

  ResponseResult<Object> deleteProject(String projectId);
}