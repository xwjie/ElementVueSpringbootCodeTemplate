<template>
    <el-form :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="名称">
            <el-input v-model="form.name" placeholder="配置项名称在这里"></el-input>
        </el-form-item>
        <el-form-item label="取值">
            <el-input v-model="form.value" placeholder="配置项取值在这里"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">添加</el-button>
        </el-form-item>
    </el-form>
</template>
<script>
export default {
    data() {
        return {
            form: {
                name: '晓风轻',
                value: 'https://github.com/xwjie'
            }
        }
    },
    methods: {
        onSubmit() {
            let self = this;
            this.ajax.post('/config/add', this.form).then(result => {
                if (result.code == 0) {
                    self.info('add success, new id:' + result.data);

                    self.$store.dispatch('config/reload');
                }
                else {
                    self.error(result.msg);
                }
            })
        }
    }
}
</script>