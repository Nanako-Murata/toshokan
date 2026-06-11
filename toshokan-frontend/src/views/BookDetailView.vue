<template>
  <div v-if="book">
    <h1>{{ book.title }}</h1>
    <p>{{ book.author }}</p>
    <p>{{ book.detail }}</p>

    <button @click="borrow">借りる</button>
  </div>

  <div v-else>
    <p>読み込み中...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import axios from "axios"
import { useRoute } from "vue-router"

const route = useRoute()
const book = ref(null)

onMounted(async () => {
  try {
    const res = await axios.get(
      `http://localhost:8080/books/${route.params.id}`
    )
    book.value = res.data
    console.log("BOOK:", res.data)
  } catch (err) {
    console.error("取得失敗:", err)
  }
})

const borrow = async () => {
  try {
    await axios.post("http://localhost:8080/loans/borrow", {
      bookId: route.params.id
    })

    alert("貸出成功")
  } catch (err) {
    console.error(err)
    alert("貸出不可")
  }
}
</script>