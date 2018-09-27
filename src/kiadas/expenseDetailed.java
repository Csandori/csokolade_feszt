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

@WebServlet(value = { "/expenseDetailed" })
public class expenseDetailed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String[] csoportok;
	String[] focsoportok;

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		getMainGroups();
		getGroups();
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
				rs = stmt.executeQuery("SELECT * FROM " + sqlDatabase.expenses() + " WHERE inaktiv='0'");
				out.println(
						"<table id=\"employeeTable\" class=\"mdl-data-table mdl-js-data-table tablesorter\" align=\"center\">");
				out.println("<thead><tr>");
				out.println("<th>Csoport</th>");
				out.println("<th>Megnevezés</th>");
				out.println("<th>Összeg</th>");
				out.println("<th>Dátum</th><th>Megjegyzés</th><th></th></tr></thead>");
				out.println("<tbody>");
				while (rs.next()) {
					out.println("<tr>");
					out.println("<td>");
					out.println(csoportok[rs.getInt(4)]);
					out.println("</td>");
					out.println("<td>");
					out.println(rs.getString(2));
					out.println("</td>");
					out.println("<td>");
					out.println(rs.getString(3));
					out.println("</td>");
					out.println("<td>");
					out.println(rs.getString(6));
					out.println("</td>");
					out.println("<td>");
					out.println(rs.getString(5));
					out.println("</td>");
					out.println("<td>");
					out.println("<button onclick=\"showModify('" + rs.getString(1)
							+ "')\" class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored\"><i class=\"material-icons\">\r\n"
							+ "edit\r\n" + "</i></button>");
					out.println("<button onclick=\"deleteExpense('" + rs.getString(1)
							+ "')\" class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored\"><i class=\"material-icons\">\r\n"
							+ "delete_outline\r\n" + "</i></button>");
					out.println("</td>");
					out.println("</tr>");
				}
				out.println("</tbody>");
				out.println("</table>");
				out.println(
						"<button class=\"mdl-button mdl-js-button mdl-button--fab mdl-button--colored\" data-toggle=\"modal\" data-target=\"#newExpense\">+</button>");
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

	protected void getGroups() throws IOException, ServletException {
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
				rs = stmt.executeQuery("SELECT count(*) FROM " + sqlDatabase.expense_groups());
				while (rs.next()) {
					csoportok = new String[rs.getInt(1) + 1];
				}
				rs.close();
				rs = stmt.executeQuery("SELECT * FROM " + sqlDatabase.expense_groups());
				while (rs.next()) {
					csoportok[rs.getInt(1)] = focsoportok[rs.getInt(4)] + "-" + rs.getString(2);
				}
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

	protected void getMainGroups() throws IOException, ServletException {
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
				rs = stmt.executeQuery("SELECT count(*) FROM " + sqlDatabase.expense_maingroups());
				while (rs.next()) {
					focsoportok = new String[rs.getInt(1) + 1];
				}
				rs.close();
				rs = stmt.executeQuery("SELECT * FROM " + sqlDatabase.expense_maingroups());
				while (rs.next()) {
					focsoportok[rs.getInt(1)] = rs.getString(2);
				}
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