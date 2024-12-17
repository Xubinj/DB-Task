package Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.Advisor;
import DAO.Applicant;
import DAO.UserAccount;
import Service.AdvisorService;
import Service.ApplicantService;

/**
 * Servlet implementation class StudentDashBoardServlet
 */
@WebServlet("/ApplicantDashBoardServlet")
public class ApplicantDashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ApplicantService applicantService;
	private AdvisorService advisorService;
    public ApplicantDashBoardServlet() {
    	this.applicantService = new ApplicantService();
    	this.advisorService = new AdvisorService();
    }
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
		
		
		//获取学生
		UserAccount applicant = (UserAccount) request.getSession().getAttribute("user");
		String idnumber = (String) request.getSession().getAttribute("idnumber");
		//判断是否为空以及身份是否正确
		if (applicant != null && "Applicant".equals(applicant.getRole())) {
			// 获取导师的 advisorId
            try {
//				int applicantId = applicantService.getApplicantIdByUsername(applicant.getUsername());
				int applicantId = applicantService.getApplicantIdByIdNumber(idnumber);
				// 获取该学生的申请信息	个人信息
				Applicant appli = applicantService.getApplicantById(applicantId);
				// 获取导师ID并传递给JSP
				Integer advisorId = applicantService.getAssignedAdvisor(applicantId);
				if (advisorId != null) {
                    Advisor advisor = advisorService.getAdvisorById(advisorId);  // 获取导师信息
                    request.setAttribute("advisorDetails", advisor);  // 将导师信息传递到 JSP
                }
				// 存入session
				request.setAttribute("applicantDetails", appli);
				
//				request.setAttribute("advisorId", advisorId);  // 添加导师ID
				
				//转发
				request.getRequestDispatcher("applicant_dashboard.jsp").forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendRedirect("login.jsp?error=spe");
			}
		}
		else {
			 // 用户未登录或角色不为学生，重定向到登录页面
            response.sendRedirect("login.jsp?error=Applicant");
		}
	}

}