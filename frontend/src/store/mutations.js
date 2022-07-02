import userRoles from "../models/userRoles";

const mutations = {
  setLoading(state, payload) {
    state.loading = payload;
  },
  toggleLoading(state) {
    state.loading = !state.loading;
  },
  setUser(state, payload) {
    state.user = payload;
    localStorage.setItem('user', JSON.stringify(payload));
    const userRole = payload ? payload.authentication.authorities[0].authority : userRoles.ROLE_ANONYMOUS;
    localStorage.setItem('userRole', userRole)
    localStorage.setItem('isAuthenticated', userRole !== userRoles.ROLE_ANONYMOUS);
  },
  clearUser(state) {
    localStorage.clear();

    state.user = null;
    state.activeSubjectId = '';
    state.error = null;
    state.loading = false;

    state.rooms = [];
    state.students = [];
    state.studentGroups = [];
    state.materials = [];
    state.subjects = [];
    state.solutions = [];
    state.examinations = [];
    state.tasks = [];
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
  setStudentsData(state, payload){
    state.students = payload;
  },
  setMaterialsData(state, payload) {
    state.materials = payload;
  },
  setSubjectsData(state, payload) {
    state.subjects = payload;
  },
  setSolutionsData(state, payload){
    state.solutions = payload;
  },
  setTasksData(state, payload){
    state.tasks = payload;
  },
  setExaminationsData(state, payload){
    state.examinations = payload;
  },

  deleteRoom(state, payload){
    const room = payload;
    state.userData.rooms?.splice(state.userData.rooms.findIndex(r => room.id === r.id), 1); // Teacher
    state.rooms.splice(state.rooms.findIndex(r => room.id === r.id), 1);
  },
  createRoom(state, payload){
    const room = payload;
    state.subjects = state.subjects ?? [];
    state.userData.rooms = state.userData.rooms ?? [];

    state.userData.rooms.push(room); // Teacher
    state.rooms.push(room);
  },
  updateRoom(state, payload){
    const room = payload;
    const updatedRoomIdTeacher = state.userData?.rooms?.findIndex(r => room.id === r.id); // Teacher
    state.userData.rooms[updatedRoomIdTeacher] = room; // Teacher
    
    const updatedRoomId = state.rooms.findIndex(r => room.id === r.id);
    state.rooms[updatedRoomId] = room;
  },

  createSubject(state, payload) {
    const subject = payload;
    
    state.subjects = state.subjects ?? [];
    state.userData.subjects = state.userData.subjects ?? [];

    state.subjects.push(subject);
    state.userData.subjects.push(subject); // Teacher
  },
  deleteSubject(state, payload) {
    const subject = payload;
    state.subjects.splice(state.subjects.findIndex(s => subject.id === s.id), 1);

    state.userData.subjects.splice(state.subjects.findIndex(s => subject.id === s.id), 1); // Teacher
  },
  updateSubject(state, payload) {
    const updatedSubject = payload;
    const prevSubjectId = state.subjects.findIndex(s => updatedSubject.id === s.id);
    state.subjects[prevSubjectId] = updatedSubject;

    const prevSubjectIdTeacher = state.userData.subjects.findIndex(s => updatedSubject.id === s.id); // Teacher
    state.userData.subjects[prevSubjectIdTeacher] = updatedSubject; // Teacher
  },

  createTask(state, payload) {
    const task = payload;
    state.tasks.push(task);
  },
  deleteTask(state, payload) {
    const task = payload;
    state.tasks.splice(state.tasks.findIndex(s => task.id === s.id), 1);
  },
  updateTask(state, payload) {
    const task = payload;
    const updateTaskId = state.tasks.findIndex(t => task.id === t.id);
    state.tasks[updateTaskId] = task;
  },

  createSolution(state, payload){
    const solution = payload;
    state.solutions.push(solution);
  },
  deleteSolution(state, payload){
    const solution = payload;
    state.solutions.splice(state.solutions.findIndex(s => solution.id === s.id), 1);
  },
  updateSolution(state, payload){
    // TODO; (?)
  },

  createExamination(state, payload){
    const examination = payload;
    state.examinations.push(examination);
  },
  deleteExamination(state, payload){
    const examination = payload;
    state.examinations.splice(state.examinations.findIndex(s => examination.id === s.id), 1);
  },
  updateExamination(state, payload){
    // TODO; (?)
  },

  createMaterial(state, payload){
    const material = payload;

    state.materials.push(material);
    state.userData.materials = state.userData.materials ?? [];
    state.userData.materials.push(material); // Teacher
  },
  deleteMaterial(state, payload){
    const material = payload;

    state.materials.splice(state.materials.findIndex(m => material.id === m.id), 1);
    state.userData.materials?.splice(state.materials?.findIndex(m => material.id === m.id), 1); // Teacher
  },
  updateMaterial(state, payload){
    const material = payload;

    const updateMaterialId = state.materials.findIndex(m => material.id === m.id);
    const userUpdateMaterialId = state.userData.materials.findIndex(m => material.id === m.id); // Teacher

    state.materials[updateMaterialId] = material;
    state.userData.materials[userUpdateMaterialId] = material;
  },

  setActiveSubject(state, payload) {
    const subject = payload;
    state.activeSubjectId = subject.id;
    localStorage.setItem('activeSubjectId', subject.id);
    localStorage.setItem('hasActiveSubject', true);
  },
  deleteActiveSubject(state) {
    state.activeSubjectId = '';
    localStorage.removeItem('activeSubjectId');
    localStorage.removeItem('hasActiveSubject');
  },

};

export default mutations;
