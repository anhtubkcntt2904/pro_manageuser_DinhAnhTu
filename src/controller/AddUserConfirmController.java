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
			String keyAdd = request.getParameter("keyAdd");
			request.setAttribute("keyAdd", keyAdd);
			HttpSession session = request.getSession();
			UserInfor userInfor = (UserInfor) session.getAttribute("userInfor" + keyAdd);
			request.setAttribute("userInfor", userInfor);
			request.getRequestDispatcher(Constant.ADM004).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + Constant.SUCCESS_SERVLET);
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
			String keyAdd = request.getParameter("keyAdd");
			HttpSession session = request.getSession();
			UserInfor userInfor = (UserInfor) session.getAttribute("userInfor" + keyAdd);
			TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
			Boolean isSuccess = false;
			if (userInfor != null) {
				isSuccess = tblUserLogic.createUser(userInfor);
			}
			if (isSuccess) {
				System.out.println("insert success");
				response.sendRedirect(request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.INSERT_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

}
