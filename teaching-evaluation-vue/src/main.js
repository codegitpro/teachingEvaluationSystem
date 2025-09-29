import Vue from 'vue'
import App from './App.vue'
import router from './router/permisson'  //引入 vue-router
import store from './store'    //引入 vuex

// 全局配置
import '@/assets/scss/reset.scss' //全局样式
import 'element-ui/lib/theme-chalk/index.css' //element-ui样式
import * as echarts from 'echarts'
import i18n from '@/language/index'
// 第三方包
import ElementUI from 'element-ui'

// 注册 Element UI
Vue.use(ElementUI)

Vue.config.productionTip = false
Vue.prototype.$echarts = echarts
new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app')
