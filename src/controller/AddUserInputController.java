/**
 * Copyright(C) 2017 Luvina
 * AddUserInputController.java, 2/11/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Common;
import common.Constant;
import entity.MstGroup;
import entity.MstJapan;
import entity.UserInfor;
import logic.impl.MstGroupLogicImpl;
import logic.impl.MstJapanLogicImpl;
import validate.Validate;

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
		 * *
		 * response.getWriter().append("Served at: ").append(request.getContextPath());
		 */

		try {
			setDataLogic(request, response);
			UserInfor userInfor = setDefaultValue(request, response);
			request.setAttribute("userInfor", userInfor);
			request.getRequestDispatcher(Constant.ADM003).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + Constant.ERROR_SERVLET);
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
				Validate validate = new Validate();
				UserInfor userInfor = setDefaultValue(request, response);
				List<String> lstError = validate.validateUserInfor(userInfor);

				/*if (lstError.size() > 0) {
					setDataLogic(request, response);
					request.setAttribute("lstError", lstError);
					request.getRequestDispatcher(Constant.ADM003).forward(request, response);
				} else {*/
					// tạo key để thêm vào userInfor session
					long keyAdd = System.currentTimeMillis() % 1000;
					HttpSession session = request.getSession();
					session.setAttribute("userInfor" + keyAdd, userInfor);
					response.sendRedirect(request.getContextPath() + Constant.ADM004_SERVLET + "?keyAdd=" + keyAdd);
				//}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + Constant.ERROR_SERVLET);
		}
	}

	/**
	 * phương thức set dữ liệu logic cho màn hình ADM003
	 * 
	 * @param request
	 * @param response
	 */
	private void setDataLogic(HttpServletRequest request, HttpServletResponse response) {

		// lấy danh sách group và mst japan
		MstGroupLogicImpl mstGroupLogic = new MstGroupLogicImpl();
		List<MstGroup> lstMstGroup = mstGroupLogic.getAllGroup();
		MstJapanLogicImpl mstJapanLogic = new MstJapanLogicImpl();
		List<MstJapan> lstMstJapan = mstJapanLogic.getAllMstJapan();

		// lấy danh sách ngày,tháng và năm
		Common common = new Common();
		List<Integer> lstDay = common.getListDay();
		List<Integer> lstMonth = common.getListMonth();

		int yearNow = common.getYearNow();
		int yearStart = Constant.START_YEAR;
		List<Integer> lstYear = common.getListYear(yearStart, yearNow);

		// set các thuộc tính lên request
		request.setAttribute("lstMstGroup", lstMstGroup);
		request.setAttribute("lstMstJapan", lstMstJapan);
		request.setAttribute("lstYear", lstYear);
		request.setAttribute("lstMonth", lstMonth);
		request.setAttribute("lstDay", lstDay);

	}

	/**
	 * phương thức set dữ liệu mặc định cho màn hình ADM003
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private UserInfor setDefaultValue(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		UserInfor userInfor = new UserInfor();
		Common common = new Common();

		switch (type) {
		case Constant.DEFAULT:
			userInfor = new UserInfor();
			break;
		case Constant.CONFIRM:
			userInfor = new UserInfor();
			String loginName = request.getParameter("loginName");
			int group_id = common.convertStringToInt(request.getParameter("group_id"));
			String fullName = request.getParameter("fullName");
			String fullNameKana = request.getParameter("fullNameKana");

			// lấy ra birthday của user
			int yearbirthday = common.convertStringToInt(request.getParameter("yearbirthday"));
			int monthbirthday = common.convertStringToInt(request.getParameter("monthbirthday"));
			int daybirthday = common.convertStringToInt(request.getParameter("daybirthday"));
			Date dateBirthday = common.toDate(yearbirthday, monthbirthday, daybirthday);

			String email = request.getParameter("email");
			String tel = request.getParameter("tel");
			String password = request.getParameter("password");
			String confirmpass = request.getParameter("confirmpass");
			String code_level = request.getParameter("code_level");

			// lấy ra ngày cấp chứng chỉ của user
			int yearvalidate = common.convertStringToInt(request.getParameter("yearvalidate"));
			int monthvalidate = common.convertStringToInt(request.getParameter("monthvalidate"));
			int dayvalidate = common.convertStringToInt(request.getParameter("dayvalidate"));
			Date dateValidate = common.toDate(yearvalidate, monthvalidate, dayvalidate);

			// lấy ra ngày hết hạn chứng chỉ của user
			int yearinvalidate = common.convertStringToInt(request.getParameter("yearinvalidate"));
			int monthinvalidate = common.convertStringToInt(request.getParameter("monthinvalidate"));
			int dayinvalidate = common.convertStringToInt(request.getParameter("dayinvalidate"));
			Date dateInvalidate = common.toDate(yearinvalidate, monthinvalidate, dayinvalidate);

			String total = request.getParameter("total");

			userInfor.setLoginName(loginName);
			userInfor.setGroupId(group_id);
			userInfor.setFullName(fullName);
			userInfor.setFullNameKana(fullNameKana);
			userInfor.setBirthday(dateBirthday);
			userInfor.setYearbirthday(yearbirthday);
			userInfor.setMonthbirthday(monthbirthday);
			userInfor.setDaybirthday(daybirthday);
			userInfor.setEmail(email);
			userInfor.setTel(tel);
			userInfor.setPassword(password);
			userInfor.setCodeLevel(code_level);
			userInfor.setStartDate(dateValidate);
			userInfor.setYearvalidate(yearvalidate);
			userInfor.setMonthvalidate(monthvalidate);
			userInfor.setDayvalidate(dayvalidate);
			userInfor.setEndDate(dateInvalidate);
			userInfor.setYearinvalidate(yearinvalidate);
			userInfor.setMonthinvalidate(monthinvalidate);
			userInfor.setDayinvalidate(dayinvalidate);
			userInfor.setTotal(total);
			break;
		case Constant.BACK:
			String keyBack = request.getParameter("keyAdd");
			userInfor = (UserInfor) request.getSession().getAttribute("userInfor" + keyBack);
			break;
		case Constant.OK:
			String keyOK = request.getParameter("keyAdd");
			userInfor = (UserInfor) request.getSession().getAttribute("userInfor" + keyOK);
		default:
			break;
		}

		/*
		 * request.setAttribute("userInfor", userInfor);
		 * request.getRequestDispatcher("/jsp/ADM003.jsp").forward(request, response);
		 */
		return userInfor;
	}
}
