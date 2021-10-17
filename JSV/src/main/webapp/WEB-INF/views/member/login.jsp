<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <c:set var="contextPath" value="${pageContext.request.contextPath}"/> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
*{
  margin: 0px;
  padding: 0px;
  text-decoration: none;
  font-family:sans-serif;
}

.loginForm {
  color:#fff;
  position:absolute;
  width:500px;
  height:500px;
  padding: 30px, 20px;
  text-align:center;
  top:50%;
  left:50%;
  transform: translate(-50%,-50%);
  background: #34298e;
  border-radius: 15px;
}

.loginForm h2{
  text-align: center;
  margin: 30px;
}

.idForm{
  color:#fff;
  border-bottom: 2px solid #fff;
  margin: 30px;
  padding: 10px 10px;
}

.passForm{
  color:#fff;
  border-bottom: 2px solid #fff;
  margin: 30px;
  padding: 10px 10px;
}

.id, .password {
  width: 100%;
  height:80px;
  border:none;
  outline:none;
  color: #636e72;
  font-size:18px;
  height:25px;
  background: none;
}
.id{
	color:#fff;
}
.password{
	color:#fff;
}
input::placeholder {color:#fff;}

.btn {
  position:relative;
  left:40%;
  transform: translateX(-50%);
  margin-bottom: 40px;
  width:80%;
  height:40px;
  background: linear-gradient(125deg,#81ecec,#6c5ce7,#81ecec);
  background-position: left;
  background-size: 200%;
  color:white;
  font-weight: bold;
  border:none;
  cursor:pointer;
  transition: 0.4s;
  display:inline;
}

.btn:hover {
  background-position: right;
}

.bottomText_wrap {
  text-align: center;
  display:block;
}

.bottomText{
	display:inline-block;
	text-aling:center;
}
.bottomText a{
	font-size:16px;
	color:#fff;
	display:inline-block;
	padding-right:15px;
}

</style>
<c:choose>
	<c:when test="${param.result eq 'loginFailed'}">
		<script>
			alert('아이디 또는 비밀번호가 일치하지 않습니다');
		</script>
	</c:when>
	<c:when test="${param.result eq 'withdrawal'}">
		<script>
			alert('탈퇴한 ID입니다.');
		</script>
	</c:when>
	<c:when test="${param.result eq 'stop'}">
		<script>
			alert('이용정지된 ID입니다.\n 문의사항은 고객센터(02-0000-0000)로 문의 바랍니다.');
		</script>
	</c:when>
	<c:when test="${param.result eq 'joinSuccess'}">
		<script>
		alert('회원가입이 완료 되었습니다');
		</script>
	</c:when>
</c:choose>
<script type="text/javascript">
	function login(f_obj){
		
		if(f_obj.u_id.value==""){
			f_obj.u_id.focus();
			alert("ID를 입력하세요");
			return;
		}
		
		if(f_obj.u_password.value==""){
			f_obj.u_password.focus();
			alert("비밀번호를 입력하세요");
			return;
		}
			f_obj.method="post";
			f_obj.action="login.mo";
			f_obj.submit();
	}
</script>
</head>
<body>
	<form class="loginForm" name="loginForm" id="loginForm">
		<h2>Login</h2>
		<div class="idForm">
		  <input type="text" class="id" placeholder="id" name="u_id" id="u_id">
		</div>
		<div class="passForm">
		  <input type="password" class="password" placeholder="password" name="u_password">
		</div>
		<input type="button" class="btn" onclick="login(this.form);" value="LOGIN">
		<div class="bottomText_wrap">
			<div class="bottomText">
				<a href="signupForm.mo">회원가입</a>
			</div>
		</div>
	</form>
</body>
</html>