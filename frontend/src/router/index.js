import { createRouter, createWebHistory } from 'vue-router'
import Main from '../views/Main.vue'
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
    path: '/',
    name: 'Main',
    component: Main,
  },
]
const router = createRouter({
  history: createWebHistory(),
  routes,
})
export default router