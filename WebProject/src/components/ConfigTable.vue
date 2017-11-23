/**
 * 表格组件范例
 * 
 * @author xiaowenjie https://github.com/xwjie
 */
<template>
  <div>
    <el-table
      :data="configs | filterKeyword(keyword)"
      border
      stripe
      style="width: 100%">
      <el-table-column
        fixed
        sortable
        prop="id"
        label="ID"
        width="150">
      </el-table-column>
      <el-table-column
        prop="name"
        sortable
        label="名称"
        width="300">
      </el-table-column>
      <el-table-column
        sortable
        prop="value"
        label="取值"
        width="300">
      </el-table-column>
      <el-table-column
        sortable
        prop="description"
        label="描述">
      </el-table-column>
      
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="deleteConfig(scope.row)" type="text" size="small"><i class="el-icon-delete"></i></el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination ref="page1" url="/config/list" :updateData="data => this.configs = data"/>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  props: ["keyword"],
  methods: {
    handleClick(row) {
      console.log(row);
    },
    deleteConfig(row) {
      this.ajax.post("/config/delete?id=" + row.id).then(result => {
        if (result.code == 0) {
          this.info("delete success");
          this.refreshConfig();
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
      configs: []
    };
  }
};
</script>