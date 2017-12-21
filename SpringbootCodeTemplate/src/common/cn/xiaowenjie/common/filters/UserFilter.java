package cn.xiaowenjie.common.filters;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.core.ApplicationFilterChain;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.web.filter.DelegatingFilterProxy;

import cn.xiaowenjie.beans.User;
import cn.xiaowenjie.common.exceptions.UnloginException;
import cn.xiaowenjie.common.utils.UserUtil;
import cn.xiaowenjie.services.UserService;
import lombok.SneakyThrows;

/**
 * 用户filter，设置当前用户和语言到threadlocal中。
 * 
 * @author 肖文杰
 *
 */
@WebFilter(filterName = "userFilter", urlPatterns = "/*")
public class UserFilter implements Filter {

	private static UserService userService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		fillUserInfo((HttpServletRequest) request);

		try {
			chain.doFilter(request, response);

			// TODO : delete 测试代码
			printAllFilters(chain);
			printResponseInfo((HttpServletResponse) response);
		} finally {
			clearAllUserInfo();
		}
	}

	private void printResponseInfo(HttpServletResponse response) {
		System.out.println("printResponseInfo()" + response.getStatus());
	}

	@SneakyThrows
	private void printAllFilters(FilterChain chain) {
		ApplicationFilterChain filterChain = (ApplicationFilterChain) chain;

		// 读取私有变量 filters
		ApplicationFilterConfig[] filterConfigs = (ApplicationFilterConfig[]) readField(filterChain, "filters");
		int filterSize = (int) readField(filterChain, "n");

		System.out.println("\n\nprintAllFilters(), size=" + filterSize);

		for (int i = 0; i < filterSize; i++) {
			System.out.println(filterConfigs[i].getFilterName() + ", " + filterConfigs[i].getFilterClass());

			Filter filter = (Filter) invokeMethod(filterConfigs[i], "getFilter");

			// spring 的 filter 代理类
			if (filter instanceof DelegatingFilterProxy) {
				DelegatingFilterProxy filterProxy = (DelegatingFilterProxy) filter;

				FilterChainProxy springFilter = (FilterChainProxy) readField(DelegatingFilterProxy.class, filterProxy,
						"delegate");

				System.out.println(springFilter.getFilterChains());
				// List<Filter> springAdditionalFilters = (List<Filter>) readField(
				// springFilter, "additionalFilters");
				//
				// for (Filter f : springAdditionalFilters) {
				// System.out.print("\t\t");
				// System.out.println(f.getClass());
				// }
			}
		}

		System.out.println("\n\n");
	}

	private Object invokeMethod(Object obj, String methodName) throws IllegalAccessException, IllegalArgumentException,
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

	private Object readField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException {
		return readField(obj.getClass(), obj, name);
	}

	private void clearAllUserInfo() {
		UserUtil.clearAllUserInfo();
	}

	private void fillUserInfo(HttpServletRequest request) {
		// 用户信息
		// User user = getUserFromSession(request);
		User user = getUserFromSpringSecurity();

		if (user != null) {
			UserUtil.setUser(user);
		}

		// 语言信息
		String locale = getLocaleFromCookies(request);

		if (locale != null) {
			UserUtil.setLocale(locale);
		}
	}

	private String getLocaleFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return null;
		}

		for (int i = 0; i < cookies.length; i++) {
			if (UserUtil.KEY_LANG.equals(cookies[i].getName())) {
				return cookies[i].getValue();
			}
		}

		return null;
	}

	private User getUserFromSession(HttpServletRequest request) {
		// TODO 如果不参加session，model.addAttribute(UserUtil.KEY_USER, username);报错
		HttpSession session = request.getSession(true);

		if (session == null) {
			return null;
		}

		// 从session中获取用户信息放到工具类中
		return (User) session.getAttribute(UserUtil.KEY_USER);
	}

	@Override
	public void destroy() {

	}

	public static void setUserService(UserService userService) {
		UserFilter.userService = userService;
	}

	private User getUserFromSpringSecurity() {
		// 从spring security里面得到用户名
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return null;
		}

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// 根据用户名得到数据库用户
		return userService.findUser(userDetails.getUsername());
	}

}
