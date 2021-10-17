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
	var cnt=0; //추가되는 버튼의 id 속성값 변수
	
	//최대 8개까지 업로드 할 수 있음
	$("#addImgbtn").click(function(){
		if(cnt==9){
			alert("업로드 할 수 있는 파일은 최대 9개입니다");
			return;
		}
		if(cnt==0){
			$("#addImg").append('<input type="file" name="main_img" id="main_img"/><br/>');	//썸네일 처리도 같이 할 이미지
		}else{
			$("#addImg").append('<input type="file" name="sub_img'+cnt+'" id="sub_img'+cnt+'"/><br/>');
		}
		cnt++;
	});//addingbtn
	
});
</script>
<title></title>
</head>
<body>
	<form name="newMgzFrm" id="newMgzFrm" method="POST" enctype="multipart/form-data" action="${contextPath}/admin/mgz/newMgz.mo">
		<div class="mgz_wrap">
			<div>
				<input type="text" name="mtitle" id="mtitle"/>
			</div>
			<div>	
				Published by <input type="text" name="mwriter" id="mwriter"/> 등록일 : <input type="date" id="mregDate" name="mregDate"/>
			</div>
			<hr/>
			<div>
				<input type="button" name="addImgbtn" id="addImgbtn" value="파일추가"/>
				<div>
					<div id="addImg">
					</div>
					<div id="removeImg">
					</div>
				</div>
			</div>
			<h2>영화상세</h2>
			<hr/>
			<div class="mimg_wrap">
				<table border="1">
					<tr>
						<td>
							<table border="1">
								<tr>
									<td><input type="text" name="mname" id="mname"/></td>
									<td><input type="text" name="mcontent" id="mcontent"/></td>
								</tr>
								<tr>
									<td>감독 : <input type="text" name="mdir" id="mdir"/> 배우: <input type="text" name="mactor" id="mactor"/></td>
								</tr>
								<tr>
									<td>장르 : <input type="text" name="mgenre" id="mgenre"/>/기본: <input type="text" name="minfo" id="minfo"/></td>
								</tr>
								<tr>	
									<td>개봉: <input type="date" name="mopen" id="mopen"/></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div style="center">	
				 <table>
				 	<tr>
					  <td align="center">
						 <input  type="submit" value="등록" id="suBtn"/>
					  </td>
					</tr>
				 </table>
			</div>	 
		</div>
	</form>
</body>
</html> 