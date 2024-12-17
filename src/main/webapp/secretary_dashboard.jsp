<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="DAO.Applicant" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>秘书控制台</title>
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
            width: 80%;
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
        input[type="text"] {
            width: 80px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            text-align: center;
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
        .no-data {
            color: #f44336;
            font-weight: bold;
        }
        .logout {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #f44336;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 14px;
            transition: background-color 0.3s;
        }
        .logout:hover {
            background-color: #d32f2f;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>秘书控制台</h1>
        <p>以下是所有学生的初试和复试成绩，可以点击“更新”按钮进行修改：</p>

        <table>
            <tr>
                <th>学生ID</th>
                <th>姓名</th>
                <th>初试成绩</th>
                <th>复试成绩</th>
                <th>操作</th>
            </tr>
            <%
                List<Applicant> applicants = (List<Applicant>) request.getAttribute("applicants");
                if (applicants != null && !applicants.isEmpty()) {
                    for (Applicant applicant : applicants) {
            %>
            <tr>
                <td><%= applicant.getApplicantId() %></td>
                <td><%= applicant.getName() %></td>
                <td><%= applicant.getInitialExamScore() %></td>
                <td><%= applicant.getReExamScore() %></td>
                <td>
                    <form action="SecretaryDashBoardServlet" method="post">
                        <input type="hidden" name="applicant_id" value="<%= applicant.getApplicantId() %>">
                        初试成绩：<input type="text" name="initial_exam_score" required value="<%= applicant.getInitialExamScore() %>">
                        复试成绩：<input type="text" name="re_exam_score" required value="<%= applicant.getReExamScore() %>">
                        <button type="submit">更新</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="5" class="no-data">暂无学生信息。</td>
            </tr>
            <%
                }
            %>
        </table>
        <a href="login.jsp" class="logout">退出登录</a>
    </div>
</body>
</html>
