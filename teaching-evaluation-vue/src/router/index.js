import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout/Main'

Vue.use(VueRouter)
//解决vue路由重复导航错误
//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

// 完整路由代码
export default new VueRouter({
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('@/views/login/Login'),
    },
    {
      path: '/main',
      name: 'main',
      component: Layout,
      children: [
        {
          path: '/settingQuestion',
          name: 'settingQuestion',
          component: () => import('@/views/evaluation/SettingQuestion'),
          meta: {
            icon: 'setting' // 设置问题页面使用设置图标
          }
        },
        {
          path: '/writeQuestion',
          name: 'writeQuestion',
          component: () => import('@/views/evaluation/WriteQuestion'),
        },
        {
          path: '/marking',
          name: 'marking',
          component: () => import('@/views/evaluation/Marking'),
        }
      ]
    },
    {
      path: '/entrepreneurship',
      component: Layout,
      name: 'Entrepreneurship',
      meta: { title: '创业模块', icon: 'el-icon-s-flag' },
      children: [
        {
          path: 'project',
          component: () => import('@/views/entrepreneurship/project/index'),
          name: 'EntrepreneurshipProject',
          meta: { title: '创业项目管理' }
        },
        {
          path: 'evaluation',
          component: () => import('@/views/entrepreneurship/evaluation/index'),
          name: 'EntrepreneurshipEvaluation',
          meta: { title: '项目评分列表', noCache: true },
          hidden: true
        }
      ]
    }
  ]
})
