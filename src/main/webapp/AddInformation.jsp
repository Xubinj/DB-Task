<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Information</title>
    <style>
        /* 页面整体样式 */
        body {
            background: linear-gradient(to right, #6a9c87, #a8d5ba); /* 渐变背景 */
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        /* 表单容器样式 */
        .form-container {
            background-color: #ffffff;
            padding: 40px 50px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 450px;
            text-align: center;
        }

        /* 标题样式 */
        h2 {
            font-size: 24px;
            color: #2e7d32;
            margin-bottom: 30px;
        }

        /* 输入框和标签样式 */
        label {
            display: block;
            text-align: left;
            margin-top: 15px;
            font-size: 16px;
            color: #333;
        }

        input[type="date"],
        input[type="text"] {
            width: 100%;
            padding: 12px;
            margin-top: 5px;
            border-radius: 8px;
            border: 1px solid #ccc;
            font-size: 16px;
            transition: border 0.3s ease;
        }

        input[type="date"]:focus,
        input[type="text"]:focus {
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
        }

    </style>
    <script type="text/javascript">
        function showAlert(message) {
            alert(message);
        }

        function redirectToStatusCheck() {
            // 更新成功后跳转到 StatusCheckServlet 页面
            window.location.href = "tml.html"; // 修改为你希望跳转的页面
        }
    </script>
</head>
<body>

<%
    // 获取通过 URL 传递的参数
    String extractedIDCard = request.getParameter("extractedIDCard");

    // 处理提交
    String action = request.getParameter("action");
    if ("submit".equals(action)) {
        String birthdate = request.getParameter("birthdate");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        
        Connection conn1 = null;
        PreparedStatement pstmt1 = null;
        Connection conn2 = null;
        PreparedStatement pstmt2 = null;

        try {
            // 数据库连接配置
            String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=DB-Task;encrypt=true;trustServerCertificate=true;?useUnicode=true&characterEncoding=UTF-8"; // 替换为你的数据库
            String dbUser = "sa"; // 替换为你的数据库用户名
            String dbPassword = "12345678"; // 替换为你的数据库密码

            // 连接到数据库
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn1 = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            conn2 = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // 更新数据
            String sqlUpdata = "UPDATE Applicant SET birth_date = ?, email = ? WHERE id_number = ?";
            pstmt1 = conn1.prepareStatement(sqlUpdata);
            pstmt1.setDate(1, java.sql.Date.valueOf(birthdate)); // 设置出生日期
            pstmt1.setString(2, email); // 设置邮箱
            pstmt1.setString(3, extractedIDCard); // 设置身份证号

            String sqlInsert = "UPDATE UserAccount SET password_hash=?, email=? WHERE id_number = ?";
            pstmt2 = conn2.prepareStatement(sqlInsert);
            pstmt2.setString(1, password); // 设置密码
            pstmt2.setString(2, email); // 设置邮箱
            pstmt2.setString(3, extractedIDCard); // 设置身份证号

            int rowsAffected1 = pstmt1.executeUpdate();
            int rowsAffected2 = pstmt2.executeUpdate();

            if (rowsAffected1 > 0 && rowsAffected2 > 0) {
%>
                <script type="text/javascript">
                    window.onload = function() {
                        showAlert("更新成功！");
                        redirectToStatusCheck(); // 成功后跳转
                    }
                </script>
<%
            } else {
%>
                <script type="text/javascript">
                    window.onload = function() {
                        showAlert("未找到对应用户，无法更新。");
                    }
                </script>
<%
            }
        } catch (Exception e) {
            e.printStackTrace();
%>
            <script type="text/javascript">
                window.onload = function() {
                    showAlert("更新失败！" + "<%= e.getMessage() %>");
                }
            </script>
<%
        } finally {
            // 关闭资源
            if (pstmt1 != null) try { pstmt1.close(); } catch (SQLException ignored) {}
            if (pstmt2 != null) try { pstmt2.close(); } catch (SQLException ignored) {}
            if (conn1 != null) try { conn1.close(); } catch (SQLException ignored) {}
            if (conn2 != null) try { conn2.close(); } catch (SQLException ignored) {}
        }
    }
%>

<div class="form-container">
    <h2>请补充剩余信息！</h2>
    <form action="AddInformation.jsp" method="post">
        <input type="hidden" name="action" value="submit"> <!-- 添加隐藏字段以识别提交 -->
        <input type="hidden" name="extractedIDCard" value="<%= extractedIDCard %>"> <!-- 传递身份证号 -->

        <label for="birthdate">选择出生日期(格式为年-月-日):</label>
        <input type="date" id="birthdate" name="birthdate" required>

        <label for="email">邮箱地址:</label>
        <input type="text" id="email" name="email" placeholder="请输入邮箱地址" required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$">

        <label for="password">密码:</label>
        <input type="text" id="password" name="password" required>

        <input type="submit" value="提交">
    </form>
</div>

</body>
</html>
