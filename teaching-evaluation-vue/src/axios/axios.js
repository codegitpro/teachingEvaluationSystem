import axios from 'axios'

const service = axios.create({
  timeout: 20000
})

/* 判断生产环境和开发环境 */
if (process.env.NODE_ENV === 'production') {
  // 生产环境
  service.defaults.baseURL = 'http://192.168.119.100:8888/api/evaluation'
} else {
  // 开发环境
  service.defaults.baseURL = 'http://localhost:8888/api/evaluation'
}

service.interceptors.request.use(config => {
  if (config.url !== '/user/login'){
    config.headers['token'] = window.sessionStorage.getItem('token');
  }
  return config
}, err => {
  return Promise.reject(err)
})

// 默认响应拦截器，拦截异常
service.interceptors.response.use(res => {
  if (res.data.code) {
    return res.data
  }
  return res
}, err => {
  if (err && err.response) {
    switch (err.response.status) {
      case 400:
        err.message = '请求错误(400)'
        break
      case 401:
        err.message = '未授权，请重新登录(401)'
        break
      case 403:
        err.message = '拒绝访问(403)'
        break
      case 404:
        err.message = '请求出错(404)'
        break
      case 408:
        err.message = '请求超时(408)'
        break
      case 500:
        err.message = '服务器错误(500)'
        break
      case 501:
        err.message = '服务未实现(501)'
        break
      case 502:
        err.message = '网络错误(502)'
        break
      case 503:
        err.message = '服务不可用(503)'
        break
      case 504:
        err.message = '网络超时(504)'
        break
      case 505:
        err.message = 'HTTP版本不受支持(505)'
        break
      default:
        err.message = `连接出错(${err.response.status})!`
    }
  } else {
    err.message = '连接服务器失败!'
  }
  return Promise.reject(err)
})

export default service
