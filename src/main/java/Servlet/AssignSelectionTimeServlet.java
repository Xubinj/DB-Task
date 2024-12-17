package Servlet;

import DAO.AdvisorSelectionOrderDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParseException;

@WebServlet("/AssignSelectionTimeServlet")
public class AssignSelectionTimeServlet extends HttpServlet {

    private AdvisorSelectionOrderDAO selectionOrderDAO = new AdvisorSelectionOrderDAO();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int advisorId;
    	if (request.getParameter("modify") != null) {
            advisorId = Integer.parseInt(request.getParameter("modify"));  // 获取按钮修改的值（导师 ID）
    	}else {
            advisorId = Integer.parseInt(request.getParameter("assign"));  // 获取按钮提交的值（导师 ID）
    	}
        // 获取提交的时间段
        String startTimeStr = request.getParameter("start_time_" + advisorId);
        String endTimeStr = request.getParameter("end_time_" + advisorId);
        
        // 处理 datetime-local 格式，转换为 yyyy-MM-dd HH:mm:ss 格式
        startTimeStr = startTimeStr.replace("T", " ") + ":00.000";  // 加上秒数
        endTimeStr = endTimeStr.replace("T", " ") + ":00.000";  // 加上秒数
        
        // 转换为 Timestamp 类型
        Timestamp startTime = Timestamp.valueOf(startTimeStr);
        Timestamp endTime = Timestamp.valueOf(endTimeStr);
        
        try {
            // 判断是修改还是提交
            if (request.getParameter("modify") != null) {
                // 修改时间段
                selectionOrderDAO.updateSelectionTimeForAdvisor(advisorId, startTime, endTime);
                request.setAttribute("message", "时间段修改成功！");
            } else {
                // 提交时间段
                selectionOrderDAO.insertSelectionTimeForAdvisor(advisorId, startTime, endTime);
                request.setAttribute("message", "时间段提交成功！");
            }
            // 重定向回学科秘书页面
            response.sendRedirect("studentSecretaryPage.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "数据库错误");
        }
    }
}

