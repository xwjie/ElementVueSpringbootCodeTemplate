<style scoped>
.index {
    padding-left: 20px;
}
</style>
<template>
    <div class="index">
        <h1>Element Vue Springboot CodeTemplate</h1>
        <el-button type="primary" @click="handleStart">Hello World</el-button>
        <hr/>
        <ConfigAdd/>
        <ConfigShow/>
    </div>
</template>
<script>

export default {
    created() {
        // 载入config数据
        this.$store.dispatch('config/reload');
    },
    methods: {
        handleStart() {
            this.info('工作正常');
        },
    },
};
</script>