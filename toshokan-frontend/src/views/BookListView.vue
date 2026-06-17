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

const fetchBooks = async () => {
  console.log("fetchBooks start")

  try {
    const res = await api.get("/books", {
      params: {
        page: currentPage.value,
        size: 10
      }
    })

    console.log("BOOK RESPONSE:", res)
    console.log("BOOK RESPONSE DATA:", res?.data)
    console.log("BOOK RESPONSE CONTENT:", res?.data?.content)

    const pageData = res?.data ?? res

    books.value = pageData?.content ?? []
    totalPages.value = pageData?.totalPages ?? 0
    totalElements.value = pageData?.totalElements ?? 0

    console.log("books.value:", books.value)
  } catch (e) {
    console.error("一覧取得失敗:", e)
  }
}

onMounted(() => {
  console.log("BookList mounted")
  fetchBooks()
})
</script>

<template>
  <div>
    <h1>Book List</h1>
    <p>books length: {{ books.length }}</p>


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