module.exports = {
  title: '晓风轻Vue+SpringBoot代码模板',
  description: '晓风轻Vue+SpringBoot代码模板',
  dest: './dist',    // 设置输出目录
  base: '/ElementVueSpringbootCodeTemplate/', // 设置站点根路径
  port: 7070,
  themeConfig: {
    repo: 'https://github.com/xwjie/ElementVueSpringbootCodeTemplate' ,
    nav: [
      { text: '首页', link: '/' },
      { text: '开发笔记', link: '/note/' },
    ],
    sidebar:  {
	  '/note/': [{
	      title: '开发笔记',
	      collapsable: false,
	      children: [
	        '',
	        'use-redis-cache',
	        'update-elementui',
	        'spring-jpa-data-use-h2-database',
	        'vue-filter',
	        'enable-swagger2',
	        'vue-comps',
	        'spring-security',
	        'hibernate',
	        'jms',
	        'springboot',
	        'keng',
	      ]
	  }],		  
	 
    }
  }, //themeConfig
  configureWebpack: {
    resolve: {
      alias: {
        '@img': 'images'
      }
    }
  }
}