<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="jlu.web.Pagination"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript">
	  function GetBrowserType() {
          var ua = navigator.userAgent.toLowerCase();
          if (ua == null) return "ie";
          else if (ua.indexOf('chrome') != -1) return "chrome";
          else if (ua.indexOf('opera') != -1) return "opera";
          else if (ua.indexOf('msie') != -1) return "ie";
          else if (ua.indexOf('safari') != -1) return "safari";
          else if (ua.indexOf('firefox') != -1) return "firefox";
          else if (ua.indexOf('gecko') != -1) return "gecko";
          else return "ie";

	  }
  function encodePagiUrl(a){
		var tempUrl = encodeURI(encodeURI(a.href+"&browerType="+GetBrowserType()));
		a.href = tempUrl;
		return true;
	  	}
  
  </script>
  </head>
	<body>
	<%
		Pagination pagination = (Pagination) request.getAttribute("pagination");
		int size = pagination.getSize();
		int currentPage = pagination.getPage();
		int resultCount = pagination.getResultCount();
		int pageCount = pagination.getPageCount();
		String pageUrl = request.getParameter("pageUrl");
	 	int colsTd = Integer.parseInt(request.getParameter("colsTd"));
	%>
	<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="<%=colsTd %>">
			共有记录：<%=resultCount %>条&nbsp;&nbsp;&nbsp;
			<%
			if (pageCount > 0) {
			%>
			<a href="<%=pageUrl %>&page=1" onclick="encodePagiUrl(this);">首页</a>|
			<%
			} else {
			%>
			首页|
			<%
				}

				if (currentPage > 1) {
			%>
			<a
				href="<%=pageUrl %>&page=<%=currentPage - 1%>" onclick="encodePagiUrl(this);">上一页</a>|
			<%
			} else {
			%>
			上一页|
			<%
				}

				int first = 0;
				int last = 0;
				if (pageCount <= 10) {
					first = 1;
					last = pageCount;
				} else {
					if (currentPage - 5 <= 0) {
						first = 1;
						last = 10;
					} else if (pageCount - currentPage <= 5) {
						last = pageCount;
						first = last - 9;

					} else {
						first = currentPage - 4;
						last = first + 9;
					}
				}

				for (int i = first; i <= last; i++) {
					if (i == currentPage) {
			%><span style="background-color: #5F5F5F; width: 15px"> <a
				href="<%=pageUrl %>&page=<%=i%>" onclick="encodePagiUrl(this);"><%=i%></a>
			</span>|
			<%
			} else {
			%>
			<a href="<%=pageUrl %>&page=<%=i%>" onclick="encodePagiUrl(this);"><%=i%></a>|
			<%
				}
				}

				if (currentPage < pageCount) {
			%>
			<a
				href="<%=pageUrl %>&page=<%=currentPage + 1%>" onclick="encodePagiUrl(this);">下一页</a>|
			<%
			} else {
			%>
			下一页|
			<%
				}

				if (pageCount > 0) {
			%>
			<a
				href="<%=pageUrl %>&page=<%=pageCount%>" onclick="encodePagiUrl(this);">尾页</a>
			<%
			} else {
			%>
			尾页|
			<%
			}
			%>
		</td>
	</tr>
	</table>
	</body>
	</html>