import { createStore } from 'vuex'

export default createStore({
  state: {
    toasts: []
  },
  getters: {
  },
  mutations: {
    addToast(state, toast) {
      state.toasts.push(toast);
    },
    clearToast(state, title) {
      const index = state.toasts.findIndex((toast) => toast.title === title); // find toast
      state.toasts.splice(index, 1); // remove toast from array
    }
  },
  actions: {
  },
  modules: {
  }
})
