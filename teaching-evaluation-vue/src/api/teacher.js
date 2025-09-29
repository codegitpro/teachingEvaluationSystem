import request from "../axios/request";

export function getTeacherList(params) {
    return request.get('/teacher/getTeacherList',params);
}
export function getTeacherAllList(params) {
    return request.get('/teacher/getTeacherAllList',params);
}

export function getTeacherCourseList(params) {
    return request.get('/teacherCourse/getTeacherCourseList',params);
}

export function deleteTeacherCourse(params) {
    return request.postJSON('/teacherCourse/deleteTeacherCourse',params);
}

export function updateTeacher(params){
    return request.postJSON('/teacher/updateTeacher', params);
}

export function addTeacher(params){
    return request.postJSON('/teacher/addTeacher', params);
}

export function distributeCourses(params){
    return request.postJSON('/teacher/distributeCourses', params);
}

export function deleteTeacher(params){
    return request.get('/teacher/deleteTeacher', params);
}
