package com.cbjs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.cbjs.model.User;


public class AuthDao {
	private String jdbc_url = "jdbc:mysql://db:3306/konoha";
    private String jdbc_username = "root";
    private String jdbc_password = "root";
	
	public boolean validate(User user) throws ClassNotFoundException {
        boolean status = false;
        
        Class.forName("com.mysql.cj.jdbc.Driver");

        try {
        	Connection connection = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);

            String query = "Select * from users where username = '" + user.getUsername() + "' and password = '" + user.getPassword() + "'";
            Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(query);
            status = result.next();                     
        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return status;
    }
	
	public String getId(String username) throws ClassNotFoundException {
		String id = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		try {
			Connection connection = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
			String query = "Select id from users where username= '"+ username + "'";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			id = rs.getString("id");
			
		} catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
		
		return id;
	}
	
	public int registerUser(User user) throws ClassNotFoundException {
        String INSERT_USER_SQL = "INSERT INTO users" + " (username, password, email, bio, money, credit_card, avatar, role, kyc) VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?, ?	);";
        String GET_USER_ID_SQL = "SELECT id from users where username = ?";
        user.setEmail("newbie@konoha88.com");
        user.setBio("Introduce yourself");
        user.setMoney(1000);
        user.setCreditCard("**********");
        user.setAvatar("pain.jpg");
        user.setRole("player");
        user.setKyc(false);
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection connection = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
            preparedStatement.setString(1, user.getUsername());            
            preparedStatement.setString(2, user.getPassword());   
            preparedStatement.setString(3, user.getEmail()); 
            preparedStatement.setString(4, user.getBio()); 
            preparedStatement.setInt(5, user.getMoney()); 
            preparedStatement.setString(6, user.getCreditCard()); 
            preparedStatement.setString(7, user.getAvatar()); 
            preparedStatement.setString(8, user.getRole()); 
            preparedStatement.setBoolean(9, user.isKyc()); 
//            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(GET_USER_ID_SQL);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next()){            
            	
            	break;
            }
        } catch (SQLException e) {
        	printSQLException(e);
        }     
        return result;
    }
	
	public boolean checkUser(User user) throws ClassNotFoundException{
		boolean check = false;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
        	Connection connection = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);
        	String query = "Select * from users where username = '" + user.getUsername() + "'";
        	Statement st = connection.createStatement();
        	ResultSet rs = st.executeQuery(query);
        	check = !rs.next();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return check;
	}

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
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
