<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>街坊店宝</title>
    <%@ include file="../../common/head.jsp"%>
    <script src="../../resources/js/vendor/My97DatePicker/WdatePicker.js"></script>
    <script src="../../resources/js/comm.js"></script>
    <script src="<%=request.getContextPath() %>/resources/js/angular/angular.js"></script
</head>
<body class="wrap-bd" ng-app="orderTable" ng-controller="tableController">
	<div class="mb-default">
	    <a href="" class="crumb">订单数据分析</a>
	    <a href="" class="crumb crumb-active">订单明细报表</a>
	</div>
	<div class="op-section"> 
        <select class="select" ng-model="citySelect" ng-options="v.code as v.name for v in citySelect"></select> 
        <select class="select" ng-model="areaSelect" ng-options="v.code as v.name for v in areaSelect"></select> 
        <select class="select" ng-model="spGroupIdSelect" ng-options="v.code as v.name for v in spGroupIdSelect"></select> 
        <select class="select" ng-model="supportmethoSelect" ng-options="v.code as v.name for v in supportmethoSelect"></select> 
        <select class="select" ng-model="supportStatusSelect" ng-options="v.code as v.name for v in supportStatusSelect"></select> 
        <select class="select" ng-model="statusSelect" ng-options="v.code as v.name for v in statusSelect"></select> 
	</div>
	<div class="op-section">
	    <input type="text" placeholder="订单号/子订单号" class="input input-search-text" ng-model="orderNos">
	    <input type="text" placeholder="便利店名称" class="input input-search-text" ng-model="storeName">
	    <input type="text" placeholder="配送商" class="input input-search-text" ng-model="supplierName">
	</div>
	<div class="op-section clearfix">
	    <div class="fl">
	      	  下单时间
	        <input type="text" class="input input-date J_timeS" ng-model="addTimeBegin"> 至  <input type="text" class="input input-date J_timeE" ng-model="addTimeEnd">
	        <span class="pills pills-active ml-default">昨天</span>
	        <span class="pills">最近7天</span>
	        <span class="pills">最近30天</span>
	    </div>
	    <div class="fr">
	        <input type="button" class="input input-search-button" value="确定" ng-click="queryOrders();">
	        <input type="button" class="input input-search-button-white" value="导出" ng-click="excelExport();">
	    </div>
	</div>
	<div>
	    <table class="table-list">
	        <thead class="table-thead">
	 	        <tr><th colspan="4">订单归属信息</th><th colspan="5">订单基本信息</th><th colspan="5">订单金额信息</th><th colspan="4">订单状态信息</th></tr>
		        <tr><td ng-repeat="a in cn_keys">{{a}}</td> </tr>
	        </thead>
	        <tbody class="table-tbody">
	       		<tr ng-repeat="d_row in key_dataList"><td ng-repeat="b in d_row track by $index">{{b}}</td></tr>
	        </tbody>
	    </table>
	</div>
	<%@ include file="../../common/pagination.jsp"%>
	<script>
	    var root = '<%=request.getContextPath() %>';
	</script>
	<script src="<%=request.getContextPath() %>/resources/view/sporder/sp_order.js"></script>
</body>
</html>
