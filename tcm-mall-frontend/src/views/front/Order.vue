<template>
  <div class="order-page">
    <h2>我的订单</h2>
    <el-tabs v-model="activeTab">
      <el-tab-pane label="全部" name="all"></el-tab-pane>
      <el-tab-pane label="待付款" name="0"></el-tab-pane>
      <el-tab-pane label="待发货" name="1"></el-tab-pane>
      <el-tab-pane label="待收货" name="2"></el-tab-pane>
      <el-tab-pane label="已完成" name="3"></el-tab-pane>
    </el-tabs>
    <el-card v-for="order in filteredOrders" :key="order.id" style="margin-bottom: 15px;">
      <div class="order-header">
        <span>订单号：{{ order.orderNo }}</span>
        <span>{{ formatStatus(order.status) }}</span>
      </div>
      <el-divider />
      <div class="order-body">
        <div class="order-info">
          <p>收货人：{{ order.receiverName }} {{ order.receiverPhone }}</p>
          <p>地址：{{ order.receiverAddress }}</p>
          <p>总金额：￥{{ order.totalAmount }}</p>
        </div>
      </div>
      <div class="order-actions">
        <el-button v-if="order.status === 0" type="primary" size="small" @click="pay(order.orderNo)">立即支付</el-button>
        <el-button v-if="order.status === 2" type="primary" size="small" @click="receive(order.id)">确认收货</el-button>
        <el-button v-if="order.status === 0" size="small" @click="cancel(order.id)">取消订单</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { orderList, payOrder, receiveOrder, cancelOrder } from '../../api/order'
import { ElMessage, ElMessageBox } from 'element-plus'

const orders = ref([])
const activeTab = ref('all')

const filteredOrders = computed(() => {
  if (activeTab.value === 'all') return orders.value
  return orders.value.filter(o => String(o.status) === activeTab.value)
})

const formatStatus = (status) => {
  const map = { 0: '待付款', 1: '待发货', 2: '待收货', 3: '已完成', 4: '已取消' }
  return map[status] || '未知'
}

const fetchOrders = async () => {
  const res = await orderList()
  orders.value = res.data || []
}

const pay = async (orderNo) => {
  await payOrder({ orderNo })
  ElMessage.success('支付成功')
  fetchOrders()
}

const receive = async (id) => {
  await ElMessageBox.confirm('确认收货？', '提示')
  await receiveOrder(id)
  ElMessage.success('确认收货成功')
  fetchOrders()
}

const cancel = async (id) => {
  await ElMessageBox.confirm('确认取消订单？', '提示')
  await cancelOrder(id)
  ElMessage.success('取消成功')
  fetchOrders()
}

onMounted(fetchOrders)
</script>

<style scoped>
.order-page { padding: 20px 0; }
.order-header { display: flex; justify-content: space-between; color: #666; }
.order-info p { margin: 5px 0; }
.order-actions { text-align: right; margin-top: 10px; }
</style>
