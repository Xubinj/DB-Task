<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>角色不存在</title>
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
            max-width: 500px;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #f44336;
            margin-bottom: 10px;
        }
        p {
            font-size: 16px;
            color: #555;
            margin: 10px 0;
        }
        .icon {
            font-size: 50px;
            color: #f44336;
            margin-bottom: 20px;
        }
        .actions {
            margin-top: 20px;
        }
        .button {
            display: inline-block;
            margin: 10px;
            padding: 10px 20px;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            background-color: #007BFF;
            transition: background-color 0.3s;
        }
        .button:hover {
            background-color: #0056b3;
        }
        .secondary {
            background-color: #6c757d;
        }
        .secondary:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="icon">❌</div>
        <h1>角色不存在</h1>
        <p>抱歉，当前角色无法识别或您没有权限访问此页面。</p>
        <p>请确认您的账号信息，或返回登录页面重新尝试。</p>

        <div class="actions">
            <a href="login.jsp" class="button">返回登录</a>
        </div>
    </div>
</body>
</html>
