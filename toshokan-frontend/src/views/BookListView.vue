<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import { api } from "@/api"

const router = useRouter()

const books = ref([])
const keyword = ref("")
const currentPage = ref(0)
const totalPages = ref(0)
const totalElements = ref(0)
const menuOpen = ref(false)

/* ======================
　本一覧（初期表示）
====================== */
const fetchBooks = async () => {
  try {
    const res = await api.get("/books", {
      params: {
        page: currentPage.value,
        size: 10
      }
    })

    books.value = res.data.content
    totalPages.value = res.data.totalPages
    totalElements.value = res.data.totalElements
  } catch (e) {
    console.error("一覧取得失敗:", e)
  }
}
/* 初期表示 */
onMounted(() => {
  fetchBooks()
})


/* ======================
　検索
====================== */
const search = async () => {
  try {
    const res = await api.get("/books/search", {
      params: { keyword: keyword.value }
    })

    books.value = res.data.content ?? res.data
    currentPage.value = res.data.number
    totalPages.value = res.data.totalPages
    totalElements.value = res.data.totalElements

  } catch (e) {
    console.error("検索失敗:", e)
  }
}


/* ======================
　貸出
====================== */
const borrowBook = async (bookId) => {
  try {
    await api.post(`/book/${bookId}/borrow`)

    await fetchBooks()

  } catch (e) {
    console.error("貸出失敗:", e)
  }
}


/* ======================
　画面遷移
====================== */
const goDetail = (id) => {
  router.push(`/books/${id}`)
}

const goMyPage = () => {
  router.push("/mypage")
}

const goRegister = () => {
  router.push("/books/register")
}


/* ======================
　ログアウト
====================== */
const logout = () => {
  localStorage.removeItem("token")
  router.push("/")
}
</script>

<template>
   <div style="width: 100%; max-width: 900px; margin: 0 auto; padding: 20px;">
    <div class="header">
      <h1>Book List</h1>

      <!-- ハンバーガーメニュー -->
      <div class="menu-wrapper">
        <button class="hamburger" @click="menuOpen = !menuOpen">☰</button>
        <div v-if="menuOpen" class="dropdown">
          <button @click="goMyPage">マイページ</button>
          <button @click="goRegister">本を追加</button>
          <button @click="logout">ログアウト</button>
        </div>
      </div>
    </div>

    <!-- 検索 -->
    <div class="search-bar">
      <input v-model="keyword" placeholder="検索キーワード" />
      <button @click="search">検索</button>
    </div>

    <table class="common-table">
      <tr>
        <th>タイトル</th>
        <th>著者</th>
        <th>状態</th>
        <th>操作</th>
      </tr>

      <tr v-for="b in books" :key="b.id">
        <td @click="goDetail(b.id)" style="cursor:pointer;color:blue;">
          {{ b.title }}
        </td>
        <td>{{ b.author }}</td>
        <td>{{ b.status === 0 ? "貸出可" : "貸出中" }}</td>
        <td>
          <button v-if="b.status === 0" @click="borrowBook(b.id)">
            かりる
          </button>
        </td>
      </tr>
    </table>

  </div>
</template>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.menu-wrapper {
  position: relative;
}

.hamburger {
  font-size: 24px;
  background: var(--accent);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  padding: 6px 12px;
}
.dropdown {
  position: absolute;
  right: 0;
  background: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 8px;
  display: flex;
  flex-direction: column;
  gap: 8px;
  z-index: 100;
  min-width: 120px;
}

.dropdown button {
  background: none;
  border: none;
  cursor: pointer;
  text-align: left;
  padding: 4px 8px;
  color: #1f2937;        /* ← 追加：濃いテキスト色 */
  font-size: 15px;       /* ← 追加：文字サイズ */
  font-weight: 600;      /* ← 追加：少し太く */
  white-space: nowrap;
}
.dropdown button:hover {
  background-color: var(--accent-light);
  color: var(--accent-hover);
  border-radius: 4px;
}
.search-bar {
  display: flex;
  flex-direction: row;  /* ← 追加 */
  align-items: center;  /* ← 追加 */
  gap: 8px;
  margin: 16px 0;
}

.search-bar input {
  width: 200px;  /* ← 追加：入力欄の幅を固定 */
}
.search-bar button {
  padding: 10px 16px;  /* ← グローバルを上書き */
  white-space: nowrap; /* ← 縦書き防止 */
}
</style>