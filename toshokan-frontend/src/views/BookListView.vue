<template>
  <div>
    <h1>Book List</h1>

    <input v-model="keyword" placeholder="検索" />
    <button @click="search">検索</button>

    <button @click="goMyPage">MyPage</button>
    <button @click="goRegister">登録</button>

    <table border="1">
      <tr>
        <th>タイトル</th>
        <th>著者</th>
        <th>状態</th>
      </tr>

      <tr v-for="b in books" :key="b.id">
        <td @click="goDetail(b.id)" style="cursor:pointer;color:blue;">
          {{ b.title }}
        </td>
        <td>{{ b.author }}</td>
        <td>{{ b.status === 0 ? "貸出可" : "貸出中" }}</td>
      </tr>
    </table>

    <button @click="prev" :disabled="page===0">前へ</button>
    <button @click="next">次へ</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import axios from "axios"
import { useRouter } from "vue-router"

const router = useRouter()

const books = ref([])
const keyword = ref("")
const page = ref(0)

const fetchBooks = async () => {
  const res = await axios.get("http://localhost:8080/books", {
    params: { page: page.value, size: 20 }
  })
  books.value = res.data.content
}

onMounted(fetchBooks)

const search = async () => {
  const res = await axios.get("http://localhost:8080/books/search", {
    params: { keyword: keyword.value }
  })
  books.value = res.data
}

const next = () => { page.value++; fetchBooks() }
const prev = () => { page.value--; fetchBooks() }

const goDetail = (id) => router.push(`/books/${id}`)
const goMyPage = () => router.push("/mypage")
const goRegister = () => router.push("/books/register")
</script>