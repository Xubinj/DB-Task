package Service;

import java.util.List;

import DAO.EnrollmentCatalog;
import DAO.Subject;

public class SubjectCatalogInfo {
    private Subject subject;
    private List<EnrollmentCatalog> catalogs;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<EnrollmentCatalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<EnrollmentCatalog> catalogs) {
        this.catalogs = catalogs;
    }
}
