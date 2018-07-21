<template>

<el-dialog title="请登录" :visible.sync="show2" @close="cancelLogin">
  <el-form :model="form">
    <el-form-item label="用户名" :label-width="formLabelWidth">
      <el-input v-model="form.username" auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item label="密码" :label-width="formLabelWidth">
      <el-input v-model="form.password" type="password" auto-complete="off"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="cancelLogin">取 消</el-button>
    <el-button type="primary" @click="doLogin">确 定</el-button>
  </div>
  <h3>管理员：xwjie，普通用户：user1~user10，密码默认都是 123456</h3>
</el-dialog>
 
</template>

<script>
export default {
  props: ["show"],
  data() {
    return {
      form: {
        username: "xwjie",
        password: "123456"
      },
      formLabelWidth: "120px"
    };
  },
  computed: {
    show2: {
      get: function() {
        return this.show;
      },
      set: function(v) {
        //do nothing
      }
    }
  },
  methods: {
    doLogin() {
      console.log("login: ", this.form);

      let self = this;
      //
      this.ajax.postForm("/app/login", this.form).then(result => {
        if (result.code == 0) {
          // 发送登录成功消息
          self.$bus.emit("login-success", result.data);
        } else {
          self.error(result.msg);
        }
      });
    },
    cancelLogin() {
      // 发送登录取消消息
      this.$bus.emit("login-cancel");
    }
  }
};
</script>