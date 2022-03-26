import { reactive } from 'vue';
import { useQuasar } from 'quasar'
import teacherData from '../data/Teacher.json'

// let $q = useQuasar();

const state = reactive(
  {
    teacher : {},
    admin: {},
  });

const methods = {
  // Notifications

  // triggerPositive(msg) {
  //   console.log($q);
  //   $q.notify({
  //     type: 'positive',
  //     message: msg
  //   })
  // },
  
  // triggerNegative(msg) {
  //   $q.notify({
  //     type: 'negative',
  //     message: msg
  //   })
  // },

  // Fetchs

  //  Students

  getStudentsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch('http://localhost:8080/api/students', header)
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
      body: JSON.stringify(student),
    };
    return fetch('http://localhost:8080/api/student', header)
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
    return fetch(`http://localhost:8080/api/student/${student.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteStudentFetch(student){
    const header = {
      method: 'DELETE',
    };
    return fetch(`http://localhost:8080/api/student/${student.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  //  Materials

  getMaterialsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch('http://localhost:8080/api/materials', header)
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
    return fetch('http://localhost:8080/api/material', header)
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
    return fetch(`http://localhost:8080/api/material/${material.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteMaterialFetch(material){
    const header = {
      method: 'DELETE',
    };
    return fetch(`http://localhost:8080/api/material/${material.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  //  Subjects

  getSubjectsFetch(){
    const header = {
      method: 'GET',
    };
    return fetch('http://localhost:8080/api/subjects', header)
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
    return fetch('http://localhost:8080/api/subject', header)
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
    return fetch(`http://localhost:8080/api/subject/${subject.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteSubjectFetch(subject){
    const header = {
      method: 'DELETE',
    };
    return fetch(`http://localhost:8080/api/subject/${subject.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  //  Teachers

  getTeachersFetch(){
    const header = {
      method: 'GET',
    };
    return fetch('http://localhost:8080/api/teachers', header)
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
    return fetch('http://localhost:8080/api/teacher', header)
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
      body: JSON.stringify(subject),
    };
    return fetch(`http://localhost:8080/api/teacher/${teacher.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteTeacherFetch(teacher){
    const header = {
      method: 'DELETE',
    };
    return fetch(`http://localhost:8080/api/teacher/${teacher.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

};


export default{
  state, 
  methods,
}