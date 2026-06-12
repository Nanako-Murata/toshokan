<template>
  <div v-if="book">

    <h1>{{ book.title }}</h1>

    <p>著者: {{ book.author }}</p>

    <p>詳細: {{ book.detail }}</p>

    <p>
      状態:
      {{ book.status === 0 ? "貸出可" : "貸出中" }}
    </p>

    <!-- 貸出ボタン（貸出可のみ） -->
    <button v-if="book.status === 0" @click="borrow(book.id)">
      借りる
    </button>

    <hr />

    <!-- 戻るボタン -->
    <button @click="goList">
      本一覧に戻る
    </button>

  </div>

  <div v-else>
    <p>読み込み中...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import { useRoute, useRouter } from "vue-router"
import { api } from "@/api"

const route = useRoute()
const router = useRouter()

const book = ref(null)

const goList = () => {
  router.push("/books")
}

onMounted(async () => {
  try {
    const res = await api.get(`/books/${route.params.id}`)
    book.value = res.data
    console.log("BOOK:", res.data)
  } catch (err) {
    console.error("取得失敗:", err)
  }
})
const borrow = async (bookId) => {
  try {
    await api.post(`/book/${bookId}/borrow`)
    router.push("/books")
  } catch (e) {
    console.error("貸出失敗:", e)
  }
}

</script>