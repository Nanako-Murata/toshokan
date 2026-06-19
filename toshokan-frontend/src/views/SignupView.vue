<template>
  <div>
    <h1>Signup</h1>

    <div>
      <input v-model="name" placeholder="名前" />
    </div>

    <div>
      <input v-model="password" type="password" placeholder="パスワード（6文字以上）" />
    </div>

    <div>
      <input v-model="confirmPassword" type="password" placeholder="パスワード確認" />
    </div>

    <div>
      <input v-model="email" type="email" placeholder="メールアドレス" />
    </div>

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

const router = useRouter()

const name = ref("")
const password = ref("")
const confirmPassword = ref("") // ← 追加
const email = ref("")
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
      email: email.value
    })

    successMessage.value = "確認メールを送信しました。メールのリンクをタップして登録を完了してください。"

  } catch (e) {
    console.error("signup失敗:", e)
    errorMessage.value = e.response?.data || "新規登録に失敗しました"
  }
}

const goLogin = () => {
  router.push("/")
}
</script>

<script setup>
import { ref } from "vue"
import { useRouter } from "vue-router"
import { api } from "@/api"

const router = useRouter()

const name = ref("")
const password = ref("")
const email = ref("")
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
      email: email.value
    })

    successMessage.value = "確認メールを送信しました。メールのリンクをタップして登録を完了してください。"

  } catch (e) {
    console.error("signup失敗:", e)
    errorMessage.value = e.response?.data || "新規登録に失敗しました"
  }
}

const goLogin = () => {
  router.push("/")
}
</script>