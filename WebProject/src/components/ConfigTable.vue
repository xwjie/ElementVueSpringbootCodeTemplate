<template>
  <div>
  <el-table
    :data="configs.rows | filterKeyword(keyword)"
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

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="configs.page"
      :page-sizes="[5, 10, 50, 100]"
      :page-size="configs.pagesize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="configs.total">
    </el-pagination>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  props: ["keyword"],
  mounted() {
    this.refreshConfig();
  },
  methods: {
    handleClick(row) {
      console.log(row);
    },
    deleteConfig(row) {
      let self = this;

      this.ajax.post("/config/delete?id=" + row.id).then(result => {
        if (result.code == 0) {
          this.info("delete success");
          this.refreshConfig();
        } else {
          this.error(result.msg);
        }
      });
    },
    handleSizeChange(val) {
      this.configs.pagesize = val;
      this.refreshConfig();
    },
    handleCurrentChange(val) {
      this.configs.page = val;
      this.refreshConfig();
    },
    refreshConfig() {
      this.ajax
        .get("/config/list", {
          params: {
            page: this.configs.page,
            pagesize: this.configs.pagesize
          }
        })
        .then(result => {
          if (result.code == 0) {
            this.configs = result.data;
          } else {
            //FIXME module中出错如何提示合理？ this.error(result.msg);
          }
        });
    }
  },
  data() {
    return {
      configs: {
        rows: [],
        page: 1,
        pagesize: 10,
        total: 0
      }
    };
  }
};
</script>