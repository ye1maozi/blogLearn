import axios from 'axios'
import Vue from 'vue'
import { ACCESS_TOKEN } from '@/store/mutation-types'
// import { VueAxios } from './axios'
// 创建 axios 实例
const service = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL, // api base_url
  timeout: 6000 // 请求超时时间
})

const err = (error) => {
  return Promise.reject(error)
}

service.interceptors.request.use(config => {
  const token = Vue.ls.get(ACCESS_TOKEN)
  if (token) {
    config.headers['Access-Token'] = token // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  console.log(config)
  return config
}, err)

// response interceptor
service.interceptors.response.use((response) => {
  return response.data
}, err)

// const installer = {
//   vm: {},
//   install (Vue) {
//     Vue.use(VueAxios, service)
//   }
// }
export {
  // installer as VueAxios,
  service as axios
}
