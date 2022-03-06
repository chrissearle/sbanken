import { createWebHashHistory, createRouter } from 'vue-router'
import { RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'account',
    component: () => import('./components/AccountsList.vue'),
  },
  {
    path: '/detail/:id',
    name: 'detail',
    props: true,
    component: () => import('./components/AccountDetail.vue'),
  },
  {
    path: '/transfer',
    name: 'transfer',
    component: () => import('./components/TransferForm.vue'),
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})

export default router
