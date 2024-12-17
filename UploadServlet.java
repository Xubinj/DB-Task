package Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import DAO.Applicant;
import DAO.ApplicantDAO;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=DB-Task;encrypt=true;trustServerCertificate=true;?useUnicode=true&characterEncoding=UTF-8"; // Your DB URL
    private static final String DB_USER = "sa"; // Your DB user
    private static final String DB_PASSWORD = "123456"; // Your DB password
    
    Applicant applicant;
    ApplicantDAO applicantDAO;
    
    
    public UploadServlet() {
		super();
		this.applicant = new Applicant();
		this.applicantDAO = new ApplicantDAO();
	}

//	2.加载驱动/注册驱动:只需要执行一次
	static{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");

	    Part filePart = request.getPart("file");
	    String fileName = filePart.getSubmittedFileName();
	    if (fileName != null && fileName.endsWith(".docx")) {
	        try (InputStream inputStream = filePart.getInputStream();
	             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
	            ZipEntry entry;
	            StringBuilder fileContent = new StringBuilder();

	            while ((entry = zipInputStream.getNextEntry()) != null) {
	                if (entry.getName().equals("word/document.xml")) {
	                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	                    DocumentBuilder builder = factory.newDocumentBuilder();
	                    Document doc = builder.parse(zipInputStream);
	                    NodeList nodeList = doc.getElementsByTagName("w:t");

	                    for (int i = 0; i < nodeList.getLength(); i++) {
	                        Element element = (Element) nodeList.item(i);
	                        fileContent.append(element.getTextContent()).append(" ");
	                    }
	                    break; // 找到内容后退出循环
	                }
	            }

	            // 生成响应
	            response.getWriter().write("<!DOCTYPE html>");
	            response.getWriter().write("<html><head>");
	            response.getWriter().write("<meta charset='UTF-8'>");
	            response.getWriter().write("<title>请确认个人信息</title>");
	            response.getWriter().write("<style>");
	            response.getWriter().write("body { background-color: #f1f8f5; font-family: Arial, sans-serif; text-align: center; }");
	            response.getWriter().write("h1 { color: #2e7d32; }");
	            response.getWriter().write("h7 { color: #333; }");
	            response.getWriter().write(".info { background-color: #e8f5e9; border: 1px solid #4caf50; padding: 20px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); display: inline-block; margin: 20px; }");
	            response.getWriter().write(".my-button { background-color: #4caf50; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; transition: background-color 0.3s; }");
	            response.getWriter().write(".my-button:hover { background-color: #45a049; }");
	            response.getWriter().write("</style></head><body>");
	            
	            // 标题
	            response.getWriter().write("<h1>请确认个人信息</h1>");
	            
	            //全部文本
	            //response.getWriter().write("<div class='info'><h7>");
	            //response.getWriter().write(fileContent.toString());
	            //response.getWriter().write("</h7></div>");

	            // 准考证号
	            String extractedIDCard = extractNameBetween(fileContent.toString(), "考生准考证号: ", " 姓名");
	            response.getWriter().write("<div class='info'><h7>考生准考证号: ");
	            response.getWriter().write(extractedIDCard.isEmpty() ? "未找到相关信息" : extractedIDCard);
	            response.getWriter().write("</h7></div>");

	            // 姓名
	            String extractedName = extractNameBetween(fileContent.toString(), " 姓名", " 性别");
	            response.getWriter().write("<div class='info'><h7>姓名: ");
	            response.getWriter().write(extractedName.isEmpty() ? "未找到相关信息" : extractedName);
	            response.getWriter().write("</h7></div>");

	            // 本科毕业学校
	            String extractedGuatuation = extractFirstSpaceBetween(fileContent.toString(), "本科毕业学校及时间", "毕业专业");
	            response.getWriter().write("<div class='info'><h7>本科毕业学校: ");
	            response.getWriter().write(extractedGuatuation.isEmpty() ? "未找到相关信息" : extractedGuatuation);
	            response.getWriter().write("</h7></div>");

	            // 毕业专业
	            String extractedMajor = extractNameBetween(fileContent.toString(), "毕业专业", "考生联系方式");
	            response.getWriter().write("<div class='info'><h7>毕业专业: ");
	            response.getWriter().write(extractedMajor.isEmpty() ? "未找到相关信息" : extractedMajor);
	            response.getWriter().write("</h7></div>");
	          	            
	            // 拟报研究方向 
	            String extractedTarget = extractNameBetween(fileContent.toString(), "拟报研究方向 ", " 以下信息仅报考电子");
	            response.getWriter().write("<div class='info'><h7>拟报研究方向: ");
	            response.getWriter().write(extractedTarget.isEmpty() ? "未找到相关信息" : extractedTarget);
	            response.getWriter().write("</h7></div>");
	            
	            // 导师1
	            String extractedAdvisor1 = extractNameBetween(fileContent.toString(), "报考导师优先意向 1、", "2、");
	            response.getWriter().write("<div class='info'><h7>报考导师意向1: ");
	            response.getWriter().write(extractedAdvisor1.isEmpty() ? "未找到相关信息" : extractedAdvisor1);
	            response.getWriter().write("</h7></div>");
	            
	            // 导师2
	            String extractedAdvisor2 = extractNameBetween(fileContent.toString(), "2、", "3、");
	            response.getWriter().write("<div class='info'><h7>报考导师意向2: ");
	            response.getWriter().write(extractedAdvisor2.isEmpty() ? "未找到相关信息" : extractedAdvisor2);
	            response.getWriter().write("</h7></div>");
	            
	            // 导师3
	            String extractedAdvisor3 = extractNameBetween(fileContent.toString(), "3、", "以下信息仅报考计算机科学与技术考生填写");
	            response.getWriter().write("<div class='info'><h7>报考导师意向3: ");
	            response.getWriter().write(extractedAdvisor3.isEmpty() ? "未找到相关信息" : extractedAdvisor3);
	            response.getWriter().write("</h7></div>");

	            // 手机号
	            String extractedPhone = extractNameBetween(fileContent.toString(), "手机： ", "紧急联系人手机");
	            response.getWriter().write("<div class='info'><h7>手机号: ");
	            response.getWriter().write(extractedPhone.isEmpty() ? "未找到相关信息" : extractedPhone);
	            response.getWriter().write("</h7></div>");

	            // 数据库插入逻辑
	            String sql1 = "INSERT INTO Applicant ( name, id_number, birth_date, undergraduate_school, undergraduate_major, initial_exam_score, re_exam_score, phone, email) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	                 PreparedStatement preparedStatement = connection.prepareStatement(sql1)) {

	                preparedStatement.setString(1, extractedName);
	                preparedStatement.setString(2, extractedIDCard);
	                preparedStatement.setString(3, ""); // 如果未知则留空
	                preparedStatement.setString(4, extractedGuatuation);
	                preparedStatement.setString(5, extractedMajor);
	                preparedStatement.setString(6, "");
	                preparedStatement.setString(7, "");
	                preparedStatement.setString(8, extractedPhone);
	                preparedStatement.setString(9, ""); // Email 如果没有可留空

	                int rowsAffected = preparedStatement.executeUpdate();
	                if (rowsAffected > 0) {
	                	// 插入成功
	                    response.getWriter().write("<h4><button class='my-button' onclick=\"window.location.href='AddInformation.jsp?extractedIDCard=" + extractedIDCard + "'\">确定</button></h4>");
	                } else {
	                    response.getWriter().write("<div><h2>数据插入失败！</h2></div>");
	                }
	            } catch (Exception e) {
	            	// 已经注册过
	                if (e.getMessage().contains("违反了 UNIQUE KEY 约束")) {
	                    response.sendRedirect("Already-registered.jsp?status=success");
	                } else {
	                    response.getWriter().write("<div><h2>数据库错误: " + e.getMessage() + "</h2></div>");
	                }
	            }
	            response.getWriter().write("</body></html>");
	            
	            
	            
	            
	            
	            
	            
	            String sql2 = "SELECT advisor_id FROM Advisor WHERE name = ?";
	            String sql3 = "SELECT subject_id FROM Subject WHERE name = ?";
	            String sql4 = "INSERT INTO AdvisorPreference ( applicant_id, advisor_id, subject_id, priority) VALUES ( ?, ?, ?, ?)";
	            int value1=0;
	            int value2=0;
	            int value3=0;
	            int major_id=0;
	            int applicant_id = applicantDAO.getApplicantIdByUsername(extractedName);
	            
	            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            		PreparedStatement preparedStatement = connection.prepareStatement(sql3)){
	            	
	            	preparedStatement.setString(1, extractedTarget);
	            	ResultSet resultSet = preparedStatement.executeQuery();
	            	if (resultSet.next()) {
	            		major_id = resultSet.getInt("subject_id");
	                    //System.out.println("查询结果1: " + value1);
	                }
	            }catch (Exception e) {
	            	 e.printStackTrace();
	            }
	            
	            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            		PreparedStatement preparedStatement = connection.prepareStatement(sql2)){
	            	
	            	preparedStatement.setString(1, extractedAdvisor1);
	            	ResultSet resultSet1 = preparedStatement.executeQuery();
	            	if (resultSet1.next()) {
	                    value1 = resultSet1.getInt("advisor_id");
	                    //System.out.println("查询结果1: " + value1);
	                }
	            	try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql4)){
	            		preparedStatement1.setInt(1, applicant_id);
	            		//preparedStatement1.setInt(1, Integer.parseInt(extractedIDCard));
						preparedStatement1.setInt(2, value1);
	            		preparedStatement1.setInt(3, major_id); // 如果未知则留空
	            		preparedStatement1.setInt(4, 1);
	            		preparedStatement1.executeUpdate();
	            	}catch (Exception e) {
		            	 e.printStackTrace();
		            }
	            }catch (Exception e) {
	            	 e.printStackTrace();
	            }
	              
	            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            		PreparedStatement preparedStatement = connection.prepareStatement(sql2)){
	            	
	            	preparedStatement.setString(1, extractedAdvisor2);
	            	ResultSet resultSet2 = preparedStatement.executeQuery();
	            	if (resultSet2.next()) {
	                    value2 = resultSet2.getInt("advisor_id");
	                    //System.out.println("查询结果2: " + value2);
	                }
	            	try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql4)){
	            		preparedStatement1.setInt(1, applicant_id);
	            		//preparedStatement1.setInt(1, Integer.parseInt(extractedIDCard));
						preparedStatement1.setInt(2, value2);
	            		preparedStatement1.setInt(3, major_id); // // 如果未知则留空
	            		preparedStatement1.setInt(4, 2);
	            		preparedStatement1.executeUpdate();
	            	}catch (Exception e) {
		            	 e.printStackTrace();
		            }
	            }catch (Exception e) {
	            	 e.printStackTrace();
	            }
	            
	            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            		PreparedStatement preparedStatement = connection.prepareStatement(sql2)){
	            	
	            	preparedStatement.setString(1, extractedAdvisor3);
	            	ResultSet resultSet3 = preparedStatement.executeQuery();
	            	if (resultSet3.next()) {
	                    value3 = resultSet3.getInt("advisor_id");
	                    //System.out.println("查询结果3:  " + value3);
	                }
	            	try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql4)){
	            		preparedStatement1.setInt(1, applicant_id);
	            		//preparedStatement1.setInt(1, Integer.parseInt(extractedIDCard));
						preparedStatement1.setInt(2, value3);
	            		preparedStatement1.setInt(3, major_id); // // 如果未知则留空
	            		preparedStatement1.setInt(4, 3);
	            		preparedStatement1.executeUpdate();
	            	}catch (Exception e) {
		            	 e.printStackTrace();
		            }
	            }catch (Exception e) {
	            	 e.printStackTrace();
	            }
	            
	            
	        } catch (Exception e) {
	            response.getWriter().write("<h2>读取文件时出错: " + e.getMessage() + "</h2>");
	        }
	    } else {
	        response.getWriter().write("<h2>请上传一个有效的 .docx 文件。</h2>");
	    }
	}
    
    
    
    
    
    
    
    
    
    
    

    private Float parseFloat(String value) {
        try {
            return value != null && !value.trim().isEmpty() ? Float.parseFloat(value) : null;
        } catch (NumberFormatException e) {
            return 0.0f; // 鎴栬�呭彲浠ヨ�冭檻杩斿洖 null
        }
    }

    private String extractNameBetween(String content, String startKeyword, String endKeyword) {
        int startIndex = content.indexOf(startKeyword);
        int endIndex = content.indexOf(endKeyword);

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            startIndex += startKeyword.length();
            return content.substring(startIndex, endIndex).trim();
        }
        return "";
    }

    private String extractFirstSpaceBetween(String content, String startKeyword, String endKeyword) {
        int startIndex = content.indexOf(startKeyword);
        int endIndex = content.indexOf(endKeyword);

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            startIndex += startKeyword.length();
            String extractedContent = content.substring(startIndex, endIndex).trim();
            int firstSpaceIndex = extractedContent.indexOf(" ");
            if (firstSpaceIndex != -1) {
                return extractedContent.substring(0, firstSpaceIndex);
            } else {
                return extractedContent;
            }
        }
        return "";
    }

    private String extractSecondSpaceBetween(String content, String startKeyword, String endKeyword) {
        int startIndex = content.indexOf(startKeyword);
        int endIndex = content.indexOf(endKeyword);

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            startIndex += startKeyword.length();
            String extractedContent = content.substring(startIndex, endIndex).trim();
            int firstSpaceIndex = extractedContent.indexOf(" ");
            if (firstSpaceIndex != -1) {
                String afterFirstSpace = extractedContent.substring(firstSpaceIndex + 1).trim();
                int secondSpaceIndex = afterFirstSpace.indexOf(" ");
                return secondSpaceIndex != -1 ? afterFirstSpace.substring(0, secondSpaceIndex) : afterFirstSpace;
            }
        }
        return "";
    }

    private String extractThirdSpaceBetween(String content, String startKeyword, String endKeyword) {
        // Similar logic for extracting third and fourth spaces can go here
        return "";
    }

    private String extractFourthSpaceBetween(String content, String startKeyword, String endKeyword) {
        // Similar logic for extracting third and fourth spaces can go here
        return "";
    }
}