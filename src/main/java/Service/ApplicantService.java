package Service;

import java.sql.SQLException;
import java.util.List;

import DAO.Applicant;
import DAO.ApplicantDAO;

public class ApplicantService {
    private ApplicantDAO applicantDAO;

    public ApplicantService() {
        this.applicantDAO = new ApplicantDAO();
    }
    
    // 根据学号获取学生的 ID
    public int getApplicantIdByIdNumber(String idNumber) throws SQLException {
        return applicantDAO.getApplicantIdByIdNumber(idNumber);
    }
    // 根据 username 获取学生的 ID
    public int getApplicantIdByUsername(String username) throws SQLException {
        return applicantDAO.getApplicantIdByUsername(username);
    }

    // 根据 applicant_id 获取考生信息
    public Applicant getApplicantById(int applicantId) throws SQLException {
        return applicantDAO.getApplicantById(applicantId);
    }
    // 获取指定学生的导师ID 不一定有导师
    public Integer getAssignedAdvisor(int applicantId) throws SQLException {
        return applicantDAO.getAssignedAdvisorByApplicantId(applicantId);
    }
    
    //这里是为了给秘书调用的 更新学生的信息（初试 复试成绩）
    public void updateApplicant(int applicantId, float initialExamScore, float reExamScore) throws SQLException {
    	 applicantDAO.updateApplicant(applicantId, initialExamScore, reExamScore);
    }
    
    //获取所有学生的信息
    public List<Applicant> getAllApplicants() throws SQLException {
        return applicantDAO.getAllApplicants();
    }


}
