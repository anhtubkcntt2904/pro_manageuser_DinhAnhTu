/**
 * Copyright(C) 2017 Luvina
 * LogoutController.java, 16/10/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Constant;

/**
 * Servlet implementation class LogoutController
 */
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Lấy session hiện tại và xóa session
		try {
			HttpSession session = request.getSession();
			//hủy session
			session.invalidate();
			//chuyển về trang login
			response.sendRedirect(request.getContextPath() + "/" + Constant.INDEX);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + Constant.SUCCESS_SERVLET);
		}
	}

}
