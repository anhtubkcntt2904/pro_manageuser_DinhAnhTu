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
	TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();

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
			// Lấy user id ở màn 05
			//String userid = request.getParameter("userid");
			int userid = Integer.parseInt(request.getParameter("userid"));
			// biến kiểm tra user có tồn tại hay không
			boolean existedUser = false;
			// kiểm tra user có tồn tại không
			existedUser = tblUserLogic.isExistedUser(userid);
			if (existedUser) {
				// set user id ở màn 05 để truyền sang màn 07
				request.setAttribute("userid", userid);
				request.getRequestDispatcher(Constant.ADM007).forward(request, response);
			} else {
				// nếu user không tồn tại thì gửi đến trang báo lỗi
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
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// Lấy ra thông tin pass người dùng thay đổi và user id
			String newpass = request.getParameter("newpass");
			String confirmpass = request.getParameter("confirmpass");
			int userId = Integer.parseInt(request.getParameter("user_id"));
			// biến kiểm tra user có tồn tại hay không
			boolean existedUser = false;
			// kiểm tra user có tồn tại không
			existedUser = tblUserLogic.isExistedUser(userId);
			// nếu user tồn tại
			if (existedUser) {
				Validate validate = new Validate();
				// mảng chứa lỗi nếu người dùng nhập pass sai
				List<String> lstErr = new ArrayList<String>();
				lstErr = validate.validatePass(newpass, confirmpass);
				// nếu có lỗi thì hiển thị thông báo lỗi lên màn 07
				if (lstErr.size() > 0) {
					// set các thuộc tính để gửi sang 07
					request.setAttribute("lstErr", lstErr);
					request.setAttribute("userid", userId);
					request.setAttribute("newpass", newpass);
					request.setAttribute("confirmpass", confirmpass);
					// gửi đến 07
					request.getRequestDispatcher(Constant.ADM007).forward(request, response);
					// nếu không có lỗi thì thực hiện update pass vào database
				} else {
					Boolean check = tblUserLogic.updatePass(newpass, userId);
					if (check) {
						// nếu update thành công
						response.sendRedirect(request.getContextPath() + Constant.SUCCESS_SERVLET + "?type="
								+ Constant.UPDATE_SUCCESS);
					} else {
						// nếu update không thành công
						response.sendRedirect(
								request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
					}
				}
			} else {
				// nếu user không tồn tại thì gửi đến trang báo lỗi
				response.sendRedirect(
						request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.UPDATE_NOUSER);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

}