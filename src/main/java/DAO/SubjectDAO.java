package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    // 添加新学科
    public void addSubject(Subject subject) throws SQLException {
        String query = "INSERT INTO Subject (name, type, level, parent_subject_id, description) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, subject.getName());
            stmt.setString(2, subject.getType());
            stmt.setInt(3, subject.getLevel());
            stmt.setObject(4, subject.getParentSubjectId());  // 使用 setObject 以支持 null 值
            stmt.setString(5, subject.getDescription());
            stmt.executeUpdate();
        }
    }

    // 根据 subject_id 获取学科信息
    public Subject getSubjectById(int subjectId) throws SQLException {
        String query = "SELECT * FROM Subject WHERE subject_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, subjectId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Subject subject = new Subject();
                subject.setSubject_id(rs.getInt("subject_id"));
                subject.setName(rs.getString("name"));
                subject.setType(rs.getString("type"));
                subject.setLevel(rs.getInt("level"));
                subject.setParentSubjectId((Integer) rs.getObject("parent_subject_id"));  // 支持 null 值
                subject.setDescription(rs.getString("description"));
                return subject;
            }
        }
        return null;  // 未找到该学科
    }

    // 更新学科信息
    public void updateSubject(Subject subject) throws SQLException {
        String query = "UPDATE Subject SET name = ?, type = ?, level = ?, parent_subject_id = ?, description = ? WHERE subject_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, subject.getName());
            stmt.setString(2, subject.getType());
            stmt.setInt(3, subject.getLevel());
            stmt.setObject(4, subject.getParentSubjectId());
            stmt.setString(5, subject.getDescription());
            stmt.setInt(6, subject.getSubject_id());
            stmt.executeUpdate();
        }
    }
    // 获取所有的学科信息
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM Subject"; 

        try (Connection connection = DatabaseConnection.getConnection(); 
             Statement stmt = connection.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubject_id(rs.getInt("subject_id"));
                subject.setName(rs.getString("name"));
                subject.setType(rs.getString("type"));
                subject.setLevel(rs.getInt("level"));
                subject.setParentSubjectId(rs.getInt("parent_subject_id"));
                subject.setDescription(rs.getString("description"));
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }
}
