package UserManagement;

import WebOldal.sqlConnect;
import WebOldal.sqlDBs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(value = { "/login" })
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int level;
	int userid;

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (validate(name, password)) {
			HttpSession session = request.getSession();
			session.setAttribute("name", name);
			session.setAttribute("level", level);
			session.setAttribute("userid", userid);
			out.print("Sikeres bejelentkezés");
		} else {
			out.print("Rossz felhasználónév vagy jelszó!");
		}
	}

	Boolean validate(String name, String password) {
		if (name.equals("Andor") && password.equals("ho112tel")) {
			return true;
		} else {
			return false;
		}
	}
}
