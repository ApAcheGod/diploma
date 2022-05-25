import axios from 'axios';
import router from '../router';

import userRoles from '../models/userRoles';
import mutationsTypes from './mutationsTypes'
import URL from './consts';
import methods from './methods';

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
        commit(mutationsTypes.SET_USER_LOGIN, payload.login);
        commit(mutationsTypes.SET_USER, response.data);
        commit(mutationsTypes.SET_LOADING, false); 
        
        const userRole = response.data ? response.data.authentication.authorities[0].authority : userRoles.ROLE_ANONYMOUS;

        if (userRole === userRoles.ROLE_ADMIN) {
          router.push('/admin/teachers');
        }
        else if (userRole === userRoles.ROLE_TEACHER) {
          router.push('/teacher');
        }
        else if (userRole === userRoles.ROLE_STUDENT) {
          router.push('/student');
        }
        
      })
      .catch((error) => {
        commit(mutationsTypes.SET_ERROR, error.message);
        commit(mutationsTypes.SET_LOADING, false);
    });
  },

  userSignOut({ commit }) {
    commit(mutationsTypes.CLEAR_USER_LOGIN);
    commit(mutationsTypes.CLEAR_USER);
    router.push('/signin');
  },
  
  dataInit({ commit }, payload){
    const teacherLogin = payload;
    return Promise.all([
      methods.getTeacherByLoginFetch(teacherLogin),
      methods.getRoomsFetch(),
      methods.getGroupsFetch(),
      methods.getMaterialsFetch(),
      methods.getSubjectsFetch(),
    ])
    .then((results) => {
      const [ teacherData, rooms, studentGroups, materials, subjects] = results;
      
      commit(mutationsTypes.SET_USER_DATA, teacherData);
      commit(mutationsTypes.SET_ROOMS_DATA, rooms);
      commit(mutationsTypes.SET_STUDENT_GROUPS_DATA, studentGroups);
      commit(mutationsTypes.SET_MATERIALS_DATA, materials);
      commit(mutationsTypes.SET_SUBJECTS_DATA, subjects);
    });
  },

  deleteRoom({ commit }, payload){
    const room = payload;
    return methods.deleteRoomFetch(room)
    .then(isSuccess => {
      if (isSuccess) {
        commit(mutationsTypes.DELETE_ROOM, room);
      }
    });
  },

  createRoom({ commit }, payload){
    const room = payload;
    return methods.createRoomFetch(room)
    .then(createdRoom => {
      if (createdRoom) {
        commit(mutationsTypes.CREATE_ROOM, createdRoom);
      }
    });
  },

  updateRoom({ commit }, payload){
    const room = payload;
    return methods.updateRoomFetch(room)
    .then(updatedRoom => {
      if (updatedRoom) {
        commit(mutationsTypes.UPDATE_ROOM, updatedRoom);
      }
    });
  },

  createSubject({ commit }, payload){
    const subject = payload;
    return methods.createSubjectFetch(subject)
        .then(createdSubject => {
          if (createdSubject) {
            commit(mutationsTypes.CREATE_SUBJECT, createdSubject);
          }
        });
  },

  updateSubject({ commit }, payload){
    const subject = payload;
    return methods.updateSubjectFetch(subject)
        .then(updatedSubject => {
          if (updatedSubject) {
            commit(mutationsTypes.UPDATE_SUBJECT, updatedSubject);
          }
        });
  },

  deleteSubject({ commit }, payload){
    const subject = payload;
    return methods.deleteSubjectFetch(subject)
        .then(isSuccess => {
          if (isSuccess) {
            commit(mutationsTypes.DELETE_SUBJECT, subject);
          }
        });
  },

  setActiveSubject({ commit }, payload) {
    const subject = payload;
    return new Promise((resolve, reject) => {
      commit(mutationsTypes.SET_ACTIVE_SUBJECT, subject);
      router.push('/teacher/subject/journal');
      resolve(true);
    });
  },

  deleteActiveSubject({ commit }, payload) {
    return new Promise((resolve, reject) => {
      commit(mutationsTypes.DELETE_ACTIVE_SUBJECT);
      router.push('/teacher/subjects');
      resolve(true);
    });
  },
};

export default actions;
