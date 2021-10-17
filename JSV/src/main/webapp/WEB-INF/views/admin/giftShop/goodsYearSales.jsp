<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://www.gstatic.com/charts/loader.js"></script>
<link href="${contextPath}/resources/css/giftShop/admin/sales.css" rel="stylesheet" type="text/css">
<script>

function getArrayList(){
	$.ajax({
		url:'getGoodsSales.mo',
		success:function(arrayList){
			columnChart1(arrayList);
		}
	});
}

//구글 시각화 API를 로딩하는 메소드
google.charts.load('current', {packages: ['corechart']});

/* 30초마다 재갱신 */
google.charts.setOnLoadCallback(function(){
	setInterval(columnChart1(),30000);
});

function columnChart1(arrayList) {
	
	// 실 데이터를 가진 데이터테이블 객체를 반환하는 메소드
	var dataTable = google.visualization.arrayToDataTable(arrayList);

	var options = {// 옵션
		width : 980,
        height : 400,
		title: 'JSV 기프트샵 연매출',
		hAxis: {
            format:''
        }
	};

	var objDiv = document.getElementById('chart_wrap');// 차트 그릴 영역 div 객체
	var chart = new google.visualization.ColumnChart(objDiv);// div 영역에 컬럼차트를 그릴수 있는 차트 반환

	chart.draw(dataTable, options);
	
} // drawColumnChart1()의 끝

</script>
</head>
<body onload="getArrayList()">
<form>
	<input type="hidden" value="${jsonArray}" id="jsonArray">
</form>
<div class="sales_wrap">
	<strong>기프트샵 연매출</strong>
	<div class="sales_links">
		<a href="${contextPath}/admin/gift-shop/getGoodsMonthlySales.mo">연도별 월매출 조회</a>
	</div>
	<div id="chart_wrap"></div>
</div>
</body>
</html>