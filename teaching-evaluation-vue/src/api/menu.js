import request from "../axios/request";

export function getMenuList() {
    return request.get('/menu/getMenuList');
}

export function getMenuAllList() {
    return request.get('/menu/getMenuAllList');
}

export function getMenuPageList(params) {
    return request.get('/menu/getMenuPageList',params);
}

export function updateMenu(params){
    return request.postJSON('/menu/updateMenu', params);
}

export function addMenu(params){
    return request.postJSON('/menu/addMenu', params);
}

export function getMenuDirList() {
    return request.get('/menu/getMenuDirList');
}


export function deleteMenu(params) {
    return request.get('/menu/deleteMenu',params);
}
