<template>
  <el-container class="admin-layout">
    <el-aside width="220px" class="aside">
      <div class="logo">中医商城后台</div>
      <el-menu :default-active="activeIndex" router class="admin-menu" background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
        <el-menu-item index="/admin"><el-icon><Odometer /></el-icon><span>仪表盘</span></el-menu-item>
        <el-menu-item index="/admin/user"><el-icon><User /></el-icon><span>用户管理</span></el-menu-item>
        <el-menu-item index="/admin/role"><el-icon><UserFilled /></el-icon><span>角色管理</span></el-menu-item>
        <el-menu-item index="/admin/permission"><el-icon><Lock /></el-icon><span>权限管理</span></el-menu-item>
        <el-menu-item index="/admin/category"><el-icon><Grid /></el-icon><span>分类管理</span></el-menu-item>
        <el-menu-item index="/admin/goods"><el-icon><Goods /></el-icon><span>商品管理</span></el-menu-item>
        <el-menu-item index="/admin/order"><el-icon><List /></el-icon><span>订单管理</span></el-menu-item>
        <el-menu-item index="/admin/purchase"><el-icon><Box /></el-icon><span>进销存管理</span></el-menu-item>
        <el-menu-item index="/admin/banner"><el-icon><Picture /></el-icon><span>轮播图管理</span></el-menu-item>
        <el-menu-item index="/admin/notice"><el-icon><Bell /></el-icon><span>公告管理</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="admin-header">
        <div class="header-right">
          <el-dropdown>
            <span class="user-info">{{ userStore.user?.nickname || userStore.user?.username }} <el-icon><ArrowDown /></el-icon></span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="$router.push('/')">返回前台</el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="admin-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
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

const handleLogout = async () => {
  await logout()
  userStore.logout()
  ElMessage.success('退出成功')
  router.push('/login')
}
</script>

<style scoped>
.admin-layout { min-height: 100vh; }
.aside { background: #304156; }
.logo { height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 18px; font-weight: bold; border-bottom: 1px solid #1f2d3d; }
.admin-menu { border-right: none; }
.admin-header { background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,0.1); display: flex; align-items: center; justify-content: flex-end; }
.header-right { display: flex; align-items: center; }
.user-info { cursor: pointer; color: #606266; }
.admin-main { background: #f0f2f5; padding: 20px; }
</style>
