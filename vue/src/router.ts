import { createWebHistory, createRouter } from 'vue-router'
import { RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'account',
    component: () => import('./components/Accounts.vue'),
  },
  {
    path: '/transactions/:id',
    name: 'transactions',
    props: true,
    component: () => import('./components/Transactions.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
