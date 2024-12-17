<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="DAO.UserAccount" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.Applicant" %>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>导师控制台 - 研究生招生系统</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function toggleSelection(applicantId, button) {
            var action = button.dataset.action;  // 获取 data-action 的值，判断操作类型

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "UpdateAssignmentServlet", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    if (xhr.responseText === "success") {
                        // 根据操作类型修改按钮文本
                        if (action === "select") {
                            button.innerText = "取消";  // 选择后，按钮变为“取消”
                            button.dataset.action = "remove";  // 修改 data-action 为“remove”
                        } else {
                            button.innerText = "选择";  // 取消后，按钮变为“选择”
                            button.dataset.action = "select";  // 修改 data-action 为“select”
                        }

                        // 获取剩余名额并更新页面
                        var remainingQuota = xhr.getResponseHeader("X-Remaining-Quota");
                        document.getElementById("remainingQuota").innerText = remainingQuota;

                        // 如果剩余名额为0，禁用所有“选择”按钮
                        if (remainingQuota == 0) {
                            disableSelectButtons();
                        } else {
                            enableSelectButtons();  // 如果剩余名额 > 0, 启用所有 "选择" 按钮
                        }
                    } else {
                        alert("操作失败，请稍后再试。");
                    }
                }
            };

            xhr.send("action=" + action + "&applicantId=" + applicantId);
        }

        // 禁用所有“选择”按钮
        function disableSelectButtons() {
            var buttons = document.querySelectorAll('button[data-action="select"]');
            buttons.forEach(function(button) {
                button.disabled = true;
                button.innerText = "名额已满";  // 将按钮文本更改为“名额已满”
            });
        }

        // 启用所有“选择”按钮
        function enableSelectButtons() {
            var buttons = document.querySelectorAll('button[data-action="select"]');
            buttons.forEach(function(button) {
                button.disabled = false;
                button.innerText = "选择";  // 恢复按钮文本为“选择”
            });
        }
    </script>
</head>
<body>

<!-- 容器，居中显示 -->
<div class="container mt-5">
    <div class="card">
        <div class="card-header bg-primary text-white">
            <h2 class="mb-0">欢迎，<%= ((UserAccount) session.getAttribute("user")).getUsername() %> 导师</h2>
        </div>
        <div class="card-body">
            <h3>剩余招生名额: <span id="remainingQuota"><%= request.getAttribute("remainingQuota") %></span></h3>
            <h4 class="mt-4">选择您的学生</h4>

            <form action="SelectStudentServlet" method="post">
                <table class="table table-striped table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <th>姓名</th>
                            <th>志愿优先级</th>
                            <th>初试成绩</th>
                            <th>复试成绩</th>
                            <th>学科</th>
                            <th>邮箱</th>
                            <th>电话</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            List<Applicant> studentsWithPriority = (List<Applicant>) request.getAttribute("studentsWithPriority");
                            if (studentsWithPriority != null && !studentsWithPriority.isEmpty()) {
                                for (Applicant student : studentsWithPriority) {
                                    boolean isSelected = student.isAssigned();  // Check if student is already assigned
                        %>
                        <tr>
                            <td><%= student.getName() %></td>
                            <td><%= student.getPriority() %></td>
                            <td><%= student.getInitialExamScore() %></td>
                            <td><%= student.getReExamScore() %></td>
                            <td><%= student.getUndergraduateMajor() %></td>
                            <td><%= student.getEmail() %></td>
                            <td><%= student.getPhone() %></td>
                            <td>
                                <button type="button" class="btn btn-primary" onclick="toggleSelection(<%= student.getApplicantId() %>, this)"
                                        data-action="<%= isSelected ? "remove" : "select" %>">
                                    <%= isSelected ? "取消" : "选择" %>
                                </button>
                                <!-- 添加查看简历按钮，点击后跳转到viewResume.jsp，并传递学生的applicantId -->
                                <a href="viewResume.jsp?applicantId=<%= student.getApplicantId() %>" class="btn btn-info">查看简历</a>
                            </td>
                        </tr>
                        <% 
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="9">没有选择您的学生。</td>
                        </tr>
                        <% 
                            }
                        %>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>

</body>
</html>
