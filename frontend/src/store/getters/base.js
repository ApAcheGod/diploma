export default {
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
  getActiveSubject(state, getters) {
    const filteredSubjects = state.subjects.filter(s => s?.id === state.activeSubjectId);
    if (filteredSubjects.length === 0) return;

    const filteredSubject = JSON.parse(JSON.stringify(filteredSubjects[0]));

    filteredSubject.materials = filteredSubject.materials ?? [];
    filteredSubject.tasks = filteredSubject.tasks ?? [];
    filteredSubject.groups = filteredSubject.groups ?? [];

    const groupsMap = getters.getGroupsMapByGroupId;
    const tasksMap = getters.getTasksMapByTaskId;
    const materialsMap = getters.getMaterialsMapByMaterialId;

    filteredSubject.groups = filteredSubject.groups.map(group => groupsMap.get(group.id) ?? group);
    filteredSubject.tasks = filteredSubject.tasks.map(task => tasksMap.get(task.id) ?? task);
    filteredSubject.materials = filteredSubject.materials.map(material => materialsMap.get(material.id) ?? material);

    return filteredSubject;
  },
  getActiveSubjectEmpty(state) {
    const filteredSubjects = state.subjects.filter(s => s?.id === state.activeSubjectId);
    if (filteredSubjects.length === 0) return;

    const filteredSubject = JSON.parse(JSON.stringify(filteredSubjects[0]));
      
    filteredSubject.materials = filteredSubject.materials ?? [];
    filteredSubject.tasks = filteredSubject.tasks ?? [];
    filteredSubject.groups = filteredSubject.groups ?? [];
    
    return filteredSubject;
  },
};