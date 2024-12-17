package DAO;

public class AdvisorSelectionOrder {
    private Advisor advisor;  // 导师信息
    private String startTime; // 自由选择开始时间
    private String endTime;   // 自由选择结束时间

    // Getters and setters
    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
