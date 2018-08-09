/**
 * 表格组件范例
 * 
 * @author xiaowenjie https://github.com/xwjie
 */
<template>
  <div>
    <el-input
        placeholder="请输入关键字过滤"
        prefix-icon="el-icon-search"
        v-model="keyword">
    </el-input>
    <p/>
    <el-table
      :data="datas"
      size = "mini"
      border
      stripe
      @sort-change="sortChange"
      style="width: 100%">
      <el-table-column
        fixed
        sortable
        prop="id"
        label="ID"        
        width="70">
      </el-table-column>
      <el-table-column
        prop="name"
        sortable
        label="用户名"
        width="200">
      </el-table-column>
      <el-table-column
        sortable
        prop="nick"
        label="昵称"
        width="200">
      </el-table-column>
      <el-table-column
        prop="roles"
        label="角色">
        <template scope="scope">
          <el-tag  v-for="(item,index) in scope.row.roles"
          :type="item.name === 'admin' ? 'primary' : 'success'"
          disable-transitions>{{item.name}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        sortable
        prop="createTime"
        label="创建时间"
        :formatter="dateFormat"
        width="150">
      </el-table-column>
      <el-table-column
        sortable
        prop="updateTime"
        label="最后修改时间"
        :formatter="dateFormat"
        width="150">
      </el-table-column>        
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="showPasswordDlg(scope.row)" type="text" size="small"  icon="el-icon-edit"></el-button>
          <el-button @click="deleteConfig(scope.row)" type="text" size="small"  icon="el-icon-delete"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination ref="page1" url="/user/list" :keyword="keyword" :sort="sort" v-model="datas"/>

    <!--修改密码对话框-->
    <el-dialog title="修改密码" :visible.sync="passwordDlg.show" @close="hidePasswordDlg">
      <el-form :model="passwordDlg.form">
        <el-form-item label="密码" label-width="100px">
          <el-input v-model="passwordDlg.form.password" type="password" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="hidePasswordDlg">取 消</el-button>
        <el-button type="primary" @click="doModifyPwd">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  methods: {
    handleClick(row) {
      console.log(row);
    },
    sortChange({column, prop, order}){
      this.sort = {prop, order};
    },
    dateFormat: function(row, column) {
      var date = row[column.property];
      if (date == undefined) {
        return "";
      }
      return new Date(date).format("yyyy/MM/dd hh:mm");
    },
    deleteConfig(row) {
      // this.ajax.post("/config/delete?id=" + row.id).then(result => {
      //   if (result.code == 0) {
      //     this.info("delete success");
      //     this.refreshConfig();
      //   } else {
      //     this.error(result.msg);
      //   }
      // });
    },
    // 显示修改密码 
    showPasswordDlg(row){
      this.passwordDlg.row = row;
      this.passwordDlg.show = true;
    },
    // 隐藏
    hidePasswordDlg(){
      this.passwordDlg.row = null;
      this.passwordDlg.show = false;
      this.passwordDlg.form.password = "";
    },
    // 修改密码
    doModifyPwd(){
      if(this.passwordDlg.form.password == ""){
        this.error("密码不能为空");
        return;
      }

      this.ajax.postForm("/user/updatepwd" ,{
        id: this.passwordDlg.row.id,
        password: this.passwordDlg.form.password
      }).then(result => {
        if (result.code == 0) {
          this.info("Password Update Success.");
          this.hidePasswordDlg();
        } else {
          this.error(result.msg);
        }
      });
    },
    // 刷新表格数据
    refreshConfig(){
      this.$refs.page1.reload();
    }
  },
  data() {
    return {
      keyword:"",
      datas: [],
      sort: {},
      passwordDlg:{
        row: null,
        show: false,
        form:{
          password:""
        }
      }
    };
  }
};
</script>

<style scoped>

</style>
