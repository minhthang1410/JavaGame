package com.cbjs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.cbjs.model.User;

public class UserDao {
	private String jdbc_url = "jdbc:mysql://db:3306/konoha";
	private String jdbc_username = "root";
	private String jdbc_password = "root";

	public User getInfo(String id) throws ClassNotFoundException {
		User user = new User();
		user.setId(id);
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
			String query = "Select * from users where id = " + id;//sqli
			Statement st = connection.createStatement();
			ResultSet result = st.executeQuery(query);

			while (result.next()) {
				user.setId(result.getString("id"));
				user.setUsername(result.getString("username"));
				user.setEmail(result.getString("email"));
				user.setBio(result.getString("bio"));
				user.setMoney(result.getInt("money"));
				user.setCreditCard(result.getString("credit_card"));
				user.setAvatar(result.getString("avatar"));
				user.setRole(result.getString("role"));
				user.setKyc(result.getBoolean("kyc"));
				break;
			}

		} catch (SQLException e) {
			printSQLException(e);
		}

		return user;
	}

	public void updateAvatar(String id, String avatar) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
			String query = "UPDATE users SET avatar='" + avatar + "' WHERE id=" + id;
			Statement st = connection.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public void updateKYC(String id) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
			String query = "UPDATE users SET kyc=1 WHERE id=" + id;
			Statement st = connection.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public void updateProfile(String id, String bio, String credit_card, String email) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
			String query = "UPDATE users SET credit_card='" + credit_card + "', email='"+ email + "', bio='" + bio + "' WHERE id=" + id;//sqli
			Statement st = connection.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	public void updateMoney(String id, int money) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
			String query = "UPDATE users SET money=" + money + " WHERE id=" + id;//sqli
			Statement st = connection.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	public ArrayList<User> getTopUser() throws ClassNotFoundException{
		ArrayList<User> list = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			String query = "Select id, username, money from users order by money DESC limit 5";
			Connection connection = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			ResultSet rs = preparedStatement.getResultSet();
			while (rs.next()) {
				User user = new User(rs.getString("id"), rs.getString("username"), rs.getInt("money"));
				list.add(user);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return list;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
