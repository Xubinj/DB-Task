package DAO;
import java.sql.*;

public class UserAccountDAO {

		
	// 根据用户名、密码和 id_number 获取用户，用于登录验证
	public UserAccount getUserByUsernameAndPasswordAndIdNumber(String username, String passwordHash, String id_number) throws SQLException {
	    String query = "SELECT * FROM UserAccount WHERE username = ? AND password_hash = ? AND id_number = ?";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, username);
	        stmt.setString(2, passwordHash);
	        stmt.setString(3, id_number);  // 设置 id_number

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            UserAccount user = new UserAccount();
	            user.setUsername(rs.getString("username"));
	            user.setPasswordHash(rs.getString("password_hash"));
	            user.setRole(rs.getString("role"));
	            user.setEmail(rs.getString("email"));
	            user.setId_number(rs.getString("id_number"));  // 获取 id_number
	            return user;
	        }
	    }
	    return null;  // 用户不存在或密码错误
	}
	
    // 根据用户名和密码获取用户，用于登录验证
    public UserAccount getUserByUsernameAndPassword(String username, String passwordHash) throws SQLException {
        String query = "SELECT * FROM UserAccount WHERE username = ? AND password_hash = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserAccount user = new UserAccount();
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        }
        return null;  // 用户不存在或密码错误
    }

    // 根据用户名获取用户信息
    public UserAccount getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM UserAccount WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserAccount user = new UserAccount();
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        }
        return null;  // 用户不存在
    }
}
