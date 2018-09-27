package WebOldal;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(value={"/sqlConnect"})
public class sqlConnect
extends HttpServlet {
    private static final long serialVersionUID = 1;
    String driverName = "com.mysql.jdbc.Driver";
    String connectDB;
    String connectUN;
    String connectP;

    public sqlConnect() {
        boolean i = false;
        driverName = "com.mysql.jdbc.Driver";
        if (i) {
            connectDB = "jdbc:mysql://naturadent.cshfiwll9luq.us-east-2.rds.amazonaws.com:3306/beosztas?useUnicode=true&characterEncoding=UTF-8";
            connectUN = "andor";
            connectP = "andorjelszo";
        } else {
            connectDB = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7258056?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            connectUN = "sql7258056";
            connectP = "znmDWVB6tx";
        }
    }

    public  String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
    	this.driverName = driverName;
    }

    public String getConnectDB() {
        return connectDB;
    }

    public void setConnectDB(String connectDB) {
    	this.connectDB = connectDB;
    }

    public String getConnectUN() {
        return connectUN;
    }

    public void setConnectUN(String connectUN) {
    	this.connectUN = connectUN;
    }

    public String getConnectP() {
        return connectP;
    }

    public void setConnectP(String connectP) {
    	this. connectP = connectP;
    }
}