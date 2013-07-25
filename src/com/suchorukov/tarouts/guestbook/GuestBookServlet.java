package com.suchorukov.tarouts.guestbook;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/guestbook")
public class GuestBookServlet extends javax.servlet.http.HttpServlet {

	@Resource(name = "JDBC/GuestBook")
	private DataSource ds;

	private GuestBookController getController() throws SQLException, ClassNotFoundException {
		GuestBookController controller = (GuestBookController) getServletContext().getAttribute("controller");
		if (controller == null) {
			controller = new GuestBookControllerImpl(ds);
			getServletContext().setAttribute("controller", controller);
		}
		return controller;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			GuestBookController controller = getController();
			controller.addRecord(request.getParameter("userName"), request.getParameter("message"));
			response.sendRedirect("/guestbook");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			GuestBookController controller = getController();

			request.setAttribute("list", controller.getRecords());

			RequestDispatcher rd = getServletContext().getRequestDispatcher("/posts.jsp");
			rd.forward(request, response);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
