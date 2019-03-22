<template>
  <div style="background-color: #EBEBEB;min-height:900px">
    <div style="width:100%;background-color: #636363; overflow: hidden">
      <span class="demonstration" style="float:left;padding-top:10px;color:white;margin-left:1%">
        网站首页
      </span>
      <span class="demonstration" style="float:left;padding:5px;color:white;margin-left:2%;width:15%">
        <el-input placeholder="请输入" icon="search" v-model="searchCriteria" :on-icon-click="handleIconClick">
        </el-input>
      </span>
      <span class="demonstration" style="float:right;padding-top:10px;margin-right:1%">
        <el-dropdown trigger="click">
          <span class="el-dropdown-link" style="color:white">
            admin
            <i class="el-icon-caret-bottom el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item @click.native.prevent="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </span>
    </div>
    <div style="margin-top:5px">
      <el-row :gutter="10">
        <el-col :xs="4" :sm="4" :md="4" :lg="4">
          <div>
            <el-menu :default-active="$route.path" class="el-menu-vertical-demo" @open="handleopen" @close="handleclose" @select="handleselect" unique-opened router v-show="!collapsed">
              <template v-for="(item,index) in $router.options.routes" v-if="!item.hidden">
                <el-submenu :index="index+''" v-if="!item.leaf" :key="item">
                  <template slot="title">
                    <i :class="item.iconCls"></i>{{item.name}}</template>
                  <el-menu-item v-for="child in item.children" :index="child.path" :key="child.path" v-if="!child.hidden">{{child.name}}</el-menu-item>
                </el-submenu>
                <el-menu-item v-if="item.leaf&&item.children.length>0" :index="item.children[0].path" :key="item">
                  <i :class="item.iconCls"></i>{{item.children[0].name}}</el-menu-item>
              </template>
            </el-menu>
          </div>
        </el-col>
        <el-col :xs="20" :sm="20" :md="20" :lg="20">
          <div>
            <div>
              <!-- style="border: 1px solid #A6A6A6; border-radius:6px; padding:5px; margin:2px; background-color: white" -->
              <el-breadcrumb separator="/">
                <el-breadcrumb-item v-for="item in breadcrumbItems" :key="item">{{item}}</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
          </div>
          <div style="margin-top:10px">
            <transition name="fade" mode="out-in">
              <router-view></router-view>
            </transition>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
export default {
  data () {
    return {
      searchCriteria: '',
      collapsed: false,
      breadcrumbItems: []
    }
  },

  methods: {
    logout () {
      var _this = this;
      this.$confirm('确认退出吗?', '提示', {
      }).then(() => {
        sessionStorage.removeItem('user');
        sessionStorage.removeItem('token');
        _this.$router.push('/login');
      }).catch(() => {

      });
    },
    handleopen () {
      //console.log('handleopen');
    },
    handleclose () {
      //console.log('handleclose');
    },
    handleselect (key, keyPath) {
    },
    handleIconClick (ev) {
      console.log(ev);
    }
  },
}
</script>
