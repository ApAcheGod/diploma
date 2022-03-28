import { createRouter, createWebHistory } from 'vue-router'

import Admin from '../views/Admin.vue'
import AdminStudents from '../components/admin/StudentsView.vue'
import AdminMaterials from '../components/admin/MaterialsView.vue'
import AdminTeachers from '../components/admin/TeachersView.vue'
import AdminSubjects from '../components/admin/SubjectsView.vue'

import Teacher from '../views/Teacher.vue'
import TeacherProfile from '../components/TeacherProfile.vue'
import TeacherRooms from '../components/TeacherRooms.vue'

const routes = [
  {
    path: '/teacher',
    name: 'Teacher',
    component: Teacher,
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
    name: 'Admin',
    component: Admin,
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
  
]
const router = createRouter({
  history: createWebHistory(),
  routes,
})
export default router