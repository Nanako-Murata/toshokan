<template>
  <div class="login-container">
    <div class="login-card">

      <h2>Login</h2>

      <form @submit.prevent="login">

        <input
          v-model="username"
          type="text"
          placeholder="username"
          autocomplete="username"
        />

        <input
          v-model="password"
          type="password"
          placeholder="password"
          autocomplete="current-password"
        />

        <button type="submit">
          Login
        </button>

      </form>

      <p v-if="error" class="error">
        {{ error }}
      </p>

      <button class="link" type="button" @click="goSignup">
        Create account
      </button>

    </div>
  </div>
</template>
<script setup>
import { ref } from "vue"
import axios from "axios"
import { useRouter } from "vue-router"

const router = useRouter()

const username = ref("")
const password = ref("")

const login = async () => {
  try {
    const res = await axios.post(
      "http://localhost:8080/login",
      {
        username: username.value,
        password: password.value
      },
      { withCredentials: true }
    )

    console.log("ログイン成功:", res.data)

    router.push("/books")

  } catch (e) {
    console.error("ログイン失敗:", e)
  }
}

const goSignup = () => {
  router.push("/signup")
}
</script>