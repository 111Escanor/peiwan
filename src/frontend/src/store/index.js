import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: null
  },
  mutations: {
    SET_USER_INFO(state, info) {
      state.userInfo = info
    }
  },
  actions: {
    setUserInfo({ commit }, info) {
      commit('SET_USER_INFO', info)
    }
  }
})