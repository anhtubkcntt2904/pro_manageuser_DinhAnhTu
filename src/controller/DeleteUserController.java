/**
 * Copyright(C) 2017 Luvina
 * DeleteUserController.java, 2/11/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Constant;
import entity.UserInfor;
import logic.impl.TblUserLogicImpl;
import properties.MessageProperties;

/**
 * Servlet implementation class DeleteUserController
 */
public class DeleteUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteUserController() {
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
			// từ màn 05,delete user theo user id
			int userid = Integer.parseInt(request.getParameter("userid"));
			TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
			// biến kiểm tra user có tồn tại không
			boolean existedUser = false;
			// biến kiểm tra delete user có thành công không
			Boolean check = false;
			// kiểm tra user có tồn tại hay không
			existedUser = tblUserLogicImpl.isExistedUser(userid);
			// nếu user tồn tại
			if (existedUser) {
				// xóa thông tin user
				check = tblUserLogicImpl.deleteUser(userid);
				// nếu xóa thành công
				if (check) {
					// thông báo thành công
					response.sendRedirect(
							request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.DELETE_SUCCESS);
					// nếu xóa không thành công
				} else {
					// thông báo error
					response.sendRedirect(
							request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
				}
			} else {
				// nếu user không tồn tại thì gửi đến trang báo không có user
				response.sendRedirect(
						request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.UPDATE_NOUSER);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *//*
		 * protected void doPost(HttpServletRequest request, HttpServletResponse
		 * response) throws ServletException, IOException { // TODO Auto-generated
		 * method stub doGet(request, response); }
		 */

}