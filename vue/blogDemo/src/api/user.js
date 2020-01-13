import { axios } from '@/util/request'

export function loginUser (data) {
  return axios({
    url: 'login',
    method: 'post',
    data: data
  })
}

export function registerUser (data) {
  return axios({
    url: 'register',
    method: 'post',
    data: data
  })
}
