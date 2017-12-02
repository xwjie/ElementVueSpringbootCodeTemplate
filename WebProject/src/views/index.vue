<style scoped>
.index {
  padding-left: 20px;
}

/*
* {
  outline: 1px solid;
}
*/

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  height: 100%;
}
</style>
<template>
<el-container style="height:100%">
  <el-header>
    <h1>晓风轻 Element Vue Springboot 代码模板
      <el-button type="primary" @click="handleStart">Hello World</el-button>
    </h1>
      <h2 v-if="user != null">当前用户：{{user.nick}}
        <el-button type="danger" @click="logout">退出</el-button>
    </h2>
  </el-header>
  <el-container>
    <el-aside style="width:auto;">
      <!--
      <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
        <el-radio-button :label="false" v-show="isCollapse">展开</el-radio-button>
        <el-radio-button :label="true"v-show="!isCollapse">收起</el-radio-button>
      </el-radio-group>
      -->
      <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        :collapse="isCollapse"
        >
        <el-submenu index="1">

          <template slot="title" collapse=false>
            <i class="el-icon-location"></i>
            <span>基本组件</span>
          </template>
          <el-menu-item-group>
            <template slot="title">表格</template>
            <el-menu-item index="1-1" @click="addTab('最基本表格', 'ConfigTableSimple')">最基本表格</el-menu-item>
            <el-menu-item index="1-2" @click="addTab('浏览器数据过滤', 'ConfigTableSimpleFilter')">浏览器数据过滤</el-menu-item>
            <el-menu-item index="1-2" @click="addTab('后台数据过滤', 'ConfigTable')">后台数据过滤</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="1-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="1-4">
            <template slot="title">选项4</template>
            <el-menu-item index="1-4-1">选项1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-menu-item index="2">
          <i class="el-icon-menu"></i>
          <span slot="title">导航二</span>
        </el-menu-item>
        <el-submenu index="3">
        <el-menu-item>
          <i class="el-icon-setting"></i>
          <span slot="title">自定义组件</span>
        </el-menu-item>
        <el-menu-item-group>
          <el-menu-item index="1-2" >分页组件</el-menu-item>
        </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-main>      
      <el-tabs v-model="selectTabName" type="card" closable @tab-remove="removeTab">
        <el-tab-pane
          v-for="item in tabs"
          :key="item.name"
          :label="item.title"
          :name="item.name"
        >
          <component v-bind:is="item.currentView"></component>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
  <LoginDialog :show='showLogin' />
</el-container>
</template>
<script>

export default {
  created() {
    // 载入config数据
    //this.$store.dispatch("config/reload");
    this.$bus.on("login-open", this.loginOpen);
    this.$bus.on("login-success", this.loginSuccess);
    this.$bus.on("login-cancel", this.loginCancel);
  },
  data() {
    return {
      showLogin: false,
      user: null,
      keyword: "",
      isCollapse: false,

      menus: [
        {}
      ],

      //Tabs
      selectTabName: "ConfigAdd",
      tabs: {
        ConfigAdd : {
          title: "新建页面",
          name: "ConfigAdd",
          currentView: "ConfigAdd"
        }
      },
    };
  },
  methods: {
    handleStart() {
      this.info("工作正常");
    },
    loginOpen() {
      this.showLogin = true;
    },
    loginSuccess(user) {
      console.log("success", user);

      this.showLogin = false;
      this.user = user;
    },
    loginCancel() {
      this.showLogin = false;
    },
    logout() {
      this.ajax.post("/app/logout").then(result => {
        if (result.code == 0) {
          self.user = null;
        } else {
          self.error(result.msg);
        }
      });
    },
    addTab(targetName , commentName) {
      // 如果已经存在
      if(this.tabs[commentName] ){
        this.selectTabName = commentName;
        return;
      }

      // add table
      this.$set(this.tabs, commentName, {
        title: targetName,
        name: commentName,
        currentView: commentName 
      });

      this.selectTabName = commentName;
    },
    removeTab(targetName) {
      this.$delete(this.tabs, targetName);
    }
  }
};
</script>