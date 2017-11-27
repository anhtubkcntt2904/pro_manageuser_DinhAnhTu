/**
 * Copyright(C) 2017 Luvina
 * MyFilter.java, 16/10/2017 Đinh Anh Tú
 */
package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Constant;

/**
 * Servlet Filter implementation class MyFilter
 */
public class LoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// lấy ra session hiện tại của user
		HttpSession session = req.getSession();

		// Lấy đường dẫn url
		String path = req.getRequestURI();

		// nếu đã đăng nhập
		if (req.getSession().getAttribute("loginName") != null) {
			// xét xem đường dẫn có đến trang login không
			if (path.startsWith(Constant.LOGIN_REQUEST_URI) || path.equals(Constant.CONTEXT_ROOT)) {
				// nếu có thì chuyển sang trang list user ADM002
				res.sendRedirect("." + Constant.ADM002_SERVLET);
			} else {
				// nếu không thì cho request đi tiếp
				chain.doFilter(request, response); // Just continue chain.
			}

			// nếu chưa đăng nhập thì xét xem đường dẫn hiện tại có vào trang login servlet
			// không
		} else if (path.startsWith(Constant.LOGIN_REQUEST_URI)) {
			// nếu có thì cho request đi tiếp
			chain.doFilter(request, response);
		} else {
			// nếu không thì đến trang login servlet
			res.sendRedirect("." + Constant.ADM001_SERVLET);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
