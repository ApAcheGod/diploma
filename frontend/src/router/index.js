import {createRouter, createWebHistory} from 'vue-router';

import LoginView from '../views/LoginView.vue';

import AdminView from '../views/AdminView.vue';
import AdminStudents from '../components/admin/StudentsView.vue';
import AdminMaterials from '../components/admin/MaterialsView.vue';
import AdminTeachers from '../components/admin/TeachersView.vue';
import AdminSubjects from '../components/admin/SubjectsView.vue';
import AdminGroups from '../components/admin/GroupView.vue';
import AdminTasks from '../components/admin/TaskView.vue';
import AdminRooms from '../components/admin/RoomView.vue';

import TeacherView from '../views/TeacherView.vue';
import TeacherProfile from '../components/teacher/TeacherProfile.vue';
import TeacherRooms from '../components/teacher/TeacherRooms.vue';

import ROLES from '../models/userRoles.js';

const routes = [
  {
    path: '/teacher',
    name: 'TeacherView',
    component: TeacherView,
    children: [
      {
        path: 'profile',
        name: 'TeacherProfile',
        component: TeacherProfile
      },
      {
        path: 'rooms',
        name: 'TeacherRooms',
        component: TeacherRooms
      }
    ],
  },
  {
    path: '/admin',
    beforeEnter(to, from, next) {

    },
    name: 'AdminView',
    component: AdminView,
    children: [
      {
        path: 'students',
        name: 'AdminStudents',
        component: AdminStudents
      },
      {
        path: 'materials',
        name: 'AdminMaterials',
        component: AdminMaterials
      },
      {
        path: 'subjects',
        name: 'AdminSubjects',
        component: AdminSubjects
      },
      {
        path: 'teachers',
        name: 'AdminTeachers',
        component: AdminTeachers
      },
      {
        path: 'groups',
        name: 'AdminGroups',
        component: AdminGroups
      },
      {
        path: 'tasks',
        name: 'AdminTasks',
        component: AdminTasks
      },
      {
        path: 'rooms',
        name: 'AdminRooms',
        component: AdminRooms
      },
    ],
  },
  {
    path: '/',
    beforeEnter(to, from, next) {
      if (to.path === '/') {
        next('/admin/students')
      }
    },
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  let isAuthenticated = false;
  let userRole = '';



  let userLogin = async function (){
    let myHeaders = new Headers();
      myHeaders.append("Content-Type", "application/json");
      myHeaders.append("Cookie", "JSESSIONID=4E1DAD045FB65D39B0D290352F821A2A");

    let raw = JSON.stringify({
      "login": "BGruStudent",
      "password": "nxup1Z9rAw"
    });

    let requestOptions = {
      method: 'POST',
      headers: myHeaders,
      body: raw,
      redirect: 'follow'
    };

    await fetch("http://localhost:8080/api/login", requestOptions)
    .then(response => response.text())
    .then(result => console.log(result))
    .catch(error => console.log('error', error));
  } 


  let getUserRole = async function () {
    const header = {
      method: 'GET',
    };
    await fetch(`http://localhost:8080/api/check`, header)
      .then(res => res.json()
        .then(json => {
          isAuthenticated = true;
          userRole = json.authorities[0].authority;
        }))
        .catch(err => console.log(err))
      .catch(error => {
        console.error(error);
        isAuthenticated = false;
      });
  };
  await userLogin().then(await getUserRole());
  
  console.log(isAuthenticated);
  console.log(userRole);
  //let isAuthenticated = this.auth.$isAuthenticated;

 next();
});

export default router