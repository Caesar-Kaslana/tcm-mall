<template>
  <div class="admin-page">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between; align-items: center;">
          <div style="display: flex; gap: 10px;">
            <el-input v-model="query.orderNo" placeholder="订单号" style="width: 200px;" @keyup.enter="fetchData" />
            <el-select v-model="query.status" placeholder="订单状态" clearable style="width: 150px;" @change="fetchData">
              <el-option label="待付款" :value="0" />
              <el-option label="待发货" :value="1" />
              <el-option label="待收货" :value="2" />
              <el-option label="已完成" :value="3" />
              <el-option label="已取消" :value="4" />
            </el-select>
            <el-button type="primary" @click="fetchData">搜索</el-button>
          </div>
        </div>
      </template>
      <el-table :data="list" style="width: 100%">
        <el-table-column prop="orderNo" label="订单号" />
        <el-table-column prop="receiverName" label="收货人" />
        <el-table-column prop="receiverPhone" label="手机号" />
        <el-table-column prop="totalAmount" label="总金额" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag v-if="row.status === 0" type="warning">待付款</el-tag>
            <el-tag v-else-if="row.status === 1" type="primary">待发货</el-tag>
            <el-tag v-else-if="row.status === 2" type="success">待收货</el-tag>
            <el-tag v-else-if="row.status === 3" type="info">已完成</el-tag>
            <el-tag v-else type="danger">已取消</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="下单时间" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button v-if="row.status === 1" size="small" type="primary" @click="handleShip(row.id)">发货</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="fetchData" style="margin-top: 20px; justify-content: center;" />
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { adminOrderList, shipOrder } from '../../api/order'
import { ElMessage } from 'element-plus'

const list = ref([])
const total = ref(0)
const query = reactive({ orderNo: '', status: undefined, pageNum: 1, pageSize: 10 })

const fetchData = async () => {
  const res = await adminOrderList(query)
  list.value = res.data?.list || []
  total.value = res.data?.total || 0
}

const handleShip = async (id) => {
  await shipOrder(id)
  ElMessage.success('发货成功')
  fetchData()
}

onMounted(fetchData)
</script>
