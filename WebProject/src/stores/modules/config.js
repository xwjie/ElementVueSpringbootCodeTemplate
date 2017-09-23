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
                console.log(result);
                if (result.code == 0) {
                    commit('reload', result.data);
                }
                else {
                    //FIXME this.error(result.msg);
                }
            })
        }
    }
}

export default config
