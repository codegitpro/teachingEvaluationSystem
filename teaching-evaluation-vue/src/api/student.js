import request from "../axios/request";

export function getStudentList(params) {
    return request.get('/student/getStudentList',params);
}

export function getStudentCourseList(params) {
    return request.get('/studentCourse/getStudentCourseList',params);
}

export function deleteStudentCourse(params) {
    return request.postJSON('/studentCourse/deleteStudentCourse',params);
}

export function updateStudent(params){
    return request.postJSON('/student/updateStudent', params);
}

export function addStudent(params){
    return request.postJSON('/student/addStudent', params);
}

export function courseAllocation(params){
    return request.postJSON('/student/courseAllocation', params);
}


export function deleteStudent(params){
    return request.get('/student/deleteStudent', params);
}
