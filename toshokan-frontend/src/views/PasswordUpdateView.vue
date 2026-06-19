<template>
  <div>
    <h1>新しいパスワードの設定</h1>

    <div v-if="!completed">
      <div>
        <input v-model="newPassword" type="password" placeholder="新しいパスワード" />
      </div>

      <div>
        <input v-model="confirmPassword" type="password" placeholder="パスワード確認" />
      </div>

      <button @click="updatePassword">変更する</button>

      <p v-if="errorMessage" style="color:red;">{{ errorMessage }}</p>
    </div>

    <div v-else>
      <p style="color:green;">パスワードを変更しました！</p>
    </div>

    <br />
    <button @click="goLogin">ログイン画面へ戻る</button>
  </div>
</template>

<script setup>
import { ref } from "vue"
import { useRouter, useRoute } from "vue-router"
import { api } from "@/api"

const router = useRouter()
const route = useRoute()

const newPassword = ref("")
const confirmPassword = ref("")
const errorMessage = ref("")
const completed = ref(false)

const updatePassword = async () => {
  try {
    errorMessage.value = ""

    if (newPassword.value !== confirmPassword.value) {
      errorMessage.value = "パスワードが一致しません"
      return
    }

    if (newPassword.value.length < 6) {
      errorMessage.value = "パスワードは6文字以上で入力してください"
      return
    }

    const token = route.query.token

    if (!token) {
      errorMessage.value = "トークンが見つかりません"
      return
    }

    await api.post("/api/password-reset/update", {
      token: token,
      newPassword: newPassword.value
    })

    completed.value = true

  } catch (e) {
    console.error("パスワード変更失敗:", e)
    errorMessage.value = e.response?.data || "パスワードの変更に失敗しました"
  }
}

const goLogin = () => {
  router.push("/")
}
</script>