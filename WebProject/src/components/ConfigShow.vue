<template>
    <ul>
        <li v-for='item in configs'>
            <el-button type="primary" size="mini" @click='delConfig(item.id)'>删除</el-button> {{item.name}}
        </li>
    </ul>
</template>
<script>

import { mapState } from 'vuex'

export default {
    methods: {
        delConfig(id) {
            let self = this;

            this.ajax.post('/config/delete?id=' + id).then(result => {
                console.log(result);
                if (result.code == 0) {
                    this.info('delete success');

                    //通知更新
                    this.$store.dispatch('config/reload');
                }
                else {
                    this.error(result.msg);
                }
            })
        }
    },
    computed: mapState({
        configs: state => state.config.configs,
    })
}
</script>