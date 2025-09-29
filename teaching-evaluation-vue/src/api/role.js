import request from "../axios/request";

export function getRoleList(params) {
    return request.get('/role/getRoleList',params);
}

export function getRoleAllList() {
    return request.get('/role/getRoleAllList');
}

export function updateRole(params){
    return request.postJSON('/role/updateRole', params);
}

export function grantPermission(params){
    return request.postJSON('/role/grantPermissions', params);
}

export function addRole(params){
    return request.postJSON('/role/addRole', params);
}

export function deleteRole(params){
    return request.get('/role/deleteRole', params);
}

