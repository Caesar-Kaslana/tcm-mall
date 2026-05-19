<template>
  <div class="dashboard-page">
    <el-row :gutter="20">
      <el-col :span="6"><el-card><div class="stat-title">用户总数</div><div class="stat-value">{{ stats.userCount }}</div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat-title">今日订单</div><div class="stat-value">{{ stats.orderCount }}</div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat-title">商品总数</div><div class="stat-value">{{ stats.goodsCount }}</div></el-card></el-col>
      <el-col :span="6"><el-card><div class="stat-title">低库存商品</div><div class="stat-value" style="color: #f56c6c;">{{ stats.lowStockCount }}</div></el-card></el-col>
    </el-row>
    <el-card style="margin-top: 20px;">
      <template #header>最近订单</template>
      <el-table :data="recentOrders" style="width: 100%">
        <el-table-column prop="orderNo" label="订单号" />
        <el-table-column prop="receiverName" label="收货人" />
        <el-table-column prop="totalAmount" label="金额" />
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
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../utils/request'
import { adminGoodsList } from '../../api/goods'
import { orderStatistics } from '../../api/order'
import { userList } from '../../api/user'

const stats = ref({ userCount: 0, orderCount: 0, goodsCount: 0, lowStockCount: 0 })
const recentOrders = ref([])

const fetchData = async () => {
  const [uRes, oRes, gRes] = await Promise.all([
    userList(),
    orderStatistics(),
    adminGoodsList({ pageSize: 1 })
  ])
  stats.value.userCount = uRes.data?.length || 0
  stats.value.goodsCount = gRes.data?.total || 0
  recentOrders.value = oRes.data?.recentOrders || []
  const lowRes = await request.get('/admin/goods/lowStock?threshold=10')
  stats.value.lowStockCount = lowRes.data?.length || 0
}

onMounted(fetchData)
</script>

<style scoped>
.stat-title { color: #999; font-size: 14px; }
.stat-value { font-size: 28px; font-weight: bold; color: #409EFF; margin-top: 10px; }
</style>
