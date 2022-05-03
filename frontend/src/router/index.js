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
    meta: { loginPage: true, nonRequiresAuth: true },
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'not-found',
    component: NotFound,
    meta: { nonRequiresAuth: true },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.resolve({
  name: 'not-found',
  params: { pathMatch: ['not', 'found'] },
}).href

router.beforeEach((to, from, next) => {
  const requiredStudent = to.matched.some((record) => record.meta.requiredStudent);
  const requiredTeacher = to.matched.some((record) => record.meta.requiredTeacher);
  const requiredAdmin = to.matched.some((record) => record.meta.requiredAdmin);
  const requiresAuth = !to.matched.some((record) => record.meta.nonRequiresAuth);
  const isLoginPage = to.matched.some((record) => record.meta.loginPage);

  const isAuthenticated = localStorage.getItem('isAuthenticated');
  const userRole = localStorage.getItem('userRole');
  
  if (requiresAuth && !isAuthenticated) {
    next('/signin');
  } 
  else if (isLoginPage && isAuthenticated) {
    if (userRole === userRoles.ROLE_ADMIN) {
      router.push('/admin/teachers');
    }
    else if (userRole === userRoles.ROLE_TEACHER) {
      router.push('/teacher');
    }
    router.push('/student');
  } 
  else if (requiredTeacher && userRole !== userRoles.ROLE_TEACHER) {
    router.push('/404');
  }
  else if (requiredAdmin && userRole !== userRoles.ROLE_ADMIN) {
    router.push('/404');
  }
  else if (requiredStudent && userRole !== userRoles.ROLE_STUDENT) {
    router.push('/404');
  }
  next();
});

export default router