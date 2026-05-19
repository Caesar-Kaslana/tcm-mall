import request from '../utils/request'

export const purchaseList = (params) => request.get('/admin/purchase/list', { params })
export const addPurchase = (data) => request.post('/admin/purchase/add', data)
