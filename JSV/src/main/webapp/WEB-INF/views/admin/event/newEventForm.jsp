<%@ taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(function(){
	
	
})
</script>
<title></title>
</head>
<body>
	<form name ="newEvent" id="newEvent" method="POST"  enctype="multipart/form-data"
	 				action="${contextPath}/admin/event/newEvent.mo">
	 	<div class="eventwrap">
	 		<table border="1">
	 			<tr>
	 				<td>스페셜이벤트</td>
	 				<td>이벤트명: <input type="text" id="ename" name="ename"/></td>
	 				<td>기간: <input type="text" id="estart" name="estart"/>
	 				~ <input type="text" id="eEnd" name="eEnd"/>
	 				</td>
	 			</tr>
	 		</table>
	 	</div>
	 	<div class="eimgwrap">
		 	<table border="1">
		 		<tr>
		 			<td>이벤트 설명 이미지 등록</td>
		 			<td rowspan="2"><input type="file" name="file" id="file"/></td>
		 		</tr>
		 	</table>	
	 	</div>
	 	<div>
	 		<input type="submit" id="subtn" class="subtn"/>
	 	</div>
	</form>
</body>
</html> 