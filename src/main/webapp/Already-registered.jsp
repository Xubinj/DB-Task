<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>您已经注册过了</title>
    <style>
        /* 页面背景和字体 */
        body {
            background-color: #f1f8f5; /* 浅绿色背景 */
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 50px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        /* 文字居中和容器 */
        .message-container {
            background-color: #e8f5e9; /* 灰绿色消息背景 */
            border: 1px solid #4caf50; /* 边框 */
            padding: 40px 50px;
            border-radius: 10px; /* 圆角 */
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1); /* 阴影 */
            text-align: center;
            width: 100%;
            max-width: 450px; /* 最大宽度 */
        }

        /* 标题样式 */
        h2 {
            font-size: 28px;
            color: #2e7d32; /* 深绿色标题颜色 */
            margin-bottom: 20px;
            font-weight: 600;
            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2); /* 标题阴影 */
        }

        /* 按钮样式 */
        button {
            background-color: #4caf50; /* 按钮背景色 */
            color: white; /* 按钮字体颜色 */
            border: none;
            padding: 12px 25px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        /* 按钮悬停效果 */
        button:hover {
            background-color: #45a049; /* 悬停时的颜色 */
            transform: translateY(-2px); /* 向上移动的效果 */
        }

        /* 按钮按下时的效果 */
        button:active {
            transform: translateY(2px); /* 按钮按下时的效果 */
        }

        /* 小屏幕下的样式 */
        @media (max-width: 600px) {
            .message-container {
                padding: 30px 20px;
            }

            h2 {
                font-size: 24px;
            }

            button {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="message-container">
        <h2>您已经注册过了</h2>
        <p>您已完成注册，点击下面的按钮查看您的录取情况。</p>
        <form action="StatusCheckServlet" method="post">
            <button type="submit">查看录取情况</button>
        </form>
    </div>
</body>
</html>
