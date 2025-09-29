package com.geqian.evalution.teachingevalution.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.geqian.common.common.PageRequest;
import com.geqian.common.common.PageResult;
import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.entity.VolunteerProject;

public interface VolunteerProjectService extends IService<VolunteerProject> {

  ResponseResult<PageResult<VolunteerProject>> getProjectList(PageRequest pageRequest);

  ResponseResult<Object> addProject(VolunteerProject project);

  ResponseResult<Object> updateProject(VolunteerProject project);

  ResponseResult<Object> deleteProject(String projectId);
}