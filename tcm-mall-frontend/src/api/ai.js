import request from '../utils/request'

export const sendMessage = (message, history) => {
  return request.post('/ai/chat', { message, history })
}
