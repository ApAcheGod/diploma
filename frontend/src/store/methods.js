import axios from 'axios';

import userRoles from "../models/userRoles";
import URL from './consts';

axios.defaults.withCredentials = true;

export default {
  // HELPERS

  getUserRole(user) {
    return user ? user.authentication.authorities[0].authority : userRoles.ROLE_ANONYMOUS;
  },
  
  idArrToObjs(array){
    if (array) {
      return array.map((m) => {
        if (typeof m === 'string' || m instanceof String)
          return {
            id: m,
          };
        return m;
      });
    }
    return [];
  },

  // FETCHS

  //  AUTHORIZATION/AUTENTIFICATION/LOGOUT

  userLogin(credentials) {
    const header = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json'
      },
      body: JSON.stringify(credentials),
    };
    return fetch(`${URL}/login`, header)
      .then(res => {
        return res.ok;
      })
  },
  
  userCheck() {
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/check`, header)
      .then(res => {
        res.text();
      })
      .then(text => text)
  },

  userLoginAxios(payload) {
    const data = {
      login: payload.login,
      password: payload.password,
    };
    return axios.post(`${URL}/login`, data);
  },

  userCheckAxios() {
    return axios.get(`${URL}/api/check`);
  },

  userLogout() {
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/logout`, header)
      .then(res => {
          if (res.ok)
            return res.json();  
          throw `${res.status} ${res.statusText}`;
        })
      .then(json => json)
  },

  //  END AUTHORIZATION/AUTENTIFICATION/LOGOUT

  //  STUDENTS

  getStudentsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/students`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  getStudentsWithoutGroupFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/studentsWithoutGroup`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  createStudentFetch(student){
    const header = {
      method: 'POST',
      headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
      },
      body: JSON.stringify(
        {
          first_name: student.first_name,
          last_name: student.last_name,
          patronymic: student.patronymic,
          email: student.email
        }
      ),
    };
    return fetch(`${URL}/api/student`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  updateStudentFetch(student){
    const header = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json'
      },
      body: JSON.stringify(student),
    };
    return fetch(`${URL}/api/student`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  deleteStudentFetch(student){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/student/${student.id}`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  //  END STUDENTS

  //  MATERIALS

  getMaterialsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/materials`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  createMaterialFetch(material){
    const header = {
      method: 'POST',
      headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
      },
      body: JSON.stringify(material),
    };
    return fetch(`${URL}/api/material`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  updateMaterialFetch(material){
    const header = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json'
      },
      body: JSON.stringify(material),
    };
    return fetch(`${URL}/api/material`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  deleteMaterialFetch(material){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/material/${material.id}`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  //  END MATERIALS

  //  SUBJECTS

  getSubjectsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/subjects`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  createSubjectFetch(subject){
    const header = {
      method: 'POST',
      headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
      },
      body: JSON.stringify(subject),
    };
    return fetch(`${URL}/api/subject`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  updateSubjectFetch(subject){
    const header = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json'
      },
      body: JSON.stringify(subject),
    };
    return fetch(`${URL}/api/subject`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  deleteSubjectFetch(subject){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/subject/${subject.id}`, header)
      .then(res => {
        if (res.ok)
          return res.ok;
        throw `${res.status} ${res.statusText}`;
      })
      // .then(json => json)
  },

  // END SUBJECTS

  //  TEACHERS

  getTeachersFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/teachers`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  getTeacherByLoginFetch(login){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/teacherByLogin/${login}`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },  

  createTeacherFetch(teacher){
    const header = {
      method: 'POST',
      headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
      },
      body: JSON.stringify(teacher),
    };
    return fetch(`${URL}/api/teacher`, header)
    .then(res => {
      if (res.ok)
        return res.json();  
      throw `${res.status} ${res.statusText}`;
    })
    .then(json => json)
  },

  updateTeacherFetch(teacher){
    const header = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json'
      },
      body: JSON.stringify(teacher),
    };
    return fetch(`${URL}/api/teacher`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  deleteTeacherFetch(teacher){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/teacher/${teacher.id}`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  // END TEACHERS

  //  TASKS

  getTasksFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/tasks`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  createTaskFetch(task){
    const header = {
      method: 'POST',
      headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
      },
      body: JSON.stringify(task),
    };
    return fetch(`${URL}/api/task`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  updateTaskFetch(task){
    const header = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json'
      },
      body: JSON.stringify(task),
    };
    return fetch(`${URL}/api/task`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)

  },

  deleteTaskFetch(task){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/task/${task.id}`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)

  },

  //  END TASKS

  //  ROOMS

  getRoomsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/rooms`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)

  },

  createRoomFetch(room){
    const header = {
      method: 'POST',
      headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
      },
      body: JSON.stringify(room),
    };
    return fetch(`${URL}/api/room`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  updateRoomFetch(room){
    const header = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json'
      },
      body: JSON.stringify(room),
    };
    return fetch(`${URL}/api/room`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  deleteRoomFetch(room){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/room/${room.id}`, header)
      .then(res => {
        if (res.ok)
          return res.ok;  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  // END ROOMS

  //  GROUPS

  getGroupsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/groups`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  createGroupFetch(group){
    const header = {
      method: 'POST',
      headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
      },
      body: JSON.stringify(group),
    };
    return fetch(`${URL}/api/group`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  updateGroupFetch(group){
    const header = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json'
      },
      body: JSON.stringify(group),
    };
    return fetch(`${URL}/api/group`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
    .then(json => json)
  },

  deleteGroupFetch(group){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/group/${group.id}`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
    .then(json => json)
  },

  // END GROUPS

  //  SOLUTIONS

  getSolutionsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/solutions`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  createSolutionFetch(solution){
    const header = {
      method: 'POST',
      headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
      },
      body: JSON.stringify(solution),
    };
    return fetch(`${URL}/api/solution`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  updateSolutionFetch(solution){
    const header = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json'
      },
      body: JSON.stringify(solution),
    };
    return fetch(`${URL}/api/solution`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  deleteSolutionFetch(solution){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/solution/${solution.id}`, header)
      .then(res => {
        if (res.ok)
          return res.json();  
        throw `${res.status} ${res.statusText}`;
      })
      .then(json => json)
  },

  //  END SOLUTIONS

  // EXAMINATIONS

  getExaminationsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/examinations`, header)
        .then(res => {
          if (res.ok)
            return res.json();
          throw `${res.status} ${res.statusText}`;
        })
        .then(json => json)
  },


  createExaminationFetch(examination){
    const header = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json'
      },
      body: JSON.stringify(examination),
    };
    return fetch(`${URL}/api/examination`, header)
        .then(res => {
          if (res.ok)
            return res.json();
          throw `${res.status} ${res.statusText}`;
        })
        .then(json => json)
  },

  updateExaminationFetch(examination){
    const header = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json'
      },
      body: JSON.stringify(examination),
    };
    return fetch(`${URL}/api/examination`, header)
        .then(res => {
          if (res.ok)
            return res.json();
          throw `${res.status} ${res.statusText}`;
        })
        .then(json => json)
  },

  deleteExaminationFetch(examination){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/examination/${examination.id}`, header)
        .then(res => {
          if (res.ok)
            return res.json();
          throw `${res.status} ${res.statusText}`;
        })
        .then(json => json)
  },

};