<template>
  <div class="front-layout">
    <el-header class="header">
      <div class="header-content">
        <div class="logo" @click="$router.push('/')">中医商城</div>
        <el-menu mode="horizontal" :default-active="activeIndex" router class="nav-menu">
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/goods">商品</el-menu-item>
          <el-menu-item index="/cart">购物车</el-menu-item>
          <el-menu-item index="/order">我的订单</el-menu-item>
        </el-menu>
        <div class="user-actions">
          <template v-if="userStore.user">
            <el-dropdown>
              <span class="user-info">
                {{ userStore.user.nickname || userStore.user.username }}
                <el-icon><arrow-down /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="$router.push('/user')">个人中心</el-dropdown-item>
                  <el-dropdown-item v-if="isAdmin" @click="$router.push('/admin')">后台管理</el-dropdown-item>
                  <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <el-button type="primary" size="small" @click="$router.push('/login')">登录</el-button>
            <el-button size="small" @click="$router.push('/register')">注册</el-button>
          </template>
        </div>
      </div>
    </el-header>
    <el-main class="main">
      <router-view />
    </el-main>
    <el-footer class="footer">
      <p>中医商城管理系统 &copy; 2026 JavaEE课程设计</p>
    </el-footer>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../../store'
import { logout } from '../../api/user'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeIndex = computed(() => route.path)
const isAdmin = computed(() => userStore.roles.some(r => ['admin','goods_admin','order_admin'].includes(r.code)))

const handleLogout = async () => {
  await logout()
  userStore.logout()
  ElMessage.success('退出成功')
  router.push('/')
}
</script>

<style scoped>
.front-layout { min-height: 100vh; display: flex; flex-direction: column; }
.header { background: #fff; box-shadow: 0 2px 12px rgba(0,0,0,0.1); padding: 0; }
.header-content { max-width: 1200px; margin: 0 auto; width: 100%; display: flex; align-items: center; justify-content: space-between; }
.logo { font-size: 24px; font-weight: bold; color: #409EFF; cursor: pointer; padding: 0 20px; }
.nav-menu { flex: 1; border-bottom: none; }
.user-actions { display: flex; gap: 10px; padding: 0 20px; align-items: center; }
.user-info { cursor: pointer; color: #333; }
.main { flex: 1; max-width: 1200px; margin: 0 auto; width: 100%; padding: 20px; }
.footer { background: #2c3e50; color: #fff; text-align: center; }
</style>
