import { createWebHistory, createRouter } from 'vue-router'
import { RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'account',
    component: () => import('./components/Accounts.vue'),
  },
  {
    path: '/detail/:id',
    name: 'detail',
    props: true,
    component: () => import('./components/AccountDetail.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
