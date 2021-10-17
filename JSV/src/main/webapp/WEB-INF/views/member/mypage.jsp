<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

$(document).ready(function(){

	$("#set_pwd").one('click',function(){
		$(this).parent().next().append("<input type='password' id='u_password' name='u_password' placeholder='비밀번호를 입력해주세요'>")
		$(this).parent().parent().after("<tr>"+
										 	"<td>비밀번호 확인</td>"+
										 	"<td>"+
										 		"<input type='password' id='u_con_password' name='u_con_password' placeholder='비밀번호를 재입력해주세요'>"+
										 	"</td>"+
										 "</tr>"
										 );
	});
	
	$("#setMyPageInfo").click(function(){
		
		if($("#u_nic").val()==''){
			alert("닉네임을 입력해주세요");
			$("#u_nic").focus();
			return;
		}
		
		if($("#u_password").val() ==''){
			alert("비밀번호를 입력해주세요");
			$("#u_password").focus();
			return;
		}
		
		if($("#u_con_password").val() ==''){
			alert("비밀번호 확인을 입력해주세요");
			$("#u_con_password").focus();
			return;
		}
		
		if($("#u_password").val()!=$("#u_con_password").val()){
			alert("비밀번호와 비밀번호 확인이 일치하지 않습니다");
			$("#u_password").focus();
			return;
		}
		
		if($("#u_phone").val()==''){
			alert("휴대폰 번호를 입력해주세요");
			$("#u_phone").focus();
			return;
		}
		
		if($("#u_phone").val().includes("-")){
			$("#u_phone").val($("#u_phone").val().replace(/-/g,""));
		}

		if($("#u_email").val()==''){
			alert("이메일을 입력해주세요");
			$("#u_email").focus();
			return;
		}
		
		var formData = $("#setMemberInfoFrm").serialize() ;
		
		$.ajax({
			type:"post",
			url :"setMypageInfo.mo",
			data:formData,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success:function(data){
				if(data==1){
					alert("수정이 완료되었습니다");
				}else{
					alert("수정이 실패되었습니다. 다시 수정해주세요");
				}
			},
			error:function(jqXHR, textStatus, errorThrown){
				console.log("수정실패 \n" + textStatus + " : " + errorThrown);
			}
		});//$.ajax
		
	});//setMyPageInfo
	
	$("#withdrawal").click(function(){
		if(confirm("탈퇴하시겠습니까?")){
			$.ajax({
				type:"post",
				url :"withdrawal.mo",
				success:function(data){
					if(data==1){
						alert("탈퇴가 완료되었습니다.\n탈퇴 후 재가입시 기존 ID는 사용할 수 없습니다.");
						location.href="../"
					}else{
						alert("탈퇴에 실패하였습니다");
					}
				},
				error:function(jqXHR, textStatus, errorThrown){
					console.log("탈퇴실패 \n" + textStatus + " : " + errorThrown);
				}
			});//$.ajax
		}
	});//withdrawal
	
});
</script>
</head>
<style>
.mypageForm{
	width:980px;
    margin-top: 40px;
	margin-bottom:100px;
}
.mypageForm table{
	margin-top: 30px;
	border-collapse: collapse;
	width:60%;
}

.mypageForm_title{
	font-weight: 500;
    font-size: 28px;
	border-bottom: 2px solid #000;
	padding-bottom: 5px;
    margin-bottom: 10px;
}
.mypage_wrap tbody{
	border: 1px solid #373e46;
}
.mypage_wrap td{
	padding-top:10px;
	padding-bottom:10px;
}

.mypage_wrap tr td{
	font-size:18px;
}

.mypage_wrap input[type="text"], .mypage_wrap input[type="password"]{
	border:1px solid #ccc;
	width: 80%;
    height: 50px;
    font-size:17px
}

.btn_set, .btn_cancel{
    width: 130px;
    height: 40px;
    border: none;
    margin: 10px 4px;
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
}

.btn_set{
	background: #373e46;
	color:#fff
}

.mypage_wrap tr:first-child td{
	text-align:right;
}

.btn_withdrawal{
	width: 20%;
    height: 30px;
    border: none;
    margin-right: 40px;
    cursor: pointer;
}
#set_pwd{
	width: 40px;
    height: 20px;
    border: none;
    cursor: pointer;
}
</style>
<body>
<form class="mypageForm" id="setMemberInfoFrm">
	<div class="mypageForm_title">마이페이지</div>
	<table class="mypage_wrap">
		<tr>
			<td colspan="2">
				<input type="button" class="btn_withdrawal" id="withdrawal" value="탈퇴하기">
			</td>
		</tr>
		<tr>
			<td>이름</td>
			<td>${memberDTO.u_name}</td>
		</tr>
		<tr>
			<td>닉네임</td>
			<td><input type="text" name="u_nic" id="u_nic" value="${memberDTO.u_nic}"></td>
		</tr>
		<tr>
			<td>아이디</td>
			<td>${memberDTO.u_id}</td>
		</tr>
		<tr>
			<td>
				비밀번호
				<input type="button" name="set_pwd" id="set_pwd" value="변경">
			</td>
			<td></td>
		</tr>
		<tr>
			<td>휴대전화</td>
			<td><input type="text" name="u_phone" id="u_phone" value="${memberDTO.u_phone}" placeholder="'-'를 제외한 번호만 입력해주세요"></td>
		</tr>
		<tr>
			<td>이메일</td>
			<td><input type="text" name="u_email" id="u_email" value="${memberDTO.u_email}" placeholder="ex) aaa@aaa.com"></td>
		</tr>
		<tr>
			<td>가입일</td>
			<td>
				<fmt:formatDate value="${memberDTO.u_join_date}" pattern="yyyy년MM월dd일"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" id="setMyPageInfo" class="btn_set" name="btn_set" value="수정">
				<input type="button" name="btn_cancel" class="btn_cancel" onclick="history.back()" value="취소">
			</td>
		</tr>
	</table>
</form>
</body>
</html>