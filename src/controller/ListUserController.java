/**
 * Copyright(C) 2017 Luvina
 * ListUserController.java, 16/10/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Common;
import common.Constant;
import entity.MstGroup;
import entity.UserInfor;
import logic.impl.MstGroupLogicImpl;
import logic.impl.TblUserLogicImpl;
import properties.MessageProperties;

/**
 * Servlet implementation class ListUserServlet
 */
public class ListUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListUserController() {
		super();
	}

	/**
	 * Trường hợp paging và sort vào do get
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doPost(request, response);
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
		try {
			MessageProperties mess = new MessageProperties();
			Common common = new Common();
			HttpSession session = request.getSession();
			TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
			MstGroupLogicImpl mstGroupLogic = new MstGroupLogicImpl();
			// tạo danh sách lưu các đối tượng MstGroup
			List<MstGroup> lstGroup = new ArrayList<>();
			// Danh sách lưu thông tin user
			List<UserInfor> lstUserInfor = new ArrayList<>();
			// danh sách lưu các page cần hiển thị
			List<Integer> lstPaging = new ArrayList<>();
			

			// tạo giá trị default để gửi đến trang ADM002
			String name = Constant.NAME_DEFAULT;
			String sortType = Constant.SORTTYPE_DEFAULT;
			String sortByFullname = Constant.SORTBYFULLNAME_DEFAULT;
			String sortByCodeLevel = Constant.SORTBYCODELEVEL_DEFAULT;
			String sortByEndDate = Constant.SORTBYENDDATE_DEFAULT;
			// số lượng recore tối đa trên một trang
			int limit = common.getLimit();
			int groupid = Constant.GROUP_ID_DEFAULT;
			int currentPage = Constant.CURRENTPAGE_DEFAULT;
			// vị trí data cần lấy
			int offSet = Constant.OFFSET_DEFAULT;
			int totalPage = Constant.TOTALPAGE_DEFAULT;
			int totalUser = Constant.TOTALUSER_DEFAULT;
			// xét type gửi đến
			String type = request.getParameter("type");
			
			// Trường hợp type là search
			if (Constant.SEARCH_TYPE.equals(type)) {
				// Lấy name và group id từ request gửi đến
				name = request.getParameter("name");
				groupid = common.parseInt(request.getParameter("group_id"), 0);

				// Trường hợp type là sort
			} else if (Constant.SORT_TYPE.equals(type)) {
				// lấy thuộc tính trong session
				name = (String) session.getAttribute("name");
				groupid = (int) session.getAttribute("group_id");
				
				sortByFullname = (String) session.getAttribute("sortByFullname");
				sortByCodeLevel = (String) session.getAttribute("sortByCodeLevel");
				sortByEndDate = (String) session.getAttribute("sortByEndDate");
				currentPage = Constant.CURRENTPAGE_DEFAULT;		

				sortType = request.getParameter("sortType");

				// sắp xếp ưu tiên full name
				if (Constant.FULLNAME_SORT.equals(sortType)) {
					// thay đổi trạng thái sort của full name
					sortByFullname = common.changeType(sortByFullname);
					// các thành phần khác về mặc định
					sortByCodeLevel = Constant.SORTBYCODELEVEL_DEFAULT;
					sortByEndDate = Constant.SORTBYENDDATE_DEFAULT;
					// sắp xếp ưu tiên code_level
				} else if (Constant.CODELEVEL_SORT.equals(sortType)) {
					// thay đổi trạng thái sort của code level
					sortByCodeLevel = common.changeType(sortByCodeLevel);
					// các thành phần khác về mặc định
					sortByEndDate = Constant.SORTBYENDDATE_DEFAULT;
					sortByFullname = Constant.SORTBYFULLNAME_DEFAULT;
					// sắp xếp ưu tiên end_date
				} else if (Constant.ENDDATE_SORT.equals(sortType)) {
					// thay đổi trạng thái sort của end_date
					sortByEndDate = common.changeType(sortByEndDate);
					// các thành phần khác về mặc định
					sortByFullname = Constant.SORTBYFULLNAME_DEFAULT;
					sortByCodeLevel = Constant.SORTBYCODELEVEL_DEFAULT;
				}

				// Trường hợp paging
			} else if (Constant.PAGING_TYPE.equals(type)) {
				name = (String) session.getAttribute("name");
				groupid = (int) session.getAttribute("group_id");
				sortType = (String) session.getAttribute("sortType");
				sortByFullname = (String) session.getAttribute("sortByFullname");
				sortByCodeLevel = (String) session.getAttribute("sortByCodeLevel");
				sortByEndDate = (String) session.getAttribute("sortByEndDate");
				
				currentPage = common.parseInt(request.getParameter("currentPage"), 1);
				
				//Trường hợp back
			}else if(Constant.BACK_TYPE.equals(type)) {
				name = (String) session.getAttribute("name");
				groupid = (int) session.getAttribute("group_id");
				sortType = (String) session.getAttribute("sortType");
				sortByFullname = (String) session.getAttribute("sortByFullname");
				sortByCodeLevel = (String) session.getAttribute("sortByCodeLevel");
				sortByEndDate = (String) session.getAttribute("sortByEndDate");
				currentPage = (int) session.getAttribute("currentPage");
				// nếu type là null thì đến trang ADM002 mặc định
			} 
			
			// xét giá trị tìm kiếm lên session
			session.setAttribute("name", name);
			session.setAttribute("group_id", groupid);
			session.setAttribute("sortType", sortType);
			session.setAttribute("sortByFullname", sortByFullname);
			session.setAttribute("sortByCodeLevel", sortByCodeLevel);
			session.setAttribute("sortByEndDate", sortByEndDate);
			session.setAttribute("currentPage", currentPage);
			
			request.setAttribute("lstPaging", lstPaging);
			request.setAttribute("totalPage", totalPage);
			
			totalUser = tblUserLogic.getTotalUser(groupid, name);
			// Lấy vị trí cần lấy data
			offSet = common.getOffset(currentPage, limit);
			// Tổng số page lấy được theo trường hợp search
			totalPage = common.getTotalPage(totalUser, limit);
			// Lấy ra danh sách paging
			lstPaging = new ArrayList<>();
			lstPaging = common.getListPaging(totalUser, limit, currentPage);
			// lấy danh dách các group có trong database
			lstGroup = mstGroupLogic.getAllGroup();

			// Nếu tổng số user lấy ra > 0
			if (totalUser > 0) {
				// lấy danh sách user
				lstUserInfor = tblUserLogic.getListUser(offSet, limit, groupid, name, sortType, sortByFullname,
						sortByCodeLevel, sortByEndDate);
				// Nếu không có user nào được lấy ra
			} else {
				// Lấy message không có user để hiển thị màn 02
				String message = mess.getMessageProperties(Constant.MESS_ADM002_NORECORD);
				request.setAttribute("message", message);
			}

			// Xét các giá trị về danh sách user tìm thấy và danh sách nhóm lên request
			request.setAttribute("lstUserInfo", lstUserInfor);
			request.setAttribute("lstGroup", lstGroup);
			// gửi đến màn 02
			request.getRequestDispatcher(Constant.ADM002).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

}
