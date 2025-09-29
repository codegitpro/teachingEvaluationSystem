import request from "../axios/request";

export function getEvaluationList(params) {
    return request.get('/evaluation/getEvaluationInfoList',params);
}

export function submitMarking(params) {
    return request.postJSON('/marking/submitMarking',params);
}


export function getMarkChartData(params) {
    return request.get('/marking/getMarkChartData',params);
}

export function getIndexAvgScore(params) {
    return request.get('/marking/getIndexAvgScore',params);
}

export function exportMarkData(params) {
    return request.downloadFile('/marking/exportMarkData',params);
}

export function getEvaluationTime(params) {
    return request.get('/evaluation/getEvaluationTime',params);
}


export function openEvaluationInfo(params) {
    return request.postJSON('/evaluation/openEvaluationInfo',params);
}

export function getMarkingDetail(params) {
    return request.get('/marking/getTeacherMarkDetail',params);
}

