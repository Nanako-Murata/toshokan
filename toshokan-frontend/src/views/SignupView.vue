<template>
  <div>
    <h1>Signup</h1>
<input v-model="name" placeholder="お名前" />
<input v-model="password" type="password" placeholder="パスワード（6文字以上）" />
<input v-model="confirmPassword" type="password" placeholder="パスワード確認" />


    <button @click="signup">登録</button>

    <br /><br />

    <p v-if="errorMessage" style="color: red;">
      {{ errorMessage }}
    </p>

    <p v-if="successMessage" style="color: green;">
      {{ successMessage }}
    </p>

    <br />

    <button @click="goLogin">
      ログイン画面へ戻る
    </button>
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