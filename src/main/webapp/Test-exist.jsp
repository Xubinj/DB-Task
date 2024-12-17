<%@ page import="java.sql.*" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%
	String id_number = (String) request.getSession().getAttribute("idnumber");
	out.print((String)id_number);
    if (id_number == null) {
        // 如果没有id_number，跳转到upload.jsp
        response.sendRedirect("upload.jsp");
    } else {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // 数据库连接信息
            String url = "jdbc:sqlserver://localhost:1433;databaseName=DB-Task;encrypt=true;trustServerCertificate=true;?useUnicode=true&characterEncoding=UTF-8"; // Your DB URL
            String user = "sa"; // 数据库用户名
            String password = "12345678"; // 数据库密码
            
            // 创建数据库连接
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, password);

            // 检查id_number是否存在
            String sql = "SELECT * FROM Applicant WHERE id_number = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, (String)id_number);
            rs = pstmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                // id_number 存在，跳转到 ChangeInfo.jsp
                response.sendRedirect("ChangeInfo.jsp");
            } else {
                // id_number 不存在，跳转到 upload.jsp
                response.sendRedirect("upload.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 发生错误时跳转到 upload.jsp
            response.sendRedirect("upload.jsp");
        } finally {
            // 关闭资源
            if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
        }
    }
%>