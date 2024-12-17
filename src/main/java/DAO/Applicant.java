package DAO;

import java.sql.Date;

public class Applicant {
    private int applicantId;
    private String name;
    private String idNumber;
    private Date birthDate;
    private String undergraduateSchool;
    private String undergraduateMajor;
    private float initialExamScore;
    private float reExamScore;
    private String phone;
    private String email;
    
    private int priority;  // 用于存储优先级，不会持久化到数据库中

    private boolean assigned;  // 新增的属性，表示学生是否已被分配给导师

    private String status;  // 新添加的字段
    
    // Getters and Setters
    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getUndergraduateSchool() {
        return undergraduateSchool;
    }

    public void setUndergraduateSchool(String undergraduateSchool) {
        this.undergraduateSchool = undergraduateSchool;
    }

    public String getUndergraduateMajor() {
        return undergraduateMajor;
    }

    public void setUndergraduateMajor(String undergraduateMajor) {
        this.undergraduateMajor = undergraduateMajor;
    }

    public float getInitialExamScore() {
        return initialExamScore;
    }

    public void setInitialExamScore(float initialExamScore) {
        this.initialExamScore = initialExamScore;
    }

    public float getReExamScore() {
        return reExamScore;
    }

    public void setReExamScore(float reExamScore) {
        this.reExamScore = reExamScore;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
