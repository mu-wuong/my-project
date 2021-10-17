<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${context}/js/jquery-3.6.0.min.js"></script>
<script>
$(function(){
	
	$("#joinForm").submit(function(){
		
		if($("#u_name").val()==""){
			alert("이름은 필수입력입니다.");
			$("#u_name").focus(); 
			return false;
		}
		
		if($("#u_id").val()==""){
			alert("id는 필수입력입니다.");
			$("#u_id").focus();
			return false;
		}
		
		if($("#u_password").val()==""){ 
			alert("비밀번호는 필수입력입니다.");
			$("#u_password").focus(); 
			return false;
		}
		
		if($("#u_password2").val()==""){ 
			alert("비밀번호 확인은 필수입력입니다.");
			$("#u_password2").focus(); 
			return false;
		}
		
		if($("#u_password2").val()!=$("#u_password").val()){ 
			alert("비밀번호와 확인용 비밀번호가 일치하지 않습니다");
			$("#u_password2").focus(); 
			return false;
		}
		
		
		if($("#u_nic").val()==""){ 
			alert("닉네임은 필수입력입니다.");
			$("#u_nic").focus(); 
			return false;
		}
		
		if($("#u_phone").val()==""){ 
			alert("휴대폰번호는 필수입력입니다.");
			$("#u_phone").focus(); 
			return false;
		}

		if($("#u_email").val()==""){ 
			alert("이메일은 필수입력입니다.");
			$("#u_email").focus(); 
			return false;
		}
		
		if($("#u_phone").val().includes("-")){
			$("#u_phone").val($("#u_phone").val().replace(/-/g,""));
		}
		
		$("#joinForm").attr("method","post");
		$("#joinForm").attr("action","signup.mo");
		return true;
		
	});
	
}); 
//ID중복체크
function fn_idChk(){
	var u_id = $("#u_id").val();
	if(u_id==''){
		alert('ID를 입력하세요');
		$("#u_id").focus();
		return;
	}else{
		$.ajax({
			url:"idChk.mo",
			type:"post",
			dataType:"json",
			data:{"u_id":$("#u_id").val()},
			success:function(data){
				if(data==1){
					alert("중복된 ID입니다.");
					$("#u_id").val("");
					$("#u_id").focus();
					return;
				}else if(data==0){
					alert("사용가능한 ID입니다.");
					
				}
			}
		});
	}
}
</script>
<style>
.joinForm{
	width:980px;
    margin-top: 40px;
	margin-bottom:100px;
	
}
.joinForm table{
	margin-top: 30px;
	border-collapse: collapse;
	width:60%;
}

.joinForm_title{
	font-weight: 500;
    font-size: 28px;
	border-bottom: 2px solid #000;
	padding-bottom: 5px;
    margin-bottom: 10px;
}

.joinForm_sub_title{
	text-align:center;
}

.join_wrap tbody{
	border: 1px solid #373e46;
}
.join_wrap tr td:first-child, .joinForm_sub_title{
	font-size:18px;
}

.essential::after{
	content:'*';
	color:red;
}

.joinForm_sub_title::before{
	content:' * ';
	color:red;
}

.join_wrap td{
	padding-top:10px;
	padding-bottom:10px;
}

.join_wrap input[type="text"], .join_wrap input[type="password"]{
	border:1px solid #ccc;
	width: 80%;
    height: 50px;
    font-size:17px
}

#u_id{
    width: 245px;
}
.btn_check{
    height: 50px;
    border: none;
    width: 20%;
    cursor: pointer;
}

.btn_join, .btn_cancel{
    width: 130px;
    height: 40px;
    border: none;
    margin: 10px 4px;
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
}

.btn_join{
	background: #373e46;
	color:#fff
}
</style>
</head>
<body>
<form name="joinForm" class="joinForm" id="joinForm">
	<div class="joinForm_title">회원가입</div>
	<table class="join_wrap">
		<tr>
			<td colspan="2">
				<div class="joinForm_sub_title">표시는 필수입력란 입니다.</div>
			</td>
		</tr>
		<tr>
			<td>
				<label for="u_name" class="essential">이름</label>
			</td>
			<td>
				<input type="text" id="u_name" name="u_name" placeholder="이름을 입력해주세요."/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="u_id" class="essential">아이디</label>
			</td>
			<td>
				<input type="text" id="u_id" name="u_id" placeholder="ID를 입력해주세요."/>
				<input type="button" class="btn_check" onclick="fn_idChk()" value="id중복체크">
			</td>
		</tr>
		<tr>
			<td>
				<label for="u_password" class="essential">비밀번호</label>
			</td>
			<td>
				<input type="password" id="u_password" name="u_password" placeholder="비밀번호를 입력해주세요."/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="u_password2" class="essential">비밀번호 확인</label>
			</td>
			<td>
				<input type="password" id="u_password2" name="u_password2" placeholder="비밀번호를 확인하세요"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="u_nic" class="essential">닉네임</label>
			</td>
			<td>
				<input type="text" id="u_nic" name="u_nic" placeholder="닉네임을 입력해주세요."/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="u_phone" class="essential">휴대폰 번호</label>
			</td>
			<td>
				<input type="text" id="u_phone" name="u_phone" placeholder="'-'제외한 번호를 입력주세요"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="u_email" class="essential">이메일</label>
			</td>
			<td>
				<input type="text" id="u_email" name="u_email" maxlength="30" placeholder="ex)gildong@naver.com"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" id="submit" class="btn_join" value="회원가입">
				<input type="button" value="취소" class="btn_cancel" onclick="history.back()">
			</td>
		</tr>
	</table>
</form>
</body>
</html>