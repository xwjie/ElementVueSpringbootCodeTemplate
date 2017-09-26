import util from '../../libs/util';

const config = {
    namespaced: true,
    state: {
        configs: [
            { id: 1, 'name': '初始化配置项', value: '晓风轻' }
        ]
    },
    mutations: {
        reload(state, data) {
            state.configs = data;
        }
    },
    actions: {
        reload({ state, commit, rootState }) {
            util.ajax.get('/config/all').then(result => {
                if (result.code == 0) {
                    //提交数据修改
                    commit('reload', result.data);
                }
                else {
                    //FIXME module中出错如何提示合理？ this.error(result.msg);
                }
            });
        }
    }
}

export default config
