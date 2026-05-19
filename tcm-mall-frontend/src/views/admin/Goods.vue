<template>
  <div class="admin-page">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <div style="display: flex; gap: 10px;">
            <el-input v-model="query.keyword" placeholder="搜索商品" style="width: 200px;" @keyup.enter="fetchData" />
            <el-select v-model="query.categoryId" placeholder="选择分类" clearable style="width: 150px;" @change="fetchData">
              <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
            </el-select>
            <el-button type="primary" @click="fetchData">搜索</el-button>
          </div>
          <el-button type="primary" @click="handleAdd">新增商品</el-button>
        </div>
      </template>
      <el-table :data="list" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column label="封面" width="100">
          <template #default="{ row }"><el-image :src="row.cover" style="width: 60px; height: 60px;" fit="cover" /></template>
        </el-table-column>
        <el-table-column prop="name" label="商品名称" />
        <el-table-column prop="categoryName" label="分类" width="120" />
        <el-table-column prop="price" label="价格" width="100" />
        <el-table-column prop="stock" label="库存" width="100">
          <template #default="{ row }">
            <span :style="{ color: row.stock <= 10 ? '#f56c6c' : '' }">{{ row.stock }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-switch v-model="row.status" :active-value="1" :inactive-value="0" @change="(val) => toggleStatus(row.id, val)" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="fetchData" style="margin-top: 20px; justify-content: center;" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑商品' : '新增商品'" width="700px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="商品名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="分类">
          <el-select v-model="form.categoryId" placeholder="选择分类" @change="handleCategoryChange">
            <el-option v-for="cat in leafCategories" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="封面图"><el-input v-model="form.cover" placeholder="图片URL" /></el-form-item>
        <el-form-item label="价格"><el-input-number v-model="form.price" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="库存"><el-input-number v-model="form.stock" :min="0" /></el-form-item>
        <el-form-item label="商品描述"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="中医功效"><el-input v-model="form.effect" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="用法用量"><el-input v-model="form.usage" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="禁忌"><el-input v-model="form.contraindication" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { adminGoodsList, addGoods, updateGoods, deleteGoods, updateGoodsStatus } from '../../api/goods'
import request from '../../utils/request'
import { ElMessage, ElMessageBox } from 'element-plus'

const list = ref([])
const categories = ref([])
const leafCategories = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const form = ref({})
const query = reactive({ keyword: '', categoryId: '', pageNum: 1, pageSize: 10 })

const fetchData = async () => {
  const res = await adminGoodsList(query)
  list.value = res.data?.list || []
  total.value = res.data?.total || 0
}

const fetchCategories = async () => {
  const res = await request.get('/admin/category/all')
  const data = res.data || []
  categories.value = data.filter(c => c.parentId === 0)
  leafCategories.value = data.filter(c => c.parentId !== 0)
}

const handleCategoryChange = (val) => {
  const cat = leafCategories.value.find(c => c.id === val)
  if (cat) form.value.categoryName = cat.name
}

const handleAdd = () => { form.value = {}; dialogVisible.value = true }
const handleEdit = (row) => { form.value = { ...row }; dialogVisible.value = true }

const save = async () => {
  if (form.value.id) {
    await updateGoods(form.value)
  } else {
    await addGoods(form.value)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  fetchData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确认删除？', '提示')
  await deleteGoods(id)
  ElMessage.success('删除成功')
  fetchData()
}

const toggleStatus = async (id, status) => {
  await updateGoodsStatus(id, status)
  ElMessage.success('状态更新成功')
}

onMounted(() => { fetchData(); fetchCategories() })
</script>
