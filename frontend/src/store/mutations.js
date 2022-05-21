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
  setUserLogin(state, payload) {
    state.userLogin = payload;
    localStorage.setItem('userLogin', payload);
  },
  clearUserLogin(state) {
    state.userLogin = null;
    localStorage.removeItem('userLogin');
  },
  setUserData(state, payload) {
    state.userData = payload;
  },
  clearUserData(state) {
    state.userData = [];
  },

  setError(state, payload) {
    state.error = payload;
  },
  setLoading(state, payload) {
    state.loading = payload;
  },

  setRoomsData(state, payload) {
    state.rooms = payload;
  },
  setStudentGroupsData(state, payload) {
    state.studentGroups = payload;
  },
  setMaterialsData(state, payload) {
    state.materials = payload;
  },
  setSubjectsData(state, payload) {
    state.subjects = payload;
  },

  deleteRoom(state, payload){
    const room = payload;
    state.userData.rooms?.splice(state.userData.rooms.findIndex(r => room.id === r.id), 1); // Teacher
    state.rooms.splice(state.rooms.findIndex(r => room.id === r.id), 1);
  },
  createRoom(state, payload){
    const room = payload;
    state.userData.rooms?.push(room); // Teacher
    state.rooms.push(room);
  },
  updateRoom(state, payload){
    const room = payload;
    const updatedRoomIdTeacher = state.userData.rooms.findIndex(r => room.id === r.id); // Teacher
    state.userData.rooms[updatedRoomIdTeacher] = {...state.userData.rooms[updatedRoomIdTeacher], ...payload}; // Teacher
    
    const updatedRoomId = state.rooms.findIndex(r => room.id === r.id);
    state.rooms[updatedRoomId] = {...state.rooms[updatedRoomId], ...payload};
  },
};

export default mutations;
