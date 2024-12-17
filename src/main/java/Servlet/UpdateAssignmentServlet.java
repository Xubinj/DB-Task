package Servlet;

import DAO.*;
import Service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@WebServlet("/UpdateAssignmentServlet")
public class UpdateAssignmentServlet extends HttpServlet {
    private FinalAssignmentDAO finalAssignmentDAO = new FinalAssignmentDAO();
    private AdvisorService advisorService = new AdvisorService();
    private StudentService studentService = new StudentService();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int applicantId = Integer.parseInt(request.getParameter("applicantId"));

        // 从 session 中获取当前登录用户
        UserAccount tutor = (UserAccount) request.getSession().getAttribute("user");

        if (tutor == null || !"Advisor".equals(tutor.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 根据用户名查询导师的 advisorId
            int advisorId = advisorService.getAdvisorIdByUsername(tutor.getUsername());

            boolean isSuccess = false;

            // 根据操作执行不同的逻辑
            if ("select".equals(action)) {
                // 检查剩余名额，确保不会超出
                if (advisorService.getRemainingQuota(advisorId) > 0) {
                    isSuccess = advisorService.assignStudentToAdvisor(advisorId, applicantId, 2024);
                    studentService.updateStudentStatus(applicantId, "已选择");
                } else {
                    isSuccess = false;  // 名额已满
                    response.getWriter().write("error");  // 返回“名额已满”错误
                    return;
                }
            } else if ("remove".equals(action)) {
                isSuccess = advisorService.deleteAssignmentByApplicant(advisorId, applicantId);
                studentService.updateStudentStatus(applicantId, "未选择");
            }

            // 如果操作成功，返回更新后的剩余名额
            if (isSuccess) {
                int remainingQuota = advisorService.getRemainingQuota(advisorId);

                // 设置剩余名额为响应头
                response.setHeader("X-Remaining-Quota", String.valueOf(remainingQuota));

                response.getWriter().write("success");  // 返回成功消息
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("error");  // 返回错误消息
        }
    }
}
