<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Servlet.AssignmentDetail" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>领导控制台</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f8ff;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        .container {
            width: 90%;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        p {
            color: #555;
            font-size: 16px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
            font-weight: bold;
        }
        td {
            background-color: #f9f9f9;
        }
        th, td {
            padding: 12px;
            text-align: center;
        }
        form {
            margin: 0;
        }
        button {
            padding: 5px 15px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #0056b3;
        }
        .message {
            color: #4CAF50;
            font-size: 16px;
            margin: 10px 0;
        }
        .error {
            color: #f44336;
            font-size: 16px;
        }
        .status-pending {
            color: #FFA500; /* 待审批为橙色 */
            font-weight: bold;
        }
        .status-accepted {
            color: #4CAF50; /* 已通过为绿色 */
            font-weight: bold;
        }
        .status-rejected {
            color: #f44336; /* 已拒绝为红色 */
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>领导控制台</h1>
        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
        <p class="message"><%= message %></p>
        <%
            }
        %>
        <p>以下是学生与导师的分配关系：</p>
        <table>
            <tr>
                <th>分配ID</th>
                <th>学号</th>
                <th>学生姓名</th>
                <th>初试成绩</th>
                <th>复试成绩</th>
                <th>导师ID</th>
                <th>导师姓名</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            <%
                List<AssignmentDetail> assignments = (List<AssignmentDetail>) request.getAttribute("assignmentDetails");
                if (assignments != null && !assignments.isEmpty()) {
                    for (AssignmentDetail assignment : assignments) {
            %>
            <tr>
                <td><%= assignment.getAssignmentId() %></td>
                <td><%= assignment.getApplicant().getIdNumber() %></td>
                <td><%= assignment.getApplicant().getName() %></td>
                <td><%= assignment.getApplicant().getInitialExamScore() %></td>
                <td><%= assignment.getApplicant().getReExamScore() %></td>
                <td><%= assignment.getAdvisor().getAdvisorId() %></td>
                <td><%= assignment.getAdvisor().getName() %></td>
                <td>
                    <% if (assignment.getStatus() == 1) { %>
                        <span class="status-accepted">已通过</span>
                    <% } else if (assignment.getStatus() == -1) { %>
                        <span class="status-rejected">已拒绝</span>
                    <% } else { %>
                        <span class="status-pending">待审批</span>
                    <% } %>
                </td>
                <td>
                      <form action="LeaderDashboardServlet" method="post">
					        <input type="hidden" name="assignment_id" value="<%= assignment.getAssignmentId() %>">
					        <input type="hidden" name="action" value="update">
					        <button type="submit" name="status" value="1">同意</button>
					        <button type="submit" name="status" value="-1">不同意</button>
					   </form>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="9" class="error">暂无分配数据。</td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
</body>
</html>
