import Customer from '../page/customer/Customer'
import login from '../page/login'
import CustomerInfo from '../page/customer/CustomerInfo'

let routes = [
  {
    path: '/login',
    component: login,
    name: '',
    // hidden  这个可以设置 菜单上不显示 login
    hidden: true
  },
  {
    path: '/customer',
    component: Customer,
    name: '客户管理',
    iconCls: 'el-icon-message',//图标样式class
    children: [
      { path: '/CustomerInfo', component: CustomerInfo, name: '客户基本信息' }
      // {path: '/'}
    ]
  }
]

export default routes;