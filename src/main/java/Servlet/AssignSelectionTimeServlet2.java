package Servlet;

import DAO.SelectionPhaseDAO;
import Service.SelectionPhaseService;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AssignSelectionTimeServlet2")
public class AssignSelectionTimeServlet2 extends HttpServlet {

    private SelectionPhaseDAO selectionPhaseDAO = new SelectionPhaseDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 获取选择阶段ID
            int phaseId = Integer.parseInt(request.getParameter("phaseId"));
            System.out.println(phaseId);

            // 获取提交的时间段
            String startTimeStr = request.getParameter("start_time");
            String endTimeStr = request.getParameter("end_time");

            // 处理时间段格式：datetime-local 格式转换为 yyyy-MM-dd HH:mm:ss 格式
            startTimeStr = startTimeStr.replace("T", " ") + ":00.000";  // 加上秒数
            endTimeStr = endTimeStr.replace("T", " ") + ":00.000";  // 加上秒数

            // 转换为 Timestamp 类型
            Timestamp startTime = Timestamp.valueOf(startTimeStr);
            Timestamp endTime = Timestamp.valueOf(endTimeStr);

            // 更新选择阶段的时间段
            selectionPhaseDAO.updateSelectionPhaseTime(phaseId, startTime, endTime);
            request.setAttribute("message", "时间段修改成功！");

            // 重定向回学科秘书页面
            response.sendRedirect("studentSecretaryPage2.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "数据库错误");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "参数错误");
        }
    }
}
