import request from './request'

// 用户相关
export const login = (data) => request.post('/user/login', data)
export const register = (data) => request.post('/user/register', data)
export const getUserInfo = () => request.get('/user/info')

// 陪玩师相关
export const getMasterList = () => request.get('/master/list')
export const getMasterDetail = (userId) => request.get(`/master/detail/${userId}`)

// 订单相关
export const createOrder = (data) => request.post('/order/create', data)
export const acceptOrder = (orderId) => request.post(`/order/accept/${orderId}`)
export const completeOrder = (orderId) => request.post(`/order/complete/${orderId}`)
export const getPlayerOrders = () => request.get('/order/player/orders')
export const getMasterOrders = () => request.get('/order/master/orders')

// 支付相关
export const mockPay = (data) => request.post('/pay/mock', data)

// 管理员相关
export const getPendingMasters = () => request.get('/admin/pending-masters')
export const approveMaster = (userId, approved) => request.post(`/admin/approve-master/${userId}?approved=${approved}`)
export const getAllOrders = () => request.get('/admin/all-orders')
export const getStatistics = () => request.get('/admin/statistics')
