/**
 * Copyright(C) 2017 Luvina
 * LoginServletController.java, 16/10/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Constant;
import logic.impl.TblUserLogicImpl;

/**
 * Servlet implementation class LoginServletController
 */
public class LoginServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginServletController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher(Constant.INDEX).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			TblUserLogicImpl userLogic = new TblUserLogicImpl();
			userLogic.lstErr = new ArrayList<>();
			// Kiểm tra tài khoản đăng nhập có hợp lệ không
			if (!userLogic.ExistLogin(loginName, password)) {
				// nếu không thì quay về trang login và gửi các thông báo lỗi tương ứng cùng với
				// thông tin loginName
				request.setAttribute("lstErr", userLogic.lstErr);
				request.setAttribute("loginName", loginName);
				RequestDispatcher req = request.getRequestDispatcher("/" + Constant.INDEX);
				req.forward(request, response);
			} else {
				// nếu tài khoản đăng nhập hợp lệ thì tạo session và đến trang ADM002
				HttpSession session = request.getSession();
				session.setAttribute("loginName", loginName);
				response.sendRedirect(request.getContextPath() + Constant.ADM002_SERVLET);
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/Error.do");
		}
	}

}
