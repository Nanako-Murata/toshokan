<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import { api } from "@/api"

const router = useRouter()
const loans = ref([])

/* 現在貸出中 */
const fetchLoans = async () => {
  try {
    const res = await api.get("/loans/current")

    loans.value = res.data.content ?? res.data
  } catch (e) {
    console.error("取得失敗:", e)
  }
}

/* 返却 */
const returnBook = async (loanId) => {
  try {
    await api.post(`/loan/${loanId}/return`, {})

    await fetchLoans()
  } catch (e) {
    console.error("返却失敗:", e)
  }
}

/* ナビ */
const goList = () => router.push("/books")
const goHistory = () => router.push("/loans/history")

const logout = () => {
  localStorage.removeItem("token")
  router.push("/")
}

onMounted(fetchLoans)
</script>

<template>
  <div>
    <h1>マイページ</h1>

    <button @click="goList">本一覧</button>
    <button @click="goHistory">履歴</button>
    <button @click="logout">ログアウト</button>

    <hr />

    <table border="1">
      <tr>
        <th>タイトル</th>
        <th>著者</th>
        <th>借りた日</th>
        <th>操作</th>
      </tr>

      <tr v-for="l in loans" :key="l.id">
 <td>{{ l.title }}</td>
<td>{{ l.author }}</td>
<td>{{ l.loanDate }}</td>
        <td>
          <button @click="returnBook(l.id)">
            返却
          </button>
        </td>
      </tr>
    </table>
  </div>
</template>