import request from '../utils/request'

export const login = (data) => request.post('/user/login', data)
export const register = (data) => request.post('/user/register', data)
export const logout = () => request.post('/user/logout')
export const getUserInfo = () => request.get('/user/info')
export const updateUser = (data) => request.put('/user/update', data)
export const updatePassword = (data) => request.put('/user/password', data)
export const userList = (params) => request.get('/admin/user/list', { params })
export const assignRole = (data) => request.post('/admin/user/assignRole', data)
