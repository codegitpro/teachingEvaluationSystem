import request from "../axios/request";

export function getCourseList(params) {
    return request.get('/course/getCourseList',params);
}

export function getCourseAllList(params) {
    return request.get('/course/getCourseAllList',params);
}

export function getExistsCourseList(params) {
    return request.get('/course/getExistsCourseList',params);
}

export function getCourseInfoList(params) {
    return request.get('/course/getCourseInfoList',params);
}

export function updateCourse(params){
    return request.postJSON('/course/updateCourse', params);
}

export function addCourse(params){
    return request.postJSON('/course/addCourse', params);
}

export function deleteCourse(params){
    return request.get('/course/deleteCourse', params);
}
