import userRoles from "../models/userRoles";

const getters = {
  // appTitle(state) {
  //   return state.appTitle;
  // },
  getUserRole() {
    return localStorage.getItem('userRole');
  },
  isAuthenticated() {
    return localStorage.getItem('isAuthenticated');
  },
  getUser() {
    return JSON.parse(localStorage.getItem('user'));
  },
  getUserLogin() {
    return localStorage.getItem('userLogin');
  },
  getError(state) {
    return state.error;
  },
  getLoading(state) {
    return state.loading;
  },
  getTeacher(state) {
    return state.teacher;
  }
};

export default getters;
