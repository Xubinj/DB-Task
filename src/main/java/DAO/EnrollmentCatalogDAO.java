package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//负责管理招生目录中的数据，尤其是每个导师的招生名额
public class EnrollmentCatalogDAO {

    // 获取导师的年度招生指标
    public int getAdvisorQuota(int advisorId) throws SQLException {
        String query = "SELECT total_quota FROM EnrollmentCatalog WHERE advisor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, advisorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_quota");
            }
        }
        return 0;
    }

    // 更新导师的年度招生指标
    public void updateAdvisorQuota(int advisorId, int year, int totalQuota) throws SQLException {
        String query = "UPDATE EnrollmentCatalog SET total_quota = ? WHERE advisor_id = ? AND year = ?";
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, totalQuota);
            stmt.setInt(2, advisorId);
            stmt.setInt(3, year);
            stmt.executeUpdate();
        }
    }
    // 获取每个学科的招生信息
    public List<EnrollmentCatalog> getCatalogsBySubjectId(int subject_id) {
        List<EnrollmentCatalog> catalogs = new ArrayList<>();
        String sql = "SELECT * FROM EnrollmentCatalog WHERE subject_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, subject_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EnrollmentCatalog catalog = new EnrollmentCatalog();
                    catalog.setCatalogId(rs.getInt("catalog_id"));
                    catalog.setYear(rs.getInt("year"));
                    catalog.setSubjectId(rs.getInt("subject_id"));
                    catalog.setAdvisorId(rs.getInt("advisor_id"));
                    catalog.setTotalQuota(rs.getInt("total_quota"));
                    catalog.setAdditionalQuota(rs.getInt("additional_quota"));
                    catalog.setInitialExamSubject(rs.getString("initial_exam_subject"));
                    catalog.setReExamSubject(rs.getString("re_exam_subject"));
                    catalogs.add(catalog);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogs;
    }
}
