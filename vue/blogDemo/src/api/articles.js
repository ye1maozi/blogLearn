import { axios } from '@/util/request'

export function getArticles (query, page) {
  return axios({
    url: '/articles',
    method: 'get',
    params: {
      pageNo: page.pageNo,
      pageSize: page.pageSize,
      six: page.name,
      sort: page.sort,
      year: query.year,
      month: query.month,
      tagId: query.tagId,
      categoryId: query.categoryId
    }
  })
}

export function getArticle (id) {
  return axios({
    url: '/articles/' + id,
    method: 'get'
  })
}

export function getAllCateogry (flag) {
  // console.log("getAllCateogry")
  // console.log(flag)
  if (flag === 'tagId') {
    return axios({
      url: '/tags',
      method: 'get'
    })
  } else {
    return axios({
      url: '/categorys',
      method: 'get'
    })
  }
}

export function commentArticle (data) {
  return axios({
    url: '/comments',
    method: 'post',
    data: data
  })
}
export function getComment (query, page) {
  return axios({
    url: '/comments',
    method: 'get',
    params: {
      pageNo: page.pageNo,
      pageSize: page.pageSize,
      six: page.name,
      sort: page.sort,
      articleId: query.articleId
    }
  })
}

export function getArticleHistory () {
  return axios({
    url: '/articles/history',
    method: 'get'
  })
}
