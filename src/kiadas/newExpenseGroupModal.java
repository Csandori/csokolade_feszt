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
 * Servlet implementation class newExpenseGroupModal
 */
@WebServlet("/newExpenseGroupModal")
public class newExpenseGroupModal extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String eredmeny = "";
	String[] csoportok;
	String[] focsoportok;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public newExpenseGroupModal() {
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
		eredmeny="";
		getMainGroups();
		getGroups();
		getModal();
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

	protected void getModal() {
		eredmeny += "<div class=\"modal\" id=\"newExpense\" tabindex=\"-1\" role=\"dialog\"\r\n"
				+ "		aria-labelledby=\"modifyEmployeeModalCenterTitle\" aria-hidden=\"true\">\r\n"
				+ "		<div class=\"modal-dialog modal-dialog-centered\" role=\"document\">\r\n"
				+ "			<div class=\"modal-content\">\r\n" + "				<div class=\"modal-header\">\r\n"
				+ "					<h2 class=\"modal-title\" id=\"exampleModalLongTitle\">Kiadás\r\n"
				+ "						szerkesztése</h2>\r\n" + "				</div>\r\n"
				+ "				<div class=\"modal-body\" id=\"modal-body\">" + modalBody() + "</div>\r\n"
				+ "				<div class=\"modal-footer\">\r\n" + "					<button type=\"button\"\r\n"
				+ "						class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect\"\r\n"
				+ "						data-dismiss=\"modal\">Mégse</button>\r\n"
				+ "					<button id=\"saveButton\" type=\"button\"\r\n"
				+ "						class=\"mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent\"\r\n"
				+ "						onclick=\"saveExpense()\">Mentés</button>\r\n" + "				</div>\r\n"
				+ "			</div>\r\n" + "		</div>\r\n" + "	</div>";
	}

	protected String modalBody() {
		String eredmeny = "<form>\r\n"
				+ "  <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label is-dirty is-upgraded\" data-upgraded=\",MaterialTextfield\"><input name=\"name\" class=\"mdl-textfield__input\" id=\"name\" type=\"text\"><label class=\"mdl-textfield__label\" for=\"name\">Név</label></div>\r\n"
				+ "  <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label is-dirty is-upgraded\" data-upgraded=\",MaterialTextfield\"><input name=\"value\" class=\"mdl-textfield__input\" pattern=\"-?[0-9]*(\\.[0-9]+)?\" id=\"value\" type=\"text\" value=\"0\"><label class=\"mdl-textfield__label\" for=\"value\">Összeg</label><span class=\"mdl-textfield__error\">Számot adj meg!</span></div>\r\n"
				+ "  <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label is-dirty is-upgraded\"\r\n"
				+ "    data-upgraded=\",MaterialTextfield\"><input name=\"date\" class=\"mdl-textfield__input\" id=\"date\" type=\"date\"><label class=\"mdl-textfield__label\" for=\"date\">Dátum</label></div>\r\n"
				+ "  <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label is-dirty is-upgraded\"\r\n"
				+ "    data-upgraded=\",MaterialTextfield\"><input name=\"note\" class=\"mdl-textfield__input\" id=\"note\" type=\"text\"><label class=\"mdl-textfield__label\" for=\"note\">Megjegyzés</label></div>\r\n"
				+ "  <div class=\"mdl-textfield mdl-js-textfield mdl-textfield--floating-label is-dirty is-upgraded\"\r\n"
				+ "    data-upgraded=\",MaterialTextfield\">\r\n"
				+ "    <select class=\"mdl-textfield__input\" id=\"focsoport\" name=\"focsoport\">\r\n" + options()
				+ "</select> <label class=\"mdl-textfield__label\" for=\"focsoport\">Csoport</label>\r\n"
				+ "  </div>\r\n" + "</form>";

		return eredmeny;
	}

	protected String options() {
		String eredmeny = "";
			for (int i = 1; i < csoportok.length; i++) {
				eredmeny += "<option";
				eredmeny += " value=\"" + i + "\">";
				eredmeny += csoportok[i];
				eredmeny += "</option>";
			}
		return eredmeny;
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
