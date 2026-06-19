vue<template>
  <div class="register-container">
    <h1>本の追加</h1>

    <input v-model="title" placeholder="タイトル" />
    <br/>
    <input v-model="author" placeholder="著者" />
    <br/>
    <textarea v-model="detail" placeholder="詳細"></textarea>
    <br/>
    <button @click="register">登録</button>
    <br/>
    <button @click="goList">一覧へ戻る</button>
  </div>
</template>

<script setup>
import { ref } from "vue"
import { useRouter } from "vue-router"
import { api } from "@/api"

const router = useRouter()

const title = ref("")
const author = ref("")
const detail = ref("")

// 登録
const register = async () => {
  try {
    await api.post("/books", {
      title: title.value,
      author: author.value,
      detail: detail.value
    })

    alert("登録しました")
    router.push("/books")

  } catch (e) {
    console.error("登録失敗:", e)
  }
}

// 一覧へ戻る
const goList = () => {
  router.push("/books")
}
</script>

<style scoped>
.register-container {
  width: 420px;
  max-width: 100%;
  margin: 0 auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

input, textarea {
  width: 100%;
  max-width: 400px;
}

textarea {
  height: 120px;
  resize: vertical;
}
</style>