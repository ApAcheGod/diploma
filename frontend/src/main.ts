import {Notify, Quasar} from 'quasar'
import {createApp} from 'vue'
import authPlugin from './plugins/auth'

import '@quasar/extras/roboto-font/roboto-font.css'
import '@quasar/extras/material-icons/material-icons.css' // Icon libraries
import 'quasar/src/css/index.sass' // Import Quasar css
import './index.css'

import router from './router/index.js'
import App from './App.vue'

const app = createApp(App)

app.config.globalProperties.auth = authPlugin;

app.use(router)
app.use(Quasar, {
  plugins: { Notify },
})

app.mount('#app')
