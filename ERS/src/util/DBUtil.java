package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class DBUtil {
	static String url, username, password;
	static Connection conn = null;
	static BasicDataSource connectionPool= new BasicDataSource();
	static {
		Properties properties= new Properties();
		try {
			properties.load(new FileReader("C:\\Users\\Johnny Tran\\eclipse-workspace\\ERS\\src\\resource\\database.properties"));
			url = properties.getProperty("url");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
			connectionPool.setUrl(url);
			connectionPool.setUsername(username);
			connectionPool.setPassword(password);
			connectionPool.setMaxTotal(20);
			connectionPool.setDefaultAutoCommit(false);
			
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file");
		} catch (IOException e) {
			System.out.println("Error reading file");
		}
	}
	
	public static Connection getInstance() throws SQLException {
		try {
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/TestDB");
		Connection conn = ds.getConnection();
		System.out.println(conn);
		return conn;
		}catch (NamingException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static void main(String[] args) throws SQLException {
		Connection conn = DBUtil.getInstance();
		System.out.println(conn);
		
		Statement st = conn.createStatement();
		ResultSet resultSet = st.executeQuery("select * from bank_accounts");
		while(resultSet.next()) {
			int empNum = resultSet.getInt("account_id");
			String name = resultSet.getString("username");
			System.out.println(empNum + " " + name);
		}
		
		resultSet.close();
		st.close();
		conn.close();
	}
}
