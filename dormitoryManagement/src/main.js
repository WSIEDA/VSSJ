import Vue from 'vue'
import App from './App'
import routes from './router/index'
// import axios from 'axios';
// 配置全局的访问路径
// axios.defaults.baseURL = "/api";


// 第一种：设置post 请求 
// import qs from 'qs';
// Vue.prototype.$qs = qs;
// let postData = this.$qs.stringify({name: 'king', pwd: '123'});   传递参数的方式

// 第二种：设置post请求方式  这个种方式对IE浏览器不兼容
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
// let data = new URLSearchParams();
// data.append('name', 'king');


// 安装ElementUI
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import VueRouter from 'vue-router'
import Vuex from 'vuex'

Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)

Vue.config.productionTip = false

// 加载路由的对象
const router = new VueRouter({
  routes
})

// 路由限定首页进入是登录页，并且有用户信息
router.beforeEach((to, from, next) => {
  if (to.path == '/login') {
    sessionStorage.removeItem('user');
  }
  let user = JSON.parse(sessionStorage.getItem('user'));
  if (!user && to.path != '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
