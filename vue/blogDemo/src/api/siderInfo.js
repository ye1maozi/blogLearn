import { axios } from '@/util/request'

export function getUserInfo () {
  return axios({
    url: 'userhome',
    method: 'get'
  })
}
