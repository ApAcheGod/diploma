import userRoles from "../models/userRoles";
import workStatuses from "../models/workStatuses";

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

  getStudentsMapByStudentId(state){
    const studentsMapByStudentId = new Map();
    state.students.forEach(student => studentsMapByStudentId.set(student.id, student));
    return studentsMapByStudentId;
  },
  
  getGroupsMapByGroupId(state){
    const groupsMapByGroupId = new Map();
    state.studentGroups.forEach(group => groupsMapByGroupId.set(group.id, group));
    return groupsMapByGroupId;
  },

  getExamsByStudentIdTaskId(state){
    const examsByStudentIdTaskId = new Map();
    state.students.forEach(student => {
      const examinationsMap = new Map();
      state.examinations.filter(e => e.studentId === student.id)
        .forEach(e => examinationsMap.has(e.taskId) ? examinationsMap.get(e.taskId).push(e) : examinationsMap.set(e.taskId, [e]));
      examsByStudentIdTaskId.set(student.id, examinationsMap);
    });
    return examsByStudentIdTaskId;
  },

  getSolutionsByStudentIdTaskId(state){
    const solutionsByStudentIdTaskId = new Map();
    state.students.forEach(student => {
      const solutionsMap = new Map();
      state.solutions.filter(e => e.studentId === student.id)
        .forEach(s => solutionsMap.has(s.taskId) ? solutionsMap.get(s.taskId).push(s) : solutionsMap.set(s.taskId, [s]));
      solutionsByStudentIdTaskId.set(student.id, solutionsMap);
    });
    return solutionsByStudentIdTaskId;
  },

  getSubjectBySubjectId(state){
    const subjectBySubjectId = new Map();
    state.subjects.forEach(subject => subjectBySubjectId.set(subject.id, subject));
    return subjectBySubjectId;
  },

  getFormattedJournal(state, getters){ 
    const solutionsByStudentIdTaskId = getters.getSolutionsByStudentIdTaskId;
    const examsByStudentIdTaskId = getters.getExamsByStudentIdTaskId;
    const groupsMapByGroupId = getters.getGroupsMapByGroupId;
    const studentsMapByStudentId = getters.getStudentsMapByStudentId;

    const setGroupResults = (group, subject) => {
      const getMark = (studentId, taskId) => {
        const examinationResult = examsByStudentIdTaskId.get(studentId).get(taskId);
        const solutionResult = solutionsByStudentIdTaskId.get(studentId).get(taskId);
        const examination = examinationResult ? examinationResult[0] : examinationResult;
        const solution = solutionResult ? solutionResult[0] : solutionResult;
        if (examination)
          return examination.examinationStatus;
        if (solution)
          return workStatuses.COMPLETED;
        return workStatuses.NOT_COMPLETED;
      };

      group.students?.forEach(student => {
        subject.tasks.forEach(task => {
          student[task.id] = getMark(student.id, task.id); 
        })
      });
      return group;
    };

    const roomSubjectsHeadByTasks = state.userData.rooms?.map(room => {
      return { 
        roomName : room.name,
        roomId : room.id,
        roomSubjects : state.subjects.filter(subject => subject.roomId === room.id)
        .map(subject => {
          subject.tasks.sort((taskA, taskB) => taskA.name.localeCompare(taskB.name)); // Сортировка по названиям заданий.
          return {
            name : subject.name,
            tasks: subject.tasks,
            groups: !subject.groups ? [] : subject.groups.map(group => { 
              let fullGroupData = groupsMapByGroupId.get(group.id);
              fullGroupData.students = fullGroupData.students.map(student => studentsMapByStudentId.get(student.id));
              setGroupResults(fullGroupData, subject);
              return fullGroupData;
            }),
            tasksTableHead : [{
              name: 'name',
              required: true,
              label: 'Имя студента',
              align: 'left',
              field: row => row.name,
              format: val => `${val}`,
              sortable: true }, 
              ...subject.tasks.map(task => {
                return {
                  name: task.id, 
                  align: 'center', 
                  label: task.name, 
                  field: task.id, 
                  sortable: false, 
                }
              })
            ],
          }
        }),
      }
    });
    return roomSubjectsHeadByTasks;
  },

  getActiveSubject(state, getters) {
    const filteredSubjects = state.subjects.filter(s => s?.id === state.activeSubjectId);
    if (filteredSubjects.length === 0)
      return null;

    const filteredSubject = JSON.parse(JSON.stringify(filteredSubjects[0]));
    const groupsMapByGroupId = getters.getGroupsMapByGroupId;
    filteredSubject.groups = filteredSubject.groups.map(group => groupsMapByGroupId.get(group.id));
    return filteredSubject;
  },

  getActiveSubjectEmpty(state, getters) {
    const filteredSubjects = state.subjects.filter(s => s?.id === state.activeSubjectId);
    if (filteredSubjects.length === 0)
      return null;
    return JSON.parse(JSON.stringify(filteredSubjects[0]));
  }
};

export default getters;
