import request from "../axios/request";

export function getHomeData(params) {
    return request.get('/home/getCount',params);
} 