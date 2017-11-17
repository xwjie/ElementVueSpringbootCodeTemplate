package cn.xiaowenjie.common.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.xiaowenjie.beans.User;
import cn.xiaowenjie.common.utils.UserUtil;

/**
 * 用户filter，设置当前用户和语言到threadlocal中。
 * 
 * @author 肖文杰
 *
 */
@WebFilter(filterName = "userFilter", urlPatterns = "/*")
public class UserFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		fillUserInfo((HttpServletRequest) request);

		chain.doFilter(request, response);

		clearAllUserInfo();
	}

	private void clearAllUserInfo() {
		UserUtil.clearAllUserInfo();
	}

	private void fillUserInfo(HttpServletRequest request) {
		// 用户信息
		User user = getUserFromSession(request);

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
		//TODO 如果不参加session，model.addAttribute(UserUtil.KEY_USER, username);报错
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

}
