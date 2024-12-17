<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="DAO.AdvisorDAO" %>
<%@ page import="DAO.AdvisorSelectionOrderDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.Advisor" %>
<%@ page import="Service.*" %>
<%@ page import="DAO.*" %>



<%
    SelectionPhaseService selectionPhaseService = new SelectionPhaseService();
    SelectionPhase sp = selectionPhaseService.getCurrentSelectionPhase();
    AdvisorSelectionOrderDAO selectionOrderDAO = new AdvisorSelectionOrderDAO();
    AdvisorService advisorService = new AdvisorService();
    
    // 获取符合条件的导师
    List<Advisor> eligibleAdvisors = selectionPhaseService.getEligibleAdvisors("大数据技术与人工智能");
    List<Advisor> advisorsWithoutApplicants = advisorService.getAdvisorsWithoutApplicants();

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
    <%if(sp!=null&&"学科集中商议与抽签自由选择阶段".equals(sp.getPhaseName())){ %>
    <div class="container mt-4">
        <h3>符合自由选择阶段的导师</h3>

        <% if (message != null) { %>
            <div class="alert alert-success" role="alert">
                <%= message %>
            </div>
        <% } %>

        <form method="post" action="AssignSelectionTimeServlet">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>导师姓名</th>
                        <th>剩余名额</th>
                        <th>是否有学生报考</th>
                        <th>自由选择时间段</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        for (Advisor advisor : eligibleAdvisors) {
                            // 判断导师是否已有时间段
                            boolean hasTimeAssigned = selectionOrderDAO.hasAssignedTime(advisor.getAdvisorId());
                    %>
                    <tr>
                        <td><%= advisor.getName() %></td>
                        <td><%= advisor.getRemainingQuota() %></td>
                        <td><%
								boolean isNoApplicants = false;
								for (Advisor noApplicantAdvisor : advisorsWithoutApplicants) {
   									if (noApplicantAdvisor.getAdvisorId() == advisor.getAdvisorId()) {
        								isNoApplicants = true;
        								break;
    								}
								}
%>
<%= isNoApplicants ? "没有学生报考" : "有学生报考" %>
</td>
                        
                        <td>
                            <% if (hasTimeAssigned) { %>
                                <!-- 已有时间段，显示修改按钮 -->
                                <input type="datetime-local" name="start_time_<%= advisor.getAdvisorId() %>" value="<%= selectionOrderDAO.getAssignedStartTime(advisor.getAdvisorId()) %>" >
                                至
                                <input type="datetime-local" name="end_time_<%= advisor.getAdvisorId() %>" value="<%= selectionOrderDAO.getAssignedEndTime(advisor.getAdvisorId()) %>" >
                                <button type="submit" name="modify" value="<%= advisor.getAdvisorId() %>" class="btn btn-warning">修改</button>
                            <% } else { %>
                                <!-- 没有时间段，显示提交按钮 -->
                                <input type="datetime-local" name="start_time_<%= advisor.getAdvisorId() %>" >
                                至
                                <input type="datetime-local" name="end_time_<%= advisor.getAdvisorId() %>" >
                                <button type="submit" name="assign" value="<%= advisor.getAdvisorId() %>" class="btn btn-primary">提交</button>
                            <% } %>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </form>
    </div>
    <%} %>
</body>
</html>
