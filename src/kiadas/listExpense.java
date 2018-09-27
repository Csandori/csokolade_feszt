package kiadas;

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

@WebServlet(value = { "/listExpense" })
public class listExpense extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		sqlDBs sqlDatabase = new sqlDBs();
		try {
			try {
				sqlConnect connect = new sqlConnect();
				Class.forName(connect.getDriverName());
				con = DriverManager.getConnection(connect.getConnectDB(), connect.getConnectUN(),
						connect.getConnectP());
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT * FROM " + sqlDatabase.expense_maingroups() + "");
				out.println(
						"<table id=\"employeeTable\" class=\"mdl-data-table mdl-js-data-table tablesorter\" align=\"center\">");
				out.println("<thead><tr>");
				out.println("<th>Kiadás</th>");
				out.println("<th>Limit</th>");
				out.println("<th>Jelenlegi összeg</th><th></th></tr></thead>");
				out.println("<tbody>");
				while (rs.next()) {
					out.println("<tr>");
					out.println("<td>");
					out.println(rs.getString(2));
					out.println("</td>");
					out.println("<td>");
					out.println("-");
					out.println("</td>");
					out.println("<td>");
					out.println("0");
					out.println("</td>");
					out.println("<td>");
					out.println("<button onclick=\"showModify('1', '" + rs.getString(1)
							+ "')\" class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored\">Módosítás</button>");

					out.println("</td>");
					out.println("</tr>");
					out.println(alcsoport(rs.getInt(1)));
				}
				out.println("</tbody>");
				out.println("</table>");
				out.println(
						"<button class=\"mdl-button mdl-js-button mdl-button--fab mdl-button--colored\">+</button>");
			} catch (SQLException e) {
				throw new ServletException("Servlet Could not display records.", (Throwable) e);
			} catch (ClassNotFoundException e) {
				throw new ServletException("JDBC Driver not found.", (Throwable) e);
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException sQLException) {
			}
		}
	}

	protected String alcsoport(int id) throws IOException, ServletException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		sqlDBs sqlDatabase = new sqlDBs();
		String eredmeny = "";
		try {
			try {
				sqlConnect connect = new sqlConnect();
				Class.forName(connect.getDriverName());
				con = DriverManager.getConnection(connect.getConnectDB(), connect.getConnectUN(),
						connect.getConnectP());
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT * FROM " + sqlDatabase.expense_groups() + " WHERE focsoport=" + id);
				while (rs.next()) {
					eredmeny += "<tr>";
					eredmeny += "<td><i class=\"material-icons\">\r\n" + 
							"subdirectory_arrow_right\r\n" + 
							"</i>";
					eredmeny += rs.getString(2);
					eredmeny += "</td>";
					eredmeny += "<td>";
					eredmeny += rs.getString(3);
					eredmeny += "</td>";
					eredmeny += "<td>";
					eredmeny += "0";
					eredmeny += "</td>";
					eredmeny += "<td>";
					eredmeny += "<button onclick=\"showModify('0','" + rs.getString(1)
							+ "')\" class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored\">Módosítás</button>";
					eredmeny += "</td>";
					eredmeny += "</tr>";
				}
				return eredmeny;
			} catch (SQLException e) {
				throw new ServletException("Servlet Could not display records.", (Throwable) e);
			} catch (ClassNotFoundException e) {
				throw new ServletException("JDBC Driver not found.", (Throwable) e);
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException sQLException) {
			}
		}
	}
}