<template>
  <div class="admin-page">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>角色管理</span>
          <el-button type="primary" @click="handleAdd">新增角色</el-button>
        </div>
      </template>
      <el-table :data="list" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="角色名称" />
        <el-table-column prop="code" label="角色编码" />
        <el-table-column prop="description" label="描述" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="primary" @click="handlePermission(row)">分配权限</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑角色' : '新增角色'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="角色名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="角色编码"><el-input v-model="form.code" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="permDialogVisible" title="分配权限" width="400px">
      <el-tree ref="permTree" :data="permissions" :props="{ label: 'name', value: 'id' }" show-checkbox node-key="id" />
      <template #footer>
        <el-button @click="permDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="savePermissions">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'
import { ElMessage, ElMessageBox } from 'element-plus'

const list = ref([])
const dialogVisible = ref(false)
const permDialogVisible = ref(false)
const permissions = ref([])
const permTree = ref()
const currentRoleId = ref(null)
const form = ref({ name: '', code: '', description: '' })

const fetchData = async () => {
  const res = await request.get('/admin/role/list')
  list.value = res.data || []
}

const handleAdd = () => { form.value = {}; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }

const save = async () => {
  if (form.value.id) {
    await request.put('/admin/role/update', form.value)
  } else {
    await request.post('/admin/role/add', form.value)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  fetchData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确认删除？', '提示')
  await request.delete(`/admin/role/delete/${id}`)
  ElMessage.success('删除成功')
  fetchData()
}

const handlePermission = async (row) => {
  currentRoleId.value = row.id
  const res = await request.get('/admin/permission/list')
  permissions.value = res.data || []
  permDialogVisible.value = true
}

const savePermissions = async () => {
  const keys = permTree.value.getCheckedKeys()
  await request.post('/admin/role/assignPermission', { roleId: currentRoleId.value, permissionIds: keys })
  ElMessage.success('分配成功')
  permDialogVisible.value = false
}

onMounted(fetchData)
</script>
