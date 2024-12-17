<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="DAO.AdvisorDAO" %>
<%@ page import="DAO.AdvisorSelectionOrderDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.Advisor" %>
<%@ page import="Service.*" %>
<%@ page import="DAO.*" %>

<%
    SelectionPhaseService selectionPhaseService = new SelectionPhaseService();
    // 获取所有选择阶段的时间段
    List<SelectionPhase> selectionPhases = selectionPhaseService.getAllSelectionPhases();
    String message = (String) request.getAttribute("message"); // 显示提示信息
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>学科秘书管理</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>学科秘书管理</h2>
        <h3>选择阶段时间段管理</h3>

        <% if (message != null) { %>
            <div class="alert alert-success" role="alert">
                <%= message %>
            </div>
        <% } %>

        <% 
            for (SelectionPhase phase : selectionPhases) {
                int phaseId = phase.getPhaseId();
        %>
        <form method="post" action="AssignSelectionTimeServlet2">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>阶段名称</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><%= phase.getPhaseName() %></td>
                        <td><%= phase.getStartTime() %></td>
                        <td><%= phase.getEndTime() %></td>
                        <td>
                            <input type="datetime-local" name="start_time" value="<%= phase.getStartTime() %>" >
                            至
                            <input type="datetime-local" name="end_time" value="<%= phase.getEndTime() %>" >
                            <input type="hidden" name="phaseId" value="<%= phaseId %>">
                            <button type="submit" class="btn btn-primary">修改时间段</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
        <% } %>
    </div>
</body>
</html>
