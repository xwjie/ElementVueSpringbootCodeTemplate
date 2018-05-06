# ElementUI升级2.0.2

:::tip
时间：2017.11.02
:::

先升级vue到2.5.2，在升级elementui

命令：

```
npm update fsevents
npm update vue
npm update vue-template-compiler

npm uninstall element-ui
npm install element-ui@2.0.2 -S
```

修改elementui的css路径：

```
import 'element-ui/lib/theme-default/index.css'
```

改为

```
import 'element-ui/lib/theme-chalk/index.css'
```

done.

