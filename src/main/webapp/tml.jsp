<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="DAO.AdvisorDAO" %>
<%@ page import="DAO.AdvisorSelectionOrderDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.Advisor" %>
<%@ page import="Service.*" %>
<%@ page import="DAO.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>2025年硕士研究生招生专业目录</title>
    <style>
        body {
            font-family: '微软雅黑', sans-serif;
            background-color: #f8f9fa; /* 淡灰背景 */
            margin: 0;
            padding: 0;
            position: relative;
        }

        h1 {
            text-align: center;
            color: #0066cc;
            margin-top: 40px;
            font-size: 36px;
        }

        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            background-color: #fff;
            border-radius: 10px;
            overflow: hidden;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
            font-size: 16px;
        }

        th {
            background-color: #f1f1f1;
            font-weight: bold;
            color: #333;
        }

        td a {
            text-decoration: none;
            color: #0066cc;
        }

        td a:hover {
            color: #ff6600;
            text-decoration: underline;
        }

        button {
            font-size: 18px;
            background-color: #0066cc;
            color: white;
            border: none;
            padding: 12px 25px;
            cursor: pointer;
            border-radius: 8px;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #005bb5;
        }

        .page-number {
            text-align: center;
            margin-top: 30px;
            font-weight: bold;
            color: #0066cc;
            font-size: 18px;
        }

        /* 响应式设计 */
        @media (max-width: 768px) {
            table {
                width: 95%;
            }

            button {
                width: 100%;
                padding: 15px;
                font-size: 20px;
            }
        }

        /* 设置右上角超链接上下排列 */
        .link-container {
            position: absolute;
            top: 20px;
            right: 20px;
            display: flex;
            flex-direction: column;
            gap: 10px; /* 设置链接之间的间隔 */
        }

        /* 统一三个超链接的样式 */
        .link-container a {
            font-size: 18px;
            color: #0066cc;
            text-decoration: none;
            transition: color 0.3s ease, text-decoration 0.3s ease;
        }

        .link-container a:hover {
            color: #ff6600;
            text-decoration: underline;
        }
    </style>
    <script>
        function redirectToSubjectCatalog() {
            window.location.href = "/DB-Task/SubjectServlet";
        }

        function redirectToMaterialScience() {
            window.location.href = "/DB-Task/MaterialScienceServlet";
        }
    </script>
</head>
<body>
    <h1>2025年硕士研究生招生专业目录</h1>
    <table>
        <tr>
            <th colspan="2">
                <!-- 使用按钮代替超链接，点击后跳转到 SubjectServlet -->
                <button onclick="redirectToSubjectCatalog()">信息学院</button>
            </th>
        </tr>
        <tr>
            <td>1</td>
            <td><a href="https://it.bjfu.edu.cn/xkjs/xkxwdjj/372706.html" target="_blank">计算机科学与技术</a></td>
        </tr>
        <tr>
            <td>9</td>
            <td><a href="https://it.bjfu.edu.cn/xkjs/xkxwdjj/372709.html" target="_blank">电子信息（全日制专业学位）</a></td>
        </tr>
        <tr>
            <td>16</td>
            <td><a href="https://it.bjfu.edu.cn/xkjs/xkxwdjj/372709.html" target="_blank">电子信息（非全日制专业学位）</a></td>
        </tr>
        <tr>
            <td>20</td>
            <td><a href="https://it.bjfu.edu.cn/xkjs/xkxwdjj/372710.html" target="_blank">农业工程与信息技术（全日制专业学位）</a></td>
        </tr>
        <!-- 将“材料科学与技术学院”也改为按钮样式 -->
        <tr>
            <th colspan="2">
                <!-- 使用按钮代替超链接，点击后跳转到 MaterialScienceServlet -->
                <button onclick="redirectToMaterialScience()">材料科学与技术学院</button>
            </th>
        </tr>
        <tr>
            <td>21</td>
            <td><a href="https://clxy.bjfu.edu.cn/xkjs/xkfx/259837.html" target="_blank">木材科学与技术</a></td>
        </tr>
        <tr>
            <td>28</td>
            <td><a href="https://clxy.bjfu.edu.cn/xkjs/xkfx/257389.html" target="_blank">林产化学加工工程</a></td>
        </tr>
        <tr>
            <td>31</td>
            <td><a href="https://baike.baidu.com/item/%E7%94%9F%E7%89%A9%E8%B4%A8%E8%83%BD%E6%BA%90%E4%B8%8E%E6%9D%90%E6%96%99/60171734?fr=ge_ala" target="_blank">生物质能源与材料</a></td>
        </tr>
        <tr>
            <td>33</td>
            <td><a href="https://hgx.bzu.edu.cn/2021/0602/c18948a218594/page.htm" target="_blank">材料与化工</a></td>
        </tr>
    </table>

    <div class="page-number">页码：1</div>

    <!-- 右上角超链接 -->
    <div class="link-container">
    	<%UserAccount user = (UserAccount)request.getSession().getAttribute("user");  %>
    	<label><%= user.getUsername()%></label>
    	<label><%= user.getId_number()%></label>
        <a href="login.jsp">返回登录页面</a>
        <a href="Test-exist.jsp">上传个人信息</a>
        <a href="StatusCheckServlet">查看录取结果</a>
    </div>
</body>
</html>
