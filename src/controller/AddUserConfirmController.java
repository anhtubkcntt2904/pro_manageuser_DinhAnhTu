package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			String keyAdd = request.getParameter("keyAdd");
			request.setAttribute("keyAdd", keyAdd);
			HttpSession session = request.getSession();
			UserInfor userInfor = (UserInfor) session.getAttribute("userInfor" + keyAdd);
			request.setAttribute("userInfor", userInfor);
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
			String keyAdd = request.getParameter("keyAdd");
			HttpSession session = request.getSession();
			UserInfor userInfor = (UserInfor) session.getAttribute("userInfor" + keyAdd);
			TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
			Boolean isSuccess = false;
			Boolean isExistedUser = false;
			int userId = Integer.parseInt(request.getParameter("user_id"));
			System.out.println("user id do post : " + userId);
			isExistedUser = tblUserLogic.isExistedUser(userId);
			System.out.println("true false do post :" + isExistedUser);
			if (userInfor != null && !isExistedUser) {
				isSuccess = tblUserLogic.createUser(userInfor);
			}else {
				isSuccess = tblUserLogic.updateUserInfor(userInfor);
			}
			if (isSuccess) {
				response.sendRedirect(
						request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.INSERT_SUCCESS);
			} else {
				response.sendRedirect(
						request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

}
