package Servlet;
//辅助类用于封装展示的数据 
//辅助领导用到

//在原本基础上添加的

//辅助LeaderDashboardServlet用的 存放领导页面需要展示的信息

import DAO.Advisor;
import DAO.Applicant;

public class AssignmentDetail {
	 private int assignmentId;
	 private Applicant applicant;
	 private Advisor advisor;
	
	 private int status;

	 public AssignmentDetail(int assignmentId, Applicant applicant, Advisor advisor, int status) {
	     this.assignmentId = assignmentId;
	     this.applicant = applicant;
	     this.advisor = advisor;
	     this.status = status;
	 }
	//定义办法
	 public int getAssignmentId() { return assignmentId; }
	 public Applicant getApplicant() { return applicant; }
	 public Advisor getAdvisor() { return advisor; }
	 public int getStatus() { return status; }
}
