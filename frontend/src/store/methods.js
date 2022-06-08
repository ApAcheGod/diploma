import axios from 'axios';

import userRoles from "../models/userRoles";
import URL from './consts';

axios.defaults.withCredentials = true;

const getFetch = (route) => {
  const header = {
    method: 'GET',
  };
  return fetch(`${URL}${route}`, header)
    .then(res => {
      if (res.ok) return res.json();  
      throw `${res.status} ${res.statusText}`;
    })
    .then(json => json)
}
const createFetch = (route, object) => {
  const header = {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json;charset=utf-8',
    'Accept': 'application/json'
    },
    body: JSON.stringify(object),
  };
  return fetch(`${URL}${route}`, header)
    .then(res => {
      if (res.ok) return res.json();  
      throw `${res.status} ${res.statusText}`;
    })
    .then(json => json)
}
const updateFetch = (route, object) => {
  const header = {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Accept': 'application/json'
    },
    body: JSON.stringify(object),
  };
  return fetch(`${URL}${route}`, header)
    .then(res => {
      if (res.ok) return res.json();  
      throw `${res.status} ${res.statusText}`;
    })
    .then(json => json)
}
const deleteFetch = (route, object) => {
  const header = {
    method: 'DELETE',
  };
  return fetch(`${URL}${route}${object.id}`, header)
    .then(res => {
      if (res.ok) return res.ok;  
      throw `${res.status} ${res.statusText}`;
    })
    .then(json => json)
}


export default {
  getUserRole: (user) => user ? user.authentication.authorities[0].authority : userRoles.ROLE_ANONYMOUS,
  
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

  getStudentsFetch: () => getFetch('/api/students/'),
  getStudentsWithoutGroupFetch: () => getFetch('/api/studentsWithoutGroup/'),
  createStudentFetch: (student) => createFetch('/api/student/',
    {
      first_name: student.first_name,
      last_name: student.last_name,
      patronymic: student.patronymic,
      email: student.email
    }
  ),
  updateStudentFetch: (student) => updateFetch('/api/student/', student),
  deleteStudentFetch: (student) => deleteFetch('/api/student/', student),

  getMaterialsFetch: () => getFetch('/api/materials/'),
  createMaterialFetch: (material) => createFetch('/api/material/', material),
  updateMaterialFetch: (material) => updateFetch('/api/material/', material),
  deleteMaterialFetch: (material) => deleteFetch('/api/material/', material),

  getSubjectsFetch: () => getFetch('/api/subjects/'),
  createSubjectFetch: (subject) => createFetch('/api/subject/', subject),
  updateSubjectFetch: (subject) => updateFetch('/api/subject/', subject),
  deleteSubjectFetch: (subject) => deleteFetch('/api/subject/', subject),

  getTeachersFetch: () => getFetch('/api/teachers/'),
  getTeacherByLoginFetch: (login) => getFetch(`/api/teacherByLogin/${login}`),
  createTeacherFetch: (teacher) => createFetch('/api/teacher/', teacher),
  updateTeacherFetch: (teacher) => updateFetch('/api/teacher/', teacher),
  deleteTeacherFetch: (teacher) => deleteFetch('/api/teacher/', teacher),

  getTasksFetch: () => getFetch('/api/tasks/'),
  createTaskFetch: (task) => createFetch('/api/task/', task),
  updateTaskFetch: (task) => updateFetch('/api/task/', task),
  deleteTaskFetch: (task) => deleteFetch('/api/task/', task),
  
  getRoomsFetch: () => getFetch('/api/rooms/'),
  createRoomFetch: (room) => createFetch('/api/room/', room),
  updateRoomFetch: (room) => updateFetch('/api/room/', room),
  deleteRoomFetch: (room) => deleteFetch('/api/room/', room),

  getGroupsFetch: () => getFetch('/api/groups/'),
  createGroupFetch: (group) => createFetch('/api/group/', group),
  updateGroupFetch: (group) => updateFetch('/api/group/', group),
  deleteGroupFetch: (group) => deleteFetch('/api/group/', group),

  getSolutionsFetch: () => getFetch('/api/solutions/'),
  createSolutionFetch: (solution) => createFetch('/api/solution/', solution),
  updateSolutionFetch: (solution) => updateFetch('/api/solution/', solution),
  deleteSolutionFetch: (solution) => deleteFetch('/api/solution/', solution),

  getExaminationsFetch: () => getFetch('/api/examinations/'),
  createExaminationFetch: (examination) => createFetch('/api/examination/', examination),
  updateExaminationFetch: (examination) => updateFetch('/api/examination/', examination),
  deleteExaminationFetch: (examination) => deleteFetch('/api/examination/', examination),
};