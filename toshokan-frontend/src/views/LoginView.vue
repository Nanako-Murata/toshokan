<template>
  <div class="login-container">
    <div class="login-card">

      <h2>Login</h2>

      <form @submit.prevent="login">

        <input
          v-model="name"
          type="text"
          placeholder="name"
          autocomplete="name"
        />
        &ensp;

        <input
          v-model="password"
          type="password"
          placeholder="password"
          autocomplete="current-password"
        />
        &ensp;

        <button type="submit">
          Login
        </button>

      </form>

      <p v-if="errorMessage" style="color:red;">
        {{ errorMessage }}
      </p>

      <button class="link" type="button" @click="goSignup">
        Create account
      </button>

      &ensp;

      <button class="link" type="button" @click="goPasswordReset">
        パスワードを忘れた方はこちら
      </button>

    </div>
  </div>
</template>

<script setup>
import { ref } from "vue"
import { useRouter } from "vue-router"
import { api } from "@/api"

const router = useRouter()
const errorMessage = ref("")

const name = ref("")
const password = ref("")

const login = async () => {
  try {
    errorMessage.value = ""

    const res = await api.post("/api/login", {
      name: name.value,
      password: password.value
    })

    const token =
      res.data.token ||
      res.data.jwt ||
      res.data

    if (!token) {
      throw new Error("tokenが取れてない")
    }

    localStorage.setItem("token", token)
    router.push("/books")

  } catch (e) {
    console.error("login失敗:", e)
    errorMessage.value =
      e.response?.data || "name, passwordが違います"
  }
}

const goSignup = () => {
  router.push("/signup")
}

const goPasswordReset = () => {
  router.push("/password-reset")
}
</script>