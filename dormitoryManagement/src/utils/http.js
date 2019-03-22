import axios from 'axios'
import { Util } from './common'

// axios.defaults.headers['Authorization'] = sessionStorage.getItem('Authorization');

const http = axios.create({
  // 这个是设置基础的url 
  baseURL: "/api"
});

/**
 * 响应后进行统一拦截
 * 假如后台返回的数据对象为：
 *    {
        code: string;
        content: any;
        message: string;
      }
 */
http.interceptors.response.use(
  res => {
    let _data = res.data;
     return Promise.resolve(_data);
  }
);

/**
 * 请求前进行拦截
 */
http.interceptors.request.use(
  req => {
    // 先判断本地是否存储了token
    console.log(sessionStorage.getItem('Authorization'));
    if (sessionStorage.getItem('Authorization')) {
      req.url = appendToken(req.url);
    }
    return req;
  },
  error => {
    Promise.reject(error);
  }
);
/**
 * 拼接Token
 * @param { String } url   请求接口url
 */
function appendToken (url) {
  if (!url || !Util.isString) return url;
  if (url.indexOf('?') > -1) {
    url = `${url}&token=${sessionStorage.getItem('Authorization')}`;
  }
  else {
    url = `${url}?token=${sessionStorage.getItem('Authorization')}`;
  }
  return url;
}

export { http };