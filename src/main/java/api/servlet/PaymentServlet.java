package api.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import api.entity.Product;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
	
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/wallet";

	static final String USER = "root";
	static final String PASS = "123";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(req.getInputStream(), Map.class);

        System.out.println("Buy " + map.getProductName() + " by " + map.getName());

        try {
			// Register JDBC driver
			Class.forName(MYSQL_DRIVER);

			// Open a connection
			Connection conn = DriverManager.getConnection(MYSQL_URL, USER, PASS);

			
			// Execute SQL query
			Statement stmt = conn.createStatement();
			String sql;
			
			sql = "INSERT INTO wallet.receipt (orderId, description, orderDate, totalPrice, name) VALUES(" + stmt.getGeneratedKeys() + ", '" + map.getProductName() + "','" + new java.sql.Date(System.currentTimeMillis()) + "'," + map.getPrice() + ",'" + map.getName() + "')";
			
			PreparedStatement dbStatement = conn.prepareStatement(sql);
			dbStatement.executeUpdate();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        mapper.writeValue(res.getOutputStream(), map);
	}
	
}

class Map {
	private int userId;
	private Double balance;
	private String name;
	
	private int productId;
	private String productName;
	private Double price;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
