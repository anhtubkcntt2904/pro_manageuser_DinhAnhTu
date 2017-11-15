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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//click id user ADM002
		int userid = Integer.parseInt(request.getParameter("userid"));
		TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
		UserInfor userInfor = new UserInfor();
		boolean existedUser = false;
		existedUser = tblUserLogic.isExistedUser(userid);
		if (existedUser) {
			userInfor = tblUserLogic.getUserInforById(userid);
			System.out.println("user id do get view detail :" + userInfor.getUserId());
			request.setAttribute("userInfor05", userInfor);
			request.getRequestDispatcher(Constant.ADM005).forward(request, response);
		}else {
			MessageProperties mess = new MessageProperties();
			String error = mess.getMessageProperties("ER013");
			response.sendRedirect(request.getContextPath() + Constant.ERROR + "?error=" + error);
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
