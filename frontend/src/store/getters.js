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
    const studentGroupsObject = new Map();
    state.studentGroups.forEach(group => studentGroupsObject.set(group.id, group));
    const groupsByRooms = state.userData.rooms?.map(room => {
      return { 
        roomName : room.name, 
        roomGroups : state.subjects.filter(subject => subject.roomId === room.id)
          .map(subject => subject.groups.map(group => studentGroupsObject.get(group.id)))
          .flat(3),
      }
    });
    return groupsByRooms;
  },

  getUserSubjects(state) {
    const userSubjectsByRooms = state.userData.rooms?.map(room => {
      return { 
        roomName : room.name,
        roomId : room.id,
        roomSubjects : state.subjects.filter(subject => subject.roomId === room.id),
      }
    });
    return userSubjectsByRooms;
  },

  getUserMaterials(state) {
    const userMaterialsByRooms = state.userData.rooms?.map(room => {
      return {
        roomName: room.name,
        roomMaterials: state.subjects.filter(subject => subject.roomId === room.id)?.map(subject => subject.materials)[0],
      }
    });
    return userMaterialsByRooms;
  },

};

export default getters;
