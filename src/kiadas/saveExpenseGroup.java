package kiadas;

import java.io.IOException;
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
@WebServlet("/saveExpenseGroup")
public class saveExpenseGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String eredmeny = "";
	int id;
	String name;
	int limit;
	int focsoport;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public saveExpenseGroup() {
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
		// alcsoport
		if (request.getParameterMap().containsKey("limit")) {
			name = request.getParameter("name");
			id = Integer.parseInt(request.getParameter("groupid"));
			limit = Integer.parseInt(request.getParameter("limit"));
			focsoport = Integer.parseInt(request.getParameter("focsoport"));
			saveAlcsoport();
		} else {
			// Fõcsoport
			name = request.getParameter("name");
			id = Integer.parseInt(request.getParameter("groupid"));
			saveFocsoport();
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

	protected void saveFocsoport() throws IOException, ServletException {
		sqlDBs sqlDatabase = new sqlDBs();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String database = "";

		try {
			try {
				sqlConnect connect = new sqlConnect();
				Class.forName(connect.getDriverName());
				con = DriverManager.getConnection(connect.getConnectDB(), connect.getConnectUN(),
						connect.getConnectP());
				stmt = con.createStatement();
				String update = "UPDATE " + sqlDatabase.expense_maingroups() + " SET name='" + name + "' WHERE id='"
						+ id + "'";
				PreparedStatement preparedStmt = con.prepareStatement(update);
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

	protected void saveAlcsoport() throws IOException, ServletException {
		sqlDBs sqlDatabase = new sqlDBs();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String database = "";

		try {
			try {
				sqlConnect connect = new sqlConnect();
				Class.forName(connect.getDriverName());
				con = DriverManager.getConnection(connect.getConnectDB(), connect.getConnectUN(),
						connect.getConnectP());
				stmt = con.createStatement();
				String update = "UPDATE " + sqlDatabase.expense_groups() + " SET name='" + name + "', limitft='"
						+ limit + "', focsoport='" + focsoport + "' WHERE id='" + id + "'";
				PreparedStatement preparedStmt = con.prepareStatement(update);
				//eredmeny += update;
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
