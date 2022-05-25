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

  getAllGroups(state) {
    return state.studentGroups;
  },
  getAllStudents(state) {
    return state.students;
  },
  getAllExminations(state) {
    return state.examinations;
  },
  getAllSubjects(state) {
    return state.subjects;
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
        roomMaterials: state.subjects.filter(subject => subject.roomId === room.id)?.map(subject => subject.materials).flat(3),
      }
    });
    return userMaterialsByRooms;
  },

  getUserSolutions(state){
    const userSolutionsByRooms = state.userData.rooms?.map(room => {
      return {
        roomName : room.name,
        roomId : room.id,
        roomSolutions : state.tasks.map(task => task.solutions).flat(3),
      }
    });
    return userSolutionsByRooms;
  },

  getUserExaminations(state){
    const userExaminationsByRooms = state.userData.rooms?.map( room => {
      return {
        roomName : room.name,
        roomId : room.id,
        roomExaminations : state.examinations.flat(3),
      }
    });
    return userExaminationsByRooms;
  },

  getFormattedJournal(state){
  //   const groupsMapByGroupId = new Map();
  //   this.getAllGroups(state)
  //   .forEach(g => groupsMapByGroupId.set(g.id, g));

  //   const studentsMapByStudentId = new Map();
  //   this.getAllStudents(state)
  //   .forEach(s => studentsMapByStudentId.set(s.id, s));

  //   const examinationsMapByTaskId = new Map();
  //   this.getAllExminations(state)
  //   .forEach(e => examinationsMapByTaskId.has(e.id) ? examinationsMapByTaskId.get(e.id).push(e) : examinationsMapByTaskId.set(e.id, [e]))

  //   const allSubjects = this.getAllSubjects(state);
  //   const subjectsMapBySubjectId = new Map();
  //   allSubjects.forEach(s => subjectsMapBySubjectId.set(s.id, s));
  //   allSubjects.forEach(subject => )

  //   const subjectsByRooms = this.getUserSubjects(state)
  //   .forEach(r => r.subjects
  //     .forEach(s => s.groups.forEach));


  },

  getActiveSubject(state) {
    const filteredSubjects = state.subjects.filter(s => s?.id === state.activeSubjectId);
    return filteredSubjects.length > 0 ? JSON.parse(JSON.stringify(filteredSubjects[0])) : null;
  },
};

export default getters;
