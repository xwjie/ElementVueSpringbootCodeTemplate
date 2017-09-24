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
        <!--
        <Table showIndex=true showSelecet=true showSummary=true />
        -->
        <LoginDialog :show='showLogin' />
    </div>
</template>
<script>

export default {
    created() {
        // 载入config数据
        this.$store.dispatch('config/reload');

        this.$bus.on('login-open', this.loginOpen);
        this.$bus.on('login-success', this.loginSuccess);
        this.$bus.on('login-cancel', this.loginCancel);
    },
    data(){
        return {
            showLogin: false
        }
    },
    methods: {
        handleStart() {
            this.info('工作正常');
        },
        loginOpen(){
            this.$data.showLogin = true;
        },
        loginSuccess(){
            this.$data.showLogin = false;
        },
        loginCancel(){
            this.$data.showLogin = false;
        }
    },
};
</script>