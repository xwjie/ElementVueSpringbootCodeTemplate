<template>
  <div>
    <el-tag
    :key="tag.id"
    v-for="tag in dynamicTags"
    closable
    :disable-transitions="false"
    @close="handleClose(tag)">
    {{tag.nick}}
    </el-tag>
    <el-autocomplete
        popper-class="my-autocomplete"
        v-if="inputVisible"
        v-model="inputValue"
        ref="saveTagInput"
        size="small"
        :fetch-suggestions="querySearchAsync"
        placeholder="请输入内容"
        @select="handleSelect">
    <!--
        @keyup.enter.native="handleInputConfirm"
        @blur="handleInputConfirm"
        @blur="handleBlur"
        -->
    <!--
        <i
            class="el-icon-edit el-input__icon"
            slot="suffix"
            @click="handleIconClick">
        </i>
        -->
        <template slot-scope="props">
            <span class="name">{{ props.item.name }}</span>
            <span class="addr">{{ props.item.nick }}</span>
        </template>
    </el-autocomplete>
    </el-input>
    <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
  </div>
</template>

<style>
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

.my-autocomplete {
  width: 400px;
  li {
    line-height: normal;
    padding: 7px;

    .name {
      text-overflow: ellipsis;
      overflow: hidden;
    }

    .addr {
      font-size: 12px;
      color: #b4b4b4;
    }

    .highlighted .addr {
      color: #ddd;
    }
  }
}
</style>

<script>
export default {
  data() {
    return {
      url: "/user/search",
      dynamicTags: [{ name: "name1", nick: "标签一" }],
      inputVisible: false,
      inputValue: ""
    };
  },
  methods: {
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },
    add(data) {
      if (data) {
        this.dynamicTags.push(data);
      }

      this.inputVisible = false;
      this.inputValue = "";
    },
    handleBlur() {
      this.inputVisible = false;
      this.inputValue = "";
    },
    handleInputConfirm() {
      this.add(this.inputValue);
    },
    handleSelect(item) {
      console.log(item);
      this.add(item);
    },
    querySearchAsync(queryString, cb) {
      if (!queryString) {
        return;
      }

      this.ajax
        .get(this.url, {
          params: {
            keyword: queryString
          }
        })
        .then(result => {
          if (result.code == 0) {
            cb(result.data);
          } else {
            this.error(result.msg);
          }
        });
    }
  }
};
</script>