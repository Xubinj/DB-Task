package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.*;
import java.util.Calendar;

public class SelectionPhaseDAO {

    // 获取当前学年
    public int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);  // 返回当前年份
    }

    // 获取当前正在进行的志愿选择阶段
    public SelectionPhase getCurrentSelectionPhase(int currentYear) throws SQLException {
        String query = "SELECT * FROM SelectionPhase WHERE current_year = ? ORDER BY start_time ASC";  // 按开始时间升序查询阶段
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, currentYear);  // 设置当前学年
            ResultSet rs = stmt.executeQuery();
            
            // 当前时间
            Date currentTime = new Date(System.currentTimeMillis());  // 获取当前时间

            while (rs.next()) {
                SelectionPhase phase = new SelectionPhase();
                phase.setPhaseName(rs.getString("phase_name"));
                phase.setStartTime(rs.getTimestamp("start_time"));
                phase.setEndTime(rs.getTimestamp("end_time"));

                // 判断当前时间是否在该阶段的开始和结束时间之间
                if (currentTime.after(phase.getStartTime()) && currentTime.before(phase.getEndTime())) {
                    // 如果当前时间在该阶段的时间范围内，返回当前阶段
                    return phase;
                }
            }
        }
        return null;  // 如果没有找到正在进行的阶段，返回 null
    }

 // 获取下一个尚未开始的志愿选择阶段
    public SelectionPhase getNextSelectionPhase(int currentYear) throws SQLException {
        String query = "SELECT TOP 1 * FROM SelectionPhase WHERE current_year = ? AND start_time > GETDATE() ORDER BY start_time ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, currentYear);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                SelectionPhase phase = new SelectionPhase();
                phase.setPhaseName(rs.getString("phase_name"));
                phase.setStartTime(rs.getTimestamp("start_time"));
                phase.setEndTime(rs.getTimestamp("end_time"));
                return phase;
            }
        }
        return null;  // 没有下一个尚未开始的阶段
    }
    
    // 判断当前是否为自由选择阶段
    public boolean isFreeSelectionPhase() throws SQLException {
        String query = "SELECT * FROM SelectionPhase WHERE phase_name = '学科集中商议与抽签自由选择阶段' AND start_time <= ? AND end_time >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // 如果当前时间在自由选择阶段内，返回true
        }
    }

    // 判断导师是否可以选择学生（判断是否在该导师的自由选择时间段内）
    public boolean canSelectStudentForAdvisor(int advisorId) throws SQLException {
        String query = "SELECT * FROM AdvisorSelectionOrder WHERE advisor_id = ? AND free_selection_start <= ? AND free_selection_end >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // 如果当前时间在该导师的自由选择时间段内，返回true
        }
    }

    // 获取当前未被选择的学生
    public List<Applicant> getAvailableStudents() throws SQLException {
        String query = "SELECT * FROM Applicant WHERE status = '未选择'";
        List<Applicant> students = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Applicant student = new Applicant();
                student.setApplicantId(rs.getInt("applicant_id"));
                student.setName(rs.getString("name"));
                student.setStatus(rs.getString("status"));
                students.add(student);
            }
        }
        return students;
    }

    	
    // 获取所有选择阶段的时间段
    public List<SelectionPhase> getAllSelectionPhases() throws SQLException {
        String query = "SELECT * FROM SelectionPhase";

        List<SelectionPhase> phases = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SelectionPhase phase = new SelectionPhase();
                phase.setPhaseId(rs.getInt("selection_phase_id"));
                phase.setCurrentYear(rs.getInt("current_year"));
                phase.setPhaseName(rs.getString("phase_name"));
                phase.setStartTime(rs.getTimestamp("start_time"));
                phase.setEndTime(rs.getTimestamp("end_time"));
                phases.add(phase);
            }
        }
        return phases;
    }

    // 更新选择阶段的时间段
    public void updateSelectionPhaseTime(int phaseId, Timestamp startTime, Timestamp endTime) throws SQLException {
        String query = "UPDATE SelectionPhase SET start_time = ?, end_time = ? WHERE selection_phase_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, startTime);
            stmt.setTimestamp(2, endTime);
            stmt.setInt(3, phaseId);
            stmt.executeUpdate();
        }
    }
}

