<template>
  <div class="admin-page">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>用户管理</span>
          <el-input v-model="keyword" placeholder="搜索用户" style="width: 200px;" @keyup.enter="fetchData">
            <template #append><el-button @click="fetchData"><el-icon><Search /></el-icon></el-button></template>
          </el-input>
        </div>
      </template>
      <el-table :data="list" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="nickname" label="昵称" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }"><el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '正常' : '禁用' }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleAssignRole(row)">分配角色</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" title="分配角色" width="400px">
      <el-checkbox-group v-model="selectedRoles">
        <el-checkbox v-for="role in roles" :key="role.id" :label="role.id">{{ role.name }}</el-checkbox>
      </el-checkbox-group>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveAssign">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { userList, assignRole } from '../../api/user'
import request from '../../utils/request'
import { ElMessage } from 'element-plus'

const list = ref([])
const keyword = ref('')
const dialogVisible = ref(false)
const roles = ref([])
const selectedRoles = ref([])
const currentUser = ref(null)

const fetchData = async () => {
  const res = await userList({ keyword: keyword.value })
  list.value = res.data || []
}

const handleAssignRole = async (row) => {
  currentUser.value = row
  const res = await request.get(`/admin/role/list`)
  roles.value = res.data || []
  const userRoleRes = await request.get(`/admin/user/list?keyword=${row.username}`)
  selectedRoles.value = []
  dialogVisible.value = true
}

const saveAssign = async () => {
  await assignRole({ userId: currentUser.value.id, roleIds: selectedRoles.value })
  ElMessage.success('分配成功')
  dialogVisible.value = false
}

onMounted(fetchData)
</script>

<style scoped>
.admin-page { padding: 20px 0; }
</style>
