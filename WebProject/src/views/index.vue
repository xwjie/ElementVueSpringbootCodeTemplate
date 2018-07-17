
<template>
<el-container style="height:100%">
  <el-header style="height: 30px">
    <el-row  type="flex" justify="center" align="middle">
      <el-col :span="12"><div class="grid-content bg-purple">
        <span class="logo-title">晓风轻 Element Vue Springboot 代码模板</span>
        </div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple-light">
        <el-button  plain size="mini" type="primary" @click="handleStart">Hello World</el-button></div></el-col>
      <el-col :span="8">
        <div class="grid-content bg-purple-light text-right">
          <span v-if="user != null">
            <span class="user">{{user.nick}}</span><el-button  plain size="mini"  type="danger" @click="logout">退出</el-button>
          </span>
          <span v-else><el-button type="success" plain size="mini" @click="loginOpen">点击登陆</el-button></span>
          
          <el-dropdown  @command="switchLang">
            <el-button  plain size="mini"  type="primary">
              {{lang.startsWith('zh') ? '中': 'En' }}<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="zh">中</el-dropdown-item>
              <el-dropdown-item command="en">En</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

        </div></el-col>
    </el-row>
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
            <i class="el-icon-location" @click="isCollapse = !isCollapse"></i>
            <span> 基本组件</span>
          </template>
          <el-menu-item-group>
            <template slot="title">表格</template>
            <el-menu-item index="1-11" @click="addTab('最基本表格', 'ConfigTableSimple')">最基本表格</el-menu-item>
            <el-menu-item index="1-12" @click="addTab('前台数据过滤', 'ConfigTableSimpleFilter')">前台数据过滤</el-menu-item>
            <el-menu-item index="1-13" @click="addTab('后台数据过滤', 'ConfigTable')">后台数据过滤</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="树">
            <el-menu-item index="1-21" @click="addTab('简单树', 'SimpleTree')">简单树</el-menu-item>
            <el-menu-item index="1-22" @click="addTab('简单的带图标树', 'SimpleTreeWithIcon')">简单的带图标树</el-menu-item>
          </el-menu-item-group>
          <el-menu-item index="1-31" @click="addTab('上传组件', 'UploadFile')">上传组件</el-menu-item>
        </el-submenu>

        <el-submenu index="9">
          <template slot="title" collapse=false>
            <i class="el-icon-setting"></i>
            <span>系统配置</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="9-1" @click="addTab('系统配置', 'ConfigTable2')">系统配置</el-menu-item>
          </el-menu-item-group>
        </el-submenu>

        <el-submenu index="4">
          <template slot="title" collapse=false>
            <i class="el-icon-setting"></i>
            <span>日志分析</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="4-1" @click="addTab('上传日志', 'UploadFile')">上传日志</el-menu-item>
            <el-menu-item index="4-2" @click="addTab('上传记录', 'UploadHistory')">上传记录</el-menu-item>
          </el-menu-item-group>
        </el-submenu>

        <el-submenu index="0">
          <template slot="title" collapse=false>
            <i class="el-icon-setting"></i>
            <span>自定义组件</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="0-1" >分页组件</el-menu-item>
            <el-menu-item index="0-2" @click="addTab('选人组件', 'SelectUser')">选人组件</el-menu-item>
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
  <LoginDialog :show='showLogin'/>
</el-container>
</template>
<script>
import Vue from "vue";

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

      menus: [{}],

      //Tabs
      selectTabName: "ConfigAdd",
      tabs: {
        ConfigAdd: {
          title: "新建页面",
          name: "ConfigAdd",
          currentView: "ConfigAdd"
        }
      }
    };
  },
  computed: {
    lang: {
      get: function() {
        console.log("config", Vue.config);
        return Vue.config.lang;
      },
      set: function(v) {
        //do nothing
        this.$bus.emit("lang-change", v);
      }
    }
  },
  mounted() {
    this.$nextTick(function() {
      this.ajax.post("/app/user").then(result => {
        if (result.code == 0) {
          this.user = result.data;
        }
      });
    });
  },
  methods: {
    switchLang(command) {
      this.lang = command;
    },
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
      console.log("loginCancel");
      this.showLogin = false;
    },
    logout() {
      this.ajax.post("/app/logout").then(result => {
        if (result.code == 0) {
          this.user = null;
        } else {
          this.error(result.msg);
        }
      });
    },
    addTab(targetName, commentName) {
      // 如果已经存在
      if (this.tabs[commentName]) {
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

      // 选中第一个tab
      for (let key in this.tabs) {
        this.selectTabName = key;
        break;
      }
    }
  }
};
</script>

<style>
.text-right {
  padding-right: 0px;
  text-align: right;
}

.user {
  margin: 10px;
  font-size: 12px;
}

.header {
  position: relative;
  z-index: 1;
}

.el-header{
  margin: 10px 0 10px 0;  
}



.header .nav {
  height: 40px;
  color: #fff;
  text-align: center;
}
.banner {
  position: relative;
  z-index: 0;
  margin: 3px auto;
  height: 200px;
}

.el-container .el-main{
  padding: 0px 5px 5px 5px;
}

.index {
  padding-left: 10px;
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

.logo-title{
  font-size: 18px;
  font-weight: bold;
}
</style>