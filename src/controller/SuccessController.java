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
			RequestDispatcher req = request.getRequestDispatcher(Constant.ERROR);
			req.forward(request, response);
			break;
		case Constant.INSERT_SUCCESS:
			break;
		default:
			break;
		}
	}

}
