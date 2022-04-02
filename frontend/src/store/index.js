import { reactive } from 'vue';
import { useQuasar } from 'quasar'
import teacherData from '../data/Teacher.json'

const state = reactive(
  {
    teacher : {},
    admin: {},
  });

const methods = {

  // Helpers

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
      body: JSON.stringify(
        {
          first_name: student.first_name,
          last_name: student.last_name,
          patronymic: student.patronymic,
          email: student.email
        }
      ),
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
    return fetch(`http://localhost:8080/api/student`, header)
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
    return fetch(`http://localhost:8080/api/material`, header)
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
    return fetch(`http://localhost:8080/api/subject`, header)
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
      body: JSON.stringify(teacher),
    };
    return fetch(`http://localhost:8080/api/teacher`, header)
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

  //  Tasks

  getTasksFetch(){
    const header = {
      method: 'GET',
    };
    return fetch('http://localhost:8080/api/tasks', header)
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
    return fetch('http://localhost:8080/api/task', header)
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
    return fetch(`http://localhost:8080/api/task`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

  deleteTaskFetch(task){
    const header = {
      method: 'DELETE',
    };
    return fetch(`http://localhost:8080/api/task/${task.id}`, header)
      .then(res => res.ok)
      .catch(error => console.error(error));
  },

    //  Rooms

    getRoomsFetch(){
      const header = {
        method: 'GET',
      };
      return fetch('http://localhost:8080/api/rooms', header)
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
      return fetch('http://localhost:8080/api/room', header)
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
      return fetch(`http://localhost:8080/api/room`, header)
        .then(res => res.ok)
        .catch(error => console.error(error));
    },
  
    deleteRoomFetch(room){
      const header = {
        method: 'DELETE',
      };
      return fetch(`http://localhost:8080/api/room/${room.id}`, header)
        .then(res => res.ok)
        .catch(error => console.error(error));
    },

    //  Groups

    getGroupsFetch(){
      const header = {
        method: 'GET',
      };
      return fetch('http://localhost:8080/api/groups', header)
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
      return fetch('http://localhost:8080/api/group', header)
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
      return fetch(`http://localhost:8080/api/group`, header)
        .then(res => res.ok)
        .catch(error => console.error(error));
    },
  
    deleteGroupFetch(group){
      const header = {
        method: 'DELETE',
      };
      return fetch(`http://localhost:8080/api/group/${group.id}`, header)
        .then(res => res.ok)
        .catch(error => console.error(error));
    },

    //  Solutions

    getSolutionsFetch(){
      const header = {
        method: 'GET',
      };
      return fetch('http://localhost:8080/api/solutions', header)
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
      return fetch('http://localhost:8080/api/solution', header)
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
      return fetch(`http://localhost:8080/api/solution`, header)
        .then(res => res.ok)
        .catch(error => console.error(error));
    },
  
    deleteSolutionFetch(solution){
      const header = {
        method: 'DELETE',
      };
      return fetch(`http://localhost:8080/api/group/${solution.id}`, header)
        .then(res => res.ok)
        .catch(error => console.error(error));
    },
};


export default{
  state, 
  methods,
}