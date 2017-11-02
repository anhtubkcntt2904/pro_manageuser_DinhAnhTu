/**
 * Copyright(C) 2017 Luvina
 * AddUserInputController.java, 2/11/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Common;
import common.Constant;
import entity.MstGroup;
import entity.MstJapan;
import entity.UserInfo;
import entity.UserInfor;
import logic.impl.MstGroupLogicImpl;
import logic.impl.MstJapanLogicImpl;

/**
 * Servlet implementation class AddUserInputController
 */
public class AddUserInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddUserInputController() {
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
		/*
		 * response.getWriter().append("Served at: ").append(request.getContextPath());
		 */
		try {
			setDataLogic(request, response);
			setDefault(request, response);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/Error.do");
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

	/**
	 * phương thức set dữ liệu logic cho màn hình ADM003
	 * @param request
	 * @param response
	 */
	private void setDataLogic(HttpServletRequest request, HttpServletResponse response) {

		MstGroupLogicImpl mstGroupLogic = new MstGroupLogicImpl();
		List<MstGroup> lstMstGroup = mstGroupLogic.getAllGroup();
		MstJapanLogicImpl mstJapanLogic = new MstJapanLogicImpl();
		List<MstJapan> lstMstJapan = mstJapanLogic.getAllMstJapan();

		Common common = new Common();
		List<Integer> lstDay = common.getListDay();
		List<Integer> lstMonth = common.getListMonth();

		int yearNow = common.getYearNow();
		int yearStart = Constant.START_YEAR;
		List<Integer> lstYear = common.getListYear(yearStart, yearNow);

		request.setAttribute("lstMstGroup", lstMstGroup);
		request.setAttribute("lstMstJapan", lstMstJapan);
		request.setAttribute("lstYear", lstYear);
		request.setAttribute("lstMonth", lstMonth);
		request.setAttribute("lstDay", lstDay);

	}

	/**
	 * phương thức set dữ liệu mặc định cho màn hình ADM003
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void setDefault(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		UserInfor userInfor = new UserInfor();

		switch (type) {
		case "default":
			break;
		default:
			break;
		}
		
		request.setAttribute("userInfor", userInfor);
		request.getRequestDispatcher("/jsp/ADM003.jsp").forward(request, response);
	}
}
