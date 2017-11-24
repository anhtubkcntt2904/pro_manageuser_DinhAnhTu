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
		// TODO Auto-generated constructor stub
	}

	/**
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
		// TODO Auto-generated method stub
		try {
			Common common = new Common();
			TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
			// Danh sách lưu thông tin user
			List<UserInfor> lstUserInfor = new ArrayList<>();

			List<Integer> lstPaging = new ArrayList<>();
			// mặc định ban đầu trong list có một bản ghi
			/* lstPaging.add(1); */

			MessageProperties mess = new MessageProperties();

			// tạo giá trị default để gửi đến trang ADM002
			String group_id = Constant.GROUP_ID_DEFAULT;
			String name = Constant.NAME_DEFAULT;
			String sortType = Constant.SORTTYPE_DEFAULT;
			String sortByFullname = Constant.SORTBYFULLNAME_DEFAULT;
			String sortByCodeLevel = Constant.SORTBYCODELEVEL_DEFAULT;
			String sortByEndDate = Constant.SORTBYENDDATE_DEFAULT;
			int limit = common.getLimit(); // số lượng recore tối đa trên một trang
			int groupid;
			int currentPage = Constant.CURRENTPAGE_DEFAULT;
			int offSet = Constant.OFFSET_DEFAULT;
			int totalPage = Constant.TOTALPAGE_DEFAULT;

			// xét type gửi đến
			String type = request.getParameter("type");
			HttpSession session = request.getSession();

			// Trường hợp type là search
			if (Constant.SEARCH_TYPE.equals(type)) {
				name = request.getParameter("name");
				group_id = request.getParameter("group_id");
				groupid = Integer.parseInt(group_id);
				int totalUser = tblUserLogic.getTotalUser(groupid, name);
				offSet = common.getOffset(currentPage, limit);
				totalPage = common.getTotalPage(totalUser, limit);
				lstPaging = new ArrayList<>();
				lstPaging = common.getListPaging(totalUser, limit, currentPage);
			} else if (Constant.SORT_TYPE.equals(type)) {
				// lấy thuộc tính trong session
				name = (String) session.getAttribute("name");
				sortByFullname = (String) session.getAttribute("sortByFullname");
				sortByCodeLevel = (String) session.getAttribute("sortByCodeLevel");
				sortByEndDate = (String) session.getAttribute("sortByEndDate");
				group_id = (String) session.getAttribute("group_id");
				lstPaging = (List<Integer>) session.getAttribute("lstPaging");
				totalPage = (int) session.getAttribute("totalPage");
				currentPage = (int) session.getAttribute("currentPage");
				offSet = common.getOffset(currentPage, limit);

				groupid = Integer.parseInt(group_id);
				sortType = request.getParameter("sortType");

				// sắp xếp ưu tiên full name
				if (Constant.FULLNAME_SORT.equals(sortType)) {
					sortByFullname = common.changeType(sortByFullname);
					sortByCodeLevel = Constant.SORTBYCODELEVEL_DEFAULT;
					sortByEndDate = Constant.SORTBYENDDATE_DEFAULT;
					// sắp xếp ưu tiên code_level
				} else if (Constant.CODELEVEL_SORT.equals(sortType)) {
					sortByCodeLevel = common.changeType(sortByCodeLevel);
					sortByEndDate = Constant.SORTBYENDDATE_DEFAULT;
					sortByFullname = Constant.SORTBYFULLNAME_DEFAULT;
					// sắp xếp ưu tiên end_date
				} else if (Constant.ENDDATE_SORT.equals(sortType)) {
					sortByEndDate = common.changeType(sortByEndDate);
					sortByFullname = Constant.SORTBYFULLNAME_DEFAULT;
					sortByCodeLevel = Constant.SORTBYCODELEVEL_DEFAULT;
				}

				// nếu type là null thì đến trang ADM002 mặc định
			} else if (Constant.PAGING_TYPE.equals(type)) {
				name = (String) session.getAttribute("name");
				groupid = Integer.parseInt((String) session.getAttribute("group_id"));
				int totalUser = tblUserLogic.getTotalUser(groupid, name);
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
				offSet = common.getOffset(currentPage, limit);
				totalPage = common.getTotalPage(totalUser, limit);
				lstPaging = new ArrayList<>();
				lstPaging = common.getListPaging(totalUser, limit, currentPage);
			} else {
				groupid = Integer.parseInt(group_id); // group id mặc định = 0
				int totalUser = tblUserLogic.getTotalUser(groupid, name);
				lstPaging = new ArrayList<>();
				lstPaging = common.getListPaging(totalUser, limit, currentPage);
				totalPage = common.getTotalPage(totalUser, limit);
			}
			// xét giá trị tìm kiếm lên session
			session.setAttribute("lstPaging", lstPaging);
			session.setAttribute("totalPage", totalPage);
			session.setAttribute("currentPage", currentPage);
			session.setAttribute("name", name);
			session.setAttribute("group_id", group_id);
			session.setAttribute("sortByFullname", sortByFullname);
			session.setAttribute("sortByCodeLevel", sortByCodeLevel);
			session.setAttribute("sortByEndDate", sortByEndDate);

			MstGroupLogicImpl mstGroupLogic = new MstGroupLogicImpl();
			// tạo danh sách lưu các đối tượng MstGroup
			List<MstGroup> lstGroup = new ArrayList<>();
			// lấy danh dách các group có trong database
			lstGroup = mstGroupLogic.getAllGroup();

			// lấy danh sách user1
			lstUserInfor = tblUserLogic.getListUser(offSet, limit, groupid, name, sortType, sortByFullname,
					sortByCodeLevel, sortByEndDate);
			// nếu danh sách rỗng
			if (lstUserInfor.size() < 1) {
				String message = mess.getMessageProperties(Constant.MESS_ADM002_NORECORD);
				request.setAttribute("message", message);
			}
			request.setAttribute("lstUserInfo", lstUserInfor);
			request.setAttribute("lstGroup", lstGroup);
			request.getRequestDispatcher(Constant.ADM002).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(
					request.getContextPath() + Constant.SUCCESS_SERVLET + "?type=" + Constant.SYSTEM_ERROR);
		}
	}

}
