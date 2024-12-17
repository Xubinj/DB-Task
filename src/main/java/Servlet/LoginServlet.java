package Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserAccount;
import DAO.UserAccountDAO;
import Service.SelectionPhaseService;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String idNumber = request.getParameter("id_number");  // 获取 id_number

        // 创建 DAO 实例
        UserAccountDAO userAccountDAO = new UserAccountDAO();
        SelectionPhaseService phaseService = new SelectionPhaseService();  // 获取 SelectionPhaseService

        try {
            // 验证用户名和密码
        	UserAccount user = userAccountDAO.getUserByUsernameAndPasswordAndIdNumber(username, password,idNumber);

            if (user != null) {
                // 登录成功，根据角色跳转到不同页面
            	request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("username", user.getUsername());
                request.getSession().setAttribute("idnumber", user.getId_number());

                
                // 判断用户角色并跳转
                if ("Advisor".equals(user.getRole())) {
                    response.sendRedirect("selectionPhasePage.jsp");  
                } 
                else if("Applicant".equals(user.getRole())) {
                	response.sendRedirect("tml.jsp");
                }else if ("StudentSecretary".equals(user.getRole())) {
                    // 学生秘书角色，跳转到学生秘书管理页面
                    response.sendRedirect("studentSecretaryPage.jsp");  // 学生秘书管理页面
                }else if("Secretary".equals(user.getRole())) {
                    // 转发到秘书页面
                    request.getRequestDispatcher("SecretaryDashBoardServlet").forward(request, response);
                }
                else if ("Leader".equals(user.getRole())) {
                    // 新增 Leader 的处理逻辑
                    request.getRequestDispatcher("LeaderDashboardServlet").forward(request, response);
                }
                else {
                	// 不存在的角色的处理逻辑
                    response.sendRedirect("no_exist.jsp");
                }
            } else {
                // 如果验证失败，返回登录页面并显示错误消息
                response.sendRedirect("login.jsp?error=invalid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=databaseError");
        }
    }
}
