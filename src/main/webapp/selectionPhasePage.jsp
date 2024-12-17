<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Service.SelectionPhaseService" %>
<%@ page import="DAO.UserAccount" %>
<%@ page import="java.util.Date" %>
<%@ page import="DAO.*" %>
<%@ page import="Service.*" %>

<%
    SelectionPhaseService phaseService = new SelectionPhaseService();
    SelectionPhase currentPhase = phaseService.getCurrentSelectionPhase();  // 获取当前阶段
    SelectionPhase nextPhase = phaseService.getNextSelectionPhase();        // 获取下一个阶段
    AdvisorService advisorService = new AdvisorService();
    // 获取当前用户信息
    UserAccount user = (UserAccount) session.getAttribute("user");
    String username = user.getUsername();
    
    // 通过用户名获取当前导师的advisorId
    int advisorId = advisorService.getAdvisorIdByUsername(username);
    
    // 判断导师是否可以进行自由选择
    boolean canSelectStudent = phaseService.canSelectStudentForAdvisor(advisorId); 
    boolean isFreeSelectionPhase = phaseService.isFreeSelectionPhase();  // 判断是否是自由选择阶段
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>导师选择导航</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>导师选择系统</h2>

        <% if (currentPhase != null) { %>
            <p>当前志愿选择阶段：<%= currentPhase.getPhaseName() %></p>
            <p>选择阶段开始时间：<%= currentPhase.getStartTime() %></p>
            <p>选择阶段结束时间：<%= currentPhase.getEndTime() %></p>
            
            <% if (isFreeSelectionPhase && canSelectStudent) { %>
                <a href="TutorDashboardServlet" class="btn btn-primary">进入自由选择</a>
            <% } else if (isFreeSelectionPhase && !canSelectStudent) { %>
                <p>当前不在您的选择时间段内，请等待您的选择时段。</p>
            <% } else { %>
                <a href="TutorDashboardServlet" class="btn btn-primary">进入志愿选择</a>
            <% } %>
        <% } else if (nextPhase != null) { %>
            <p>下一个志愿选择阶段：<%= nextPhase.getPhaseName() %></p>
            <p>开始时间：<%= nextPhase.getStartTime() %></p>
            <p>结束时间：<%= nextPhase.getEndTime() %></p>
            <button class="btn btn-secondary" disabled>下一个阶段尚未开始</button>
        <% } else { %>
            <p>当前没有进行中的志愿选择阶段。</p>
            <button class="btn btn-secondary" disabled>没有进行中的阶段</button>
        <% } %>
    </div>
</body>
</html>
