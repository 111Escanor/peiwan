import axios from 'axios'
import { Message } from 'element-ui'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 请求拦截器：添加token
request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers['Authorization'] = token
  }
  return config
}, error => {
  return Promise.reject(error)
})

// 响应拦截器：处理错误
request.interceptors.response.use(response => {
  return response.data
}, error => {
  if (error.response) {
    const { status } = error.response
    if (status === 401) {
      Message.error('登录已过期，请重新登录')
      localStorage.clear()
      window.location.href = '/login'
    } else {
      Message.error(error.response.data?.msg || '请求失败')
    }
  } else {
    Message.error('网络错误')
  }
  return Promise.reject(error)
})

export default request