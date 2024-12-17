<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>申请状态 - 等待领导审批</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f8ff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            text-align: center;
            background-color: #fff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #FF9800;
            margin-bottom: 20px;
        }
        p {
            color: #555;
            font-size: 16px;
            margin-bottom: 30px;
        }
        .highlight {
            color: #007BFF; /* 蓝色高亮 */
            font-weight: bold;
        }
        a {
            text-decoration: none;
            padding: 10px 20px;
            background-color: #007BFF;
            color: white;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>申请状态：已提交志愿</h1>
        
        <% 
            // 获取 session 中的 username 和 idnumber
            String username = (String) session.getAttribute("username");
            String idnumber = (String) session.getAttribute("idnumber");
        
            if (username != null && idnumber != null) {
        %>
        <p>亲爱的同学，您的学号为 <span class="highlight"><%= idnumber %></span>，姓名为 <span class="highlight"><%= username %></span></p>
        <p>您的申请正提交，请您耐心等候。</p>
        <% } else { %>
        <p>未能找到您的信息，请您先登录后再尝试。</p>
        <% } %>

        <p>如果您有任何问题或需要帮助，请随时联系我们。</p>
        <a href="login.jsp">返回登录页面</a>
    </div>
</body>
</html>
