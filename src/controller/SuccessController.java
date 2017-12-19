/**
 * Copyright(C) 2017 Luvina
 * SuccessController.java, 16/10/2017 Đinh Anh Tú
 */
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Constant;
import properties.MessageProperties;

/**
 * Servlet implementation class ErrorController
 */
public class SuccessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SuccessController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// đương dẫn đến trang thông báo
		String url;
		// câu thông báo
		String noti;
		// xét type gửi đến
		String type = request.getParameter("type");
		switch (type) {
		// trường hợp vào màn hình error
		case Constant.SYSTEM_ERROR:
			noti = MessageProperties.getMessageProperties("ER015");
			//Xét câu thông báo lên request
			request.setAttribute("error", noti);
			url = Constant.ERROR;
			break;
		// trường hợp vào màn hình insert thành công
		case Constant.INSERT_SUCCESS:
			noti = MessageProperties.getMessageProperties("MSG001");
			//Xét câu thông báo lên request
			request.setAttribute("mess", noti);
			url = Constant.SUCCESS;
			break;
		// trường hợp vào màn hình delete thành công
		case Constant.DELETE_SUCCESS:
			noti = MessageProperties.getMessageProperties("MSG003");
			//Xét câu thông báo lên request
			request.setAttribute("mess", noti);
			url = Constant.SUCCESS;
			break;
		// trường hợp vào màn hình update thành công
		case Constant.UPDATE_SUCCESS:
			noti = MessageProperties.getMessageProperties("MSG002");
			//Xét câu thông báo lên request
			request.setAttribute("mess", noti);
			url = Constant.SUCCESS;
			break;
		// trường hợp vào màn hình không có user nào
		case Constant.UPDATE_NOUSER:
			noti = MessageProperties.getMessageProperties("ER013");
			//Xét câu thông báo lên request
			request.setAttribute("error", noti);
			url = Constant.ERROR;
			break;
		// trường hợp vào màn hình error
		default:
			noti = MessageProperties.getMessageProperties("ER015");
			//Xét câu thông báo lên request
			request.setAttribute("error", noti);
			url = Constant.ERROR;
			break;
		}
		//gửi đến trang tương ứng
		RequestDispatcher req = request.getRequestDispatcher(url);
		req.forward(request, response);
	}

}
