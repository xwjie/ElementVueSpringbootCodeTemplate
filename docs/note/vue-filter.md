# 增加vue过滤器

## 定义filter

[官方文档](https://cn.vuejs.org/v2/guide/filters.html) 。 这里定义的是全局的，也可以定义局部的。

```JavaScript
// 定义全局filter
Vue.filter('filterKeyword', function (value, key) {
    if (!key) return value;
    return value.filter(e => Util.isMatch(e, key));
});
```

判断对象是否包含关键字

```JavaScript
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
```

## 使用

给要过滤的组件传入 `keyword`

```JavaScript
<el-input
    placeholder="请输入内容"
    prefix-icon="el-icon-search"
    v-model="keyword">
</el-input>

<ConfigTable :keyword='keyword'/>

```

组件 `ConfigTable` 中增加 `keyword` props。

```JavaScript
export default {
  props: ["keyword"],
  ...
}
```

`ConfigTable` 中需要过滤的控件`el-table`上增加 `filterKeyword(keyword)` 。

```JavaScript
  <el-table
    :data="configs.rows | filterKeyword(keyword)"
    border
    stripe
    style="width: 100%">
  ...
```

## 效果图

![](./pictures/vue-filter.gif)