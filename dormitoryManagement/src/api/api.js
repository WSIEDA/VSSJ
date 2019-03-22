import axios from 'axios';
import { http } from '../utils/http';

let config = {
  headers: {
    Authorization: sessionStorage.getItem('Authorization')
  }
}

// 设置基础的路径
let base = '/dormitoryManagementSystem';

/**
 * GET请求的形式
 * @param {*} data 
 */
export function login (data) {
  return http.get('url', {
    params: {
      data: data
    }
  });
}
/**
 * POST形式的请求
 * @param {*} data 
 */
export function loginTest (data) {
  return http.post('/dormitoryManagementSystem/doLogin.action', data, config);
}

export function api_test (data) {
  return http.post('/dormitoryManagementSystem/doLogin.action', data, config);
}

// 测试登录的实现
// export const loginTest = params => { return axios.get(`${base}/doLogin.action`, { params: params }); }

// export const loginTest = params => { return http.get(`${base}/doLogin2.action`, { params: params }); }

// export const requestLogin = params => { return axios.post(`${base}/login`, params).then(res => res.data); };

// export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params }); };

// export const getUserListPage = params => { return axios.get(`${base}/user/listpage`, { params: params }); };

// export const removeUser = params => { return axios.get(`${base}/user/remove`, { params: params }); };

// export const batchRemoveUser = params => { return axios.get(`${base}/user/batchremove`, { params: params }); };

// export const editUser = params => { return axios.get(`${base}/user/edit`, { params: params }); };

// export const addUser = params => { return axios.get(`${base}/user/add`, { params: params }); };