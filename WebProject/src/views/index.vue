<style scoped>
.index {
  padding-left: 20px;
}
</style>
<template>
    <div class="index">
        <h1>晓风轻 Element Vue Springboot 代码模板
            <el-button type="primary" @click="handleStart">Hello World</el-button>
        </h1>

        <h2 v-if="user != null">当前用户：{{user.nick}}
            <el-button type="danger" @click="logout">退出</el-button>
        </h2>

        <hr/>
        <ConfigAdd/>
        <el-input
            placeholder="请输入关键字过滤"
            prefix-icon="el-icon-search"
            v-model="keyword">
        </el-input>
        <p/>
        <ConfigTable :keyword='keyword'/>

        <LoginDialog :show='showLogin' />
    </div>
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
      keyword:''
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
    }
  }
};
</script>