<template>
  <div>
    <h1>Signup</h1>

    <div>
      <input v-model="name" placeholder="名前" />
    </div>

    <div>
      <input v-model="password" type="password" placeholder="パスワード" />
    </div>

    <button @click="signup">登録</button>

    <br /><br />

    <!-- ⭐エラー表示（これ重要） -->
    <p v-if="errorMessage" style="color: red;">
      {{ errorMessage }}
    </p>

    <br />

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

const name = ref("")
const password = ref("")
const errorMessage = ref("")

const signup = async () => {
  try {
    errorMessage.value = ""

    await axios.post("http://localhost:8080/api/signup", {
      name: name.value,
      password: password.value
    })

    alert("登録完了")
    router.push("/")
  } catch (e) {
    console.error("signup失敗:", e)

    errorMessage.value =
      e.response?.data || "この名前はすでに使われています"
  }
}

const goLogin = () => {
  router.push("/")
}
</script>