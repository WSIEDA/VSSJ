
<template>
  <!--背景图-->
  <div class="note" :style="note">
    <!--login框，表单+tab标签页的组合-->
    <div class="loginFrame">
      <el-form ref="AccountForm" :model="account" rules="rules" label-position="left" label-width="0px" class="demo-ruleForm login-container">
        <!--tab标签-->
        <el-tabs v-model="activeName" @tab-click="handleClick" class="users">
          <el-tab-pane label="学生" name="students"></el-tab-pane>
          <el-tab-pane label="教师" name="teacher"></el-tab-pane>
          <el-tab-pane label="教务老师" name="eduTeacher"></el-tab-pane>
        </el-tabs>
        <el-form-item prop="username">
          <el-input type="text" v-model="username" auto-complete="off" placeholder="请输入您的账号"></el-input>
        </el-form-item>
        <!-- {{'1'|genderTypeId}} -->
        <el-form-item prop="password">
          <el-input type="password" v-model="password" auto-complete="off" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>

        <el-form-item style="width:100%;">
          <el-button type="primary" style="width:100%;" @click.native.prevent="Submit">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>

</template>
 
<script>
import { formatGenderType } from '../formatters/dormitoryManagment/index';
import axios from 'axios';
import {
  loginTest,
  api_test
} from '../api/api';
import { api_findToken } from '../api/base';
export default {
  name: '登录',
  data () {
    return {
      account: null,
      logining: false,
      note: {
        position: "absolute",
        top: "0px",
        left: "0px",
        width: "100%",
        height: "100%",
        backgroundImage: "url(" + require("../assets/login2.jpg") + ")",
        backgroundSize: "100% 100%",
        backgroundRepeat: "no-repeat",
      },
      account: {
        username: '',
        password: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          //{ validator: validaePass }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          //{ validator: validaePass2 }
        ]
      },
      checked: false
    };
  },
  filters: {
    genderTypeId (v) {
      return formatGenderType(v);
    }
  },
  methods: {
    // 获取tab-click的节点
    handleClick (tab, event) {
      console.log(event)
      console.log(event.target.getAttribute('id'))
    },
    Submit () {
      // 登录之后，将Token存储在本地，以后所有的请求都拼接Token
      // let data = { username: this.account.username, password: this.account.password };
      // api_findToken().then(res => {
      //   if (res) {
      //     sessionStorage.removeItem('Authorization');
      //     sessionStorage.setItem('Authorization', res.result);
      //     loginTest(data).then(res2 => {
      //       sessionStorage.setItem('user', JSON.stringify(data));
      //       api_test(null).then(res3 => {

      //       });
      //       // this.$router.push({ path: '/customer' });
      //     });
      //   }
      // });

      let data = { username: this.account.username, password: this.account.password };
          loginTest(data).then(res => {
            console.log(res.result);
            sessionStorage.setItem('user', JSON.stringify(data));
            sessionStorage.setItem('Authorization', res.result);
            // this.$router.push({ path: '/customer' });
          });
    }
  },
  created () {
    // 获取Token
    // api_findToken().then(res => {
    //   if (res) {
    //     sessionStorage.setItem('Authorization', res.result.result);
    //   }
    // });
  }
}
</script>
 
<style>
.login-container {
  box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);
  -webkit-border-radius: 5px;
  border-radius: 5px;
  -moz-border-radius: 5px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
</style>
