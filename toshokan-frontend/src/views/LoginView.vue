<template>
  <div>
    <h1>Login</h1>

    <input v-model="name" placeholder="name" />
    <input v-model="password" type="password" placeholder="password" />

    <button @click="login">Login</button>
    <button @click="goSignup">Signup</button>
  </div>
</template>

<script setup>
import { ref } from "vue"
import axios from "axios"
import { useRouter } from "vue-router"

const router = useRouter()

const name = ref("")
const password = ref("")

const login = async () => {
  const res = await axios.post("http://localhost:8080/login", {
    name: name.value,
    password: password.value
  })

  if (res.data === true) {
    router.push("/books")
  } else {
    alert("ログイン失敗")
  }
}

const goSignup = () => {
  router.push("/signup")
}
</script>