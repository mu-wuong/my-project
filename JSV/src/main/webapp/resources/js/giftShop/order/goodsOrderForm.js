/**
 * 상품 주문 폼 js
 */

function payment(sum){
	
	if(!$("#check00").is(":checked")){
		alert("서비스 약관동의는 필수입니다");
		return;
	}
	
	var buyer_name = $("#member_info_name").val();	//구매자 이름
	var buyer_tel = $("#member_info_ph").val();		//구매자 번호 
	
	//상품가격
	var goods_price = [];
	$("input[name=goods_price]").each(function(i){
		goods_price.push($(this).val());
	});
	
	//수량
	var goods_qty = [];
	$(".goods_qty").each(function(i){
		goods_qty.push($(this).text());
	});
	
	//상품번호
	var goods_no = [];	
	$("input[name=goods_no]").each(function(i){
		goods_no.push($(this).val());
	});
	
	var IMP = window.IMP;
	IMP.init('imp98062220');
	IMP.request_pay({
	pg: 'inicis', 
	pay_method: 'card',
	merchant_uid: 'merchant_' + new Date().getTime(),
	name: 'JSV기프트샵',
	amount: 1000,
	buyer_email: 'iamport@siot.do',
	buyer_name: buyer_name,
	buyer_tel: buyer_tel,
	}, function (rsp) {
	console.log(rsp);
	
	if (rsp.success) {
		var msg = '결제가 완료되었습니다.';
		msg += '고유ID : ' + rsp.imp_uid;
		msg += '상점 거래ID : ' + rsp.merchant_uid;
		msg += '결제 금액 : ' + rsp.paid_amount;
		msg += '카드 승인번호 : ' + rsp.apply_num;
		 $.ajax({
	          type: 'post',
	          url: '../verifyIamport.mo',
	          data: {'imp_uid': rsp.imp_uid},
	          success: function(data) {
		          if(data.code!=200){
		               	alert("결제에 실패하였습니다");     
		          }else{
		                $.ajax({
		            		type: "post",
		            		url : "goodsOrder.mo",
		            		data: {
		            				"goods_no":goods_no,
		            		 		"goods_qty":goods_qty,
		            		 		"goods_price":goods_price,
		            				"user_ph":buyer_tel,
		            				"sum":sum
		            			   },
		            		dataType :"json",	   
		            		success:function(data, textStatus){//정상요청 및 응답시 호출되는 함수
		            			if(data.code==200){
		            				alert(msg);
		            				location.href="isOrderComplete.mo?code=200";
		            			}else{
		            				location.href="isOrderComplete.mo?code=404";
		            			}
		            		},
		            		error:function(data, status, error){ //오류발생시 호출되는 함수 
		            			console.log("에러발생했어요 data="+data); 
		            		}
		            	});//ajax
		          }//else
	           },
	            error: function(data) {
	           		console.log("error" +data);
	           }
	      	})//ajax
	} else {
		var msg = '결제에 실패하였습니다.';
		msg += '에러내용 : '+ rsp.error_msg;
		console.log(msg);
		location.href="isOrderComplete.mo?code=404";
	}
	});
}				
$(document).ready(function(){
			
	//전체선택 & 해제
	$("#check00").click(function(){
		
		var checkLength = $("input[name=agreeCheck]").length;
		var checkedLength =  $("input[name='agreeCheck']:checked").length;
		var checked = $("input[name=agreeCheck]").is(":checked");

		if(!checked || checkLength!=checkedLength){
			$("input[name=agreeCheck]").prop("checked",true);
			$("input[name=agreeCheck1]").prop("checked",true);
		}else{
			$("input[name=agreeCheck]").prop("checked",false);
			$("input[name=agreeCheck1]").prop("checked",false);
		}
	});//$("#allCheck").click
	
	//개별 체크박스 선택
	$("input[name=agreeCheck]").click(function(){
		
		var checkLength = $("input[name='agreeCheck']").length;
		var checkedLength =  $("input[name='agreeCheck']:checked").length;

		if(checkLength==checkedLength){
			$("#check00").prop("checked",true);
		}else{
			$("#check00").prop("checked",false);
		}
		
	});//$("input[name='checkOne']")

});