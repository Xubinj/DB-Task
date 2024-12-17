<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="DAO.Applicant" %>
<%@ page import="DAO.ApplicantDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>查看学生简历</title>
</head>
<body>
    <h2>学生简历</h2>
    <%
        // 获取请求参数 applicantId
        String applicantIdStr = request.getParameter("applicantId");
        
        // 确保 applicantId 存在
        if (applicantIdStr != null) {
            int applicantId = Integer.parseInt(applicantIdStr);  // 将 String 转换为 int
            
            ApplicantDAO applicantDAO = new ApplicantDAO();  // 创建 ApplicantDAO 对象
            try {
                // 获取该学生的详细信息
                Applicant student = applicantDAO.getApplicantById(applicantId);
                if (student != null) {  // 如果找到了学生信息
    %>
                    <!-- 显示学生简历 -->
                    <h3><%= student.getName() %>的简历</h3>
                    <p><strong>姓名：</strong><%= student.getName() %></p>
                    <p><strong>学号：</strong><%= student.getIdNumber() %></p>
                    <p><strong>出生日期：</strong><%= student.getBirthDate() %></p>
                    <p><strong>本科院校：</strong><%= student.getUndergraduateSchool() %></p>
                    <p><strong>本科专业：</strong><%= student.getUndergraduateMajor() %></p>
                    <p><strong>初试成绩：</strong><%= student.getInitialExamScore() %></p>
                    <p><strong>复试成绩：</strong><%= student.getReExamScore() %></p>
                    <p><strong>电话：</strong><%= student.getPhone() %></p>
                    <p><strong>邮箱：</strong><%= student.getEmail() %></p>
    <%
                } else {
    %>
                    <p>找不到该学生的简历。</p>
    <%
                }
            } catch (SQLException e) {
                e.printStackTrace();
    %>
                <p>数据库访问错误。</p>
    <%
            }
        } else {
    %>
            <p>无效的学生 ID。</p>
    <%
        }
    %>

    <a href="TutorDashboardServlet">返回导师控制台</a>
</body>
</html>

