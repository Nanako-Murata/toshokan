<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import { api } from "@/api"

const router = useRouter()

const loans = ref([])

/* ======================
   履歴取得
====================== */
const fetchHistory = async () => {
  try {
    const res = await api.get("/loans/history", {
      withCredentials: true
    })

    loans.value = res.data.content ?? res.data
  } catch (e) {
    console.error("履歴取得失敗:", e)
  }
}

/* ======================
   ナビゲーション
====================== */
const goList = () => {
  router.push("/books")
}

const goMyPage = () => {
  router.push("/mypage")
}

/* ======================
   ログアウト
====================== */
const logout = () => {
  localStorage.removeItem("token")
  router.push("/")
}

onMounted(fetchHistory)
</script>

<template>
  <div>
    <h1>貸出履歴</h1>

    <!-- ナビボタン -->
    <div style="margin-bottom: 12px;">
      <button @click="goList">本一覧へ</button>
      <button @click="goMyPage">マイページ</button>
      <button @click="logout">ログアウト</button>
    </div>

    <!-- テーブル -->
    <table border="1">
      <tr>
        <th>タイトル</th>
        <th>著者</th>
        <th>貸出日</th>
        <th>返却日</th>
      </tr>

      <tr v-for="l in loans" :key="l.id">
  <td>{{ l.title }}</td>
  <td>{{ l.author }}</td>
  <td>{{ l.loanDate }}</td>
</tr>
    </table>
  </div>
</template>