import request from '@/axios/request'

// 获取创业项目列表
export function getProjectList(params) {
  return request.get('/health/project/list', params)
}

// 添加创业项目
export function addProject(data) {
  return request.postJSON('/health/project/add', data)
}

// 更新创业项目
export function updateProject(data) {
  return request.putJSON('/health/project/update', data)
}

// 删除创业项目
export function deleteProject(projectId) {
  return request.delete(`/health/project/delete/${projectId}`)
}

// 获取项目评分列表
export function getEvaluationList(params) {
  return request.get('/health/evaluation/list', params)
}

// 提交项目评分
export function submitEvaluation(data) {
  return request.postJSON('/health/evaluation/submit', data)
}

// 获取已评价项目列表
export function getEvaluatedProjects(params) {
  return request.get('/health/evaluation/evaluated', params)
}

// 获取未评价项目列表
export function getUnevaluatedProjects(params) {
  return request.get('/health/evaluation/unevaluated', params)
}

