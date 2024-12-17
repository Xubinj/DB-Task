package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdvisorPreferenceDAO {

    // 获取选择某个导师的所有学生，按志愿优先级排序
    public List<Applicant> getApplicantsByAdvisor(int advisorId) throws SQLException {
        List<Applicant> applicants = new ArrayList<>();
        String query = "SELECT a.* FROM AdvisorPreference ap " +
                       "JOIN Applicant a ON ap.applicant_id = a.applicant_id " +
                       "WHERE ap.advisor_id = ? ORDER BY ap.priority";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            ResultSet rs = stmt.executeQuery();
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

    // 添加学生志愿信息
    public void addAdvisorPreference(int applicantId, int advisorId, int subjectId, int priority) throws SQLException {
        String query = "INSERT INTO AdvisorPreference (applicant_id, advisor_id, subject_id, priority) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, applicantId);
            stmt.setInt(2, advisorId);
            stmt.setInt(3, subjectId);
            stmt.setInt(4, priority);
            stmt.executeUpdate();
        }
    }

    // 删除学生的志愿信息（根据学生ID）
    public void deleteAdvisorPreference(int applicantId) throws SQLException {
        String query = "DELETE FROM AdvisorPreference WHERE applicant_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, applicantId);
            stmt.executeUpdate();
        }
    }
    
    public List<Applicant> getApplicantsWithPriorityByAdvisor(int advisorId) throws SQLException {
        // 更新的查询语句，包括所需的所有字段
        String query = "SELECT a.applicant_id, a.name, a.initial_exam_score, a.re_exam_score, " +
                       "a.undergraduate_major, a.email, a.phone, ap.priority " +
                       "FROM Applicant a " +
                       "JOIN AdvisorPreference ap ON a.applicant_id = ap.applicant_id " +
                       "WHERE ap.advisor_id = ? " +
                       "ORDER BY ap.priority ASC";  // 按照优先级排序

        List<Applicant> applicants = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);  // 设置查询参数（advisorId）
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Applicant applicant = new Applicant();
                    applicant.setApplicantId(rs.getInt("applicant_id"));
                    applicant.setName(rs.getString("name"));
                    applicant.setInitialExamScore(rs.getFloat("initial_exam_score"));  // 设置初试成绩
                    applicant.setReExamScore(rs.getFloat("re_exam_score"));  // 设置复试成绩
                    applicant.setUndergraduateMajor(rs.getString("undergraduate_major"));  // 设置学科
                    applicant.setEmail(rs.getString("email"));  // 设置邮箱
                    applicant.setPhone(rs.getString("phone"));  // 设置电话
                    applicant.setPriority(rs.getInt("priority"));  // 设置志愿优先级
                    
                    applicants.add(applicant);  // 将学生对象添加到列表中
                }
            }
        }
        return applicants;  // 返回所有查询到的学生数据
    }

    
    public boolean isStudentAssignedToAdvisor(int applicantId, int advisorId) throws SQLException {
        String query = "SELECT COUNT(*) FROM FinalAssignment WHERE applicant_id = ? AND advisor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, applicantId);
            stmt.setInt(2, advisorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // 如果返回的计数大于 0，表示已经分配
            }
        }
        return false;
    }

    // 获取选择该导师且优先级为指定值的未被选择且复试成绩过线的学生
    public List<Applicant> getApplicantsByAdvisorAndPriority(int advisorId, Integer priority, boolean isAssigned) throws SQLException {
        // 判断是未选择还是已选择，通过 status 字段
        String status = isAssigned ? "已选择" : "未选择";
        
        String query = "SELECT a.applicant_id, a.name, ap.priority, a.initial_exam_score, a.re_exam_score, a.undergraduate_major, a.email, a.phone " +
                       "FROM Applicant a " +
                       "JOIN AdvisorPreference ap ON a.applicant_id = ap.applicant_id " +
                       "WHERE ap.advisor_id = ? AND ap.priority = ? AND a.status = ? AND re_exam_score>=180";  // 通过 status 字段判断学生是否被选择

        List<Applicant> applicants = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            stmt.setInt(2, priority);
            stmt.setString(3, status);  // 使用 status 字段来判断学生状态
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Applicant applicant = new Applicant();
                applicant.setApplicantId(rs.getInt("applicant_id"));
                applicant.setName(rs.getString("name"));
                applicant.setPriority(rs.getInt("priority"));
                applicant.setInitialExamScore(rs.getFloat("initial_exam_score"));
                applicant.setReExamScore(rs.getFloat("re_exam_score"));
                applicant.setUndergraduateMajor(rs.getString("undergraduate_major"));
                applicant.setEmail(rs.getString("email"));
                applicant.setPhone(rs.getString("phone"));
                applicants.add(applicant);
            }
        }
        return applicants;
    }
    
    
}
