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
</script>
<title></title>
</head>
<body>
<h1>수정폼</h1>
	<form name ="updateEvent" id="updateEvent" method="POST"  enctype="multipart/form-data"
	 				action="${contextPath}/event/update.mo">
	 	<div class="eventUp">
	 		<table border="1">
	 			<tr>
	 				<td>스페셜이벤트</td>
	 				<input type="hidden" name="eno" value="${Eupdata.eno}" id="eno"/>
	 				<td>이벤트명: <input type="text" id="ename" value="${Eupdata.ename}" name="ename"/></td>
	 				<td>기간: <input type="text" id="estart" value="${Eupdata.estart}" name="estart"/>
	 				~ <input type="text" id="eEnd"  value="${Eupdata.eEnd}" name="eEnd"/>
	 				</td>
	 			</tr>
	 		</table>
	 	</div>
	 	<div class="eimgwrap">
		 	<table border="1">
		 		<tr>
		 			<td>이벤트 설명 이미지 등록</td>
		 			<td rowspan="2">
		 			<img src="${contextPath}/download.mo?eno=${Eupdata.eno}&e_img=${Eupdata.e_img}">
		 			<input type="file" value="${Eupdata.e_img}" name="file" id="file"/>
		 			</td>
		 		</tr>
		 	</table>	
	 	</div>
	 	<div>
	 		<input type="submit" id="subtn" class="subtn"/>
	 	</div>
	</form>
</body>
</html> 