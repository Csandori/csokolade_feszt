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
@WebServlet("/returnModifyExpense")
public class returnModifyExpense extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String eredmeny = "";
	int id;
	int focsoport;
	String[] csoportok;
	String[] focsoportok;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public returnModifyExpense() {
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

		getMainGroups();
		getGroups();
		id = Integer.parseInt(request.getParameter("id"));
		getData();

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
				rs = stmt.executeQuery("SELECT * FROM " + sqlDatabase.expenses() + " where idexpenses=" + id + "");
				while (rs.next()) {
					eredmeny += "<form>";
					eredmeny += "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input name=\"name\" class=\"mdl-textfield__input\" id=\"name\" type=\"text\" value=\""
							+ rs.getString(2)
							+ "\"/><label class=\"mdl-textfield__label\" for=\"name\">Név</label></div>";
					eredmeny += "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input name=\"value\" class=\"mdl-textfield__input\" pattern=\"-?[0-9]*(\\.[0-9]+)?\" id=\"value\" type=\"text\" value=\""
							+ rs.getString(3)
							+ "\"/><label class=\"mdl-textfield__label\" for=\"value\">Összeg</label><span class=\"mdl-textfield__error\">Számot adj meg!</span></div>";
					eredmeny += "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input name=\"date\" class=\"mdl-textfield__input\" id=\"date\" type=\"date\" value=\""
							+ rs.getString(6)
							+ "\"/><label class=\"mdl-textfield__label\" for=\"date\">Dátum</label></div>";
					eredmeny += "<div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\"><input name=\"note\" class=\"mdl-textfield__input\" id=\"note\" type=\"text\" value=\""
							+ rs.getString(5)
							+ "\"/><label class=\"mdl-textfield__label\" for=\"note\">Megjegyzés</label></div>";

					eredmeny += getFocsoportok(rs.getInt(4));
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
		String alcsoportSelect = "";
		alcsoportSelect += " <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label\">\r\n" + 
				"    <select class=\"mdl-textfield__input\" id=\"focsoport\" name=\"focsoport\">\r\n";
		for (int i = 1; i < csoportok.length; i++) {
			alcsoportSelect += "<option";
			if (i == selected) {
				alcsoportSelect += " selected";
			}
			alcsoportSelect += " value=\"" + i + "\">";
			alcsoportSelect += csoportok[i];
			alcsoportSelect += "</option>";
		}
		alcsoportSelect += "</select>";
		alcsoportSelect += "    <label class=\"mdl-textfield__label\" for=\"focsoport\">Csoport</label>\r\n"
				+ "  </div>";
		return alcsoportSelect;
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
					csoportok[rs.getInt(1)] = focsoportok[rs.getInt(4)] + " - " + rs.getString(2);
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
