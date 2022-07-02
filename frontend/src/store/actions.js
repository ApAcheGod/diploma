import axios from 'axios';
import router from '../router';

import userRoles from '../models/userRoles';
import mutationsTypes from './mutationsTypes'
import URL from './consts';
import methods from './methods';

const actions = {
  userSignIn({ commit }, payload) {
    const data = {
      login: payload.login,
      password: payload.password,
    };

    commit(mutationsTypes.SET_LOADING, true);
    
    axios.post(`${URL}/login`, data)
      .then(() => axios.get(`${URL}/api/check`))
      .then((response) => { 
        commit(mutationsTypes.SET_USER_LOGIN, payload.login);
        commit(mutationsTypes.SET_USER, response.data);
        commit(mutationsTypes.SET_LOADING, false); 
        
        const userRole = response.data ? response.data.authentication.authorities[0].authority : userRoles.ROLE_ANONYMOUS;

        if (userRole === userRoles.ROLE_ADMIN) {
          router.push('/admin/teachers');
        }
        else if (userRole === userRoles.ROLE_TEACHER) {
          router.push('/teacher');
        }
        else if (userRole === userRoles.ROLE_STUDENT) {
          router.push('/student');
        }
        
      })
      .catch((error) => {
        commit(mutationsTypes.SET_ERROR, error.message);
        commit(mutationsTypes.SET_LOADING, false);
    });
  },

  userSignOut({ commit }) {
    commit(mutationsTypes.CLEAR_USER_LOGIN);
    commit(mutationsTypes.CLEAR_USER);
    router.push('/signin');
  },
  
  studentDataInit({ commit }, payload){
    const studentLogin = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return Promise.all([
      methods.getStudentByLoginFetch(studentLogin),
      methods.getGroupsFetch(),
      methods.getSubjectsFetch(),
      methods.getStudentsFetch(),
      methods.getSolutionsFetch(),
      methods.getExaminationsFetch(),
      methods.getTasksFetch(),
    ])
    .then((results) => {
      const [ 
        studentData, 
        studentGroups, 
        subjects,
        students,
        solutions, 
        examinations,
        tasks,
      ] = results;
      
      commit(mutationsTypes.SET_USER_DATA, studentData);
      commit(mutationsTypes.SET_STUDENT_GROUPS_DATA, studentGroups);
      commit(mutationsTypes.SET_SUBJECTS_DATA, subjects);
      commit(mutationsTypes.SET_STUDENTS_DATA, students);
      commit(mutationsTypes.SET_SOLUTIONS_DATA, solutions);
      commit(mutationsTypes.SET_EXAMINATIONS_DATA, examinations);
      commit(mutationsTypes.SET_TASKS_DATA, tasks);

      commit(mutationsTypes.SET_LOADING, false);
    });
  },

  teacherDataInit({ commit }, payload){
    const teacherLogin = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return Promise.all([
      methods.getTeacherByLoginFetch(teacherLogin),
      methods.getRoomsFetch(),
      methods.getStudentsFetch(),
      methods.getGroupsFetch(),
      methods.getMaterialsFetch(),
      methods.getSubjectsFetch(),
      methods.getTasksFetch(),
      methods.getSolutionsFetch(),
      methods.getExaminationsFetch(),
    ])
    .then((results) => {
      const [ 
        teacherData, 
        rooms, 
        students, 
        studentGroups, 
        materials, 
        subjects, 
        tasks, 
        solutions, 
        examinations,
      ] = results;
      
      commit(mutationsTypes.SET_USER_DATA, teacherData);
      commit(mutationsTypes.SET_ROOMS_DATA, rooms);
      commit(mutationsTypes.SET_STUDENTS_DATA, students);
      commit(mutationsTypes.SET_STUDENT_GROUPS_DATA, studentGroups);
      commit(mutationsTypes.SET_MATERIALS_DATA, materials);
      commit(mutationsTypes.SET_SUBJECTS_DATA, subjects);
      commit(mutationsTypes.SET_TASKS_DATA, tasks);
      commit(mutationsTypes.SET_SOLUTIONS_DATA, solutions);
      commit(mutationsTypes.SET_EXAMINATIONS_DATA, examinations);

      commit(mutationsTypes.SET_LOADING, false);
    });
  },

  deleteRoom({ commit }, payload){
    const room = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.deleteRoomFetch(room)
    .then(isSuccess => {
      if (isSuccess) {
        commit(mutationsTypes.DELETE_ROOM, room);
      }
      commit(mutationsTypes.SET_LOADING, false);
    });
  },

  createRoom({ commit }, payload){
    const room = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.createRoomFetch(room)
    .then(createdRoom => {
      if (createdRoom) {
        commit(mutationsTypes.CREATE_ROOM, createdRoom);
      }
      commit(mutationsTypes.SET_LOADING, false);
    });
  },

  updateRoom({ commit }, payload){
    const room = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.updateRoomFetch(room)
    .then(updatedRoom => {
      if (updatedRoom) {
        commit(mutationsTypes.UPDATE_ROOM, updatedRoom);
      }
      commit(mutationsTypes.SET_LOADING, false);
    });
  },

  createSubject({ commit }, payload){
    const subject = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.createSubjectFetch(subject)
        .then(createdSubject => {
          if (createdSubject) {
            commit(mutationsTypes.CREATE_SUBJECT, createdSubject);
          }
          commit(mutationsTypes.SET_LOADING, false);
        });
  },

  updateSubject({ commit }, payload){
    const subject = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.updateSubjectFetch(subject)
        .then(updatedSubject => {
          if (updatedSubject) {
            commit(mutationsTypes.UPDATE_SUBJECT, updatedSubject);
          }
          commit(mutationsTypes.SET_LOADING, false);
        });
  },

  deleteSubject({ commit }, payload){
    const subject = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.deleteSubjectFetch(subject)
        .then(isSuccess => {
          if (isSuccess) {
            commit(mutationsTypes.DELETE_SUBJECT, subject);
          }
          commit(mutationsTypes.SET_LOADING, false);
        });
  },

    createSolution({ commit }, payload){
      const solution = payload;
      commit(mutationsTypes.SET_LOADING, true);
      return methods.createSolutionFetch(solution)
        .then(createdSolution => {
          if (createdSolution) {
            commit(mutationsTypes.CREATE_SOLUTION, createdSolution);
          }
          commit(mutationsTypes.SET_LOADING, false);
        });
    },

    updateSolution({ commit }, payload){
      const solution = payload;
      commit(mutationsTypes.SET_LOADING, true);
      return methods.updateSolutionFetch(solution)
        .then(updatedSolution => {
          if (updatedSolution) {
            commit(mutationsTypes.UPDATE_SOLUTION, updatedSolution);
          }
          commit(mutationsTypes.SET_LOADING, false);
        });
    },

    deleteSolution({ commit }, payload){
      const solution = payload;
      commit(mutationsTypes.SET_LOADING, true);
      return methods.deleteSolutionFetch(solution)
        .then(isSuccess => {
          if (isSuccess) {
            commit(mutationsTypes.DELETE_SOLUTION, solution);
          }
          commit(mutationsTypes.SET_LOADING, false);
        });
    },

    createExamination({ commit }, payload){
      const examination = payload;
      commit(mutationsTypes.SET_LOADING, true);
      return methods.createExaminationFetch(examination)
        .then(createdExamination => {
          if (createdExamination) {
            commit(mutationsTypes.CREATE_EXAMINATION, createdExamination);
          }
          commit(mutationsTypes.SET_LOADING, false);
        });
    },

    updateExamination({ commit }, payload){
      const examination = payload;
      commit(mutationsTypes.SET_LOADING, true);
      return methods.updateExaminationFetch(examination)
        .then(updatedExamination => {
          if (updatedExamination) {
            commit(mutationsTypes.UPDATE_EXAMINATION, updatedExamination);
          }
          commit(mutationsTypes.SET_LOADING, false);
        });
    },

    deleteExamination({ commit }, payload){
      const examination = payload;
      commit(mutationsTypes.SET_LOADING, true);
      return methods.deleteExaminationFetch(examination)
        .then(isSuccess => {
          if (isSuccess) {
            commit(mutationsTypes.DELETE_EXAMINATION, examination);
          }
          commit(mutationsTypes.SET_LOADING, false);
        });
    },

    setActiveSubject({ commit, getters }, payload) {
      const subject = payload;
      commit(mutationsTypes.SET_LOADING, true);
      return new Promise((resolve, reject) => {
        commit(mutationsTypes.SET_ACTIVE_SUBJECT, subject);
        
        const userRole = getters.getUserRole;
        if (userRole === userRoles.ROLE_TEACHER) router.push('/teacher/subject/journal');
        else if (userRole === userRoles.ROLE_STUDENT) router.push('/student/subject/tasks');

        resolve(true);
        commit(mutationsTypes.SET_LOADING, false);
      });
    },
  
    deleteActiveSubject({ commit, getters }, payload) {
      commit(mutationsTypes.SET_LOADING, true);
      return new Promise((resolve, reject) => {
        commit(mutationsTypes.DELETE_ACTIVE_SUBJECT);

        const userRole = getters.getUserRole;
        console.log(userRole);
        if (userRole === userRoles.ROLE_TEACHER) router.push('/teacher/subjects');
        else if (userRole === userRoles.ROLE_STUDENT) router.push('/student/subjects');

        resolve(true);
        commit(mutationsTypes.SET_LOADING, false);
      });
    },

  createTask({ commit, getters }, payload){
    const task = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.createTaskFetch(task)
      .then(createdTask => {
        commit(mutationsTypes.CREATE_TASK, createdTask);

        const activeSubject = getters.getActiveSubjectEmpty;
        activeSubject.tasks ? activeSubject.tasks.push(createdTask) : [createdTask];
        commit(mutationsTypes.UPDATE_SUBJECT, activeSubject);

        const teacherCurr = getters.getUserData;
        teacherCurr.tasks ? teacherCurr.tasks.push(createdTask) : [createdTask];
        commit(mutationsTypes.SET_LOADING, false);
      });
  },

  updateTask({ commit, getters }, payload){
    const task = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.updateTaskFetch(task)
      .then(updatedTask => {
        if (updatedTask) {
          commit(mutationsTypes.UPDATE_TASK, updatedTask);

          const activeSubject = getters.getActiveSubjectEmpty;
          const taskIdInSubject = activeSubject.tasks.findIndex(s => s.id === updatedTask.id);
          activeSubject.tasks[taskIdInSubject] = updatedTask;
          commit(mutationsTypes.UPDATE_SUBJECT, activeSubject);
  
          const teacherCurr = getters.getUserData;
          const taskIdInTeacherCurr = teacherCurr.tasks.findIndex(s => s.id === updatedTask.id);
          teacherCurr.tasks[taskIdInTeacherCurr] = updatedTask;
          commit(mutationsTypes.SET_LOADING, false);
        }
      });
  },

  deleteTask({ commit, getters }, payload){
    const task = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.deleteTaskFetch(task)
      .then(isSuccess => {
        if (isSuccess) {
          commit(mutationsTypes.DELETE_TASK, task);

          const activeSubject = getters.getActiveSubjectEmpty;
          activeSubject.tasks = activeSubject.tasks.filter(t => t.id !== task.id);
          commit(mutationsTypes.UPDATE_SUBJECT, activeSubject);
          
          const teacherCurr = getters.getUserData;
          teacherCurr.tasks = activeSubject.tasks.filter(t => t.id !== task.id);
          commit(mutationsTypes.SET_LOADING, false);
        }
      });
  },

  createMaterial({ commit, getters }, payload){
    const material = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.createMaterialFetch(material)
        .then(createdMaterial => {
          if (createdMaterial) {
            commit(mutationsTypes.CREATE_MATERIAL, material);

            const activeSubject = getters.getActiveSubjectEmpty;
            activeSubject.materials.push(createdMaterial);
            commit(mutationsTypes.UPDATE_SUBJECT, activeSubject);
            commit(mutationsTypes.SET_LOADING, false);
          }
        });
  },

  updateMaterial({ commit, getters }, payload){
    const material = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.updateMaterialFetch(material)
        .then(updatedSubject => {
          if (updatedSubject) {
            commit(mutationsTypes.UPDATE_MATERIAL, material);

            const activeSubject = getters.getActiveSubjectEmpty;
            const materialIdinSubject = activeSubject.materials.findIndex(m => m.id === material.id);
            activeSubject.materials[materialIdinSubject] = material;

            commit(mutationsTypes.UPDATE_SUBJECT, activeSubject);
            commit(mutationsTypes.SET_LOADING, false);
          }
        });
  },

  deleteMaterial({ commit, getters }, payload){
    const material = payload;
    commit(mutationsTypes.SET_LOADING, true);
    return methods.deleteMaterialFetch(material)
        .then(isSuccess => {
          if (isSuccess) {
            commit(mutationsTypes.DELETE_MATERIAL, material);

            const activeSubject = getters.getActiveSubjectEmpty;
            activeSubject.materials = activeSubject.materials.filter(m => m.id !== material.id);
            
            commit(mutationsTypes.UPDATE_SUBJECT, activeSubject);
          }
        });
  },
};

export default actions;
