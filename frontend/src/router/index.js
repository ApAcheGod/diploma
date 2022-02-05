import { createRouter, createWebHistory } from 'vue-router'
import App from '/src/App.vue'
const routes = [
    // {
    //     path: '/',
    //     name: 'App',
    //     component: App,
    // },
]
const router = createRouter({
    history: createWebHistory(),
    routes,
})
export default router