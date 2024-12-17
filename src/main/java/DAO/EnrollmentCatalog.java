package DAO;

public class EnrollmentCatalog {
    private int catalogId;
    private int year;
    private int subjectId;
    private int advisorId;
    private int totalQuota;
    private int additionalQuota;
    private String initialExamSubject;
    private String reExamSubject;
    private Advisor advisor;
    // Getters and Setters
    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    }

    public int getTotalQuota() {
        return totalQuota;
    }

    public void setTotalQuota(int totalQuota) {
        this.totalQuota = totalQuota;
    }

    public int getAdditionalQuota() {
        return additionalQuota;
    }

    public void setAdditionalQuota(int additionalQuota) {
        this.additionalQuota = additionalQuota;
    }

    public String getInitialExamSubject() {
        return initialExamSubject;
    }

    public void setInitialExamSubject(String initialExamSubject) {
        this.initialExamSubject = initialExamSubject;
    }

    public String getReExamSubject() {
        return reExamSubject;
    }

    public void setReExamSubject(String reExamSubject) {
        this.reExamSubject = reExamSubject;
    }

	public Advisor getAdvisor() {
		return advisor;
	}

	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}
}
