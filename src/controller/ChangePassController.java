/**
 * Copyright(C) 2017 Luvina
 * ChangePassController.java, 2/11/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Constant;
import logic.impl.TblUserLogicImpl;
import validate.Validate;

/**
 * Servlet implementation class ChangePassController
 */
public class ChangePassController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePassController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String userid = request.getParameter("userid");
			request.setAttribute("userid", userid);
			request.getRequestDispatcher(Constant.ADM007).forward(request, response);
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
		// TODO Auto-generated method stub
		try {
			// Lấy ra thông tin pass người dùng thay đổi
			String newpass = request.getParameter("newpass");
			String confirmpass = request.getParameter("confirmpass");
			int userId = Integer.parseInt(request.getParameter("user_id"));
			Validate validate = new Validate();
			List<String> lstErr = new ArrayList<String>();
			lstErr = validate.validatePass(newpass, confirmpass);
			// nếu có lỗi thì hiển thị thông báo lỗi lên màn 07
			if (lstErr.size() > 0) {
				request.setAttribute("lstErr", lstErr);
				request.setAttribute("userid", userId);
				request.getRequestDispatcher(Constant.ADM007).forward(request, response);
				// nếu không thì thực hiện update pass vào database
			} else {
				TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
				Boolean check = tblUserLogicImpl.updatePass(newpass, userId);
				if (check) {
					// nếu update thành công
					response.sendRedirect(
							request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.UPDATE_SUCCESS);
				} else {
					// nếu update không thành công
					response.sendRedirect(
							request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

}
