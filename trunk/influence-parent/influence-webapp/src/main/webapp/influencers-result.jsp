<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<%
		String influencersFilePath = (String)request.getAttribute("influencersFilePath");
		%>
		<p>Wrote json of full influencers profiles to file: <%=influencersFilePath%></p>
		<%
		out.println("<a href=\"FileViewer?file="+influencersFilePath+"\">"+influencersFilePath+"</a>");
		%>
	</body>
</html>