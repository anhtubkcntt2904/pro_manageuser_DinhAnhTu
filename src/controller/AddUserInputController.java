/**
 * Copyright(C) 2017 Luvina
 * AddUserInputController.java, 2/11/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import logic.impl.TblUserLogicImpl;
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
	}

	/**
	 * trường hợp 02 thêm mới và 05 sửa
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// lấy ra kiểu truyền vào
			String type = request.getParameter("type");
			switch (type) {
			// nếu là trường hợp sửa
			case Constant.EDIT:
				TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
				Common common = new Common();
				// biến kiểm tra user có tồn tại hay không
				boolean existedUser = false;
				
				// lấy user id từ màn 05
				int userid = common.parseInt(request.getParameter("user_id"), 0);
				
				// kiểm tra user có tồn tại không
				existedUser = tblUserLogic.isExistedUser(userid);
				// nếu user có tồn tại
				if (existedUser) {
					// lấy dữ liệu cho select box
					setDataLogic(request, response);
					// lấy dữ liệu hiển thị user cho trường hợp edit
					UserInfor userInfor = setDefaultValue(request, response);
					// gửi thông tin user sang trang 03
					request.setAttribute("userInfor", userInfor);
					// đến màn 03 để sửa
					request.getRequestDispatcher(Constant.ADM003).forward(request, response);
				} else {
					// nếu user không tồn tại thì gửi đến trang báo lỗi
					response.sendRedirect(
							request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.UPDATE_NOUSER);
				}
				break;
			default:
				// trường hợp 02 thêm mới
				// lấy giá trị cho selectbox
				setDataLogic(request, response);
				// Lấy giá trị default cho user để truyền sang màn 03
				UserInfor userInfor = setDefaultValue(request, response);
				request.setAttribute("userInfor", userInfor);
				// truyền sang màn 03
				request.getRequestDispatcher(Constant.ADM003).forward(request, response);
				break;
			}
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
		try {
			List<String> lstError = new ArrayList<>();
			UserInfor userInfor = new UserInfor();
			Validate validate = new Validate();
			TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
			Common common = new Common();
			//biến set giá trị user id cho user
			int userId = 0;
			// biến kiểm tra user có tồn tại hay không,mặc định là true cho trường hợp thêm mới
			boolean existedUser = true;
			
			// trường hợp click confirm 03
			userInfor = setDefaultValue(request, response);
			lstError = validate.validateUserInfor(userInfor);
			// nếu là trường hợp edit
			if (!"0".equals(request.getParameter("user_id"))) {
				userId = Integer.parseInt(request.getParameter("user_id"));
				// kiểm tra user có tồn tại hay không
				existedUser = tblUserLogic.isExistedUser(userId);
			}
	
			// nếu user tồn tại hoặc mặc định trường hợp đầu là thêm mới
			if (existedUser) {
				// nếu có lỗi validate
				if (lstError.size() > 0) {
					// set giá trị cho selectbox
					setDataLogic(request, response);
					// gửi danh sách lỗi và thông tin user sang trang 03
					request.setAttribute("lstError", lstError);
					request.setAttribute("userInfor", userInfor);
					request.getRequestDispatcher(Constant.ADM003).forward(request, response);
				} else {
					HttpSession session = request.getSession();
					// tạo key để thêm vào userInfor session,tạo userinfor riêng
					long keyAdd = common.createKey();
					// thêm user infor vào session
					session.setAttribute("userInfor" + keyAdd, userInfor);
					// chuyển sang trang 04
					response.sendRedirect(request.getContextPath() + Constant.ADM004_SERVLET + "?keyAdd=" + keyAdd);
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

	/**
	 * phương thức set dữ liệu logic cho màn hình ADM003
	 * 
	 * @param request
	 * @param response
	 */
	private void setDataLogic(HttpServletRequest request, HttpServletResponse response) {
		// Khởi tạo
		MstGroupLogicImpl mstGroupLogic = new MstGroupLogicImpl();
		MstJapanLogicImpl mstJapanLogic = new MstJapanLogicImpl();
		Common common = new Common();
		// năm hiện tại
		int yearNow = common.getYearNow();
		// năm bắt đầu của select box
		int yearStart = Constant.START_YEAR;

		// lấy danh sách group và mst japan
		List<MstGroup> lstMstGroup = mstGroupLogic.getAllGroup();
		List<MstJapan> lstMstJapan = mstJapanLogic.getAllMstJapan();
		// lấy danh sách ngày,tháng và năm
		List<Integer> lstDay = common.getListDay();
		List<Integer> lstMonth = common.getListMonth();
		// lấy danh sách năm và danh sách năm end validate
		List<Integer> lstYear = common.getListYear(yearStart, yearNow);
		List<Integer> lstYearEnd = common.getListYear(yearStart, yearNow + 1);

		// set các thuộc tính lên request
		request.setAttribute("lstMstGroup", lstMstGroup);
		request.setAttribute("lstMstJapan", lstMstJapan);
		request.setAttribute("lstYear", lstYear);
		request.setAttribute("lstMonth", lstMonth);
		request.setAttribute("lstDay", lstDay);
		request.setAttribute("lstYearEnd", lstYearEnd);
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
		UserInfor userInfor = new UserInfor();
		Calendar now = Calendar.getInstance();
		Common common = new Common();

		String type = request.getParameter("type");
		// tháng hiện tại 
		int monthnow = now.get(Calendar.MONTH) + 1;
		// ngày hiện tại
		int daynow = now.get(Calendar.DATE);
		// năm hiện tại
		int yearnow = common.getYearNow();

		switch (type) {
		case Constant.DEFAULT:
			// trường hợp default vào màn hình
			userInfor = new UserInfor();

			// lấy ngày tháng năm hiện tại cho birthday select box
			userInfor.setYearbirthday(yearnow);
			userInfor.setMonthbirthday(monthnow);
			userInfor.setDaybirthday(daynow);

			// lấy ngày tháng năm hiện tại cho start date select box
			userInfor.setYearvalidate(yearnow);
			userInfor.setMonthvalidate(monthnow);
			userInfor.setDayvalidate(daynow);

			// lấy ngày tháng năm hiện tại cho end date select box
			userInfor.setYearinvalidate(yearnow + 1);
			userInfor.setMonthinvalidate(monthnow);
			userInfor.setDayinvalidate(daynow);
			break;
		// 03 click confirm
		case Constant.CONFIRM:
			userInfor = new UserInfor();

			// Lấy ra thông tin của user
			int userId = common.parseInt(request.getParameter("user_id"), 0);
			String loginName = request.getParameter("loginName");
			int group_id = common.convertStringToInt(request.getParameter("group_id"));
			String fullName = request.getParameter("fullName");
			String fullNameKana = request.getParameter("fullNameKana");
			String email = request.getParameter("email");
			String tel = request.getParameter("tel");
			String password = request.getParameter("password");
			String confirmpass = request.getParameter("confirmpass");
			String code_level = request.getParameter("code_level");

			// lấy ra birthday của user
			int yearbirthday = common.convertStringToInt(request.getParameter("yearbirthday"));
			int monthbirthday = common.convertStringToInt(request.getParameter("monthbirthday"));
			int daybirthday = common.convertStringToInt(request.getParameter("daybirthday"));
			Date dateBirthday = common.toDate(yearbirthday, monthbirthday, daybirthday);			

			Date dateValidate, dateInvalidate;
			String total;
			int yearvalidate, monthvalidate, dayvalidate;
			int yearinvalidate, monthinvalidate, dayinvalidate;

			// nếu màn 03 user có TĐTN
			if (!"0".equals(code_level)) {
				// lấy ra ngày cấp chứng chỉ của user
				yearvalidate = common.convertStringToInt(request.getParameter("yearvalidate"));
				monthvalidate = common.convertStringToInt(request.getParameter("monthvalidate"));
				dayvalidate = common.convertStringToInt(request.getParameter("dayvalidate"));
				dateValidate = common.toDate(yearvalidate, monthvalidate, dayvalidate);

				// lấy ra ngày hết hạn chứng chỉ của user
				yearinvalidate = common.convertStringToInt(request.getParameter("yearinvalidate"));
				monthinvalidate = common.convertStringToInt(request.getParameter("monthinvalidate"));
				dayinvalidate = common.convertStringToInt(request.getParameter("dayinvalidate"));
				dateInvalidate = common.toDate(yearinvalidate, monthinvalidate, dayinvalidate);

				total = request.getParameter("total");
				// nếu không có TĐTN
			} else {
				// thì lấy các giá trị mặc định
				yearvalidate = yearnow;
				monthvalidate = monthnow;
				dayvalidate = daynow;
				dateValidate = common.toDate(yearvalidate, monthvalidate, dayvalidate);

				yearinvalidate = yearnow + 1;
				monthinvalidate = monthnow;
				dayinvalidate = daynow;
				dateInvalidate = common.toDate(yearinvalidate, monthinvalidate, dayinvalidate);

				total = "";
			}

			// set các giá trị vừa lấy được vào user infor
			userInfor.setUserId(userId);
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
			userInfor.setConfirmpass(confirmpass);

			// set các giá trị vừa lấy được vào user infor
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

		// 04 click back
		case Constant.BACK:
			// lấy ra key
			String keyBack = request.getParameter("keyAdd");
			// Lấy ra thông tin user infor theo key
			userInfor = (UserInfor) request.getSession().getAttribute("userInfor" + keyBack);
			// hủy session attribute user infor
			request.getSession().removeAttribute("userInfor" + keyBack);
			break;

		// 04 click ok
		case Constant.OK:
			// lấy ra key
			String keyOK = request.getParameter("keyAdd");
			// Lấy ra thông tin user infor theo key
			userInfor = (UserInfor) request.getSession().getAttribute("userInfor" + keyOK);

			// 05 click edit
		case Constant.EDIT:
			// khởi tạo
			TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();

			// Lấy ra user id
			int userid = common.parseInt(request.getParameter("user_id"), 0);
			// Lấy thông tin user infor theo user id
			userInfor = tblUserLogic.getUserInforById(userid);
			// Lấy thông tin và ngày sinh
			ArrayList<Integer> lstBirthday = common.toArrayInteger(userInfor.getBirthday());
			userInfor.setDaybirthday(lstBirthday.get(2));
			userInfor.setMonthbirthday(lstBirthday.get(1));
			userInfor.setYearbirthday(lstBirthday.get(0));
			// nếu user có TĐTN
			if (userInfor.getCodeLevel() != null) {
				// Lấy thông tin ngày tháng năm của start date
				ArrayList<Integer> lstStartDate = common.toArrayInteger(userInfor.getStartDate());
				userInfor.setDayvalidate(lstStartDate.get(2));
				userInfor.setMonthvalidate(lstStartDate.get(1));
				userInfor.setYearvalidate(lstStartDate.get(0));

				// Lấy thông tin ngày tháng năm của end date
				ArrayList<Integer> lstEndDate = common.toArrayInteger(userInfor.getEndDate());
				userInfor.setDayinvalidate(lstEndDate.get(2));
				userInfor.setMonthinvalidate(lstEndDate.get(1));
				userInfor.setYearinvalidate(lstEndDate.get(0));
			} else {
				// lấy ngày tháng năm hiện tại cho start date select box
				userInfor.setYearvalidate(yearnow);
				userInfor.setMonthvalidate(monthnow);
				userInfor.setDayvalidate(daynow);

				// lấy ngày tháng năm hiện tại cho end date select box
				userInfor.setYearinvalidate(yearnow + 1);
				userInfor.setMonthinvalidate(monthnow);
				userInfor.setDayinvalidate(daynow);
			}
			break;
		default:
			break;
		}
		return userInfor;
	}
}
