import { createWebHashHistory, createRouter } from "vue-router";
import { RouteRecordRaw } from "vue-router";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "account",
    component: () => import("./components/Accounts.vue"),
  },
  {
    path: "/detail/:id",
    name: "detail",
    props: true,
    component: () => import("./components/AccountDetail.vue"),
  },
  {
    path: "/transfer",
    name: "transfer",
    component: () => import("./components/Transfer.vue"),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
