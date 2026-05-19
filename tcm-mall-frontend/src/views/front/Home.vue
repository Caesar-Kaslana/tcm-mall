<template>
  <div class="home-page">
    <el-carousel height="400px">
      <el-carousel-item v-for="item in banners" :key="item.id">
        <img :src="item.image" :alt="item.title" style="width: 100%; height: 100%; object-fit: cover;" @click="$router.push(item.linkUrl)" />
      </el-carousel-item>
    </el-carousel>

    <div class="section">
      <h2 class="section-title">商品分类</h2>
      <el-row :gutter="20">
        <el-col :span="6" v-for="cat in categories" :key="cat.id">
          <el-card class="category-card" @click="goGoods(cat.id)">
            <div class="cat-name">{{ cat.name }}</div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div class="section">
      <h2 class="section-title">热销商品</h2>
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
    </div>

    <div class="section">
      <h2 class="section-title">系统公告</h2>
      <el-timeline>
        <el-timeline-item v-for="notice in notices" :key="notice.id" :timestamp="notice.createTime">
          {{ notice.title }}
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import request from '../../utils/request'

const router = useRouter()
const banners = ref([])
const categories = ref([])
const goodsList = ref([])
const notices = ref([])

const fetchData = async () => {
  const [bRes, cRes, gRes, nRes] = await Promise.all([
    request.get('/banner/list'),
    request.get('/goods/category/list'),
    request.get('/goods/list?pageSize=8'),
    request.get('/notice/list')
  ])
  banners.value = bRes.data || []
  categories.value = cRes.data?.filter(c => c.parentId === 0) || []
  goodsList.value = gRes.data?.list || []
  notices.value = nRes.data || []
}

const goGoods = (categoryId) => router.push({ path: '/goods', query: { categoryId } })
const goDetail = (id) => router.push(`/goods/${id}`)

onMounted(fetchData)
</script>

<style scoped>
.home-page { padding: 20px 0; }
.section { margin-top: 40px; }
.section-title { font-size: 22px; font-weight: bold; margin-bottom: 20px; color: #333; border-left: 4px solid #409EFF; padding-left: 10px; }
.category-card { text-align: center; padding: 20px; cursor: pointer; transition: transform 0.3s; }
.category-card:hover { transform: translateY(-5px); }
.cat-name { font-size: 16px; font-weight: bold; }
.goods-card { cursor: pointer; margin-bottom: 20px; transition: transform 0.3s; }
.goods-card:hover { transform: translateY(-5px); }
.goods-image { width: 100%; height: 200px; object-fit: cover; }
.goods-info { padding: 14px; }
.goods-name { font-size: 14px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.goods-price { color: #f56c6c; font-size: 18px; font-weight: bold; margin-top: 8px; }
.goods-sales { color: #999; font-size: 12px; margin-top: 5px; }
</style>
