import { createRouter, createWebHistory } from "vue-router"

import LoginView from "@/views/LoginView.vue"
import BookListView from "@/views/BookListView.vue"
import BookDetailView from "@/views/BookDetailView.vue"
import MyPageView from "@/views/MyPageView.vue"
import BookRegisterView from "@/views/BookRegisterView.vue"
import SignupView from "@/views/SignupView.vue"

const routes = [
  { path: "/", component: LoginView },
  { path: "/signup", component: SignupView },
  { path: "/books", component: BookListView },
  { path: "/books/:id", component: BookDetailView },
  { path: "/mypage", component: MyPageView },
  { path: "/books/register", component: BookRegisterView }
]

export default createRouter({
  history: createWebHistory(),
  routes
})