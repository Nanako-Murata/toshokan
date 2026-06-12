import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios"
import './style.css'

axios.defaults.withCredentials = true

createApp(App)
  .use(router)
  .mount('#app')