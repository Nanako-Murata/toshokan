<template>
  <h1 class="app-title">図書館アプリ</h1>
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

      <p v-if="errorMessage" style="color:red;">
        {{ errorMessage }}
      </p>

      <div class="link-buttons">
        <button class="link" type="button" @click="goSignup">
          Create account
        </button>

        <button class="link" type="button" @click="goPasswordReset">
          パスワードを忘れた方はこちら
        </button>
      </div>

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
  router.push("/password-reset-request")
}
</script>

<style scoped>
.link-buttons {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  margin-top: 8px;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding-top: 50px;
}

.login-card {
  width: 420px;
  max-width: 100%;
  background: #ffffff;
  border: 3px solid #fde68a;
  border-radius: 32px;
  padding: 36px;
  box-shadow: 0 8px 24px rgba(245, 158, 11, 0.12);
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.link-buttons {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  margin-top: 8px;
}

.app-title {
  font-size: 28px;
  color: var(--accent);
  text-align: center;
  margin-bottom: 8px;
}
</style>