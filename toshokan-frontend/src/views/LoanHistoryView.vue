<template>
  <div>
    <h1>History</h1>

    <table border="1">
      <tr>
        <th>タイトル</th>
        <th>借りた日</th>
        <th>返却日</th>
      </tr>

      <tr v-for="l in history" :key="l.id">
        <td>{{ l.book.title }}</td>
        <td>{{ l.loanDate }}</td>
        <td>{{ l.returnDate }}</td>
      </tr>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import axios from "axios"

const history = ref([])

onMounted(async () => {
  const res = await axios.get("http://localhost:8080/loans/history")
  history.value = res.data.content
})
</script>