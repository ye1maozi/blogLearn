import { axios } from '@/utils/request'

export function getAllTags () {
  return axios({
    url: '/tags',
    method: 'get'
  })
}

export function addTag (param) {
  return axios({
    url: '/tags',
    method: 'post',
    data: param
  })
}

export function getAllCateogry () {
  return axios({
    url: '/categorys',
    method: 'get'
  })
}

export function addCategory (param) {
  return axios({
    url: '/categorys',
    method: 'post',
    data: param
  })
}
export function editCategory (param) {
  return axios({
    url: '/categorys',
    method: 'put',
    data: param
  })
}
export function handleUpload (formData) {
  return axios({
    url: '/files',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: formData
  })
}

export function postArticle (formData) {
  return axios({
    url: '/articles',
    method: 'post',
    data: formData
  })
}
export function getArticleList () {
  return axios({
    url: '/articles',
    method: 'get',
  })
}
export function getArticleById (id) {
  return axios({
    url: '/articles/' + id,
    method: 'get',
  })
}
export function updateArticle (formData) {
  return axios({
    url: '/articles',
    method: 'put',
    data: formData
  })
}

export function getAllComments (param) {
  return axios({
    url: '/comments',
    method: 'get',
    params: {
      status: param.status,
      pageNo: param.pageNo,
      pageSize: 10
    }
  })
}

export function changeCommentStatus (status, id) {
  return axios({
    url: '/comments',
    method: 'put',
    data: {
      status: status,
      id: id
    }
  })
}

export function getCommentTotal (status) {
  return axios({
    url: '/comments/total',
    method: 'get',
    params: {
      status: status
    }
  })
}