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
 * Servlet implementation class deleteExpense
 */
@WebServlet("/deleteExpense")
public class deleteExpense extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String id;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public deleteExpense() {
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		id = request.getParameter("id");
		deleteData();
		response.getWriter().append(id + " törölve").append(request.getContextPath());
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

	protected void deleteData() throws IOException, ServletException {
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
				String update = "UPDATE " + sqlDatabase.expenses()
						+ " SET inaktiv='1' WHERE idexpenses="+id;
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
}
