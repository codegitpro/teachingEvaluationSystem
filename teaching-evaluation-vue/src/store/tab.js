//面包屑导航
export default {
    namespaced: true,
    //存储数据
    state: {
        isCollapse: false,
        //面包屑列表
        tabList: [
            {
                path: '/home',
                name: 'home',
                label: '首页',
                icon: 's-home',
                url: 'home/Home',
            }
        ],
        routeRecordList: ['home']
    },
    //调用方法
    mutations: {
        stepNext(state, item) {
            state.routeRecordList.push(item)
        },
        updateRouteRecord(state) {
            let name = state.routeRecordList.pop();
            if (state.tabList[state.tabList.length - 1].routeName === name) {
                state.tabList.pop();
            }
        },
        //菜单栏展开和收收起
        collapseMenu(state) {
            state.isCollapse = !state.isCollapse
        },
        //更新面包屑数据
        updateMenu(state, item) {
            if (item.label !== 'home') {
                let index = state.tabList.findIndex(tabItem => tabItem.label === item.label);
                if (index === -1) {
                    state.tabList.push(item);
                }
            }
        }
    },
    actions: {
        stepBack(context) {
            let routeRecordList = context.state.routeRecordList;
            if (routeRecordList.length > 1) {
                let name = routeRecordList[routeRecordList.length - 2];
                context.commit('updateRouteRecord')
                return name;
            }
        },
    }
}
