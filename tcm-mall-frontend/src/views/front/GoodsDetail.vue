<template>
  <div class="goods-detail-page" v-if="goods">
    <el-row :gutter="40">
      <el-col :span="10">
        <el-image :src="goods.cover" fit="cover" style="width: 100%; height: 400px; border-radius: 8px;" />
      </el-col>
      <el-col :span="14">
        <h1 class="goods-title">{{ goods.name }}</h1>
        <div class="goods-price">￥{{ goods.price }}</div>
        <div class="goods-meta">
          <span>分类：{{ goods.categoryName }}</span>
          <span>销量：{{ goods.sales }}</span>
          <span>库存：{{ goods.stock }}</span>
        </div>
        <div class="goods-action">
          <el-input-number v-model="quantity" :min="1" :max="goods.stock" />
          <el-button type="primary" size="large" @click="addToCart" style="margin-left: 20px;">加入购物车</el-button>
        </div>
      </el-col>
    </el-row>

    <el-card style="margin-top: 30px;">
      <template #header><span>商品详情</span></template>
      <div class="detail-section">
        <h3>商品描述</h3>
        <p>{{ goods.description }}</p>
      </div>
      <div class="detail-section">
        <h3>中医功效</h3>
        <p>{{ goods.effect }}</p>
      </div>
      <div class="detail-section">
        <h3>用法用量</h3>
        <p>{{ goods.usage }}</p>
      </div>
      <div class="detail-section">
        <h3>禁忌</h3>
        <p>{{ goods.contraindication }}</p>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { goodsDetail } from '../../api/goods'
import { addCart } from '../../api/cart'
import { ElMessage } from 'element-plus'

const route = useRoute()
const goods = ref(null)
const quantity = ref(1)

const fetchGoods = async () => {
  const res = await goodsDetail(route.params.id)
  goods.value = res.data
}

const addToCart = async () => {
  await addCart({ goodsId: goods.value.id, quantity: quantity.value })
  ElMessage.success('加入购物车成功')
}

onMounted(fetchGoods)
</script>

<style scoped>
.goods-detail-page { padding: 20px 0; }
.goods-title { font-size: 24px; font-weight: bold; margin-bottom: 20px; }
.goods-price { color: #f56c6c; font-size: 32px; font-weight: bold; margin-bottom: 20px; }
.goods-meta { color: #666; margin-bottom: 30px; }
.goods-meta span { margin-right: 30px; }
.goods-action { display: flex; align-items: center; }
.detail-section { margin-bottom: 20px; }
.detail-section h3 { color: #409EFF; margin-bottom: 10px; }
.detail-section p { line-height: 1.8; color: #555; }
</style>
