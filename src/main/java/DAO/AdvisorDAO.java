package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdvisorDAO {
	
    // 根据 username 查找导师的 advisor_id
    static public int getAdvisorIdByUsername(String username) throws SQLException {
        String query = "SELECT advisor_id FROM Advisor WHERE name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("advisor_id");
            }
        }
        throw new SQLException("导师未找到");
    }

    // 添加新的导师
    public void addAdvisor(Advisor advisor) throws SQLException {
        String query = "INSERT INTO Advisor (name, title, email, phone, department, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, advisor.getName());
            stmt.setString(2, advisor.getTitle());
            stmt.setString(3, advisor.getEmail());
            stmt.setString(4, advisor.getPhone());
            stmt.setString(5, advisor.getDepartment());
            stmt.setBoolean(6, advisor.isActive());
            stmt.executeUpdate();
        }
    }

    // 根据 advisor_id 获取导师信息
    public Advisor getAdvisorById(int advisorId) throws SQLException {
        String query = "SELECT * FROM Advisor WHERE advisor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Advisor advisor = new Advisor();
                advisor.setAdvisorId(rs.getInt("advisor_id"));
                advisor.setName(rs.getString("name"));
                advisor.setTitle(rs.getString("title"));
                advisor.setEmail(rs.getString("email"));
                advisor.setPhone(rs.getString("phone"));
                advisor.setDepartment(rs.getString("department"));
                advisor.setActive(rs.getBoolean("is_active"));
                advisor.setId_number(rs.getString("id_number"));  
                return advisor;
            }
        }
        return null;  // 未找到该导师
    }

    // 更新导师信息
    public void updateAdvisor(Advisor advisor) throws SQLException {
        String query = "UPDATE Advisor SET name = ?, title = ?, email = ?, phone = ?, department = ?, is_active = ? WHERE advisor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, advisor.getName());
            stmt.setString(2, advisor.getTitle());
            stmt.setString(3, advisor.getEmail());
            stmt.setString(4, advisor.getPhone());
            stmt.setString(5, advisor.getDepartment());
            stmt.setBoolean(6, advisor.isActive());
            stmt.setInt(7, advisor.getAdvisorId());
            stmt.executeUpdate();
        }
    }

    // 获取所有导师信息
    public List<Advisor> getAllAdvisors() throws SQLException {
        List<Advisor> advisors = new ArrayList<>();
        String query = "SELECT * FROM Advisor";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Advisor advisor = new Advisor();
                advisor.setAdvisorId(rs.getInt("advisor_id"));
                advisor.setName(rs.getString("name"));
                advisor.setTitle(rs.getString("title"));
                advisor.setEmail(rs.getString("email"));
                advisor.setPhone(rs.getString("phone"));
                advisor.setDepartment(rs.getString("department"));
                advisor.setActive(rs.getBoolean("is_active"));
                advisor.setRemainingQuota(rs.getInt("remaining_quota"));
                advisor.setTotalQuota(rs.getInt("total_quota"));
                advisors.add(advisor);
            }
        }
        return advisors;
    }
    
    //获取导师总招生指标
    public static int getTotalQuota(int advisorId) throws SQLException {
        String query = "SELECT total_quota FROM Advisor WHERE advisor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total_quota");
                }
            }
        }
        return 0;
    }
    
    //获取导师剩余招生名额
    public static int getRemainingQuota(int advisorId) throws SQLException {
        String query = "SELECT remaining_quota FROM Advisor WHERE advisor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("remaining_quota");
                }
            }
        }
        return 0;
    }

    //更新导师剩余招生名额
    public static void updateRemainingQuota(int advisorId) throws SQLException {
        String query = "UPDATE Advisor SET remaining_quota = remaining_quota - 1 WHERE advisor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            stmt.executeUpdate();
        }
    }

  //更新导师剩余招生名额
    public static void updateRemainingQuota2(int advisorId) throws SQLException {
        String query = "UPDATE Advisor SET remaining_quota = remaining_quota + 1 WHERE advisor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            stmt.executeUpdate();
        }
    }
    // 获取符合自由选择阶段条件的导师
    public List<Advisor> getEligibleAdvisors(String department) throws SQLException {
        String query = "SELECT * FROM Advisor WHERE remaining_quota > 0 AND department = ? ORDER BY remaining_quota DESC";
        List<Advisor> eligibleAdvisors = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)
             ) {
        	stmt.setString(1, department);
        	ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Advisor advisor = new Advisor();
                advisor.setAdvisorId(rs.getInt("advisor_id"));
                advisor.setName(rs.getString("name"));
                advisor.setRemainingQuota(rs.getInt("remaining_quota"));
                eligibleAdvisors.add(advisor);
            }
        }
        return eligibleAdvisors;
}
}
