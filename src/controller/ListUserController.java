/**
 * Copyright(C) 2017 Luvina
 * ListUserController.java, 16/10/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Constant;
import dao.impl.MstGroupDaoImpl;
import dao.impl.TblUserDaoImpl;
import entity.MstGroup;
import entity.UserInfo;
import logic.impl.MstGroupLogicImpl;
import logic.impl.UserLogicImpl;

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
		// TODO Auto-generated method stub
		/*
		 * response.getWriter().append("Served at: ").append(request.getContextPath());
		 */
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		//tạo giá trị default cho name và group_id để gửi đến trang ADM002
		String group_id = Constant.GROUP_ID_DEFAULT;
		String name = Constant.NAME_DEFAULT;
		int groupid;
		
		//xét type gửi đến
		String type = request.getParameter("type");
		if ("search".equals(type)) { //nếu type là search thì chuyển đến ADM002 kèm param name và group_id 
			name = request.getParameter("name");
			group_id = request.getParameter("group_id");
			groupid = Integer.parseInt(group_id);
		}else { //nếu type là null thì đến trang ADM002 mặc định
			groupid = Integer.parseInt(group_id); //group id mặc định = 0
		}
		
		//xét giá trị tìm kiếm name và group id lên session
		HttpSession session=request.getSession();  
		session.setAttribute("name", name);
		session.setAttribute("group_id", group_id);
		
		MstGroupLogicImpl mstGroupLogic = new MstGroupLogicImpl();
		//tạo danh sách lưu các đối tượng MstGroup
		List<MstGroup> lstGroup = new ArrayList<>();
		//lấy danh dách các group có trong database
		lstGroup = mstGroupLogic.getAllGroup();
		
		UserLogicImpl tblUserLogic = new UserLogicImpl();
		//Danh sách lưu thông tin user 
		List<UserInfo> lstUserInfo = new ArrayList<>();
		//lấy danh sách user
		lstUserInfo = tblUserLogic.getListUser(0, 0, groupid, name, null, "asc", "asc", "desc");
		
		request.setAttribute("lstUserInfo", lstUserInfo);
		request.setAttribute("lstGroup", lstGroup);
		request.getRequestDispatcher(Constant.ADM002).forward(request, response);
	}

}
