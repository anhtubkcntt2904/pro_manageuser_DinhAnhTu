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
			int userid = Integer.parseInt(request.getParameter("userid"));
			TblUserLogicImpl tblUserLogicImpl = new TblUserLogicImpl();
			UserInfor userInfor = new UserInfor();
			boolean existedUser = false;
			Boolean check = false;
			existedUser = tblUserLogicImpl.isExistedUser(userid);
			if (existedUser) {
				userInfor = tblUserLogicImpl.getUserInforById(userid);
				check = tblUserLogicImpl.deleteUser(userInfor);
				if (check) {
					response.sendRedirect(
							request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.INSERT_SUCCESS);
				} else {
					response.sendRedirect(
							request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
				}
				/*
				 * request.setAttribute("userInfor05", userInfor);
				 * request.getRequestDispatcher(Constant.ADM005).forward(request, response);
				 */
			} else {
				MessageProperties mess = new MessageProperties();
				String error = mess.getMessageProperties("ER013");
				response.sendRedirect(request.getContextPath() + Constant.ERROR + "?error=" + error);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
