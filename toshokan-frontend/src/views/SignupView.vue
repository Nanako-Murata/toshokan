<template>
  <div>
    <h1>Signup</h1>

<input v-model="username" placeholder="username" />
<input v-model="password" type="password" placeholder="password" />

<button @click="signup">登録</button>

<br><br>

<button @click="goLogin">
  ログイン画面へ戻る
</button>
  </div>
</template>

<script setup>
import { ref } from "vue"
import axios from "axios"
import { useRouter } from "vue-router"

const router = useRouter()

const username = ref("")
const password = ref("")

const signup = async () => {
  await axios.post("http://localhost:8080/signup", {
    username: username.value,
    password: password.value
  })

  alert("登録完了")
  router.push("/")
}

const goLogin = () => {
  router.push("/")
}
</script>