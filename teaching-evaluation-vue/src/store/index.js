import Vue from 'vue'
import Vuex from 'vuex'
import tab from './tab'
import user from "./user";
import menu from "./menu";
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        tab,
        user,
        menu
    },
    //持久化插件，解决刷新页面vuex中数据丢失
    plugins: [createPersistedState({
        storage: window.sessionStorage,
        // 配置需要持久化的 state 属性
        paths: ['user']
    })]
})
