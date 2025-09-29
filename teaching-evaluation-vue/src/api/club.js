import request from '@/axios/request'

// 获取项目列表
export function getProjectList(params) {
  return request.get('/club/project/list', params)
}

// 添加项目
export function addProject(data) {
  return request.postJSON('/club/project/add', data)
}

// 更新项目
export function updateProject(data) {
  return request.putJSON('/club/project/update', data)
}

// 删除项目
export function deleteProject(projectId) {
  return request.delete(`/club/project/delete/${projectId}`)
}

// 获取项目评分列表
export function getEvaluationList(params) {
  return request.get('/club/evaluation/list', params)
}

// 提交项目评分
export function submitEvaluation(data) {
  return request.postJSON('/club/evaluation/submit', data)
}

// 获取已评价项目列表
export function getEvaluatedProjects(params) {
  return request.get('/club/evaluation/evaluated', params)
}

// 获取未评价项目列表
export function getUnevaluatedProjects(params) {
  return request.get('/club/evaluation/unevaluated', params)
}