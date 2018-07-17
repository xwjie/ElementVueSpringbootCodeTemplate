/**
 * 表格组件范例
 * 
 * @author xiaowenjie https://github.com/xwjie
 */
<template>
    <el-row :gutter="10">
  <el-col :span="8">
  <div>
    <el-upload
            class="upload-demo"
            drag
            action="/logparse/upload"
            :before-upload="beforeUpload"
            multiple>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em>，只能上传log文件</div>
    </el-upload>
    <el-input
        placeholder="请输入关键字过滤"
        prefix-icon="el-icon-search"
        v-model="keyword">
    </el-input>
    <p/>
    <el-table
      ref="table1"
      :data="configs"
      size = "mini"
      border
      @sort-change="sortChange"
      @row-click="rowClick"
      highlight-current-row
      style="width: 100%">
      <el-table-column
        prop="name"
        sortable
        label="文件名">
      </el-table-column>
      <el-table-column
        sortable
        prop="createTime"
        label="上传时间"
        :formatter="dateFormat"
        width="110">
      </el-table-column>     
      <el-table-column
        label="数量"
        width="50">
        <template slot-scope="scope">
          <el-button @click="rowClick(scope.row)" type="text" size="small">{{scope.row.dataCount}}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination  ref="page1" url="/upload/list" :small="true" :keyword="keyword" :pageSize=5 :sort="sort" :refreshDone='refreshDone' v-model="configs"/>
  </div>
    </el-col>
  <el-col :span="16">
    <div>
      当前文件：<span class="current-filename">{{currentFileName}}</span>
    </div>
    <ve-line :data="chartData"></ve-line>
  </el-col>
    </el-row>
</template>

<script>
export default {
  methods: {
    sortChange({ column, prop, order }) {
      this.sort = { prop, order };
    },
    refreshDone(rows) {
      // 如果有数据，选中第一行
      if (rows && rows.length > 0) {
        //FIXME setCurrentRow not worked
        this.selectRow(0);
      }
    },
    selectRow(index){
        var row = this.configs[index];
        this.$refs.table1.setCurrentRow(row);
        this.rowClick(row);
    },
    rowClick(row) {
      console.log(row);
      this.currentRow = row;
      this.load(row.id);
    },
    load(uploadRecordId) {
      console.log("图表", uploadRecordId);

      this.ajax
        .get(
          "/chart/line?uploadRecordId=" + uploadRecordId
        )
        .then(result => {
          if (result.code == 0) {
            //this.info("delete success");
            this.chartData = result.data;
          } else {
            this.error(result.msg);
          }
        });
    },
    // 刷新表格数据
    refreshConfig() {
      this.$refs.page1.reload();
    },
    dateFormat: function(row, column) {
      var date = row[column.property];
      if (date == undefined) {
        return "";
      }
      return new Date(date).format("MMdd hh:mm");
    },
    beforeUpload(file) {
      console.log("before-upload", file);

      let param = new FormData(); // 创建form对象

      // file对象是 beforeUpload参数
      param.append("file", file, file.name);

      let config = {
        headers: { "Content-Type": "multipart/form-data" }
      };

      // 添加请求头
      this.ajax.post("/upload/", param, config).then(result => {
        if (result.code == 0) {
          this.info("上传成功!");

          // 插入到第一个位置
          this.configs.splice(0,0,result.data);

          // 选中
          this.selectRow(0);
        } else {
          this.error(result.msg);
        }
      });

      return false;
    }
  },
  data() {
    return {
      keyword: "",
      configs: [],
      sort: {},
      currentRow: {
        dataCount: 0
      },
      chartData: {
        columns: ["日期", "访问用户", "下单用户", "下单率"],
        rows: [
          { 日期: "1/1", 访问用户: 1393, 下单用户: 1093, 下单率: 0.32 },
          { 日期: "1/2", 访问用户: 3530, 下单用户: 3230, 下单率: 0.26 },
          { 日期: "1/3", 访问用户: 2923, 下单用户: 2623, 下单率: 0.76 },
          { 日期: "1/4", 访问用户: 1723, 下单用户: 1423, 下单率: 0.49 },
          { 日期: "1/5", 访问用户: 3792, 下单用户: 3492, 下单率: 0.323 },
          { 日期: "1/6", 访问用户: 4593, 下单用户: 4293, 下单率: 0.78 }
        ]
      }
    };
  },
  computed:{
    currentFileName(){
      if(this.currentRow){
        return this.currentRow.name || "";
      }
      return "";
    }
  }
};
</script>

<style>
.num {
  border: 1px solid darkgrey;
  background-color: #fcfcfc;
  width: 20px;
  margin: 5px 15px 5px 0;
  display: inline-block;
  cursor: pointer;
  text-align: center;
}

.current-filename{
  border: 1px solid darkgrey;
  background-color: #fcfcfc;
  padding: 1px 5px 1px 5px;
  margin: 5px 30px 5px 0;
  display: inline-block;
}

.active {
  background-color: yellow;
}
</style>
