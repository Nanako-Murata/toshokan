<template>
  <div>
    <h1>メール認証</h1>

    <p v-if="loading">認証中...</p>

    <p v-if="successMessage" style="color: green;">
      {{ successMessage }}
    </p>

    <p v-if="errorMessage" style="color: red;">
      {{ errorMessage }}
    </p>

    <button v-if="!loading" @click="goLogin">
      ログイン画面へ
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import { useRouter, useRoute } from "vue-router"
import { api } from "@/api"

const router = useRouter()
const route = useRoute()

const loading = ref(true)
const successMessage = ref("")
const errorMessage = ref("")

const verify = async () => {
  try {
    const token = route.query.token

    if (!token) {
      errorMessage.value = "トークンが見つかりません"
      loading.value = false
      return
    }

    await api.get("/api/signup/verify", {
      params: { token }
    })

    successMessage.value = "会員登録が完了しました！"

  } catch (e) {
    console.error("認証失敗:", e)
    errorMessage.value = "認証に失敗しました。リンクが無効か期限切れです。"
  } finally {
    loading.value = false
  }
}

const goLogin = () => {
  router.push("/")
}

onMounted(verify)
</script>