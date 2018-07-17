import axios from 'axios';
import env from '../config/env';
import Vue from 'vue';
import param from './param'

let util = {

};

util.title = function (title) {
    title = title ? title + ' - Home' : '晓风轻 Element Vue Springboot 代码模板';
    window.document.title = title;
};

const ajaxUrl = env === 'development' ?
    'http://localhost:8080' :
    env === 'production' ?
        'springboot' :
        'springboot';

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
    let data = response.data

    console.log('axiosInstance.interceptors', data);
    console.log('axiosInstance.interceptors', data.code);

    //对返回的数据进行一些处理
    // 全局的没有登录异常单独处理
    if (response.status == 401 || data.code === -1) {
        console.log('no login');

        // 通知打开登录窗口
        Vue.bus.emit('login-open');

        // 还没有找到中断promise好的办法
        return Promise.reject('ERROR_NOLOGIN');
    }

    return response;
}, function (error) {
    let response = error.response;

    console.log("response", error);

    for(var i in error){
        console.log(i, error[i]);
    }
    
    if(response){
        // 全局的没有登录异常单独处理
        if (response.status == 401 || response.data.code === -1) {
            console.log('no login');
            
            // 通知打开登录窗口
            Vue.bus.emit('login-open');
            
            // 还没有找到中断promise好的办法
            return Promise.reject('ERROR_NOLOGIN');
        }
    }

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

/**
 * 对象是否包含指定关键字 key
 */
util.isMatch = function(e, key){
    var props = ['name', 'value', 'description'];

    for(var i in props){
        var field = e[props[i]];

        if(field && field.indexOf(key) > -1){
            return true;
        }
    }

    return false;
}

export default util;