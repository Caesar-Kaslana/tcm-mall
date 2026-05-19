<template>
  <div class="goods-list-page">
    <el-row :gutter="20">
      <el-col :span="4">
        <el-card>
          <template #header>商品分类</template>
          <el-menu :default-active="String(query.categoryId)" @select="handleCategoryChange">
            <el-menu-item index="">全部商品</el-menu-item>
            <el-menu-item v-for="cat in categories" :key="cat.id" :index="String(cat.id)">{{ cat.name }}</el-menu-item>
          </el-menu>
        </el-card>
      </el-col>
      <el-col :span="20">
        <el-input v-model="query.keyword" placeholder="搜索商品" style="width: 300px; margin-bottom: 20px;" @keyup.enter="handleSearch">
          <template #append><el-button @click="handleSearch"><el-icon><search /></el-icon></el-button></template>
        </el-input>
        <el-row :gutter="20">
          <el-col :span="6" v-for="item in goodsList" :key="item.id">
            <el-card class="goods-card" :body-style="{ padding: '0px' }" @click="goDetail(item.id)">
              <img :src="item.cover" class="goods-image" />
              <div class="goods-info">
                <div class="goods-name">{{ item.name }}</div>
                <div class="goods-price">￥{{ item.price }}</div>
                <div class="goods-sales">销量 {{ item.sales }}</div>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-pagination background layout="prev, pager, next" :total="total" :page-size="query.pageSize" v-model:current-page="query.pageNum" @current-change="handlePageChange" style="margin-top: 20px; justify-content: center;" />
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { goodsList as apiGoodsList } from '../../api/goods'
import request from '../../utils/request'

const route = useRoute()
const router = useRouter()
const categories = ref([])
const goodsList = ref([])
const total = ref(0)

const query = reactive({
  categoryId: route.query.categoryId || '',
  keyword: '',
  pageNum: 1,
  pageSize: 12
})

const fetchCategories = async () => {
  const res = await request.get('/goods/category/list')
  categories.value = res.data?.filter(c => c.parentId === 0) || []
}

const fetchGoods = async () => {
  const res = await apiGoodsList({ ...query, categoryId: query.categoryId || undefined })
  goodsList.value = res.data?.list || []
  total.value = res.data?.total || 0
}

const handleSearch = () => {
  query.pageNum = 1
  fetchGoods()
}

const handleCategoryChange = (val) => {
  query.categoryId = val
  query.pageNum = 1
  fetchGoods()
}

const handlePageChange = (page) => {
  query.pageNum = page
  fetchGoods()
}

const goDetail = (id) => router.push(`/goods/${id}`)

watch(() => route.query.categoryId, (val) => {
  query.categoryId = val || ''
  fetchGoods()
})

onMounted(() => { fetchCategories(); fetchGoods() })
</script>

<style scoped>
.goods-list-page { padding: 20px 0; }
.goods-card { cursor: pointer; margin-bottom: 20px; transition: transform 0.3s; }
.goods-card:hover { transform: translateY(-5px); }
.goods-image { width: 100%; height: 200px; object-fit: cover; }
.goods-info { padding: 14px; }
.goods-name { font-size: 14px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.goods-price { color: #f56c6c; font-size: 18px; font-weight: bold; margin-top: 8px; }
.goods-sales { color: #999; font-size: 12px; margin-top: 5px; }
</style>
