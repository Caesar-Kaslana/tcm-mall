import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store'
import router from '../router'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
  withCredentials: true
})

request.interceptors.request.use(config => {
  return config
}, error => {
  return Promise.reject(error)
})

request.interceptors.response.use(response => {
  const res = response.data
  if (res.code !== 200) {
    ElMessage.error(res.message || '请求失败')
    if (res.code === 401) {
      const store = useUserStore()
      store.logout()
      router.push('/login')
    }
    return Promise.reject(new Error(res.message))
  }
  return res
}, error => {
  const status = error.response?.status
  const data = error.response?.data
  let msg = '网络错误'
  if (status) {
    msg = data?.message || `服务器错误 (HTTP ${status})`
  } else if (error.message) {
    msg = `请求失败: ${error.message}`
  }
  console.error('[Request Error]', error)
  ElMessage.error(msg)
  return Promise.reject(error)
})

export default request
