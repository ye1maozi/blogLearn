import Vue from 'vue'
import Router from 'vue-router'
// eslint-disable-next-line no-unused-vars
import HelloWorld from '@/components/HelloWorld'
import loginPage from '@/views/loginPage'
import registerPage from '../views/registerPage'
import home from '@/views/home'
import articlePage from '@/views/article'
import categoryPage from '@/views/categories'
import tagsPage from '@/views/tags'
import historyView from '@/views/historyView'
Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.NODE_ENV === 'production' ? '/' : '/',
  // 注册路由
  routes: [
    {
      path: '/',
      name: 'home',
      component: home
    },
    {
      path: '/login',
      name: 'login',
      component: loginPage
    },
    {
      path: '/register',
      name: 'register',
      component: registerPage
    },
    {
      path: '/articles/:id',
      name: 'articles',
      component: articlePage
    },
    {
      path: '/categories/:id',
      name: 'categories',
      component: categoryPage
    },
    {
      path: '/tags/:id',
      name: 'tags',
      component: tagsPage
    },
    {
      path: '/history/:year?/:month?',
      name: 'history',
      component: historyView
    }
  ]
})
