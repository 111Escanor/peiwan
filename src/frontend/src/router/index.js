import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import Home from '@/views/Home.vue'
import MasterList from '@/views/MasterList.vue'
import OrderList from '@/views/OrderList.vue'
import Profile from '@/views/Profile.vue'
import Admin from '@/views/Admin.vue'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/home', component: Home, meta: { requiresAuth: true } },
    { path: '/masters', component: MasterList, meta: { requiresAuth: true } },
    { path: '/orders', component: OrderList, meta: { requiresAuth: true } },
    { path: '/profile', component: Profile, meta: { requiresAuth: true } },
    { path: '/admin', component: Admin, meta: { requiresAuth: true, role: 'admin' } }
  ]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else if (to.meta.role && to.meta.role !== role) {
    next('/home')
  } else {
    next()
  }
})

export default router