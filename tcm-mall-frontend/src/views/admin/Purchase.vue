<template>
  <div class="admin-page">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <div style="display: flex; gap: 10px;">
            <el-input v-model="query.goodsName" placeholder="搜索商品" style="width: 200px;" @keyup.enter="fetchData" />
            <el-button type="primary" @click="fetchData">搜索</el-button>
          </div>
          <el-button type="primary" @click="handleAdd">采购入库</el-button>
        </div>
      </template>
      <el-table :data="list" style="width: 100%">
        <el-table-column prop="purchaseNo" label="入库单号" />
        <el-table-column prop="goodsName" label="商品" />
        <el-table-column prop="quantity" label="入库数量" />
        <el-table-column prop="unitPrice" label="单价" />
        <el-table-column prop="totalPrice" label="总价" />
        <el-table-column prop="supplier" label="供应商" />
        <el-table-column prop="createTime" label="入库时间" />
      </el-table>
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="fetchData" style="margin-top: 20px; justify-content: center;" />
    </el-card>

    <el-dialog v-model="dialogVisible" title="采购入库" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="商品">
          <el-select v-model="form.goodsId" placeholder="选择商品">
            <el-option v-for="g in goodsList" :key="g.id" :label="g.name" :value="g.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="入库数量"><el-input-number v-model="form.quantity" :min="1" /></el-form-item>
        <el-form-item label="采购单价"><el-input-number v-model="form.unitPrice" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="供应商"><el-input v-model="form.supplier" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import request from '../../utils/request'
import { adminGoodsList } from '../../api/goods'
import { ElMessage } from 'element-plus'

const list = ref([])
const goodsList = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const form = ref({})
const query = reactive({ goodsName: '', pageNum: 1, pageSize: 10 })

const fetchData = async () => {
  const res = await request.get('/admin/purchase/list', { params: query })
  list.value = res.data?.list || []
  total.value = res.data?.total || 0
}

const fetchGoods = async () => {
  const res = await adminGoodsList({ pageSize: 1000 })
  goodsList.value = res.data?.list || []
}

const handleAdd = () => { form.value = {}; dialogVisible.value = true }

const save = async () => {
  await request.post('/admin/purchase/add', form.value)
  ElMessage.success('入库成功')
  dialogVisible.value = false
  fetchData()
}

onMounted(() => { fetchData(); fetchGoods() })
</script>
