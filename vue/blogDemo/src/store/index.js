import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
Vue.use(Vuex)

const _store = new Vuex.Store({
  modules: {
    app
  },
  state: {

  },
  mutations: {

  },
  actions: {

  },
  getters: {
    // getCount: state => state.app.count,
  }
})
// Object.assign(app.getter, _store.getter)
console.log(_store)
export default _store
