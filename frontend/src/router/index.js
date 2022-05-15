import { createRouter, createWebHistory } from 'vue-router';
import { useStore } from 'vuex'

import NotFound from '../views/NotFound.vue';
import SigninView from '../views/SigninView.vue';

import StudentView from '../views/StudentView.vue';

import TeacherView from '../views/TeacherView.vue';
import TeacherProfile from '../components/teacher/TeacherProfile.vue';
import TeacherRooms from '../components/teacher/TeacherRooms.vue';

import AdminView from '../views/AdminView.vue';
import AdminStudents from '../components/admin/StudentsView.vue';
import AdminMaterials from '../components/admin/MaterialsView.vue';
import AdminTeachers from '../components/admin/TeachersView.vue';
import AdminSubjects from '../components/admin/SubjectsView.vue';
import AdminGroups from '../components/admin/GroupView.vue';
import AdminTasks from '../components/admin/TaskView.vue';
import AdminRooms from '../components/admin/RoomView.vue';

import userRoles from '../models/userRoles.js';

const routes = [
  {
    path: '/student',
    name: 'StudentView',
    component: StudentView,
    meta: { requiredStudent: true },
  },
  {
    path: '/teacher',
    name: 'TeacherView',
    component: TeacherView,
    meta: { requiredTeacher: true },
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
    name: 'AdminView',
    component: AdminView,
    meta: { requiredAdmin: true },
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
    path: '/signin',
    name: 'Signin',
    component: SigninView,
    meta: { loginPage: true },
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFound,
    meta: { isNotFoundPage: true },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.resolve({
  name: 'NotFound',
  params: { pathMatch: ['not', 'found'] },
}).href

router.beforeEach((to, from, next) => {
  const requiresAuth = !to.matched.some((record) => record.meta.nonRequiresAuth);
  const isNotFoundPage = to.matched.some((record) => record.meta.isNotFoundPage);
  const isLoginPage = to.matched.some((record) => record.meta.loginPage);

  const requiredStudent = to.matched.some((record) => record.meta.requiredStudent);
  const requiredTeacher = to.matched.some((record) => record.meta.requiredTeacher);
  const requiredAdmin = to.matched.some((record) => record.meta.requiredAdmin);

  const isAuthenticated = localStorage.getItem('isAuthenticated');
  const userRole = localStorage.getItem('userRole');

  const requiredRole = Object.values(userRoles)[[requiredAdmin, requiredTeacher, requiredStudent, true].findIndex(requiredRole => requiredRole)];
  const userHasRightRole = requiredRole === userRole || requiredRole === userRoles.ROLE_ANONYMOUS;

  if (!isLoginPage && !isAuthenticated) {
    next('/signin');
  } 
  else if (!userHasRightRole || isNotFoundPage) {
    if (userRole === userRoles.ROLE_ADMIN)
      router.push('/admin/teachers');
    else if (userRole === userRoles.ROLE_TEACHER)
      router.push('/teacher');
    else if (userRole === userRoles.ROLE_STUDENT)
      router.push('/student');
  }
  else {
    next();
  }
});

export default router