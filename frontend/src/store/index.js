import { createStore, createLogger } from 'vuex'

import state from './state';
import mutations from './mutations';
import actions from './actions';
import base from './getters/base';
import maps from './getters/maps';
import studentGetters from './getters/student';
import teacherGetters from './getters/teacher';

export default createStore({
  mutations: mutations,
  getters: {...base, ...maps, ...teacherGetters, ...studentGetters},
  actions: actions,
  state: state,
  
  strict: process.env.NODE_ENV === 'production',
  plugins: process.env.NODE_ENV === 'production' ? [] : [createLogger()],
})