<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="DAO.UserAccount" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.Applicant" %>
<%@ page import="Service.AdvisorService" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>自由选择 - 导师控制台</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        // 记录当前导师是否已经选择了一个学生
        var isStudentSelected = false;

        function toggleSelection(applicantId, button) {
            var action = button.dataset.action;  // 获取 data-action 的值，判断操作类型
            var row = button.closest('tr');  // 获取当前行
            var xhr = new XMLHttpRequest();

            if (isStudentSelected && action === "select") {
                alert("每个导师在现阶段每轮只能选择一名学生！");
                return;  // 如果已经选择了学生，阻止选择其他学生
            }

            xhr.open("POST", "UpdateAssignmentServlet", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    if (xhr.responseText === "success") {
                        // 根据操作类型修改按钮文本
                        if (action === "select") {
                            button.innerText = "取消";  // 选择后，按钮变为“取消”
                            button.dataset.action = "remove";  // 修改 data-action 为“remove”
                            isStudentSelected = true;  // 记录导师已经选择了一个学生
                            disableOtherSelectButtons(row);  // 禁用其他选择按钮
                        } else {
                            button.innerText = "选择";  // 取消后，按钮变为“选择”
                            button.dataset.action = "select";  // 修改 data-action 为“select”
                            isStudentSelected = false;  // 记录导师可以选择学生
                            enableOtherSelectButtons();  // 启用其他选择按钮
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

        // 禁用其他行的选择按钮
        function disableOtherSelectButtons(row) {
            var allRows = document.querySelectorAll('tr');
            allRows.forEach(function (r) {
                if (r !== row) {
                    var button = r.querySelector('button[data-action="select"]');
                    if (button) {
                        button.disabled = true;
                    }
                }
            });
        }

        // 启用其他行的选择按钮
        function enableOtherSelectButtons() {
            var allRows = document.querySelectorAll('tr');
            allRows.forEach(function (r) {
                var button = r.querySelector('button[data-action="select"]');
                if (button) {
                    button.disabled = false;
                }
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
            <h4 class="mt-4">选择您的学生（自由选择阶段）</h4>

            <form action="SelectStudentServlet" method="post">
                <table class="table table-striped table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <th>姓名</th>
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
                            List<Applicant> availableStudents = (List<Applicant>) request.getAttribute("availableStudents");
                            if (availableStudents != null && !availableStudents.isEmpty()) {
                                for (Applicant student : availableStudents) {
                                    boolean isSelected = student.isAssigned();  // 判断学生是否已被选择
                        %>
                        <tr>
                            <td><%= student.getName() %></td>
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
                            <td colspan="7">没有符合自由选择条件的学生。</td>
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
