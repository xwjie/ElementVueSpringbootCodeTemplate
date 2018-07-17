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
      :data="configs"
      border
      size = "mini"
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
        label="名称"
        width="200">
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
          <el-button @click="deleteConfig(scope.row)" type="text"><i class="el-icon-delete"></i></el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination ref="page1" url="/config/list" :pageSize=5 :keyword="keyword" :sort="sort" v-model="configs"/>
  </div>
</template>

<script>
export default {
  methods: {
    sortChange({ column, prop, order }) {
      this.sort = { prop, order };
    },
    deleteConfig(row) {
      this.confirm("此操作将永久删除该记录, 是否继续?")
        .then(() => {
          this.ajax.post("/config/delete?id=" + row.id).then(result => {
            if (result.code == 0) {
              this.info("delete success");
              this.refreshConfig();
              this.info("删除成功!");
            } else {
              this.error(result.msg);
            }
          });
        })
        .catch(() => {
          this.info("已取消删除");
        });
    },
    // 刷新表格数据
    refreshConfig() {
      this.$refs.page1.reload();
    }
  },
  data() {
    return {
      keyword: "",
      configs: [],
      sort: {}
    };
  }
};
</script>