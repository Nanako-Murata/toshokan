<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import { api } from "@/api"

const router = useRouter()

const loans = ref([])
const currentPage = ref(0)
const totalPages = ref(0)

const fetchHistory = async () => {
  try {
    const res = await api.get("/loans/history", {
      params: {
        page: currentPage.value,
        size: 10
      }
    })
    loans.value = res.data.content ?? res.data
    totalPages.value = res.data.totalPages ?? 1
  } catch (e) {
    console.error("履歴取得失敗:", e)
  }
}

const changePage = (page) => {
  currentPage.value = page
  fetchHistory()
}

const goList = () => router.push("/books")
const goMyPage = () => router.push("/mypage")
const logout = () => {
  localStorage.removeItem("token")
  router.push("/")
}

onMounted(fetchHistory)
</script>

<template>
  <div>
    <h1>貸出履歴</h1>

    <div style="margin-bottom: 12px;">
      <button @click="goList">本一覧へ</button>
      <button @click="goMyPage">マイページ</button>
      <button @click="logout">ログアウト</button>
    </div>

    <table class="common-table">
      <tr>
        <th>タイトル</th>
        <th>著者</th>
        <th>貸出日</th>
      </tr>
      <tr v-for="l in loans" :key="l.id">
        <td>{{ l.title }}</td>
        <td>{{ l.author }}</td>
        <td>{{ l.loanDate }}</td>
      </tr>
    </table>

    <div class="pagination">
      <button :disabled="currentPage === 0" @click="changePage(currentPage - 1)">
        前へ
      </button>
      <span>{{ currentPage + 1 }} / {{ totalPages }}</span>
      <button :disabled="currentPage + 1 >= totalPages" @click="changePage(currentPage + 1)">
        次へ
      </button>
    </div>
  </div>
</template>