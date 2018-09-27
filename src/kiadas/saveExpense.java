package kiadas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import WebOldal.sqlConnect;
import WebOldal.sqlDBs;

/**
 * Servlet implementation class saveExpenseGroup
 */
@WebServlet("/saveExpense")
public class saveExpense extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String eredmeny = "";
	int id;
	int value;
	String note;
	String date;
	String name;
	int focsoport;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public saveExpense() {
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

		eredmeny = "";
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		name = request.getParameter("name");
		value = Integer.parseInt(request.getParameter("value"));
		focsoport = Integer.parseInt(request.getParameter("focsoport"));
		note = request.getParameter("note");
		date = request.getParameter("date");
		// módosítás
		if (request.getParameterMap().containsKey("id")) {
			id = Integer.parseInt(request.getParameter("id"));	
			saveModified();
		} else {
			// új
			save();
		}
		response.getWriter().append(eredmeny);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void save() throws IOException, ServletException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		sqlDBs sqlDatabase = new sqlDBs();
		try {
			try {
				sqlConnect connect = new sqlConnect();
				Class.forName(connect.getDriverName());
				con = DriverManager.getConnection(connect.getConnectDB(), connect.getConnectUN(),
						connect.getConnectP());
				stmt = con.createStatement();
				String update = "INSERT INTO " + sqlDatabase.expenses()
						+ " (name, value, csoport, note, date) VALUES ('" + name + "', '" + value + "', '"
						+ focsoport + "', '" + note + "', '"
						+ date + "')";
				PreparedStatement preparedStmt = con.prepareStatement(update);
				eredmeny=update;
				preparedStmt.executeUpdate();
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

	protected void saveModified() throws IOException, ServletException {
		sqlDBs sqlDatabase = new sqlDBs();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			try {
				sqlConnect connect = new sqlConnect();
				Class.forName(connect.getDriverName());
				con = DriverManager.getConnection(connect.getConnectDB(), connect.getConnectUN(),
						connect.getConnectP());
				stmt = con.createStatement();
				String update = "UPDATE " + sqlDatabase.expenses() + " SET name='" + name + "', note='" + note
						+ "', csoport='" + focsoport + "', value='" + value + "', date='" + date
						+ "' WHERE idexpenses='" + id + "'";
				PreparedStatement preparedStmt = con.prepareStatement(update);
				eredmeny += update;
				preparedStmt.executeUpdate();
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
