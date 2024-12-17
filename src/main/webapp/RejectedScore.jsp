<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>申请状态 - 未通过</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #fef2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            text-align: center;
            background-color: #ffffff;
            padding: 40px 50px;
            border-radius: 12px;
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 90%;
        }
        .icon {
            font-size: 60px;
            color: #f44336;
            margin-bottom: 20px;
        }
        h1 {
            font-size: 24px;
            color: #f44336;
            margin-bottom: 20px;
        }
        p {
            color: #555555;
            font-size: 16px;
            margin-bottom: 30px;
            line-height: 1.5;
        }
        .highlight {
            color: #007BFF; /* 蓝色高亮 */
            font-weight: bold;
        }
        .button {
            text-decoration: none;
            padding: 12px 20px;
            background-color: #007BFF;
            color: #ffffff;
            border-radius: 6px;
            font-size: 16px;
            font-weight: bold;
            display: inline-block;
            transition: background-color 0.3s, box-shadow 0.3s;
        }
        .button:hover {
            background-color: #0056b3;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="icon">❌</div>
        <h1>申请状态：未通过</h1>
        
        <% 
            // 获取 session 中的 username 和 idnumber
            String username = (String) session.getAttribute("username");
            String idnumber = (String) session.getAttribute("idnumber");
        
            if (username != null && idnumber != null) {
        %>
        <p>
            亲爱的同学，您的学号为 <span class="highlight"><%= idnumber %></span>，姓名为 <span class="highlight"><%= username %></span><br>
            很遗憾，您的复试成绩未通过审批。<br>
            您未能成为我校研究生，但未来还有无限可能。<br>
            希望在将来的某一天与您再度相遇！
        </p>
        <% } else { %>
        <p>未能找到您的信息，请您先登录后再尝试。</p>
        <% } %>

        <a href="login.jsp" class="button">返回登录</a>
    </div>
</body>
</html>
