<template>
<div>
<el-row :gutter="20">
  <el-col :span="12">
    <div class="grid-content bg-purple">
        <el-upload
        class="upload-demo"
        ref="upload"
        :multiple="true"
        action="/upload/"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        :file-list="fileList"
        :before-upload="beforeUpload"
        :auto-upload="false">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </div>
  </el-col>
  <el-col :span="12">
      <div class="grid-content bg-purple">
        <el-upload
            class="upload-demo"
            drag
            action="/upload/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            :file-list="fileList"
            :before-upload="beforeUpload"
            multiple>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
      </div>
  </el-col>
</el-row>
</div>

</template>

<script>
export default {
  data() {
    return {
      fileList: [
      ]
    };
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length +
          fileList.length} 个文件`
      );
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
          this.fileList.push(result.data);
          this.info("上传成功!");
        } else {
          this.error(result.msg);
        }
      });

      return false;
    },
    // FIXME not worked
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    submitUpload() {
      this.$refs.upload.submit();
    }
  }
};
</script>
<style scoped>
.grid-content {
  border: 1px solid;
  min-height: 210px;
  background-color: #fefefe;
  padding: 20px;
}
</style>
