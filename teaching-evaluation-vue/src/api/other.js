import request from '@/axios/request'

// 获取项目列表
export function getProjectList(params) {
  return request.get('/other/project/list', params)
}

// 添加项目
export function addProject(data) {
  return request.postJSON('/other/project/add', data)
}

// 更新项目
export function updateProject(data) {
  return request.putJSON('/other/project/update', data)
}

// 删除项目
export function deleteProject(projectId) {
  return request.delete(`/other/project/delete/${projectId}`)
}

// 获取项目评分列表
export function getEvaluationList(params) {
  return request.get('/other/evaluation/list', params)
}

// 提交项目评分
export function submitEvaluation(data) {
  return request.postJSON('/other/evaluation/submit', data)
}

// 获取已评价项目列表
export function getEvaluatedProjects(params) {
  return request.get('/other/evaluation/evaluated', params)
}

// 获取未评价项目列表
export function getUnevaluatedProjects(params) {
  return request.get('/other/evaluation/unevaluated', params)
}