package Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.Advisor;
import DAO.AdvisorDAO;
import DAO.EnrollmentCatalog;
import DAO.EnrollmentCatalogDAO;
import DAO.Subject;
import DAO.SubjectDAO;

public class SubjectService {
	private AdvisorDAO advisorDAO;
    public SubjectService() {
		super();
		this.advisorDAO = new AdvisorDAO();
	}

	private SubjectDAO subjectDAO = new SubjectDAO();
    private EnrollmentCatalogDAO catalogDAO = new EnrollmentCatalogDAO();

    // 获取前14个学科的详细信息，包括导师、招生数等
    public List<SubjectCatalogInfo> getAllSubjectCatalogInfo() {
        List<Subject> subjects = subjectDAO.getAllSubjects();
        List<SubjectCatalogInfo> subjectCatalogInfos = new ArrayList<>();

        // 只取前14个学科
        int limit = Math.min(subjects.size(), 14);

        for (int i = 0; i < limit; i++) {
            Subject subject = subjects.get(i);
            SubjectCatalogInfo info = new SubjectCatalogInfo();
            info.setSubject(subject);

            // 获取该学科的招生信息
            List<EnrollmentCatalog> catalogs = catalogDAO.getCatalogsBySubjectId(subject.getSubject_id());

            // 为每个招生信息获取导师
            for (EnrollmentCatalog catalog : catalogs) {
                try {
                    Advisor advisor = advisorDAO.getAdvisorById(catalog.getAdvisorId());
                    if (advisor != null) {
                    	if(advisor.isActive()) {
                    		catalog.setAdvisor(advisor);
                    	}
                    }
                } catch (SQLException e) {
                    e.printStackTrace();  // 处理异常，可以打印日志或根据需求进行其他处理
                }
            }
            info.setCatalogs(catalogs);
            subjectCatalogInfos.add(info);
        }

        return subjectCatalogInfos;
    }
}
