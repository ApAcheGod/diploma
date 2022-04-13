import {Notify, Quasar} from 'quasar'
import {createApp} from 'vue'

import '@quasar/extras/roboto-font/roboto-font.css'
import '@quasar/extras/material-icons/material-icons.css' // Icon libraries
import 'quasar/src/css/index.sass' // Import Quasar css
import './index.css'

import router from './router/index.js'
import App from './App.vue'

const myApp = createApp(App)

myApp.use(router)
myApp.use(Quasar, {
  plugins: { Notify },
})

myApp.mount('#app')
