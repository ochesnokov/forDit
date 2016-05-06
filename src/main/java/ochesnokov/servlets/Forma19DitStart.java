package ochesnokov.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ochesnokov.general.WorkDataBase;

/**
 * Servlet implementation class TestServlet2
 */
@WebServlet(name = "Forma19DitStart", urlPatterns = { "/Forma19DitStart" })
public class Forma19DitStart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	WorkDataBase wdb = WorkDataBase.getInstance();

	public Forma19DitStart() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/forma19Dit.jsp");
		if (dispatcher != null) {

			dispatcher.forward(request, response);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
