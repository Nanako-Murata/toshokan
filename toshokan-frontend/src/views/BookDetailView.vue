<template>
  <div>
    <h1>{{ book?.title }}</h1>
    <p>{{ book?.author }}</p>
    <p>{{ book?.detail }}</p>

    <button @click="borrow">借りる</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import axios from "axios"
import { useRoute } from "vue-router"

const route = useRoute()
const book = ref(null)

onMounted(async () => {
  const res = await axios.get(`http://localhost:8080/books/${route.params.id}`)
  book.value = res.data
})

const borrow = async () => {
  const res = await axios.post("http://localhost:8080/loans/borrow", {
    bookId: route.params.id
  })

  alert(res.data ? "貸出成功" : "貸出不可")
}
</script>