package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.SubjectCatalogInfo;
import Service.SubjectService;

@WebServlet("/SubjectServlet")
public class SubjectServlet extends HttpServlet {

    private SubjectService subjectService = new SubjectService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取所有学科和招生信息
        List<SubjectCatalogInfo> subjectCatalogInfos = subjectService.getAllSubjectCatalogInfo();
        
        // 将数据传递给JSP页面
        request.setAttribute("subjectCatalogInfos", subjectCatalogInfos);

        // 转发请求到 subjectCatalog.jsp 页面
        RequestDispatcher dispatcher = request.getRequestDispatcher("/subjectCatalog.jsp");
        dispatcher.forward(request, response);
    }

    // 你也可以在 doPost 方法中处理表单提交 (如果需要处理 POST 请求)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 你可以在这里处理 POST 请求，如果有必要
        // 例如：处理一些用户输入，然后再跳转到 JSP 页面
        doGet(request, response);
    }
}
