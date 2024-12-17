package Service;
import DAO.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class AdvisorService {
    private EnrollmentCatalogDAO enrollmentCatalogDAO;
    private FinalAssignmentDAO finalAssignmentDAO;
    private AdvisorPreferenceDAO advisorPreferenceDAO;
    private ApplicantDAO applicantDAO;
    private AdvisorDAO advisorDAO;
    public AdvisorService() {
        this.enrollmentCatalogDAO = new EnrollmentCatalogDAO();
        this.finalAssignmentDAO = new FinalAssignmentDAO();
        this.advisorPreferenceDAO = new AdvisorPreferenceDAO();
        this.applicantDAO = new ApplicantDAO();
        this.advisorDAO = new AdvisorDAO();  
    }

    // 获取某个导师的年度招生总指标
    public int getAdvisorQuota(int advisorId) throws SQLException {
        return enrollmentCatalogDAO.getAdvisorQuota(advisorId);
    }

    // 根据用户名获取导师的ID
    public int getAdvisorIdByUsername(String username) throws SQLException {
        return AdvisorDAO.getAdvisorIdByUsername(username);  // 在 AdvisorDAO 中实现该方法
    }
    
    // 根据导师ID获取选择该导师的所有学生（按志愿优先级排序）
    public List<Applicant> getApplicantsByAdvisor(int advisorId) throws SQLException {
        return advisorPreferenceDAO.getApplicantsByAdvisor(advisorId);
    }
    
    

    // 导师选择学生，将学生分配给导师
    public boolean assignStudentToAdvisor(int advisorId, int applicantId, int year) throws SQLException {
        // 获取当前导师的招生总指标
        int totalQuota = getAdvisorQuota(advisorId);
        
        // 获取已分配学生数量
        int assignedCount = finalAssignmentDAO.getAssignedStudentCount(advisorId);
        
        // 检查是否有剩余名额
        if (assignedCount < totalQuota) {
            // 若有名额，将学生分配给导师
            finalAssignmentDAO.assignStudentToAdvisor(applicantId, advisorId);
            // 更新导师的剩余名额
            updateRemainingQuota(advisorId);
            return true;  // 分配成功
        } else {
            return false;  // 分配失败，名额已满
        }
    }
    
    // 删除导师选择的学生
    public boolean deleteAssignmentByApplicant(int advisorId, int applicantId) throws SQLException {
        // 从 FinalAssignment 表中删除学生与导师的分配记录
        finalAssignmentDAO.deleteAssignmentByApplicant(applicantId, advisorId);

        // 更新导师的剩余名额
        updateRemainingQuota2(advisorId);

        return true;
    }
    
 // 获取导师的年度招生总指标
    public int getTotalQuota(int advisorId) throws SQLException {
        return AdvisorDAO.getTotalQuota(advisorId);  // 在 AdvisorDAO 中实现
    }
    
 // 获取导师剩余招生名额
    public int getRemainingQuota(int advisorId) throws SQLException {
        return AdvisorDAO.getRemainingQuota(advisorId);  // 在 AdvisorDAO 中实现
    }

 // 更新导师剩余招生名额
    public void updateRemainingQuota(int advisorId) throws SQLException {
        AdvisorDAO.updateRemainingQuota(advisorId);
    }
    
    // 更新导师剩余招生名额
    public void updateRemainingQuota2(int advisorId) throws SQLException {
        AdvisorDAO.updateRemainingQuota2(advisorId);
    }
    
    //获取导师选择的所有学生及其优先级
    public List<Applicant> getApplicantsWithPriorityByAdvisor(int advisorId) throws SQLException {
        return advisorPreferenceDAO.getApplicantsWithPriorityByAdvisor(advisorId);
    }

    public boolean isStudentAssignedToAdvisor(int applicantId, int advisorId) throws SQLException {
        return advisorPreferenceDAO.isStudentAssignedToAdvisor(applicantId, advisorId);
    }
    
    // 获取选择该导师为第一志愿的未被选择的学生
    public List<Applicant> getFirstChoiceStudents(int advisorId) throws SQLException {
        return advisorPreferenceDAO.getApplicantsByAdvisorAndPriority(advisorId, 1, false);  // false 表示未被分配
    }

    // 获取选择该导师为第二志愿的未被选择的学生
    public List<Applicant> getSecondChoiceStudents(int advisorId) throws SQLException {
        return advisorPreferenceDAO.getApplicantsByAdvisorAndPriority(advisorId, 2, false);  // false 表示未被分配
    }

    // 获取选择该导师为第三志愿的未被选择的学生
    public List<Applicant> getThirdChoiceStudents(int advisorId) throws SQLException {
        return advisorPreferenceDAO.getApplicantsByAdvisorAndPriority(advisorId, 3, false);  // false 表示未被分配
    }

    // 获取自由选择阶段的未被选择的学生
    public List<Applicant> getFreeChoiceStudents(int advisorId) throws SQLException {
        // 获取所有未被选择的学生
        List<Applicant> allFreeChoiceStudents = applicantDAO.getFreeChoiceStudents();
        return allFreeChoiceStudents;  // 目前返回所有未被选择的学生
    }
    
    // 获取没有学生报考的导师
    public List<Advisor> getAdvisorsWithoutApplicants() throws SQLException {
        String query = "SELECT a.advisor_id, a.name, a.remaining_quota " +
                       "FROM Advisor a " +
                       "LEFT JOIN AdvisorPreference ap ON a.advisor_id = ap.advisor_id " +
                       "WHERE ap.applicant_id IS NULL AND a.remaining_quota > 0";

        List<Advisor> advisors = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Advisor advisor = new Advisor();
                advisor.setAdvisorId(rs.getInt("advisor_id"));
                advisor.setName(rs.getString("name"));
                advisor.setRemainingQuota(rs.getInt("remaining_quota"));
                advisors.add(advisor);
            }
        }
        return advisors;
    }

	public Advisor getAdvisorById(Integer advisorId) throws SQLException {
		// TODO Auto-generated method stub
		return advisorDAO.getAdvisorById(advisorId);
	}
    

}
