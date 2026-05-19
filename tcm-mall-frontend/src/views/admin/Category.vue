<template>
  <div class="admin-page">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <span>分类管理</span>
          <el-button type="primary" @click="handleAdd">新增分类</el-button>
        </div>
      </template>
      <el-table :data="list" row-key="id" default-expand-all style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="分类名称" />
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑分类' : '新增分类'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="分类名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="父分类">
          <el-select v-model="form.parentId" placeholder="选择父分类" clearable>
            <el-option label="顶级分类" :value="0" />
            <el-option v-for="cat in parentList" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sort" :min="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'
import { ElMessage, ElMessageBox } from 'element-plus'

const list = ref([])
const parentList = ref([])
const dialogVisible = ref(false)
const form = ref({ name: '', parentId: 0, sort: 0 })

const fetchData = async () => {
  const res = await request.get('/admin/category/all')
  const data = res.data || []
  parentList.value = data.filter(c => c.parentId === 0)
  const tree = []
  const map = {}
  data.forEach(item => { map[item.id] = { ...item, children: [] } })
  data.forEach(item => {
    if (item.parentId === 0) tree.push(map[item.id])
    else if (map[item.parentId]) map[item.parentId].children.push(map[item.id])
  })
  list.value = tree
}

const handleAdd = () => { form.value = { parentId: 0, sort: 0 }; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }

const save = async () => {
  if (form.value.id) {
    await request.put('/admin/category/update', form.value)
  } else {
    await request.post('/admin/category/add', form.value)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  fetchData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确认删除？', '提示')
  await request.delete(`/admin/category/delete/${id}`)
  ElMessage.success('删除成功')
  fetchData()
}

onMounted(fetchData)
</script>
