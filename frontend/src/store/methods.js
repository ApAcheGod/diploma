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
        console.log(res.headers);
        return res.ok;
      })
      .catch(error => console.error(error));
  },
  
  userCheck() {
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/check`, header)
      .then(res => {
        console.log(res.headers);
        res.text();
      })
      .then(text => text)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  
  //  END AUTHORIZATION/AUTENTIFICATION/LOGOUT

  //  STUDENTS

  getStudentsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/students`, header)
      .then(res => res.json())
      .then(json => json)
      .catch(error => console.error(error));
  },

  getStudentsWithoutGroupFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/studentsWithoutGroup`, header)
        .then(res => res.json())
        .then(json => json)
        .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteStudentFetch(student){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/student/${student.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  //  END STUDENTS

  //  MATERIALS

  getMaterialsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/materials`, header)
      .then(res => res.json())
      .then(json => json)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteMaterialFetch(material){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/material/${material.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  //  END MATERIALS

  //  SUBJECTS

  getSubjectsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/subjects`, header)
      .then(res => res.json())
      .then(json => json)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteSubjectFetch(subject){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/subject/${subject.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  // END SUBJECTS

  //  TEACHERS

  getTeachersFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/teachers`, header)
      .then(res => res.json())
      .then(json => json)
      .catch(error => console.error(error));
  },

  getTeacherByLoginFetch(login){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/teacherByLogin/${login}`, header)
      .then(res => res.json())
      .then(json => json)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteTeacherFetch(teacher){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/teacher/${teacher.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  // END TEACHERS

  //  TASKS

  getTasksFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/tasks`, header)
      .then(res => res.json())
      .then(json => json)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteTaskFetch(task){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/task/${task.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  //  END TASKS

  //  ROOMS

  getRoomsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/rooms`, header)
      .then(res => res.json())
      .then(json => json)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteRoomFetch(room){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/room/${room.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  // END ROOMS

  //  GROUPS

  getGroupsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/groups`, header)
      .then(res => res.json())
      .then(json => json)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteGroupFetch(group){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/group/${group.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  // END GROUPS

  //  SOLUTIONS

  getSolutionsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch(`${URL}/api/solutions`, header)
      .then(res => res.json())
      .then(json => json)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
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
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteSolutionFetch(solution){
    const header = {
      method: 'DELETE',
    };
    return fetch(`${URL}/api/group/${solution.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  //  END SOLUTIONS

};