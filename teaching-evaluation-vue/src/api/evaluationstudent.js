import request from "../axios/request";

export function getEvaluationStudentList(params) {
    return request.get('/evaluationStudent/getEvaluationStudentList',params);
}

export function updateEvaluationStudent(params){
    return request.postJSON('/evaluationStudent/updateEvaluationStudent', params);
}

export function addEvaluationStudent(params){
    return request.postJSON('/evaluationStudent/addEvaluationStudent', params);
}

export function deleteEvaluationStudent(params){
    return request.get('/evaluationStudent/deleteEvaluationStudent', params);
}
