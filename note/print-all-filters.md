# 找到FilterChina实现类

chain.doFilter(request, response);

按 Ctrl+T 找到实现类列表，看着就是 `org.apache.catalina.core.ApplicationFilterChain` ，下个断点一测试就知道了。

# 读取Filters列表

先强转，然后使用反射( `getDeclaredField` + `setAccessible`)读取私有变量 filters， n。

注意：filters默认是10长度，里面不一定满了，n才是真正的数量

```Java
@SneakyThrows
private void printAllFilters(FilterChain chain) {
	ApplicationFilterChain filterChain = (ApplicationFilterChain) chain;

	// 读取私有变量 filters
	ApplicationFilterConfig[] filterConfigs = (ApplicationFilterConfig[]) readField(
			filterChain, "filters");
	int filterSize = (int) readField(filterChain, "n");

	System.out.println("\n\nprintAllFilters(), size=" + filterSize);

	for (int i = 0; i < filterSize; i++) {
		System.out.println("printAllFilters()" + filterConfigs[i].getFilterClass());
	}

	System.out.println("\n\n");
}

private Object readField(Object obj, String name)
		throws NoSuchFieldException, IllegalAccessException {
	Field field = obj.getClass().getDeclaredField(name);
	field.setAccessible(true);

	return field.get(obj);
}
```

结果如图：


```
printAllFilters(), size=7
printAllFilters()org.springframework.boot.web.filter.OrderedCharacterEncodingFilter
printAllFilters()org.springframework.boot.web.filter.OrderedHiddenHttpMethodFilter
printAllFilters()org.springframework.boot.web.filter.OrderedHttpPutFormContentFilter
printAllFilters()org.springframework.boot.web.filter.OrderedRequestContextFilter
printAllFilters()org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean$1
printAllFilters()cn.xiaowenjie.common.filters.UserFilter
printAllFilters()org.apache.tomcat.websocket.server.WsFilter
```

# 反射获取属性，调用方法

```Java
private Object invokeMethod(Object obj, String methodName)
		throws IllegalAccessException, IllegalArgumentException,
		InvocationTargetException, NoSuchMethodException, SecurityException {
	Method method = obj.getClass().getDeclaredMethod(methodName, null);
	method.setAccessible(true);

	return method.invoke(obj, null);
}

private Object readField(Class<?> cls, Object obj, String name)
		throws NoSuchFieldException, IllegalAccessException {
	Field field = cls.getDeclaredField(name);
	field.setAccessible(true);

	return field.get(obj);
}

private Object readField(Object obj, String name)
		throws NoSuchFieldException, IllegalAccessException {
	return readField(obj.getClass(), obj, name);
}
```


如果是 `volatile` 类型的，不能用 `obj.getClass()`，必须用 类型的 `.class`

|That is known as a class literal. The expression results in a Class object for the given type. Calling  getClass() on a Class instance will return the Class instance for Class, not for MyClass.
|
|(All this assumes that the field mInstance is declared in MyClass.)

