<%@ page import="java.sql.*, java.util.*, javax.servlet.*, javax.servlet.http.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Applicant</title>
    <meta charset="UTF-8"> <!-- 确保使用UTF-8字符集 -->
    <style>
    /* 页面整体样式 */
    body {
        background: linear-gradient(to right, #6a9c87, #a8d5ba);
        font-family: 'Arial', sans-serif;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    /* 增大表单容器样式 */
    .form-container {
        background-color: #ffffff;
        padding: 60px 70px; /* 增大内边距 */
        border-radius: 15px;
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        width: 100%; /* 保持全宽 */
        max-width: 600px; /* 增大最大宽度 */
        text-align: center;
    }

    /* 标题样式 */
    h2 {
        font-size: 24px;
        color: #2e7d32;
        margin-bottom: 30px;
    }

    /* 输入框和标签样式 */
    .form-group {
        display: flex;
        align-items: center;
        margin-top: 15px;
        justify-content: space-between;
    }

    label {
        font-size: 16px;
        color: #333;
        flex: 2; /* 增大标签所占的空间 */
        text-align: left;
        margin-right: 10px; /* 标签和输入框间的间距 */
    }

    input[type="date"],
    input[type="text"],
    input[type="email"] {
        width: 50%; /* 保持输入框宽度为其余空间 */
        padding: 12px;
        border-radius: 8px;
        border: 1px solid #ccc;
        font-size: 16px;
        transition: border 0.3s ease;
    }

    input[type="date"]:focus,
    input[type="text"]:focus,
    input[type="email"]:focus {
        border-color: #4caf50;
        outline: none;
    }

    /* 提交按钮样式 */
    input[type="submit"] {
        background-color: #4caf50;
        color: white;
        border: none;
        padding: 12px 20px;
        border-radius: 8px;
        font-size: 18px;
        cursor: pointer;
        margin-top: 20px;
        width: 100%;
        transition: background-color 0.3s ease;
    }

    input[type="submit"]:hover {
        background-color: #45a049;
    }

    /* 响应式设计 */
    @media (max-width: 480px) {
        .form-container {
            padding: 20px;
            max-width: 90%;
        }

        h2 {
            font-size: 20px;
        }

        .form-group {
            flex-direction: column; /* 在小屏幕上，变为纵向排列 */
            align-items: flex-start; /* 左对齐 */
        }

        label {
            margin-right: 0; /* 移除小屏幕下的右边距 */
            margin-bottom: 5px; /* 增加底部间距 */
        }

        input[type="date"],
        input[type="text"],
        input[type="email"] {
            width: 100%;  /* 在小屏幕下，输入框全宽 */
        }
    }
</style>
    <script>
        function validateInput() {
            var email = document.getElementById("email").value;
            var phone = document.getElementById("phone").value;
            var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            var phonePattern = /^[0-9]{11}$/;

            if (!emailPattern.test(email)) {
                alert("无效的邮箱格式。");
                return false;
            }
            if (!phonePattern.test(phone)) {
                alert("手机号必须为11位数字。");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

<%
	String id_number = (String) request.getSession().getAttribute("idnumber");
    String query = "SELECT * FROM Applicant WHERE id_number = ?";
    String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=DB-Task;encrypt=true;trustServerCertificate=true;characterEncoding=UTF-8;";
    String DB_USER = "sa";
    String DB_PASSWORD = "12345678";

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setString(1, id_number);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) { 
%>
            <form method="POST" action="<%= request.getRequestURI() %>" onsubmit="return validateInput();">
                <div class="form-group">
                    <label for="name">名字:</label>
                    <input type="text" id="name" name="name" value="<%= rs.getString("name") %>" readonly style="background-color: #f9f9f9; cursor: not-allowed;">
                </div>

                <div class="form-group">
                    <label for="id_number">学号:</label>
                    <input type="text" id="id_number" name="id_number" value="<%= rs.getString("id_number") %>" readonly style="background-color: #f9f9f9; cursor: not-allowed;">
                </div>

                <div class="form-group">
                    <label for="birth_date">出生日期:</label>
                    <input type="date" id="birth_date" name="birth_date" value="<%= rs.getDate("birth_date") %>" readonly style="background-color: #f9f9f9; cursor: not-allowed;">
                </div>

                <div class="form-group">
                    <label for="undergraduate_school">毕业学校:</label>
                    <input type="text" id="undergraduate_school" name="undergraduate_school" value="<%= rs.getString("undergraduate_school") %>" readonly style="background-color: #f9f9f9; cursor: not-allowed;">
                </div>

                <div class="form-group">
                    <label for="undergraduate_major">毕业专业:</label>
                    <input type="text" id="undergraduate_major" name="undergraduate_major" value="<%= rs.getString("undergraduate_major") %>" readonly style="background-color: #f9f9f9; cursor: not-allowed;">
                </div>

                <div class="form-group">
                    <label for="phone">手机号:</label>
                    <input type="text" id="phone" name="phone" value="<%= rs.getString("phone") %>" required>
                </div>

                <div class="form-group">
                    <label for="email">邮箱号:</label>
                    <input type="email" id="email" name="email" value="<%= rs.getString("email") %>" required>
                </div>

                <input type="hidden" name="action" value="submit">

                <input type="submit" value="确认修改">
            </form>
<%
        } else {
            out.println("没有找到指定学号的申请人。");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        out.println("数据库错误: " + e.getMessage());
    }

    String action = request.getParameter("action");
    if ("submit".equals(action)) {
        try {
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            String updateQuery = "UPDATE Applicant SET phone = ?, email = ? WHERE id_number = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement ps = conn.prepareStatement(updateQuery)) {
                ps.setString(1, phone);
                ps.setString(2, email);
                ps.setString(3, id_number);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    out.println("记录更新成功。");
                    response.sendRedirect("tml.html"); // 重定向到成功页面
                } else {
                    out.println("没有记录更新，请检查输入数据。");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("数据库更新错误: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("处理数据错误: " + e.getMessage());
        }
    }
%>
</body>
</html>