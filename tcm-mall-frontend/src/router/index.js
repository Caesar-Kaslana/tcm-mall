import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store'

const routes = [
  {
    path: '/',
    component: () => import('../views/front/Layout.vue'),
    children: [
      { path: '', component: () => import('../views/front/Home.vue') },
      { path: 'goods', component: () => import('../views/front/GoodsList.vue') },
      { path: 'goods/:id', component: () => import('../views/front/GoodsDetail.vue') },
      { path: 'cart', component: () => import('../views/front/Cart.vue'), meta: { requireAuth: true } },
      { path: 'order', component: () => import('../views/front/Order.vue'), meta: { requireAuth: true } },
      { path: 'user', component: () => import('../views/front/UserCenter.vue'), meta: { requireAuth: true } }
    ]
  },
  { path: '/login', component: () => import('../views/front/Login.vue') },
  { path: '/register', component: () => import('../views/front/Register.vue') },
  {
    path: '/admin',
    component: () => import('../views/admin/Layout.vue'),
    meta: { requireAdmin: true },
    children: [
      { path: '', component: () => import('../views/admin/Dashboard.vue') },
      { path: 'user', component: () => import('../views/admin/User.vue') },
      { path: 'role', component: () => import('../views/admin/Role.vue') },
      { path: 'permission', component: () => import('../views/admin/Permission.vue') },
      { path: 'category', component: () => import('../views/admin/Category.vue') },
      { path: 'goods', component: () => import('../views/admin/Goods.vue') },
      { path: 'order', component: () => import('../views/admin/Order.vue') },
      { path: 'purchase', component: () => import('../views/admin/Purchase.vue') },
      { path: 'banner', component: () => import('../views/admin/Banner.vue') },
      { path: 'notice', component: () => import('../views/admin/Notice.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const store = useUserStore()
  if (to.meta.requireAuth && !store.user) {
    next('/login')
  } else if (to.meta.requireAdmin && !store.user) {
    next('/login')
  } else {
    next()
  }
})

export default router
