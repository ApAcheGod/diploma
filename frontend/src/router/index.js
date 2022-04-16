import {createRouter, createWebHistory} from 'vue-router';
import {inject} from 'vue';

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
  const store = inject('store');
  console.log(store);
  let isAuthenticated = !!store.user;

  if (!isAuthenticated && to.path !== '/login') {
    next('/login');
  }
  else if (isAuthenticated) {
    next('/');
  }
  else next();
});

export default router