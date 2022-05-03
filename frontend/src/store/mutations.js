import userRoles from "../models/userRoles";

const mutations = {
  setUser(state, payload) {
    state.user = payload;
    localStorage.setItem('user', JSON.stringify(payload));

    const userRole = payload ? payload.authentication.authorities[0].authority : userRoles.ROLE_ANONYMOUS;

    localStorage.setItem('userRole', userRole)
    localStorage.setItem('isAuthenticated', userRole !== userRoles.ROLE_ANONYMOUS);
  },
  clearUser(state) {
    state.user = null;
    localStorage.removeItem('user');

    localStorage.removeItem('userRole');
    localStorage.removeItem('isAuthenticated');
  },
  setError(state, payload) {
    state.error = payload;
  },
  setLoading(state, payload) {
    state.loading = payload;
  },
};

export default mutations;
