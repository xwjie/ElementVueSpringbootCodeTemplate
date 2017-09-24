import Vue from 'vue';
import UI from 'element-ui'
import VueRouter from 'vue-router';
import Routers from './router';
import Vuex from 'vuex';
import Util from './libs/util';
import App from './app.vue';
import 'element-ui/lib/theme-default/index.css'
import store from './stores';

//
import VueI18n from 'vue-i18n';

import enLocale from 'element-ui/lib/locale/lang/en'
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'


Vue.use(VueI18n)
Vue.use(UI)

Vue.config.lang = 'zh-cn'

Vue.use(VueRouter);
Vue.use(Vuex);
Vue.use(VueI18n);

// 工具类
Vue.prototype.info = function (msg) {
    this.$message(msg);
}

Vue.prototype.error = function (msg) {
    this.$message({ type: 'error', message: msg });
}

Vue.prototype.ajax = Util.ajax;

//
import { ConfigAdd, ConfigShow, Table, LoginDialog } from './components';
Vue.component('ConfigAdd', ConfigAdd);
Vue.component('ConfigShow', ConfigShow);
Vue.component('Table', Table);
Vue.component('LoginDialog', LoginDialog);

// event bus
import VueBus from 'vue-bus';
Vue.use(VueBus);

// 自动设置语言
const navLang = navigator.language;
const localLang = (navLang === 'zh-CN' || navLang === 'en-US') ? navLang : false;
const lang = window.localStorage.getItem('language') || localLang || 'zh-CN';

Vue.config.lang = lang;

Vue.locale('zh-cn', zhLocale)
Vue.locale('en', enLocale)

// 多语言配置

// 路由配置
const RouterConfig = {
    mode: 'history',
    routes: Routers
};
const router = new VueRouter(RouterConfig);

let loading;

router.beforeEach((to, from, next) => {
    loading = UI.Loading.service({ fullscreen: true });
    Util.title(to.meta.title);
    next();
});

router.afterEach(() => {
    loading.close();
    window.scrollTo(0, 0);
});

/*
const store = new Vuex.Store({
    state: {

    },
    getters: {

    },
    mutations: {

    },
    actions: {

    }
});
*/


new Vue({
    el: '#app',
    router: router,
    store: store,
    render: h => h(App)
});