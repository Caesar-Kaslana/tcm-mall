import request from '../utils/request'

export const createOrder = (data) => request.post('/order/create', data)
export const orderList = () => request.get('/order/list')
export const orderDetail = (id) => request.get(`/order/detail/${id}`)
export const payOrder = (data) => request.post('/order/pay', data)
export const receiveOrder = (id) => request.put(`/order/receive/${id}`)
export const cancelOrder = (id) => request.put(`/order/cancel/${id}`)
export const adminOrderList = (params) => request.get('/admin/order/list', { params })
export const shipOrder = (id) => request.put(`/admin/order/ship/${id}`)
export const orderStatistics = () => request.get('/admin/order/statistics')
