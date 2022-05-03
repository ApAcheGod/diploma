import axios from 'axios';
import router from '../router';

import userRoles from '../models/userRoles';
import mutationsTypes from '../store/mutationsTypes'
import URL from './consts';

const actions = {
  userSignIn({ commit }, payload) {
    const data = {
      login: payload.login,
      password: payload.password,
    };

    commit(mutationsTypes.SET_LOADING, true);
    
    axios.post(`${URL}/login`, data)
      .then(() => axios.get(`${URL}/api/check`))
      .then((response) => { 
        commit(mutationsTypes.SET_USER, response.data);
        commit(mutationsTypes.SET_LOADING, false); 
        const userRole = response.data ? response.data.authentication.authorities[0].authority : userRoles.ROLE_ANONYMOUS;
        if (userRole === userRoles.ROLE_ADMIN) {
          router.push('/admin/teachers');
        }
        else if (userRole === userRoles.ROLE_TEACHER) {
          router.push('/teacher');
        }
        router.push('/student');
      })
      .catch((error) => {
        commit(mutationsTypes.SET_ERROR, error.message);
        commit(mutationsTypes.SET_LOADING, false);
    });
  },

  userSignOut({ commit }) {
    commit(mutationsTypes.CLEAR_USER);
    router.push('/signin');
  },
};

export default actions;
