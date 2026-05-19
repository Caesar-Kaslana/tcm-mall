import request from '../utils/request'

export const addressList = () => request.get('/address/list')
export const addAddress = (data) => request.post('/address/add', data)
export const updateAddress = (data) => request.put('/address/update', data)
export const deleteAddress = (id) => request.delete(`/address/delete/${id}`)
export const setDefaultAddress = (id) => request.put(`/address/default/${id}`)
