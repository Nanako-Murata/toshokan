import { createRouter, createWebHistory } from "vue-router"

import LoginView from "../views/LoginView.vue"
import SignupView from "../views/SignupView.vue"
import BookListView from "../views/BookListView.vue"
import BookDetailView from "../views/BookDetailView.vue"
import MyPageView from "../views/MyPageView.vue"
import LoanHistoryView from "../views/LoanHistoryView.vue"
import BookRegisterView from "../views/BookRegisterView.vue"
import VerifyView from "../views/VerifyView.vue"

const routes = [
  { path: "/", component: LoginView },
  { path: "/signup", component: SignupView },
  { path: "/signup/verify", component: VerifyView },
  { path: "/books", component: BookListView },
  { path: "/books/:id", component: BookDetailView },
  { path: "/books/register", component: BookRegisterView },
  { path: "/mypage", component: MyPageView },
  { path: "/loans/history", component: LoanHistoryView },
]

export default createRouter({
  history: createWebHistory(),
  routes
})