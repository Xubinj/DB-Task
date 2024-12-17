package DAO;

import java.util.Date;

public class SelectionPhase {
	private int phaseId;
	private int currentYear;   // 当前学年
    private String phaseName;  // 阶段名称
    private Date startTime;    // 阶段开始时间
    private Date endTime;      // 阶段结束时间

    // 构造方法、getter、setter
    public SelectionPhase() {}

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

	public int getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
	}
}
