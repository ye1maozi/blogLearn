// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store/'

// eslint-disable-next-line no-unused-vars
import $ from 'jquery'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min.js'

// import { VueAxios } from './util/request'
import config from '@/config/defaultConfig'
import VueStorage from 'vue-ls'
// import axios from 'axios'
// Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.use(VueStorage, config.storageOptions)
// Vue.use(VueAxios)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
console.log(`API_URL: ${process.env.VUE_APP_API_BASE_URL}`)
router.afterEach((to, from, next) => {
  window.scrollTo(0, 0)
})
