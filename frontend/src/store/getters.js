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
  
  getTasksMapByTaskId(state){
    const tasksMapByTaskId = new Map();
    state.tasks.forEach(task => tasksMapByTaskId.set(task.id, task));
    return tasksMapByTaskId;
  },

  getMaterialsMapByMaterialId(state){
    const materialsMapByMaterialId = new Map();
    state.materials.forEach(material => materialsMapByMaterialId.set(material.id, material));
    return materialsMapByMaterialId;
  },

  getGroupsMapByGroupId(state){
    const groupsMapByGroupId = new Map();
    state.studentGroups.forEach(group => groupsMapByGroupId.set(group.id, group));
    return groupsMapByGroupId;
  },

  getExamsByTaskId(state){
    const examsByTasksId = new Map();
    state.examinations.forEach(exam => examsByTasksId.has(exam.taskId) ? examsByTasksId.get(exam.taskId).push(exam) : examsByTasksId.set(exam.taskId, [exam]));
    return examsByTasksId;
  },

  getSolutionsByTaskId(state){
    const solutionsByTasksId = new Map();
    state.solutions.forEach(solution => solutionsByTasksId.has(solution.taskId) ? solutionsByTasksId.get(solution.taskId).push(solution) : solutionsByTasksId.set(solution.taskId, [solution]));
    return solutionsByTasksId;
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
          subject.tasks ? subject.tasks.sort((taskA, taskB) => taskA.name.localeCompare(taskB.name)) : []; // Сортировка по названиям заданий.
          return {
            name : subject.name,
            tasks: subject.tasks,
            groups: !subject.groups ? [] : subject.groups.map(group => { 
              let fullGroupData = groupsMapByGroupId.get(group.id);
              fullGroupData.students = fullGroupData.students?.map(student => studentsMapByStudentId.get(student.id)) ?? [];
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
              ...subject.tasks ? subject.tasks.map(task => {
                return {
                  name: task.id, 
                  align: 'center', 
                  label: task.name, 
                  field: task.id, 
                  sortable: false, 
                }
              }) : []
            ],
          }
        }),
      }
    });
    return roomSubjectsHeadByTasks;
  },

  getActiveSubjectJournal(state, getters) {
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

    const activeSubject = getters.getActiveSubjectEmpty;
    if (!activeSubject) return;

    activeSubject.tableHead = [{
        name: 'name',
        required: true,
        label: 'Имя студента',
        align: 'left',
        field: row => row.name,
        format: val => `${val}`,
        sortable: true 
      }, 
      ...activeSubject.tasks.map(task => {
        return {
          name: task.id, 
          align: 'center', 
          label: task.name, 
          field: task.id, 
          sortable: false, 
        }}
      )
    ];

    activeSubject.groups = activeSubject.groups.map(group => { 
      let fullGroupData = groupsMapByGroupId.get(group.id);
      fullGroupData.students = fullGroupData.students?.map(student => studentsMapByStudentId.get(student.id)) ?? [];
      setGroupResults(fullGroupData, activeSubject);
      return fullGroupData;
    });

    return activeSubject;
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

  getHomeWorks(state, getters) {
    const activeSubject = getters.getActiveSubject;
    if (!activeSubject) return null;
    const solutionsByStudentIdTaskId = getters.getSolutionsByStudentIdTaskId;
    const examsByStudentIdTaskId = getters.getExamsByStudentIdTaskId;
    let tasks = JSON.parse(JSON.stringify(activeSubject.tasks));
    tasks.forEach(task => task.uncheckdHomeWorks = activeSubject.groups
      .map(group => {
        return {
          groupId: group.id,
          groupName: group.name,
          homeWorks: group.students.map(student => {
            if (examsByStudentIdTaskId.get(student.id).get(task.id)) return;
            return solutionsByStudentIdTaskId.get(student.id).get(task.id);
          }).filter(hw => hw).flat(3),
        }
      })
      .filter(group => group.homeWorks.length > 0)
    )
    tasks = tasks.filter(task => task.uncheckdHomeWorks.length > 0);
    tasks.sort((taskA, taskB) => taskA.name.localeCompare(taskB.name));
    console.log(tasks);
    return tasks;
  },
};

export default getters;
