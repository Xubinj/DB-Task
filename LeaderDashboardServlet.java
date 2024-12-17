package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.Advisor;
import DAO.AdvisorDAO;
import DAO.Applicant;
import DAO.ApplicantDAO;
import DAO.FinalAssignment;
import DAO.FinalAssignmentDAO;

@WebServlet("/LeaderDashboardServlet")
public class LeaderDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FinalAssignmentDAO finalAssignmentDAO = new FinalAssignmentDAO();
    private ApplicantDAO applicantDAO = new ApplicantDAO();
    private AdvisorDAO advisorDAO = new AdvisorDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // 获取操作类型
            String action = request.getParameter("action");
            //确定领导页面是否点击更新按钮
            if ("update".equals(action)) {
                int assignmentId = Integer.parseInt(request.getParameter("assignment_id"));
                int status = Integer.parseInt(request.getParameter("status")); // 1: 同意, -1: 不同意

                try {
                    finalAssignmentDAO.updateAssignmentStatus(assignmentId, status);
                    request.setAttribute("message", "状态更新成功！");
                } catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("message", "状态更新失败！");
                }
            }

            // 获取所有分配数据
            List<FinalAssignment> assignments = finalAssignmentDAO.getAllAssignments();
            List<AssignmentDetail> assignmentDetails = new ArrayList<>();
            for (FinalAssignment assignment : assignments) {
                Applicant applicant = applicantDAO.getApplicantById(assignment.getApplicantId());
                Advisor advisor = advisorDAO.getAdvisorById(assignment.getAdvisorId());
                
                AssignmentDetail detail = new AssignmentDetail(
                    assignment.getAssignmentId(),
                    applicant,
                    advisor,
                    assignment.getStatus()
                );
                assignmentDetails.add(detail);
            }
            request.setAttribute("assignments", assignments);
            request.setAttribute("assignmentDetails", assignmentDetails);

            // 转发到 JSP 页面
            request.getRequestDispatcher("leader_dashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=databaseError");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("message", "输入的分配ID或状态无效！");
            try {
                // 获取所有分配数据并重新加载页面
                List<FinalAssignment> assignments = finalAssignmentDAO.getAllAssignments();
                request.setAttribute("assignments", assignments);
            } catch (SQLException ex) {
                ex.printStackTrace();
                response.sendRedirect("login.jsp?error=databaseError");
            }
            request.getRequestDispatcher("leader_dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
