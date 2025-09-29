import request from '@/axios/request'

// 获取项目列表
export function getProjectList(params) {
  return request.get('/moral/project/list', params)
}

// 添加项目
export function addProject(data) {
  return request.postJSON('/moral/project/add', data)
}

// 更新项目
export function updateProject(data) {
  return request.putJSON('/moral/project/update', data)
}

// 删除项目
export function deleteProject(projectId) {
  return request.delete(`/moral/project/delete/${projectId}`)
}

// 获取项目评分列表
export function getEvaluationList(params) {
  return request.get('/moral/evaluation/list', params)
}

// 提交项目评分
export function submitEvaluation(data) {
  return request.postJSON('/moral/evaluation/submit', data)
}

// 获取已评价项目列表
export function getEvaluatedProjects(params) {
  return request.get('/moral/evaluation/evaluated', params)
}

// 获取未评价项目列表
export function getUnevaluatedProjects(params) {
  return request.get('/moral/evaluation/unevaluated', params)
}