<template>
  <h1 class="app-title">図書館アプリ</h1>
  <div class="login-container">
    <div class="login-card">

      <h2>新規登録</h2>

      <form @submit.prevent="signup">

        <input v-model="name" type="text" placeholder="お名前" autocomplete="name" />

        <input v-model="password" type="password" placeholder="パスワード（6文字以上）" autocomplete="new-password" />

        <input v-model="confirmPassword" type="password" placeholder="パスワード確認" autocomplete="new-password" />

        <button type="submit">登録</button>

      </form>

      <p v-if="errorMessage" style="color: red;">
        {{ errorMessage }}
      </p>

      <p v-if="successMessage" style="color: green;">
        {{ successMessage }}
      </p>

      <div class="link-buttons">
        <button class="link" type="button" @click="goLogin">
          ログイン画面へ戻る
        </button>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref } from "vue"
import { useRouter } from "vue-router"
import { api } from "@/api"
const confirmPassword = ref("")  // ← 追加
const router = useRouter()

const name = ref("")
const password = ref("")
const errorMessage = ref("")
const successMessage = ref("")

const signup = async () => {
  try {
    errorMessage.value = ""
    successMessage.value = ""

    if (password.value.length < 6) {
      errorMessage.value = "パスワードは6文字以上で入力してください"
      return
    }

    if (password.value !== confirmPassword.value) {
      errorMessage.value = "パスワードが一致しません"
      return
    }

    await api.post("/api/signup", {
      name: name.value,
      password: password.value,
    })

    successMessage.value = "会員登録が完了しました"

  } catch (e) {
    console.error("signup失敗:", e)
    errorMessage.value = e.response?.data || "新規登録に失敗しました"
  }
}

const goLogin = () => {
  router.push("/")
}
</script>

<style scoped>
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

.app-title {
  font-size: 28px;
  color: var(--accent);
  text-align: center;
  margin-bottom: 8px;
}

.link-buttons {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  margin-top: 8px;
}
</style>