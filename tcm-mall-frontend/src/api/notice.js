import request from '../utils/request'

export const noticeList = () => request.get('/notice/list')
export const adminNoticeList = () => request.get('/admin/notice/list')
export const addNotice = (data) => request.post('/admin/notice/add', data)
export const updateNotice = (data) => request.put('/admin/notice/update', data)
export const deleteNotice = (id) => request.delete(`/admin/notice/delete/${id}`)
