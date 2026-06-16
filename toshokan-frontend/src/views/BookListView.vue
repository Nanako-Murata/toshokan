<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import { api } from "@/api"

const router = useRouter()

const books = ref([])
const keyword = ref("")

/* ======================
　本一覧（初期表示）
====================== */
const fetchBooks = async () => {
  try {
    const res = await api.get("/books")

    console.log("BOOK RESPONSE:", res.data)

    books.value = res.data
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
/* ======================
   画面遷移
====================== */

</script>

<template>
  <div>
    <h1>Book List</h1>

    <!-- 検索 -->
    <input v-model="keyword" placeholder="検索キーワード" />
    &ensp;
    <button @click="search">検索</button>
&ensp;
    <!-- 操作ボタン -->
    <button @click="logout">ログアウト</button>
    &ensp;
    <button @click="goMyPage">マイページ</button>
    &ensp;
    <button @click="goRegister">本を追加</button>

    <table border="1">
      <tr>
        <th>タイトル</th>
        <th>著者</th>
        <th>状態</th>
        <th>操作</th>
      </tr>

      <tr v-for="b in books" :key="b.id">
        <!-- 詳細画面へ -->
<td @click="goDetail(b.id)" style="cursor:pointer;color:blue;">
  {{ b.title }}
</td>

<td>
  {{ b.author }}
</td>

<td>
  {{ b.status === 0 ? "貸出可" : "貸出中" }}
</td>

        <td>
          <button
            v-if="b.status === 0"
            @click="borrowBook(b.id)"
          >
            かりる
          </button>
        </td>
      </tr>
    </table>
  </div>
</template>