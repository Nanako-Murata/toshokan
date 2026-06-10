<template>
  <div>
    <h1>Signup</h1>

    <input v-model="name" placeholder="name" />
    <input v-model="password" type="password" placeholder="password" />

    <button @click="signup">登録</button>
  </div>
</template>

<script setup>
import { ref } from "vue"
import axios from "axios"

const name = ref("")
const password = ref("")

const signup = async () => {
  await axios.post("http://localhost:8080/signup", {
    name: name.value,
    password: password.value
  })

  alert("登録完了")
}
</script>