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
import java.util.ArrayList;
import java.util.List;


@WebServlet("/TutorDashboardServlet")
public class TutorDashboardServlet extends HttpServlet {
    private AdvisorService advisorService;
    private SelectionPhaseService phaseService;

    public TutorDashboardServlet() {
        this.advisorService = new AdvisorService();
        this.phaseService = new SelectionPhaseService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前导师的用户信息
        UserAccount tutor = (UserAccount) request.getSession().getAttribute("user");

        // 检查用户是否已登录且角色为导师
        if (tutor != null && "Advisor".equals(tutor.getRole())) {
            try {
                int advisorId = advisorService.getAdvisorIdByUsername(tutor.getUsername());
                int remainingQuota = advisorService.getRemainingQuota(advisorId);  // 获取剩余名额
                request.setAttribute("remainingQuota", remainingQuota);

                // 获取当前阶段
                SelectionPhase currentPhase = phaseService.getCurrentSelectionPhase();
                if (currentPhase != null) {
                    request.setAttribute("currentPhase", currentPhase);
                    
                    // 获取当前阶段的学生列表
                    List<Applicant> studentsWithPriority = new ArrayList<>();

                    // 根据当前阶段获取相应的学生列表
                    if ("第一志愿选择阶段".equals(currentPhase.getPhaseName())) {
                        studentsWithPriority = advisorService.getFirstChoiceStudents(advisorId);  // 第一志愿学生
                    } else if ("第二志愿选择阶段".equals(currentPhase.getPhaseName())) {
                        studentsWithPriority = advisorService.getSecondChoiceStudents(advisorId);  // 第二志愿学生
                    } else if ("第三志愿选择阶段".equals(currentPhase.getPhaseName())) {
                        studentsWithPriority = advisorService.getThirdChoiceStudents(advisorId);  // 第三志愿学生
                    } else if ("学科集中商议与抽签自由选择阶段".equals(currentPhase.getPhaseName())) {
                        studentsWithPriority = advisorService.getFreeChoiceStudents(advisorId);  // 自由选择学生
                    }

                    // 判断学生是否已被分配给导师
                    for (Applicant student : studentsWithPriority) {
                        boolean isSelected = advisorService.isStudentAssignedToAdvisor(student.getApplicantId(), advisorId);
                        student.setAssigned(isSelected);  // 标记学生是否已分配
                    }

                    // 设置学生列表到请求中
                    request.setAttribute("studentsWithPriority", studentsWithPriority);

                    // 根据当前阶段跳转到不同的页面
                    if ("第一志愿选择阶段".equals(currentPhase.getPhaseName())) {
                        request.getRequestDispatcher("firstChoice.jsp").forward(request, response);
                    } else if ("第二志愿选择阶段".equals(currentPhase.getPhaseName())) {
                        request.getRequestDispatcher("secondChoice.jsp").forward(request, response);
                    } else if ("第三志愿选择阶段".equals(currentPhase.getPhaseName())) {
                        request.getRequestDispatcher("thirdChoice.jsp").forward(request, response);
                    } else if ("学科集中商议与抽签自由选择阶段".equals(currentPhase.getPhaseName())) {
                        request.setAttribute("availableStudents", studentsWithPriority);
                    	request.getRequestDispatcher("freeChoice.jsp").forward(request, response);
                    } else if("录取结果公示阶段".equals(currentPhase.getPhaseName())){
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "志愿选择阶段已结束，当前为公示阶段");
                    }else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "当前没有进行中的阶段");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "没有找到当前阶段");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "数据库访问错误");
            }
        } else {
            // 用户未登录或角色不为导师，重定向到登录页面
            response.sendRedirect("login.jsp");
        }
    }
}
