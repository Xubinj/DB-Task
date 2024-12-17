package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicantDAO {

	// 根据 id_number 查找学生的 applicant_id
	public int getApplicantIdByIdNumber(String idNumber) throws SQLException {
	    String query = "SELECT applicant_id FROM Applicant WHERE id_number = ?";
	    
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, idNumber);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("applicant_id");
	        }
	    }
	    throw new SQLException("未找到对应的学生，ID号：" + idNumber);
	}
	
	
	// 根据 username 查找学生的 id
    public int getApplicantIdByUsername(String username) throws SQLException {
        String query = "SELECT applicant_id FROM Applicant WHERE name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("applicant_id");
            }
        }
        throw new SQLException("学生未找到"+username);
    }
    
    
	 // 从 FinalAssignment 表中获取指定学生的导师ID 学生可能有导师 也可能没有
    public Integer getAssignedAdvisorByApplicantId(int applicantId) throws SQLException {
        String query = "SELECT advisor_id FROM FinalAssignment WHERE applicant_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, applicantId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("advisor_id");
            }
        }
        return null;
    }
    
    
    // 更新考生信息
    public void updateApplicant(int applicantId, float initialExamScore, float reExamScore) throws SQLException {
        String query = "UPDATE Applicant SET initial_exam_score = ?, re_exam_score = ? WHERE applicant_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setFloat(1, initialExamScore);
            stmt.setFloat(2, reExamScore);
            stmt.setInt(3, applicantId);
            stmt.executeUpdate();
        }
    }
    // 添加新的考生信息
    public void addApplicant(Applicant applicant) throws SQLException {
        String query = "INSERT INTO Applicant (name, id_number, birth_date, undergraduate_school, undergraduate_major, initial_exam_score, re_exam_score, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, applicant.getName());
            stmt.setString(2, applicant.getIdNumber());
            stmt.setDate(3, applicant.getBirthDate());
            stmt.setString(4, applicant.getUndergraduateSchool());
            stmt.setString(5, applicant.getUndergraduateMajor());
            stmt.setFloat(6, applicant.getInitialExamScore());
            stmt.setFloat(7, applicant.getReExamScore());
            stmt.setString(8, applicant.getPhone());
            stmt.setString(9, applicant.getEmail());
            stmt.executeUpdate();
        }
    }

    // 根据 applicant_id 获取考生信息
    public Applicant getApplicantById(int applicantId) throws SQLException {
        String query = "SELECT * FROM Applicant WHERE applicant_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, applicantId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Applicant applicant = new Applicant();
                applicant.setApplicantId(rs.getInt("applicant_id"));
                applicant.setName(rs.getString("name"));
                applicant.setIdNumber(rs.getString("id_number"));
                applicant.setBirthDate(rs.getDate("birth_date"));
                applicant.setUndergraduateSchool(rs.getString("undergraduate_school"));
                applicant.setUndergraduateMajor(rs.getString("undergraduate_major"));
                applicant.setInitialExamScore(rs.getFloat("initial_exam_score"));
                applicant.setReExamScore(rs.getFloat("re_exam_score"));
                applicant.setPhone(rs.getString("phone"));
                applicant.setEmail(rs.getString("email"));
                return applicant;
            }
        }
        return null;  // 未找到该考生
    }

    // 更新考生信息
	/*
	 * public void updateApplicant(Applicant applicant) throws SQLException { String
	 * query =
	 * "UPDATE Applicant SET name = ?, id_number = ?, birth_date = ?, undergraduate_school = ?, undergraduate_major = ?, initial_exam_score = ?, re_exam_score = ?, phone = ?, email = ? WHERE applicant_id = ?"
	 * ; try (Connection conn = DatabaseConnection.getConnection();
	 * PreparedStatement stmt = conn.prepareStatement(query)) { stmt.setString(1,
	 * applicant.getName()); stmt.setString(2, applicant.getIdNumber());
	 * stmt.setDate(3, applicant.getBirthDate()); stmt.setString(4,
	 * applicant.getUndergraduateSchool()); stmt.setString(5,
	 * applicant.getUndergraduateMajor()); stmt.setFloat(6,
	 * applicant.getInitialExamScore()); stmt.setFloat(7,
	 * applicant.getReExamScore()); stmt.setString(8, applicant.getPhone());
	 * stmt.setString(9, applicant.getEmail()); stmt.setInt(10,
	 * applicant.getApplicantId()); stmt.executeUpdate(); } }
	 */

    // 获取所有考生信息
    public List<Applicant> getAllApplicants() throws SQLException {
        List<Applicant> applicants = new ArrayList<>();
        String query = "SELECT * FROM Applicant";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Applicant applicant = new Applicant();
                applicant.setApplicantId(rs.getInt("applicant_id"));
                applicant.setName(rs.getString("name"));
                applicant.setIdNumber(rs.getString("id_number"));
                applicant.setBirthDate(rs.getDate("birth_date"));
                applicant.setUndergraduateSchool(rs.getString("undergraduate_school"));
                applicant.setUndergraduateMajor(rs.getString("undergraduate_major"));
                applicant.setInitialExamScore(rs.getFloat("initial_exam_score"));
                applicant.setReExamScore(rs.getFloat("re_exam_score"));
                applicant.setPhone(rs.getString("phone"));
                applicant.setEmail(rs.getString("email"));
                applicants.add(applicant);
            }
        }
        return applicants;
    }
 // 更新学生的状态
    public boolean updateStudentStatus(int applicantId, String status) throws SQLException {
        String query = "UPDATE Applicant SET status = ? WHERE applicant_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);  // 设置状态为“已选择”或“未选择”
            stmt.setInt(2, applicantId);  // 设置学生 ID
            return stmt.executeUpdate() > 0;  // 执行更新操作，返回更新是否成功
        }
    }
    // 获取自由选择阶段的未被选择的学生
    public List<Applicant> getFreeChoiceStudents() throws SQLException {
        String query = "SELECT applicant_id, name, initial_exam_score, re_exam_score, undergraduate_major, email, phone " +
                       "FROM Applicant " +
                       "WHERE status = '未选择'";  // 只筛选出状态为'未选择'的学生

        List<Applicant> applicants = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Applicant applicant = new Applicant();
                    applicant.setApplicantId(rs.getInt("applicant_id"));
                    applicant.setName(rs.getString("name"));
                    applicant.setInitialExamScore(rs.getFloat("initial_exam_score"));
                    applicant.setReExamScore(rs.getFloat("re_exam_score"));
                    applicant.setUndergraduateMajor(rs.getString("undergraduate_major"));
                    applicant.setEmail(rs.getString("email"));
                    applicant.setPhone(rs.getString("phone"));
                    applicants.add(applicant);
                }
            }
        }
        return applicants;
    }
    
  //根据idnumber 来查找复试成绩
  	public float getApplicantReExamScore(String idnumber) throws SQLException {
  		String query = "SELECT re_exam_score FROM Applicant WHERE id_number = ?";
  		try (Connection conn = DatabaseConnection.getConnection();
  		         PreparedStatement stmt = conn.prepareStatement(query)) {
  		        stmt.setString(1, idnumber);
  		        ResultSet rs = stmt.executeQuery();
  		        if (rs.next()) {
  		            return rs.getFloat("re_exam_score");
  		        }
  		    }
  		    throw new SQLException("未找到对应的学生，ID号：" + idnumber + "以及复试成绩");
  	}
}
