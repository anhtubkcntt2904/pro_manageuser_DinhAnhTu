/**
 * Copyright(C) 2017 Luvina
 * ViewDetailUserController.java, 16/10/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Common;
import common.Constant;
import entity.UserInfor;
import logic.impl.TblUserLogicImpl;
import properties.MessageProperties;

/**
 * Servlet implementation class ViewDetailUserController
 */
public class ViewDetailUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewDetailUserController() {
		super();
	}

	/**
	 * Click id user từ màn 02
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
		// đối tượng lưu thông tin của user để truyền sang màn 05
		UserInfor userInfor = new UserInfor();
		try {
			// lấy user id khi click user id từ màn 02
			int userid = Common.parseInt(request.getParameter("userid"), 0);
			// kiểm tra tồn tại của user
			boolean existedUser = tblUserLogic.isExistedUser(userid);
			// nếu user tồn tại
			if (existedUser) {
				// lấy thông tin của user theo user id truyền vào
				userInfor = tblUserLogic.getUserInforById(userid);
				// hiển thị màn hình view ADM005
				request.setAttribute("userInfor05", userInfor);
				request.getRequestDispatcher(Constant.ADM005).forward(request, response);
			} else {
				// lấy ra mã lỗi
				String error = MessageProperties.getMessageProperties("ER013");
				// gửi đến màn hình lỗi
				response.sendRedirect(request.getContextPath() + Constant.ERROR + "?error=" + error);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}
}
