/**
 * Copyright(C) 2017 Luvina
 * LoginServletController.java, 16/10/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginController() {
	}

	/**
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.getRequestDispatcher(Constant.INDEX).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TblUserLogicImpl userLogic = new TblUserLogicImpl();
		try {
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			// Kiểm tra tài khoản đăng nhập có hợp lệ không
			List<String> lstErr = userLogic.checkLogin(loginName, password);
			//nếu không có lỗi
			if (lstErr.size() == 0) {
				// nếu tài khoản đăng nhập hợp lệ thì tạo session và đến trang ADM002
				HttpSession session = request.getSession();
				session.setAttribute("loginName", loginName);
				response.sendRedirect(request.getContextPath() + Constant.ADM002_SERVLET);
			} else {
				// thì quay về trang login và gửi các thông báo lỗi tương ứng cùng với
				// thông tin loginName
				request.setAttribute("lstErr", lstErr);
				request.setAttribute("loginName", loginName);
				RequestDispatcher req = request.getRequestDispatcher("/" + Constant.INDEX);
				req.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

}
