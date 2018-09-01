import Vue from 'vue';

import VueRouter from 'vue-router';
import Routers from './router';
import Vuex from 'vuex';
import Util from './libs/util';
import App from './App.vue';
import store from './stores';

Vue.use(VueRouter);
Vue.use(Vuex);

// ---------------------------------------- element ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

Vue.config.lang = 'zh-cn'

// ---------------------------------------- charts
import VCharts from 'v-charts';
Vue.use(VCharts)

// ---------------------------------------- 提示信息工具类
Vue.prototype.info = function (msg) {
    //this.$message(msg);
    this.$message({
        type: "success",
        message: msg
    });
}

Vue.prototype.error = function (msg) {
    this.$message({ type: 'error', message: msg });
}

Vue.prototype.confirm = function () {
    return this.$confirm(...arguments);
}

// ---------------------------------------- 请求
Vue.prototype.ajax = Util.ajax;

// ----------------------------------------  组件
import { registerCommonComponents } from './commons';
registerCommonComponents();

import { registerComponents } from './components';
registerComponents();

// 定义全局filter
Vue.filter('filterKeyword', function (value, key) {
    if (!key) return value;
    return value.filter(e => Util.isMatch(e, key));
});

// ---------------------------------------- event bus
import VueBus from 'vue-bus';

import './plugins/element.js'
Vue.use(VueBus);


// ---------------------------------------- 自动设置语言
function switchLanguage(value){
    var lang = value;

    if(lang){
        setCookie('lang', value);
    }
    else{
        const navLang = navigator.language;
        const localLang = (navLang === 'zh-CN' || navLang === 'en-US') ? navLang : false;
        //const lang = window.localStorage.getItem('language') || localLang || 'zh-CN';
        lang = getCookie('lang') || localLang || 'zh-CN';        
    }

    Vue.config.lang = lang;

    console.log("language", lang);
}

switchLanguage();

// ---------------------------------------- 路由配置
const RouterConfig = {
    mode: 'history',
    routes: Routers
};
const router = new VueRouter(RouterConfig);

let loading;

router.beforeEach((to, from, next) => {
    loading = ElementUI.Loading.service({ fullscreen: true });
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


var instance = new Vue({
    el: '#app',
    router: router,
    store: store,
    render: h => h(App)
});


instance.$bus.on("lang-change", switchLanguage);

// ---------------------------------------- 工具类

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return (arr[2]);
    else
        return null;
}

function setCookie(name, value) {
    document.cookie = name + '=' + value;
}