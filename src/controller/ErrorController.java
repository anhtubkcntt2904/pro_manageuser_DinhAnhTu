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
public class ErrorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ErrorController() {
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
		MessageProperties mess = new MessageProperties();
		String error = mess.getMessageProperties("ER015");
		request.setAttribute("error", error);
		RequestDispatcher req = request.getRequestDispatcher("/jsp/System_Error.jsp");
		req.forward(request, response);
	}

}
