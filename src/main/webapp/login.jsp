<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录 - 研究生招生系统</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .login-container {
            background-color: #fff;
            padding: 20px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            width: 400px;
            text-align: center;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"], input[type="password"], input[type="text"]#id_number {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #0056b3;
        }
        .error-message {
            color: red;
            margin-top: 15px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>登录界面</h2>
        <form action="LoginServlet" method="post">
            <label for="username">用户名：</label>
            <input type="text" id="username" name="username" value="<%= request.getSession().getAttribute("username") != null ? request.getSession().getAttribute("username") : "" %>" required>

            <label for="id_number">学工号：</label> 
            <input type="text" id="id_number" name="id_number" value="<%= request.getSession().getAttribute("idnumber") != null ? request.getSession().getAttribute("idnumber") : "" %>" required>

            <label for="password">密码：</label>
            <input type="password" id="password" name="password" required>

            <button type="submit">登录</button>
        </form>

        <!-- 错误消息显示 -->
        <%
            String error = request.getParameter("error");
            if ("invalid".equals(error)) {
        %>
            <p class="error-message">用户名或密码或学工号错误，请重试。</p>
        <%
            } else if ("databaseError".equals(error)) {
        %>
            <p class="error-message">数据库连接错误，请联系管理员。</p>
        <%
            } else if ("spe".equals(error)) {
        %>
            <p class="error-message">学生登录有问题。</p>
        <%
            } else if ("Applicant".equals(error)) {
        %>    
            <p class="error-message">存在账号无法登录。</p>
        <%
            }
        %>
        
    </div>
</body>
</html>
