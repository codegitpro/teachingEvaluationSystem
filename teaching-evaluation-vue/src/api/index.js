import request from "../axios/request";

export function getIndexList(params) {
    return request.get('/index/getIndexList',params);
}
export function getIndexAllList(params) {
    return request.get('/index/getIndexAllList',params);
}
export function updateIndex(params){
    return request.postJSON('/index/updateIndex', params);
}

export function addIndex(params){
    return request.postJSON('/index/addIndex', params);
}

export function deleteIndex(params){
    return request.get('/index/deleteIndex', params);
}
