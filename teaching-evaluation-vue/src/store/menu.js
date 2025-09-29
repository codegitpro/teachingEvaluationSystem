import {getMenuList} from "../api/menu";
export default {
    namespaced: true,
    state: {
        menuRoute: [],
        menuList: []
    },
    getters: {
        GETMENULIST(state){
            return state.menuList;
        }
    },
    mutations: {
        SETMENUROUTE(state, data) {
            state.menuRoute = data
        },
        REFRESHMENU(state,data){
            state.menuList = data;
        }
    },
    actions: {
        async setMenuRoute({commit}) {
            let {code, data} = await getMenuList()
            commit('SETMENUROUTE', data || [])
            return {code, data}
        },
        async refreshMenu({commit}) {
            let {data} = await getMenuList()
            commit('REFRESHMENU', data || [])
        }
    }
}

