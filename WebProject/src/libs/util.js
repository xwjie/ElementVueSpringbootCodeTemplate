import axios from 'axios';
import env from '../config/env';

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

let ajax = axios.create({
    baseURL: ajaxUrl,
    timeout: 30000
});

/*
 * 返回Response的data（即json数据）
 */
function handlerData(response) { 
    // 全局的没有登录异常单独处理
    if (response.data.code === -1) {
        console.log('no login');
        
        // 还没有找到中断promise好的办法
    } 
    
    return response.data;
}

util.ajax = {};

util.ajax.post = function () {
    return ajax.post.apply(this, arguments).then(handlerData);
}

util.ajax.get = function () {
    return ajax.get.apply(this, arguments).then(handlerData);
}

export default util;