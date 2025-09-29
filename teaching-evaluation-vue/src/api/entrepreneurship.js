import request from '@/axios/request'

// 获取项目列表
export function getProjectList(params) {
  return request.get('/entrepreneurship/project/list', params)
}

// 添加项目
export function addProject(data) {
  return request.postJSON('/entrepreneurship/project/add', data)
}

// 更新项目
export function updateProject(data) {
  return request.putJSON('/entrepreneurship/project/update', data)
}

// 删除项目
export function deleteProject(projectId) {
  return request.delete(`/entrepreneurship/project/delete/${projectId}`)
}

// 获取评分列表
export function getEvaluationList(params) {
  return request.get('/entrepreneurship/evaluation/list', params)
}

// 提交评分
export function submitEvaluation(data) {
  return request.postJSON('/entrepreneurship/evaluation/submit', data)
}

// 获取已评价项目列表
export function getEvaluatedProjects(params) {
  return request.get('/entrepreneurship/evaluation/evaluated', params)
}

// 获取未评价项目列表
export function getUnevaluatedProjects(params) {
  return request.get('/entrepreneurship/evaluation/unevaluated', params)
}

