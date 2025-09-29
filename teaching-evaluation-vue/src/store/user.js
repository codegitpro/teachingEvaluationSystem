//面包屑导航
export default {
    namespaced: true,
    //存储数据
    state: {
       userInfo: {}
    },
    //调用方法
    mutations: {
        setUserInfo(state,userInfo){
            state.userInfo = userInfo;
        }
    },
    actions: {}
}
