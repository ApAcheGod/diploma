import { createApp } from 'vue';
import { Notify, Quasar } from 'quasar';

import '@quasar/extras/roboto-font/roboto-font.css';
import '@quasar/extras/material-icons/material-icons.css';
import ru from 'quasar/lang/ru'
import 'quasar/src/css/index.sass';
import './index.css';

import store from './store';
import router from './router';
import App from './App.vue';

const app = createApp(App)

app.use(store)
app.use(router)
app.use(Quasar, {
  plugins: { Notify },
  lang: ru,
})

app.mount('#app')
