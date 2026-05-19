<template>
  <div class="cart-page">
    <h2>购物车</h2>
    <el-table :data="localCartList" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="商品" width="300">
        <template #default="{ row }">
          <div class="cart-goods">
            <el-image :src="row.goodsCover" style="width: 80px; height: 80px;" fit="cover" />
            <span style="margin-left: 10px;">{{ row.goodsName }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="goodsPrice" label="单价" width="150">
        <template #default="{ row }">￥{{ row.goodsPrice }}</template>
      </el-table-column>
      <el-table-column label="数量" width="200">
        <template #default="{ row }">
          <el-input-number v-model="row.quantity" :min="1" @change="(val) => updateQuantity(row.id, val)" />
        </template>
      </el-table-column>
      <el-table-column label="小计" width="150">
        <template #default="{ row }">￥{{ (row.goodsPrice * row.quantity).toFixed(2) }}</template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="cart-footer">
      <div class="total">合计：<span class="total-price">￥{{ totalPrice.toFixed(2) }}</span></div>
      <el-button type="primary" size="large" @click="goCheckout" :disabled="selected.length === 0">去结算</el-button>
    </div>

    <el-dialog v-model="addressDialogVisible" title="确认订单" width="500px">
      <el-form label-width="80px">
        <el-form-item label="收货地址">
          <el-select v-model="selectedAddressId" placeholder="选择收货地址" style="width: 100%">
            <el-option
              v-for="addr in addresses"
              :key="addr.id"
              :label="`${addr.receiverName} ${addr.receiverPhone} ${addr.province}${addr.city}${addr.district}${addr.detailAddress}`"
              :value="addr.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="remark" type="textarea" :rows="2" placeholder="请输入订单备注（可选）" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addressDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitOrder">提交订单</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { cartList, updateCart, deleteCart } from '../../api/cart'
import { createOrder } from '../../api/order'
import { addressList } from '../../api/address'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
// 本地变量重命名，避免和导入的API函数重名
const localCartList = ref([])
const selected = ref([])
const addressDialogVisible = ref(false)
const addresses = ref([])
const selectedAddressId = ref(null)
const remark = ref('')

const totalPrice = computed(() => {
  return selected.value.reduce((sum, item) => sum + item.goodsPrice * item.quantity, 0)
})

const fetchCart = async () => {
  const res = await cartList()
  localCartList.value = res.data || []
}

const handleSelectionChange = (val) => {
  selected.value = val
}

const updateQuantity = async (id, quantity) => {
  await updateCart(id, { quantity })
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确认删除该商品？', '提示')
  await deleteCart(id)
  ElMessage.success('删除成功')
  fetchCart()
}

const goCheckout = async () => {
  if (selected.value.length === 0) {
    ElMessage.warning('请选择要结算的商品')
    return
  }
  const res = await addressList()
  addresses.value = res.data || []
  const defaultAddr = addresses.value.find(a => a.isDefault === 1)
  selectedAddressId.value = defaultAddr ? defaultAddr.id : (addresses.value[0]?.id || null)
  if (addresses.value.length === 0) {
    ElMessage.warning('请先添加收货地址')
    router.push('/user')
    return
  }
  addressDialogVisible.value = true
}

const submitOrder = async () => {
  if (!selectedAddressId.value) {
    ElMessage.warning('请选择收货地址')
    return
  }
  const cartIds = selected.value.map(item => item.id)
  try {
    await createOrder({
      addressId: selectedAddressId.value,
      remark: remark.value,
      cartIds
    })
    ElMessage.success('订单创建成功')
    addressDialogVisible.value = false
    selected.value = []
    remark.value = ''
    fetchCart()
    router.push('/order')
  } catch (e) {
    // 错误已在请求拦截器中提示
  }
}

onMounted(fetchCart)
</script>

<style scoped>
.cart-page { padding: 20px 0; }
.cart-goods { display: flex; align-items: center; }
.cart-footer { display: flex; justify-content: flex-end; align-items: center; margin-top: 20px; gap: 20px; }
.total { font-size: 16px; }
.total-price { color: #f56c6c; font-size: 24px; font-weight: bold; }
</style>