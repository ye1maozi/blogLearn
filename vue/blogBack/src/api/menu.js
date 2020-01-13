import { axios } from '@/utils/request'

export function getAllPermissions () {
  console.log('[menu]getAllPermissions')
  return axios({
    url: '/permissions',
    method: 'get'
  })
}

export function addPermission (param) {
  return axios({
    url: '/permissions',
    method: 'post',
    data: param
  })
}

export function updatePermission (param) {
  return axios({
    url: '/permissions',
    method: 'put',
    data: param
  })
}

export function getRoleList () {
  return axios({
    url: '/roles',
    method: 'get'
  })
}

export function addRole (param) {
  return axios({
    url: '/roles',
    method: 'post',
    data: param
  })
}

export function updateRole (param) {
  return axios({
    url: '/roles',
    method: 'put',
    data: param
  })
}

export function getSelfPermission (name) {
  return axios({
    url: '/permissions/' + name,
    method: 'get'
  })
}

export function getRolePermissionData (name) {
  return axios({
    url: '/permissions/role/' + name,
    method: 'get'
  })
}

export function changeRolePermission (param) {
  return axios({
    url: '/permissions/role',
    method: 'post',
    data: {
      ...param
    }
  })
}

export function getAllUser () {
  return axios({
    url: '/user',
    method: 'get'
  })
}

export function registerUser (param) {
  return axios({
    url: '/user',
    method: 'post',
    data: param
  })
}

export function getUserRoles (param) {
  return axios({
    url: '/user/role/' + param,
    method: 'get'
  })
}
export function getAllUserRoles (param) {
  return axios({
    url: '/user/role',
    method: 'get'
  })
}
export function changeUserRoles (param) {
  return axios({
    url: '/user/role',
    method: 'post',
    data: {
      ...param
    }
  })
}

export function getPermissionByUser (param) {
  return axios({
    url: '/user/permission/' + param,
    method: 'get'
  })
}
