<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>



<%
  if(session.getAttribute("username")==null){
	  response.sendRedirect("index.html");
  }

 
 %>
 
 <h1>Welcome to Learner's Academy</h1>
 
 <br><a href='dashboard.jsp'>Go to Dashboard</a>
 
 <br> <a href='logout'>Logout</a>

</body>
</html>