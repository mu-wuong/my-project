<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
.contents{
	position: relative;
    max-width: 1060px;
    min-width: 1024px;
    margin: 0 auto;
    padding-bottom: 80px;
}

.content_title{
    padding-top: 75px;
    text-align: center;
}

.title, .sub_title{
	margin: 0;
    color: #222;
    font-size: 40px;
    line-height: 40px;
    
}

.title_desc{
    padding-top: 20px;
    color: #555;
    font-size: 16px;
}

.content_wrap{
	padding-top: 60px;
}

.regi_complete {
    padding: 50px 0;
    border-top: 1px solid #222;
    background-color: #f8f8f8;
    text-align: center;
}

.img{
	width: 89px;
    height: 89px;
    margin: 0 auto 35px;
    display: block;
    background: url(../resources/img/member/ico_regi_complete.png) no-repeat -300px 0;
    background-position: -100px -200px;
}

.sub_title{
    font-size: 24px;
}

.sub_desc{
	padding-top: 8px;
	font-size:18px;
}

.pwd_box{
	margin-top: 35px;
}

.input_txt {
    display: inline-block;
    height: 34px;
    padding-right: 32px;
    vertical-align: middle;
    zoom: 1;
}

.input_txt.w330{
    padding-right: 0;
}

.input_txt.w330 input {
    width: 298px;
}

.input_txt input {
    display: block;
    width: 100%;
    height: 32px;
    padding: 0 15px;
    border: 1px solid #ddd;
    color: #888;
    font-size: 14px;
    line-height: 32px;
}

.btn_center {
    padding-top: 20px;
}

.btn {
    display: inline-block;
    min-width: 128px;
    height: 38px;
    margin: 0 3px;
    *margin: 0 5px;
    padding: 0 15px;
    border: 1px solid #333;
    border-radius: 3px;
    background-color: #fff;
    color: #333;
    text-align: center;
    line-height: 38px;
    vertical-align: middle;
    cursor: pointer;
    transition: border .5s, background .5s, color .5s;
}
.btn.btn_em {
    background-color: #333;
    color: #fff;
}

</style>
<script>
function check(){
	var u_password = $("#u_password").val();
	if(u_password==''){
		alert("비밀번호를 입력해 주세요");
		return;
	}	
	$.ajax({
		type:"post",
		url :"pwdCheck.mo",
		data:{"u_password":u_password},
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		dataType:"json",
		success:function(data){
			console.log(data);
			if(data>0){
				location.href="mypage.mo";
			}else{
				alert("비밀번호가 일치하지 않습니다");
			}
		},
		error:function(jqXHR, textStatus, errorThrown){
			console.log("비밀번호 조회 실패 \n" + textStatus + " : " + errorThrown);
		}
	});//$.ajax
	
}

</script>
<title>마이페이지</title>
</head>
<body>
<a href="../gift-shop/getOrderList.mo">기프트샵 구매내역(임시링크)</a>
<form name="confirm_pwd_frm" id="confirm_pwd_frm">
	<div class="contents">
		<div class="content_title">
			<h1 class="title">회원정보 수정</h1>
			<p class="title_desc">회원님의 소중한 정보를 안전하게 관리하세요.</p>
		</div>
		<div class="content_wrap">
			<div class="mypage_modify">
				<div class="regi_complete">
					<span class="img"></span>
					<p class="sub_title">회원정보를 수정하시려면 비밀번호를 입력하셔야 합니다.</p>
					<p class="sub_desc">회원님의 개인정보 보호를 위한 본인 확인 절차이오니, 회원 로그인 시 사용하시는 비밀번호를 입력해주세요.</p>
					<div class="pwd_box">
						<span class="input_txt w330">
							<input type="password" id="u_password" class="text" placeholder="비밀번호를 입력해주세요." title="비밀번호" maxlength="20">
						</span>
					</div>
					<div class="btn_center">
						<input type="button" onclick="check()" class="btn btn_em" value="확인">
						<input type="button" onclick="history.back()" class="btn" value="취소">
					</div>
				</div>
			</div>
		</div>
	</div>
</form>	
</body>
</html>