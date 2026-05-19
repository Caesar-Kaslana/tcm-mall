import request from '../utils/request'

export const goodsList = (params) => request.get('/goods/list', { params })
export const goodsDetail = (id) => request.get(`/goods/detail/${id}`)
export const adminGoodsList = (params) => request.get('/admin/goods/list', { params })
export const addGoods = (data) => request.post('/admin/goods/add', data)
export const updateGoods = (data) => request.put('/admin/goods/update', data)
export const deleteGoods = (id) => request.delete(`/admin/goods/delete/${id}`)
export const updateGoodsStatus = (id, status) => request.put(`/admin/goods/status/${id}?status=${status}`)
export const lowStock = (params) => request.get('/admin/goods/lowStock', { params })
