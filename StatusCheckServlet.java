package Servlet;

import java.io.IOException;
//在原本基础上添加的
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ApplicantDAO;
import DAO.FinalAssignmentDAO;
import DAO.SelectionPhase;
import Service.SelectionPhaseService;

/**
 * Servlet implementation class StatusCheckServlet
 */
@WebServlet("/StatusCheckServlet")
public class StatusCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FinalAssignmentDAO finalAssignmentDAO = new FinalAssignmentDAO();
    private ApplicantDAO applicantDAO = new ApplicantDAO();
    private SelectionPhaseService sps = new SelectionPhaseService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatusCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
        String username = (String) request.getSession().getAttribute("username");
        String idnumber = (String) request.getSession().getAttribute("idnumber");
        System.out.println(idnumber);
        if (username == null) {
            response.getWriter().println("<h1>未登录，请先登录。</h1>");
            return;
        }
        


        try {
            // 根据用户名获取 applicantId
//            int applicantId = applicantDAO.getApplicantIdByUsername(username);
        	//根据学号获取 applicantId
            int applicantId = applicantDAO.getApplicantIdByIdNumber(idnumber);
            Float re_examscore = applicantDAO.getApplicantReExamScore(idnumber);
            // 获取申请的 status
            int status = finalAssignmentDAO.getStatusByApplicantId(applicantId);
            
            SelectionPhase sp = sps.getCurrentSelectionPhase();
            //System.out.println(sp.getPhaseName());
            if(sp==null) {
            	 request.getRequestDispatcher("PendingStatus.jsp").forward(request, response);
            }
            
            if("录取结果公示阶段".equals(sp.getPhaseName())) {
            	 if(re_examscore < 180) {
                 	request.getRequestDispatcher("RejectedScore.jsp").forward(request, response);
                 }
                 else if (status == 1) {
                     response.sendRedirect("ApplicantDashBoardServlet");
                 } else if (status == -1 ) {
                 	//已拒绝
                     request.getRequestDispatcher("RejectedStatus.jsp").forward(request, response);
                 }else {
                     request.getRequestDispatcher("PendingStatus.jsp").forward(request, response);

                 }
            }else {
            	//待审批
                request.getRequestDispatcher("PendingStatus.jsp").forward(request, response);
            }
            }catch (SQLException e) {
            e.printStackTrace();
         // 错误信息并添加跳转按钮
         // 错误信息并添加跳转按钮，居中展示
            response.getWriter().println("<div style=\"text-align: center; margin-top: 50px;\">");
            response.getWriter().println("<h1 style=\"color: #388e3c;\">同学您尚未注册，请点击按钮注册</h1>");
            response.getWriter().println("<br><br>");
            response.getWriter().println("<button style=\"font-size: 18px; padding: 10px 20px; background-color: #66bb6a; color: white; border: none; border-radius: 5px; cursor: pointer;\" onclick=\"window.location.href='Test-exist.jsp'\">点击跳转到个人注册页面</button>");
            response.getWriter().println("</div>");

        }
    }


}
