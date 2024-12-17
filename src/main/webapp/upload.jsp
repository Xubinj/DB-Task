<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>文件上传</title>
    <style>
        body {
            background-color: #f1f8f5; /* 浅绿色背景 */
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 50px;
        }
        h2 {
            color: #2e7d32; /* 深绿色标题颜色 */
        }
        form {
            background-color: #e8f5e9; /* 灰绿色表单背景 */
            border: 1px solid #4caf50; /* 边框 */
            padding: 20px;
            border-radius: 8px; /* 圆角 */
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 阴影 */
            display: inline-block;
        }
        input[type="file"] {
            padding: 10px;
            border: 1px solid #4caf50; /* 文件输入框边框 */
            border-radius: 5px; /* 圆角 */
            margin: 10px 0;
            width: 100%;
            box-sizing: border-box; /* 包含内边距和边框 */
        }
        input[type="submit"] {
            background-color: #4caf50; /* 按钮背景色 */
            color: white; /* 按钮文字颜色 */
            border: none; /* 取消边框 */
            padding: 10px 20px;
            border-radius: 5px; /* 圆角 */
            cursor: pointer; /* 鼠标指针样式 */
            font-size: 16px; /* 字体大小 */
        }
        input[type="submit"]:hover {
            background-color: #45a049; /* 按钮悬停效果 */
        }
    </style>
</head>
<body>
    <center><h2>上传文件(志愿填写完成后无法修改 请谨慎填写)</h2></center>
    <center>
        <form action="UploadServlet" method="post" enctype="multipart/form-data">
            <input type="file" name="file" required />
            <input type="submit" value="上传" />
        </form>
    </center>
</body>
</html>