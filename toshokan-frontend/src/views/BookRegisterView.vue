<template>
  <div>
    <h1>Register Book</h1>

    <input v-model="title" />
    <input v-model="author" />
    <textarea v-model="detail"></textarea>

    <button @click="register">登録</button>
  </div>
</template>

<script setup>
import { ref } from "vue"
import axios from "axios"
import { useRouter } from "vue-router"

const router = useRouter()

const title = ref("")
const author = ref("")
const detail = ref("")

const register = async () => {
  await axios.post("http://localhost:8080/books", {
    title: title.value,
    author: author.value,
    detail: detail.value,
    status: 0
  })

  router.push("/books")
}
</script>