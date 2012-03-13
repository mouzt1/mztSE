<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
 </style>
  <script type="text/javascript" src="js/script.js"></script>
  	<link rel="stylesheet" href="css/jquery.ui.all.css">
	<script src="js/jquery-1.7.1.js"></script>
	<script src="js/autocomplete/jquery.ui.core.js"></script>
	<script src="js/autocomplete/jquery.ui.widget.js"></script>
	<script src="js/autocomplete/jquery.ui.position.js"></script>
	<script src="js/autocomplete/jquery.ui.autocomplete.js"></script>
	<link rel="stylesheet" href="css/demos.css">
	<style>
	.ui-autocomplete-loading { background: white url('img/ui-anim_basic_16x16.gif') right center no-repeat; }
	#city { width: 25em; }
	</style>
	<script>
	$(function() {
		function log( message ) {
			$( "<div/>" ).text( message ).prependTo( "#log" );
			$( "#log" ).scrollTop( 0 );
		}

		$( "#textArea" ).autocomplete({
			source: function( request, response ) {
				$.ajax({
					type: 'POST',
					url: "autoCompleted",
					dataType: "json",
					data: {
						featureClass: "P",
						style: "full",
						maxRows: 12,
						name_startsWith: request.term
					},
					success: function( data ) {
						response( $.map( data.autoComplete, function( item ) {
							return {
								label: " "+item.content,
								value: item.content
							}
						}));
					}
				});
			},
			minLength: 2,
			select: function( event, ui ) {
				log( ui.item ?
					"Selected: " + ui.item.label :
					"Nothing selected, input was " + this.value);
			},
			open: function() {
				$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
			},
			close: function() {
				$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
			}
		});
	});
	</script>
  </head> 
  
  <body> <br/><br/><br/><br/><br/><br/><br/><br/>
 <p align="center"><img src="img/IBMSearch.png"  style="padding-left: 0px; margin-left: -100px;" /></p> 
 <form action="mySearchByPage"  name="search" method="post"> 
 <table border="0" height="30px" width="450px" align="center"> 
 <tr> 
 <td width ="66%" align="center">
 <div class="ui-widget">
 <input id="textArea" onfocus="borderChange()" onblur="borderChange1()" name="keyword" type="text" maxlength="100" 
 >
 </div>
 </td> 
 <td height="29" align="center"><input type="submit" value="搜  索" 
 id = "search"></td> 
 </tr> 
 </table> 
 </form> 
  </body> 
