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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type = request.getParameter("type");
		switch (type) {
		case Constant.SYSTEM_ERROR:
			MessageProperties mess = new MessageProperties();
			String error = mess.getMessageProperties("ER015");
			request.setAttribute("error", error);
			RequestDispatcher reqError = request.getRequestDispatcher(Constant.ERROR);
			reqError.forward(request, response);
			break;
		case Constant.INSERT_SUCCESS:
			MessageProperties messinsert = new MessageProperties();
			String insertsuccess = messinsert.getMessageProperties("MSG001");
			request.setAttribute("mess", insertsuccess);
			RequestDispatcher reqInsert = request.getRequestDispatcher(Constant.SUCCESS);
			reqInsert.forward(request, response);
			break;
		case Constant.DELETE_SUCCESS:
			MessageProperties messdelete = new MessageProperties();
			String deletesuccess = messdelete.getMessageProperties("MSG003");
			request.setAttribute("mess", deletesuccess);
			RequestDispatcher reqDelete = request.getRequestDispatcher(Constant.SUCCESS);
			reqDelete.forward(request, response);
			break;
		case Constant.UPDATE_SUCCESS:
			MessageProperties messupdate = new MessageProperties();
			String updatesuccess = messupdate.getMessageProperties("MSG002");
			request.setAttribute("mess", updatesuccess);
			RequestDispatcher reqUpdate = request.getRequestDispatcher(Constant.SUCCESS);
			reqUpdate.forward(request, response);
			break;
		default:
			MessageProperties messD = new MessageProperties();
			String errorD = messD.getMessageProperties("ER015");
			request.setAttribute("error", errorD);
			RequestDispatcher reqD = request.getRequestDispatcher(Constant.ERROR);
			reqD.forward(request, response);
			break;
		}
	}

}
