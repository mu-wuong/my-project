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
$(document).ready(function(){
    $(".delete").on('click', function() {  
        if(confirm("정말로 삭제하시겠습니까?")) {  // 확인 창이 열림
            location.href = "${contextPath}/event/delete.mo?eno=${eDetail.eno}" ;  
        }else{
        	location.href="${contextPath}/event/eventList.mo";
        }
    });
});
//수정
$(document).ready(function(){
	$(".update").on('click',function(){
		if(confirm("수정하시겠습니까?")){
			location.href="${contextPath}/admin/event/updateForm.mo?eno=${eDetail.eno}";
		}else{
			location.href="${contextPath}/event/eventList.mo";
		}
	});
});
</script>
<title></title>
</head>
<body>
<h1>이벤트상세보기</h1>
<div class="Edtail_wrap">
	<table>
		<tr>
			<td>${eDetail.ename}</td>
			<td>${eDetail.estart} ~ ${eDetail.eEnd}</td>
		</tr>
	</table>
</div>
		<hr/>
		<c:if test="${authUser ne null}">
			<img src="${contextPath}/download.mo?eno=${eDetail.eno}&e_img=${eDetail.e_img}"/>
			<input type="button" value="수정" class="update"/>
			<input type="button" value="삭제" class="delete"/>
			<input type="button" value="목록" onclick=""/>
		</c:if>
</body>
</html> 