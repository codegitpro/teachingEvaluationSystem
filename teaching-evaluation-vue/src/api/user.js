import request from "../axios/request";

export function login(params) {
    return request.postJSON('/user/login',params)
}

export function updatePassword(params) {
    return request.postForm('/user/updatePassword',params)
}

export function getUserInfo(params) {
    return request.get('/user/getUserInfo',params);
}

export function logout() {
    return request.get('/user/logout');
}

export function getUserList(params) {
    return request.get('/user/pageSearch',params);
}

export function deleteUser(params) {
    return request.get('/user/delete',params);
}


export function updateUser(file,params) {
    return request.uploadFile('/user/update',file,params);
}


export function addUser(file,params) {
    return request.uploadFile('/user/add',file,params);
}

export function grantRole(params) {
    return request.postJSON('/user/grantRole',params);
}

