package Service;

import DAO.ApplicantDAO;
import java.sql.SQLException;

public class StudentService {

    private ApplicantDAO applicantDAO;

    public StudentService() {
        this.applicantDAO = new ApplicantDAO();
    }

    // 更新学生的选择状态
    public boolean updateStudentStatus(int applicantId, String status) throws SQLException {
        // 调用 DAO 层的方法，更新学生状态
        return applicantDAO.updateStudentStatus(applicantId, status);
    }
}
