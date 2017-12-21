<template>
  <div>
    <pre>
      使用JSX语法。需要先安装一下依赖

      npm install\
        babel-plugin-syntax-jsx\
        babel-plugin-transform-vue-jsx\
        babel-helper-vue-jsx-merge-props\
        babel-preset-env\
        --save-dev
      
      然后修改文件 .babelrc:

      {
        "presets": ["env"],
        "plugins": ["transform-vue-jsx"]
      }

      参考: https://github.com/vuejs/babel-plugin-transform-vue-jsx#usage
    </pre>
    <el-tree :data="nodes" :props="defaultProps" @node-click="handleNodeClick" 
      :render-content="renderContent"></el-tree>
  </div>
</template>

<script>
export default {
  mounted() {
    this.reload();
  },
  data() {
    return {
      nodes: [],
      defaultProps: {
        children: "subnodes",
        label: "name"
      }
    };
  },
  methods: {
    handleNodeClick(node) {
      console.log(node);
    },
    reload() {
      this.ajax.get("/tree/simple").then(result => {
        if (result.code == 0) {
          this.nodes = result.data;
        } else {
          this.error(result.msg);
        }
      });
    },
    renderContent(h, { node, data, store }) {
      let icon="el-icon-"+node.icon;
      return (
        <span>
          <i class={icon} />
          {node.label}
        </span>
      );
    }
  }
};
</script>