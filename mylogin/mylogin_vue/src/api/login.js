import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  return request({
    url: '/login',
    method: 'post',
    data: { username, password, code, uuid }
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'delete'
  })
}
