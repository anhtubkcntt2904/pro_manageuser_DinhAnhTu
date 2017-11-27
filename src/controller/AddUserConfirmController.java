/**
 * Copyright(C) 2017 Luvina
 * AddUserConfirmController.java, 2/11/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Common;
import common.Constant;
import entity.UserInfor;
import logic.impl.TblUserLogicImpl;

/**
 * Servlet implementation class AddUserConfirmController
 */
public class AddUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddUserConfirmController() {
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
			// 03 confirm sang 04
			// lấy key add vào user infor session và gửi sang màn 04
			String keyAdd = request.getParameter("keyAdd");
			request.setAttribute("keyAdd", keyAdd);
			HttpSession session = request.getSession();
			// lấy user infor trên session theo key adđ
			UserInfor userInfor = (UserInfor) session.getAttribute("userInfor" + keyAdd);
			// gửi user infor sang trang 04
			request.setAttribute("userInfor", userInfor);
			// sang trang 04
			request.getRequestDispatcher(Constant.ADM004).forward(request, response);
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
			// 04 submit create user
			TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
			Common common = new Common();
			// lấy key add qua request từ 04
			String keyAdd = request.getParameter("keyAdd");
			HttpSession session = request.getSession();
			// lấy user infor trên session bằng key add
			UserInfor userInfor = (UserInfor) session.getAttribute("userInfor" + keyAdd);
			// biến kiểm tra update hoặc create user có thành công không
			Boolean isSuccess = false;
			// biến kiểm tra user có tồn tại không
			Boolean isExistedUser = false;
			// lấy user id từ request của 04
			int userId = common.parseInt(request.getParameter("user_id"), 0);
			String url;

			// nếu user id không bằng 0
			if (userId != 0) {
				// kiểm tra user có tồn tại hay không
				isExistedUser = tblUserLogic.isExistedUser(userId);
			}

			// nếu trường hợp thêm mới
			if (userId == 0 && !isExistedUser) {
				// thêm mới user
				isSuccess = tblUserLogic.createUser(userInfor);
				// nếu trường hợp update
			} else {
				// update user vào database
				isSuccess = tblUserLogic.updateUserInfor(userInfor);
			}

			// nếu thao thêm mới thành công
			if (isSuccess && !isExistedUser) {
				// Link đến trang thông báo thêm mới thành công
				url = Constant.SUCCESS_SERVLET + "?type=" + Constant.INSERT_SUCCESS;
				// nếu update thành công
			} else if (isSuccess && isExistedUser) {
				// Link đến trang thông báo update thành công
				url = Constant.SUCCESS_SERVLET + "?type=" + Constant.UPDATE_SUCCESS;
				// nếu lỗi thì gửi đến trang system error
			} else {
				url = Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR;
			}

			// xóa thông tin user infor trên session
			session.removeAttribute("userInfor" + keyAdd);
			response.sendRedirect(request.getContextPath() + url);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

}
