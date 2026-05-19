import request from '../utils/request'

export const bannerList = () => request.get('/banner/list')
export const adminBannerList = () => request.get('/admin/banner/list')
export const addBanner = (data) => request.post('/admin/banner/add', data)
export const updateBanner = (data) => request.put('/admin/banner/update', data)
export const deleteBanner = (id) => request.delete(`/admin/banner/delete/${id}`)
