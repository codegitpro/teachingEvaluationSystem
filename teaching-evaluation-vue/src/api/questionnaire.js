import request from "../axios/request";

export function getQuestionnaireList(params) {
    return request.get('/questionnaire/pageSearchQuestionnaire',params);
}
export function getQuestionList(params) {
    return request.get('/question/getQuestionList',params);
}

export function getSurveyAllList(params) {
    return request.get('/questionnaire/getSurveyAllList',params);
}

export function getWriteQuestionnaire(params) {
    return request.get('/questionnaire/getWriteQuestionnaire',params);
}

export function getSurveyQuestionList(params) {
    return request.get('/questionnaire/getSurveyQuestionList',params);
}

export function getSurveyChartData(params) {
    return request.get('/questionnaire/getSurveyChartData',params);
}

export function getQuestionDisplayData(params) {
    return request.get('/question/getQuestionDisplayData',params);
}

export function addQuestionnaire(params) {
    return request.postJSON('/questionnaire/addQuestionnaire',params);
}

export function submitQuestionnaire(params) {
    return request.postJSON('/answer/submitQuestionnaire',params);
}

export function addQuestionList(params) {
    return request.postJSON('/question/addQuestionList',params);
}


export function updateQuestionnaire(params) {
    return request.postJSON('/questionnaire/updateQuestionnaire',params);
}

export function operateQuestionnaire(params) {
    return request.postJSON('/questionnaire/operateQuestionnaire',params);
}

export function deleteQuestionnaire(params) {
    return request.get('/questionnaire/deleteQuestionnaire',params);
}

export function exportSurveyData(fileName) {
    return request.downloadFile('/questionnaire/exportSurveyData',fileName);
}
