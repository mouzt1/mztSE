<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="jlu.search.SearchResultBean"%>
<%@page import="jlu.web.ConstantFactory"%>
<%@page import="jlu.search.Searcher"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'ibm.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
          <style> 
#search{ 
 width:78px; 
 height:28px; 
 font:14px 黑体;
 } 
 #textArea{ 
 width:300px; 
 height:30px; 
 font:14px 黑体;

 } 
 .pSmalle{
 	padding-left: 5px;
	font-family: 微软雅黑;
    font-size: 15px;
    line-height: 35px
	color: #555555;
 }
 .contentDiv{
 	padding-left:20px;
  	margin-left: 5px;
	font-family: 微软雅黑,黑体;
 } 
 </style> 
	</head>
	
	<body link="#0000cc">
	<div class="contentDiv"> 
 <form action="mySearchByPage" name="search" method="post" enctype="application/x-www-form-urlencoded"> 
	 <table border="0" height="30px" width="450px"> 
		 <tr> 
		 	<td><a href="index.jsp"><img src="img/IBMSearch_s.png" style="border:0;"></a></td>
			 <td width ="66%">
			 	<input name="keyword" type="text" maxlength="100" id="textArea">
			 </td> 
			 <td height="29px" align="center">
			 	<input type="submit" value="搜索一下" id = "search">
			 </td> 
		 </tr> 
	 </table> 
 </form> 
 <hr/>
 </div>

 <div class="contentDiv">
	<%
	List<SearchResultBean> docList = new ArrayList<SearchResultBean>();
	docList = (List<SearchResultBean>)request.getAttribute("retList");
	String pageUrl = (String)request.getAttribute("pageUrl");
	for(SearchResultBean s : docList){
		
		%>
		<table cellpadding="0" cellspacing="0">
		<tr>
			<td width="900px;">
			<h3 class="t">
				<a href="contentShow.jsp?url=<%=s.getFullpath() %>" target="_blank"><%=s.getTitle() %></a>
				</h3>
				<p class="pSmalle"> 
			<%=s.getHighlightedAbstract() %>
			</p>
			</td>
		</tr></table><br>
		<%
	}
	%>
		<jsp:include page="share/pagination.jsp">
			<jsp:param name="pageUrl" value="<%=pageUrl %>"/>
			<jsp:param name="colsTd" value="<%=1%>"/>
		</jsp:include>
	</div>

	</body>
</html>
