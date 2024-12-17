package DAO;

import java.sql.*;
import java.util.*;

public class AdvisorSelectionOrderDAO {

    // 获取所有符合条件的导师，判断他们是否可以进入自由选择阶段
    public List<Advisor> getEligibleAdvisors() throws SQLException {
        String query = "SELECT advisor_id, remaining_quota FROM Advisor WHERE remaining_quota > 0"; // 查询剩余名额大于0的导师

        List<Advisor> eligibleAdvisors = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Advisor advisor = new Advisor();
                advisor.setAdvisorId(rs.getInt("advisor_id"));
                advisor.setRemainingQuota(rs.getInt("remaining_quota"));
                eligibleAdvisors.add(advisor);
            }
        }
        return eligibleAdvisors;
    }

    // 插入导师的自由选择时间段记录
    public void insertSelectionTimeForAdvisor(int advisorId, Timestamp startTime, Timestamp endTime) throws SQLException {
        String query = "INSERT INTO AdvisorSelectionOrder (advisor_id, free_selection_start, free_selection_end) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            stmt.setTimestamp(2, startTime);
            stmt.setTimestamp(3, endTime);
            stmt.executeUpdate();
        }
    }

    // 更新导师的自由选择时间段
    public void updateSelectionTimeForAdvisor(int advisorId, Timestamp startTime, Timestamp endTime) throws SQLException {
        String query = "UPDATE AdvisorSelectionOrder SET free_selection_start = ?, free_selection_end = ? WHERE advisor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, startTime);
            stmt.setTimestamp(2, endTime);
            stmt.setInt(3, advisorId);
            stmt.executeUpdate();
        }
    }

    // 检查导师是否已有时间分配
    public boolean hasAssignedTime(int advisorId) throws SQLException {
        String query = "SELECT COUNT(*) FROM AdvisorSelectionOrder WHERE advisor_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    // 获取已分配的开始时间
    public Timestamp getAssignedStartTime(int advisorId) throws SQLException {
        String query = "SELECT free_selection_start FROM AdvisorSelectionOrder WHERE advisor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("free_selection_start");
            }
        }
        return null;
    }


    // 获取已分配的结束时间
    public Timestamp getAssignedEndTime(int advisorId) throws SQLException {
        String query = "SELECT free_selection_end FROM AdvisorSelectionOrder WHERE advisor_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("free_selection_end");
            }
        }
        return null;
    }

    // 为导师分配时间
    public void assignTime(int advisorId, Timestamp startTime, Timestamp endTime) throws SQLException {
        String query = "INSERT INTO AdvisorSelectionOrder (advisor_id, free_selection_start, free_selection_end) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            stmt.setTimestamp(2, startTime);  // 使用 Timestamp
            stmt.setTimestamp(3, endTime);    // 使用 Timestamp
            stmt.executeUpdate();
        }
    }

    // 修改导师的时间
    public void modifyTime(int advisorId, Timestamp startTime, Timestamp endTime) throws SQLException {
        String query = "UPDATE AdvisorSelectionOrder SET free_selection_start = ?, free_selection_end = ? WHERE advisor_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, startTime);  // 使用 Timestamp
            stmt.setTimestamp(2, endTime);    // 使用 Timestamp
            stmt.setInt(3, advisorId);
            stmt.executeUpdate();
        }
    }
}
