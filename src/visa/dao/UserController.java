package visa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jackson.map.ObjectMapper;

import visa.entity.User;
import visa.general.ConnectionManager;

public class UserController implements visa.general.Constant{

	/**
	 * Return id of the user.
	 * if username or password is incorrect, return -1
	 * @param username
	 * @param password
	 * @return
	 */
	public int login(String username, String password){
		Connection conn;
		try {
			conn = ConnectionManager.getConnection();
			String sql = "SELECT u.id FROM user u  WHERE  u.username = '" + username + "' AND u.password = '" + password + "'" ;
	        PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet resultSet = ps.executeQuery();
	         resultSet.next();
	         int id = resultSet.getInt(1);
	         return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}

	public boolean register(String username, String password, String name,
			String department, String role, String position) {
		// TODO Auto-generated method stub
		Connection conn;
		try {
			conn = ConnectionManager.getConnection();
			String sql = "SELECT count(username) FROM user u  WHERE  u.username = '" + username +"'" ;
	        PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery();
	         rs.next();
	         if (rs.getInt(1) > 0){
	        	 return false;
	         }
	         String sql2 = "INSERT INTO `user`(`name`,`position`,`role`,`department`,`username`, `password`)VALUES('" + name + "','" + position + "','" + role + "','" + department + "','" + username + "','" + password + "')";
	         ps = conn.prepareStatement(sql2);
	         System.out.println (sql2);
	         ps.execute();
	        return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @param userID
	 * @return user information
	 */
	public String getUserInfo(String userID){
		String jsonString = "";
		User user = new User();
		try {
			Connection conn = ConnectionManager.getConnection();
			String sql = "SELECT * FROM user WHERE id="+ userID;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			user.setId(userID);
			user.setDepartment(rs.getString("department"));
			user.setRole(rs.getString("role"));
			user.setName(rs.getString("name"));
			user.setPosition(rs.getString("position"));
			ObjectMapper om = new ObjectMapper();
			jsonString += om.writeValueAsString(user);
			jsonString +="\n";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
		
	}
}
