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
  getUser(state) {
    return state.user;
  },
  getError(state) {
    return state.error;
  },
  getLoading(state) {
    return state.loading;
  },
};

export default getters;
