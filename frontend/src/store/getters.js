import userRoles from "../models/userRoles";

const getters = {
  // appTitle(state) {
  //   return state.appTitle;
  // },

  isAuthenticated() {
    return localStorage.getItem('isAuthenticated');
  },
  getUser() {
    return JSON.parse(localStorage.getItem('user'));
  },
  getUserRole() {
    return localStorage.getItem('userRole');
  },
  getUserLogin() {
    return localStorage.getItem('userLogin');
  },
  getUserData(state) {
    return state.userData;
  },

  getError(state) {
    return state.error;
  },
  getLoading(state) {
    return state.loading;
  },

  getTeacherRooms(state) {
    return state.userData.rooms;
  },

  getTeacherStudentsGroups(state) {
    const studentGroupsObject = state.studentGroups.map(group => { return {[group.id] : group} });
    const groupsByRooms = state.userData.rooms?.map(room => {
      return { 
        roomName : room.name, 
        roomGroups : state.subjects.filter(subject => subject.roomId === room.id)
          .map(subject => subject.groups
            .map(group => studentGroupsObject[group.id])),
      }
    });
    return groupsByRooms;
  },

  getUserSubjects(state) {
    const userSubjectsByRooms = state.userData.rooms?.map(room => {
      return { 
        roomName : room.name, 
        roomSubjects : state.subjects.filter(subject => subject.roomId === room.id),
      }
    });
    return userSubjectsByRooms;
  },

  getUserMaterials(state) {

  },

};

export default getters;
