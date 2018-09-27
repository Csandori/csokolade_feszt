package kiadas;

import java.io.IOException;
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

import WebOldal.sqlConnect;
import WebOldal.sqlDBs;

/**
 * Servlet implementation class returnModifyExpenseGroup
 */
@WebServlet("/returnModifyExpenseGroup")
public class returnModifyExpenseGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String eredmeny = "";
	int id;
	int focsoport;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public returnModifyExpenseGroup() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		eredmeny = "";
		id = Integer.parseInt(request.getParameter("modifyId"));
		focsoport = Integer.parseInt(request.getParameter("focsoport"));
		if (focsoport == 1) {

			getIfFocsoport();
		} else {
			getData();
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

	protected void getData() throws IOException, ServletException {
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
				rs = stmt.executeQuery("SELECT * FROM " + sqlDatabase.expense_groups() + " where id=" + id + "");
				while (rs.next()) {
					eredmeny += "<form>";
					eredmeny += "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input name=\"name\" class=\"mdl-textfield__input\" id=\"name\" type=\"text\" value=\"" + rs.getString(2)
							+ "\"/><label class=\"mdl-textfield__label\" for=\"name\">Név</label></div>";
					eredmeny += "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input name=\"limit\" class=\"mdl-textfield__input\" pattern=\"-?[0-9]*(\\.[0-9]+)?\" id=\"limit\" type=\"text\" value=\"" + rs.getString(3)
					+ "\"/><label class=\"mdl-textfield__label\" for=\"limit\">Limit</label><span class=\"mdl-textfield__error\">Számot adj meg!</span></div>";
					eredmeny += getFocsoportok(rs.getInt(4));
					eredmeny+="</form>";
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

	protected void getIfFocsoport() throws IOException, ServletException {
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
				rs = stmt.executeQuery("SELECT * FROM " + sqlDatabase.expense_maingroups() + " WHERE id=" + id);
				while (rs.next()) {
					eredmeny += "<form>";
					eredmeny += "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input name=\"name\" class=\"mdl-textfield__input\" id=\"name\" type=\"text\" value=\"" + rs.getString(2)
							+ "\"/><label class=\"mdl-textfield__label\" for=\"name\">Név</label></div>";
					eredmeny += "</form>";
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

	protected String getFocsoportok(int selected) throws IOException, ServletException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		sqlDBs sqlDatabase = new sqlDBs();
		String alcsoportSelect = "";
		int count = 1;
		try {
			try {
				sqlConnect connect = new sqlConnect();
				Class.forName(connect.getDriverName());
				con = DriverManager.getConnection(connect.getConnectDB(), connect.getConnectUN(),
						connect.getConnectP());
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT * FROM " + sqlDatabase.expense_maingroups() + "");
				alcsoportSelect += " <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\">\r\n" + 
						"    <select class=\"mdl-textfield__input\" id=\"focsoport\" name=\"focsoport\">\r\n";
				while (rs.next()) {
					alcsoportSelect += "<option";
					if (rs.getInt(1) == selected) {
						alcsoportSelect += " selected";
					}
					alcsoportSelect += " value=\""+rs.getString(1)+"\">";
					alcsoportSelect += rs.getString(2);
					alcsoportSelect += "</option>";

					count++;
				}
				alcsoportSelect += "</select>";
				alcsoportSelect += 	"    <label class=\"mdl-textfield__label\" for=\"focsoport\">Fõcsoport</label>\r\n" + 
						"  </div>";
				return alcsoportSelect;
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
