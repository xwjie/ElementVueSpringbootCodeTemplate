import axios from 'axios';
import env from '../config/env';
import Vue from 'vue';
import param from './param'

let util = {

};

util.title = function (title) {
    title = title ? title + ' - Home' : 'Element Vue Springboot CodeTemplate project';
    window.document.title = title;
};

const ajaxUrl = env === 'development' ?
    'http://127.0.0.1:8080' :
    env === 'production' ?
        'https://www.url.com' :
        'https://debug.url.com';

let axiosInstance = axios.create({
    baseURL: ajaxUrl,
    timeout: 30000,
    withCredentials: true
});

/*
 * 返回Response的data（即json数据）
 */
function handlerData(response) {
    // 全局的没有登录异常单独处理
    //if (response.data.code === -1) {
    //    console.log('no login');
    // 还没有找到中断promise好的办法
    //}

    console.log(response.data);

    return response.data;
}

//添加一个返回拦截器
axiosInstance.interceptors.response.use(function (response) {
    //对返回的数据进行一些处理

    // 全局的没有登录异常单独处理
    if (response.data.code === -1) {
        console.log('no login');

        // 通知打开登录窗口
        Vue.bus.emit('login-open');

        // 还没有找到中断promise好的办法
        return Promise.reject('ERROR_NOLOGIN');
    }

    return response;
}, function (error) {
    //对返回的错误进行一些处理
    return Promise.reject(error);
});

util.ajax = {};

util.ajax.post = function () {
    return axiosInstance.post.apply(this, arguments).then(handlerData)
}

util.ajax.postForm = function (url, data) {
    return axiosInstance.post(url, data, {
        transformRequest: [param],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(handlerData)
}

util.ajax.get = function () {
    return axiosInstance.get.apply(this, arguments).then(handlerData);
}

export default util;