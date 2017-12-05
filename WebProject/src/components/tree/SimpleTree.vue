<template>
    <el-tree :data="nodes" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
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
    }
  }
};
</script>