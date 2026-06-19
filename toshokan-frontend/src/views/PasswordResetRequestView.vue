<template>
  <div>
    <h1>パスワードリセット</h1>

    <div v-if="!sent">
      <p>登録時のなまえとメールアドレスを入力してください。</p>

      <div>
        <input v-model="name" type="text" placeholder="なまえ" />
      </div>

      <div>
        <input v-model="email" type="email" placeholder="メールアドレス" />
      </div>

      <button @click="requestReset">送信</button>

      <p v-if="errorMessage" style="color:red;">{{ errorMessage }}</p>
    </div>

    <div v-else>
      <p style="color:green;">
        パスワードリセット用のメールを送信しました。メールのリンクからパスワードを再設定してください。
      </p>
    </div>

    <br />
    <button @click="goLogin">ログイン画面へ戻る</button>
  </div>
</template>

<script setup>
import { ref } from "vue"
import { useRouter } from "vue-router"
import { api } from "@/api"

const router = useRouter()

const name = ref("")
const email = ref("")
const errorMessage = ref("")
const sent = ref(false)

const requestReset = async () => {
  try {
    errorMessage.value = ""

    await api.post("/api/password-reset/request", {
      name: name.value,
      email: email.value
    })

    sent.value = true

  } catch (e) {
    console.error("リセット要求失敗:", e)
    errorMessage.value = e.response?.data || "送信に失敗しました"
  }
}

const goLogin = () => {
  router.push("/")
}
</script>