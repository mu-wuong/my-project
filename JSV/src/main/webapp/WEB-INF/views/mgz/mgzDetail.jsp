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
            location.href = "${contextPath}/mgz/delete.mo?mno=${mgzMap.magazindto.mno}" ;  
        }else{
        	location.href="${contextPath}/mgz/mgzList.mo";
        }
    });
});
//수정
$(document).ready(function(){
	$(".update").on('click',function(){
		if(confirm("수정하시겠습니까?")){
			location.href="${contextPath}/admin/mgz/updateForm.mo?mno=${mgzMap.magazindto.mno}";
		}else{
			location.href="${contextPath}/mgz/mgzList.mo";
		}
	});
});
</script>
<title></title>
</head>
<!-- mgzMap.put("magazindto", magazindto);//매거진정보
		${mgzMap.magazindto}
		List<MfileDTO> mfiledto = magazinedao.getMgzDImg(mno);
		mgzMap.put("mfiledto", mfiledto);	//상세이미지 -->
<body>
	<div class="title">
		<h1>${mgzMap.magazindto.mtitle}</h1>
	</div>
	<div>
		published by:${mgzMap.magazindto.mwriter} 등록일 : ${mgzMap.magazindto.mregDate}
	</div>
<hr/>
	<c:forEach var="mfile" items="${mgzMap.mfiledto}">
	 <img src="${contextPath}/mdownload.mo?mno=${mfile.mno}&mFame=${mfile.mFame}"/>
	</c:forEach>
	
	<div class="minfo">
		<table border="1">
			<tr>
				<td><img src="${contextPath}/mdownload.mo?mno=${mgzMap.magazindto.mno}&mFame=${mgzMap.magazindto.mFame}"
							style="width:185px;height:260px;"/></td>
				<td>
					<table>
						<tr>
							<td>${mgzMap.magazindto.mname}</td>
						</tr>
						<tr>
							<Td>감독 : ${mgzMap.magazindto.mdir} / 배우 : ${mgzMap.magazindto.mactor}</Td>
						</tr>
						<tr>
							<Td>장르 : ${mgzMap.magazindto.mgenre} / 기본 : ${mgzMap.magazindto.minfo}</Td>
						</tr>
						<tr>
							<Td>개봉 : ${mgzMap.magazindto.mopen}</Td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
		<c:if test="${authUser ne null}">
			<input type="button" value="수정" class="update"/>
			<input type="button" value="삭제" class="delete"/>
			<input type="button" value="목록" onclick=""/>
		</c:if>
</body>
</html> 