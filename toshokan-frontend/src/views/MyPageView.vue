<template>
  <div>
    <h1>MyPage</h1>

    <button @click="goHistory">履歴</button>

    <table border="1">
      <tr>
        <th>タイトル</th>
        <th>著者</th>
        <th>返却</th>
      </tr>

      <tr v-for="l in loans" :key="l.id">
        <td>{{ l.book.title }}</td>
        <td>{{ l.book.author }}</td>
        <td><button @click="returnBook(l.id)">返却</button></td>
      </tr>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import axios from "axios"
import { useRouter } from "vue-router"

const router = useRouter()
const loans = ref([])

const fetchLoans = async () => {
  const res = await axios.get("http://localhost:8080/loans/current")
  loans.value = res.data.content
}

onMounted(fetchLoans)

const returnBook = async (id) => {
  await axios.post(`http://localhost:8080/loans/${id}/return`)
  fetchLoans()
}

const goHistory = () => router.push("/loans/history")
</script>