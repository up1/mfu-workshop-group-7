package api.servlet;

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

import org.codehaus.jackson.map.ObjectMapper;

import api.entity.Account;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/wallet";

	static final String USER = "user01";
	static final String PASS = "xitgmLwmp";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		try {
			// Register JDBC driver
			Class.forName(MYSQL_DRIVER);

			// Open a connection
			Connection conn = DriverManager.getConnection(MYSQL_URL, USER, PASS);

			// Execute SQL query
			Statement stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM wallet.account where userId = 1";
			ResultSet rs = stmt.executeQuery(sql);

			Account account = new Account();
			while (rs.next()) {
				account.setBalance(rs.getDouble("balance"));
				account.setName(rs.getString("name"));
				account.setUserId(rs.getInt("userId"));
			}

			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(res.getOutputStream(), account);
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
