package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FinalAssignmentDAO {

	
	// 根据 applicant_id 获取状态
    public int getStatusByApplicantId(int applicantId) throws SQLException {
        String query = "SELECT status FROM FinalAssignment WHERE applicant_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, applicantId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("status");
            }
        }
        return 0; // 默认未审批
    }
    
	//更新领导是否同意
	public void updateAssignmentStatus(int assignmentId, int status) throws SQLException {
        String query = "UPDATE FinalAssignment SET status = ? WHERE assignment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, status);
            stmt.setInt(2, assignmentId);
            stmt.executeUpdate();
        }
    }
	
	//获得所有的学生和导师的分配意愿
	public List<FinalAssignment> getAllAssignments() throws SQLException {
	    String query = "SELECT * FROM FinalAssignment";
	    List<FinalAssignment> assignments = new ArrayList<>();
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            FinalAssignment assignment = new FinalAssignment();
	            assignment.setAssignmentId(rs.getInt("assignment_id"));
	            assignment.setApplicantId(rs.getInt("applicant_id"));
	            assignment.setAdvisorId(rs.getInt("advisor_id"));
	            assignment.setStatus(rs.getInt("status")); // 使用 status
	            assignments.add(assignment);
	        }
	    }
	    return assignments;
	}
	
    // 获取某导师已分配的学生数量
    public int getAssignedStudentCount(int advisorId) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM FinalAssignment WHERE advisor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }

    // 将学生分配给导师
    public void assignStudentToAdvisor(int applicantId, int advisorId) throws SQLException {
        String query = "INSERT INTO FinalAssignment (applicant_id, advisor_id,status) VALUES (?, ?,?)";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, applicantId);
            stmt.setInt(2, advisorId);
            stmt.setInt(3,0);
            stmt.executeUpdate();
        }
    }

    // 根据学生ID删除分配记录（用于撤销分配）
    public void deleteAssignmentByApplicant(int applicantId) throws SQLException {
        String query = "DELETE FROM FinalAssignment WHERE applicant_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, applicantId);
            stmt.executeUpdate();
        }
    }

        // 根据学生ID和导师ID删除分配记录（用于撤销分配）
        public void deleteAssignmentByApplicant(int applicantId, int advisorId) throws SQLException {
            String query = "DELETE FROM FinalAssignment WHERE applicant_id = ? AND advisor_id = ?";
            try (Connection conn = DatabaseConnection.getConnection(); 
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, applicantId);  // 只删除特定学生的记录
                stmt.setInt(2, advisorId);    // 只删除该导师和学生之间的分配记录
                int result = stmt.executeUpdate();  // 执行删除操作
                System.out.println("Delete result: " + result);  // 输出删除操作结果
            }
        }



    
    // 检查学生是否已经分配给导师
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
    
}

