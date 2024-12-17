package Servlet;
import DAO.*;
import Service.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.FinalAssignmentDAO;

@WebServlet("/SelectStudentServlet")
public class SelectStudentServlet extends HttpServlet {
    private AdvisorService advisorService = new AdvisorService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserAccount tutor = (UserAccount) request.getSession().getAttribute("user");
        if (tutor != null && "Advisor".equals(tutor.getRole())) {
            String[] selectedStudentIds = request.getParameterValues("selectedStudents");

            if (selectedStudentIds != null) {
                try {
                    int advisorId = advisorService.getAdvisorIdByUsername(tutor.getUsername());
                    int year = 2024;  // 假设是2024年

                    // 获取导师剩余招生名额
                    int RemainingQuota = advisorService.getRemainingQuota(advisorId);

                    if (selectedStudentIds.length > RemainingQuota) {
                        // 如果选择的学生超过剩余名额，提示超出名额
                        response.sendRedirect("tutor_dashboard.jsp?error=quotaExceeded");
                        return;
                    }

                    // 遍历选择的学生，分配导师
                    for (String studentIdStr : selectedStudentIds) {
                        int applicantId = Integer.parseInt(studentIdStr);

                        // 将学生分配给导师
                        boolean success = advisorService.assignStudentToAdvisor(advisorId, applicantId, year);
                        if (!success) {
                            // 如果分配失败（名额已满），重定向并显示错误
                            response.sendRedirect("tutor_dashboard.jsp?error=quotaExceeded");
                            return;
                        }
                    }

                    // 获取更新后的剩余名额
                    int remainingQuota = advisorService.getRemainingQuota(advisorId);

                    // 将剩余名额传递给 JSP 页面
                    request.setAttribute("remainingQuota", remainingQuota);
                    
                    // 如果选择成功，重定向并显示成功消息
                    response.sendRedirect("tutor_dashboard.jsp?success=true");
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "数据库访问错误");
                }
            } else {
                response.sendRedirect("tutor_dashboard.jsp?error=noSelection");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
