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

import common.Common;
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
	}

	/**
	 * Từ màn 05 click change pass
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
			// Lấy user id ở màn 05
			int userid = Common.parseInt(request.getParameter("userid"), 0);
			// kiểm tra user có tồn tại không
			boolean existedUser = tblUserLogic.isExistedUser(userid);
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
	 * Từ màn 07 click confirm
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
		// String lưu đường dẫn cần truyền đi
		String url = "";
		try {
			int userId = Common.parseInt(request.getParameter("user_id"), 0);
			// kiểm tra user có tồn tại không
			boolean existedUser = tblUserLogic.isExistedUser(userId);
			// nếu user tồn tại
			if (existedUser) {
				// Lấy ra thông tin pass người dùng thay đổi và user id
				String newpass = request.getParameter("newpass");
				String confirmpass = request.getParameter("confirmpass");
				// mảng chứa lỗi nếu người dùng nhập pass sai
				List<String> lstErr = Validate.validatePass(newpass, confirmpass);
				// nếu có lỗi thì hiển thị thông báo lỗi lên màn 07
				if (lstErr.size() > 0) {
					// set các thuộc tính để gửi sang 07
					request.setAttribute("lstErr", lstErr);
					request.setAttribute("userid", userId);
					// gửi đến 07
					request.getRequestDispatcher(Constant.ADM007).forward(request, response);
					// nếu không có lỗi thì thực hiện update pass vào database
				} else {
					boolean check = tblUserLogic.updatePass(newpass, userId);
					if (check) {
						// nếu update thành công
						url = Constant.SUCCESS_SERVLET + "?type=" + Constant.UPDATE_SUCCESS;
					} else {
						// nếu update không thành công
						url = Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR;
					}
				}
			} else {
				// nếu user không tồn tại thì gửi đến trang báo lỗi
				url = Constant.SUCCESS_SERVLET + "?type=" + Constant.UPDATE_NOUSER;
			}
			response.sendRedirect(request.getContextPath() + url);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}
}
