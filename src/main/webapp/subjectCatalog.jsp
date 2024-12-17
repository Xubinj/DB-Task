<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <!-- 引入 fn 标签库 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- 引入 fmt 标签库 -->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>学科招生信息</title>
    <style>
        /* 通用样式 */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        h1 {
            text-align: center;
            color: #333;
            font-size: 28px;
            margin-bottom: 20px;
        }

        /* 表格样式 */
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
            font-size: 14px;
        }

        /* 表头样式 */
        th {
            background-color: #007BFF;
            color: white;
            font-weight: bold;
        }

        /* 行间隔 */
        tbody tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tbody tr:nth-child(odd) {
            background-color: #fff;
        }

        /* 鼠标悬停行样式 */
        tbody tr:hover {
            background-color: #f1f1f1;
        }

        /* 合并单元格的样式 */
        td[rowspan] {
            vertical-align: middle;
            text-align: center;
        }

        /* 表格字体颜色 */
        td {
            color: #555;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>信息学院招生目录</h1>
        <table>
            <thead>
                <tr>
                    <th>学科名称</th>
                    <th>导师</th>
                    <th>招生数</th>
                    <th>初试科目</th>
                    <th>复试科目</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="info" items="${subjectCatalogInfos}">
                    <c:forEach var="catalog" items="${info.catalogs}">
                        <tr>
                            <!-- 显示学科名称，仅第一次显示 -->
                            <c:if test="${catalog == info.catalogs[0]}">
                                <td rowspan="${fn:length(info.catalogs)}">${info.subject.name}</td>
                            </c:if>
                            
                            <!-- 显示导师 -->
                            <td>${catalog.advisor.name}</td>
                            
                            <!-- 显示招生数、初试科目、复试科目，仅第一次显示 -->
                            <c:if test="${catalog == info.catalogs[0]}">
                                <td rowspan="${fn:length(info.catalogs)}">${catalog.totalQuota}</td>
                                <td rowspan="${fn:length(info.catalogs)}">${catalog.initialExamSubject}</td>
                                <td rowspan="${fn:length(info.catalogs)}">${catalog.reExamSubject}</td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
