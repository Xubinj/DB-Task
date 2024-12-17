package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.Applicant;
import Service.ApplicantService;

/**
 * Servlet implementation class SecretaryDashBoardServlet
 */
@WebServlet("/SecretaryDashBoardServlet")
public class SecretaryDashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ApplicantService applicantService;
       
    public SecretaryDashBoardServlet() {
        super();
        
    	this.applicantService = new ApplicantService();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
    	try {
    		//这是第二次返回这个页面
            // 获取表单提交的数据，更新成绩
    		String applicantIdStr = request.getParameter("applicant_id");
            String initialExamScoreStr = request.getParameter("initial_exam_score");
            String reExamScoreStr = request.getParameter("re_exam_score");

            // 确保所有输入数据都有效
            if (applicantIdStr != null && initialExamScoreStr != null && reExamScoreStr != null) {
                int applicantIdInt = Integer.parseInt(applicantIdStr);
                float initialExamScore = Float.parseFloat(initialExamScoreStr);
                float reExamScore = Float.parseFloat(reExamScoreStr);

                // 更新学生成绩
                applicantService.updateApplicant(applicantIdInt, initialExamScore, reExamScore);
            }
          //这是第二次返回这个页面

            // 获取所有学生成绩
            List<Applicant> applicants = applicantService.getAllApplicants();
            request.setAttribute("applicants", applicants);

            // 转发到显示页面
            request.getRequestDispatcher("secretary_dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    	
	}

}
