<template>
  <div class="page">
    <div v-if="book" class="book-detail-card">

      <h1 class="book-title">{{ book.title }}</h1>

      <div class="book-info">
        <div class="info-row">
          <span class="label">著者</span>
          <span>{{ book.author }}</span>
        </div>
        <div class="info-row">
          <span class="label">状態</span>
          <span :style="{ color: book.status === 0 ? 'green' : 'red' }">
            {{ book.status === 0 ? "貸出可" : "貸出中" }}
          </span>
        </div>
        <div class="info-row detail-row">
          <span class="label">詳細</span>
          <span>{{ book.detail }}</span>
        </div>
      </div>

      <div class="actions">
        <button v-if="book.status === 0" @click="borrow(book.id)">
          借りる
        </button>
        <button class="link" @click="goList">
          本一覧に戻る
        </button>
      </div>

    </div>
    <div v-else>
      <p>読み込み中...</p>
    </div>
  </div>
</template>

<style scoped>
.page {
  display: flex;
  justify-content: center;
  padding: 40px 20px;
}

.book-detail-card {
  width: 480px;
  max-width: 100%;
  background: #ffffff;
  border: 3px solid #fde68a;
  border-radius: 24px;
  padding: 32px;
  box-shadow: 0 8px 24px rgba(245, 158, 11, 0.12);
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.book-title {
  margin: 0;
  font-size: 22px;
  color: #1f2937;
  text-align: center;
  border-bottom: 2px solid #fde68a;
  padding-bottom: 16px;
}

.book-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.info-row {
  display: flex;
  gap: 16px;
  align-items: baseline;
}

.label {
  font-weight: bold;
  color: #f59e0b;
  min-width: 48px;
}

.actions {
  display: flex;
  flex-direction: column;
  gap: 8px;
  align-items: center;
}
</style>

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