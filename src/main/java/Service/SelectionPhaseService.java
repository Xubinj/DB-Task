package Service;
import DAO.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SelectionPhaseService {
    private SelectionPhaseDAO phaseDAO;
    private AdvisorService advisorService;
    private AdvisorDAO advisorDAO;
    
    public SelectionPhaseService() {
        this.phaseDAO = new SelectionPhaseDAO();
        this.advisorService = new AdvisorService(); // 引入 AdvisorService
        this.advisorDAO = new AdvisorDAO();
    }

    // 获取当前学年
    public int getCurrentYear() {
        return phaseDAO.getCurrentYear();  // 通过 DAO 层获取当前学年
    }

    // 获取当前正在进行的志愿选择阶段
    public SelectionPhase getCurrentSelectionPhase() throws SQLException {
        int currentYear = getCurrentYear();
        return phaseDAO.getCurrentSelectionPhase(currentYear);  // 调用 DAO 层的方法获取当前阶段
    }

    // 获取下一个尚未开始的志愿选择阶段
    public SelectionPhase getNextSelectionPhase() throws SQLException {
        int currentYear = getCurrentYear();
        return phaseDAO.getNextSelectionPhase(currentYear);  // 调用 DAO 层的方法获取下一个阶段
    }
    

    // 判断当前是否为自由选择阶段
    public boolean isFreeSelectionPhase() throws SQLException {
        return phaseDAO.isFreeSelectionPhase();
    }

    // 判断导师是否可以选择学生
    public boolean canSelectStudentForAdvisor(int advisorId) throws SQLException {
        return phaseDAO.canSelectStudentForAdvisor(advisorId);
    }

    // 获取未被选择的学生
    public List<Applicant> getAvailableStudents() throws SQLException {
        return phaseDAO.getAvailableStudents();
    }

    // 获取导师的剩余招生名额
    public int getRemainingQuota(int advisorId) throws SQLException {
        return advisorService.getRemainingQuota(advisorId);
    }
    

    // 获取符合自由选择条件的导师
    public List<Advisor> getEligibleAdvisors(String department) throws SQLException {
        return advisorDAO.getEligibleAdvisors(department);
    }
    
    // 获取所有选择阶段的时间段
    public List<SelectionPhase> getAllSelectionPhases() throws SQLException {
        return phaseDAO.getAllSelectionPhases();
    }

    // 更新阶段时间段
    public void updateSelectionPhaseTime(int phaseId, Timestamp startTime, Timestamp endTime) throws SQLException {
        phaseDAO.updateSelectionPhaseTime(phaseId, startTime, endTime);
    }
}
