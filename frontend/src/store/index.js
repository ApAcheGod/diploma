import { createStore, createLogger } from 'vuex'

import state from './state';
import mutations from './mutations';
import actions from './actions';
import getters from './getters';

export default createStore({
  mutations: mutations,
  getters: getters,
  actions: actions,
  state: state,
  strict: false,
  plugins: [createLogger()],
})