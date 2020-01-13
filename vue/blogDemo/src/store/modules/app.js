const app = {
  state: {
    siderInfo: null,
    count: 1
  },
  mutations: {
    SET_INFO: (state, info) => {
      state.siderInfo = info
    }
  },
  actions: {
    checkGetInfo ({ commit }, data) {
      return new Promise(resolve => {
        console.log('get info')
        commit('SET_INFO', data)
        resolve()
      })
    }
  },
  getters: {
    getInfo: state => state.siderInfo,
    getCount: state => state.count
  }
}

export default app
