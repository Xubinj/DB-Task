<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="DAO.Applicant, DAO.Advisor" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生分配结果界面</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            background-color: #f9f9f9;
        }
        .container {
            text-align: center;
            max-width: 600px;
            width: 100%;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        .info-box {
            background-color: #f4f4f4;
            padding: 20px;
            margin-top: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
        }
        .info-box h2 {
            color: #555;
            margin-top: 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            font-weight: bold;
            color: #333;
        }
        .success {
            color: green;
            background-color: #e6ffe6;
            padding: 10px;
            border: 1px solid #b3ffb3;
            border-radius: 5px;
            margin-top: 15px;
        }
        .error {
            color: red;
            background-color: #ffe6e6;
            padding: 10px;
            border: 1px solid #ffb3b3;
            border-radius: 5px;
            margin-top: 15px;
        }
        .logout {
            display: inline-block;
            margin-top: 20px;
            padding: 8px 16px;
            color: #fff;
            background-color: #007BFF;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .logout:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>学生分配结果</h1>
        <%
            Applicant appli = (Applicant) request.getAttribute("applicantDetails");
            Advisor advisor = (Advisor) request.getAttribute("advisorDetails");

            if (appli != null) {
        %>
            <div class="info-box">
                <h2>学生信息</h2>
                <table>
<%--                     <tr><th></th><td><%= appli.getApplicantId() %></td></tr> --%>
                    <tr><th>姓名</th><td><%= appli.getName() %></td></tr>
                    <tr><th>学号</th><td><%= appli.getIdNumber() %></td></tr>
                    <tr><th>本科院校</th><td><%= appli.getUndergraduateSchool() %></td></tr>
                    <tr><th>本科专业</th><td><%= appli.getUndergraduateMajor() %></td></tr>
                    <tr><th>初试成绩</th><td><%= appli.getInitialExamScore() %></td></tr>
                    <tr><th>复试成绩</th><td><%= appli.getReExamScore() %></td></tr>
                </table>
            </div>

            <%
                if (advisor != null) {
            %>
                <div class="info-box success">
                    <h2>导师信息</h2>
                    <p>恭喜你！你已成功被分配到导师。</p>
                    <table>
                        <tr><th>导师姓名</th><td><%= advisor.getName() %></td></tr>
                        <tr><th>职称</th><td><%= advisor.getTitle() %></td></tr>
                        <tr><th>邮箱</th><td><%= advisor.getEmail() %></td></tr>
                        <tr><th>电话</th><td><%= advisor.getPhone() %></td></tr>
                        <tr><th>部门</th><td><%= advisor.getDepartment() %></td></tr>
                    </table>
                </div>
            <%
                } else {
            %>
                <p class="error">尚未分配导师，请耐心等待。</p>
            <%
                }
            %>
        <%
            } else {
        %>
            <p class="error">无法找到你的申请信息，请稍后重试。</p>
        <%
            }
        %>

        <p>
            <a href="login.jsp" class="logout">退出登录</a>
        </p>
    </div>
</body>
</html>
