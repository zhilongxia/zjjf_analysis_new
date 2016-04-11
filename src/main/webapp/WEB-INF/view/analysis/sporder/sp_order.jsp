<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<% request.setAttribute("root", request.getContextPath());%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>街坊店宝</title>
    <%@ include file="../../common/head.jsp"%>
 	<script src="<%=request.getContextPath() %>/resources/js/echarts/echarts.js"></script>
 	<script src="<%=request.getContextPath() %>/resources/js/jquery/jquery-1.9.1.min.js"></script>
    <link href="${root}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <script src="<%=request.getContextPath() %>/resources/js/angular/angular.js"></script>
    <script src="${root}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<!-- 全局统计列表begin -->
<div class="wrap-bd" ng-app="orderTable" ng-controller="tableController">
    <div class="mb-default">
        <span class="crumb">全局统计</span><span class="crumb-active">核心数据汇总</span>
    </div>
    <div class="mb-small clearfix">
       <form action="${root}/analysis/statis/getStatisList.do" method="post" id="searchForm">
        <label>查询时间</label>
        <div class="input-append date" id="datetimepicker" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
		   <input class="span2" size="16" type="text" value="12-02-2012">
		   <span class="add-on"><i class="icon-remove"></i></span>
		   <span class="add-on"><i class="icon-th"></i></span>
		</div>
		<div class="input-append date" id="datetimepicker" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
		   <input class="span2" size="16" type="text" value="12-02-2012">
		   <span class="add-on"><i class="icon-remove"></i></span>
		   <span class="add-on"><i class="icon-th"></i></span>
		</div>
        <input type="button" class="input input-search-button ml-default"  id="btnQuery" value="搜索"  ng-click="queryOrders();"/>
        <input type="button" class="input input-search-button ml-default"  id="btnExport" value="导出" ng-click="excelExport();"/>
       </form>
    </div>
    <div>
        <table class="table-list table-border">
            <thead class="table-thead">
 				<tr class="table-header">
			      <th ng-repeat="a in cn_keys">{{a}}</th>
			    </tr>
		    </thead>
	        <tbody class="table-tbody">
	            <tr ng-repeat="d_row in key_dataList"> 
	                <td ng-repeat="b in d_row track by $index">{{b}}</td>
	            </tr>
	        </tbody>
       </table>
    </div>
    <div id="jpagination">
    	<ul class="m-pagination-page" style="">
	    	<li class="active"><a data-page-index="0">1</a></li>
	    	<li><a data-page-index="1">2</a></li>
	    	<li><a data-page-index="2">3</a></li>
	    	<li><a data-page-index="3">4</a></li>
	    	<li><a data-page-index="1" ng-click="nextPage(2);">下一页</a></li>
	    	<li><a data-page-index="6050">尾页</a></li>
    	</ul>
    <div class="m-pagination-size" style="display: none;">
	    <select data-page-btn="size">
		    <option value="5">5</option>
		    <option value="10" selected="">10</option>
		    <option value="15">15</option>
		    <option value="20">20</option>
	    </select>
    </div>
    <div class="m-pagination-jump" style="">
	    <div class="m-pagination-group">
	   		 <input data-page-btn="jump" type="text"><button data-page-btn="jump" type="button">Go</button>
	    </div>
    </div>
    <div class="m-pagination-info" style="display: none;">1 ~ 10 of 60507 entires</div></div>
</div>
<!-- 全局统计列表end -->

<script type="text/javascript">
   
	var tableController_url = '<%=request.getContextPath() %>/api/sp_order/spOrderList.do';
	var export_url = '<%=request.getContextPath() %>/report/excelExport/portExcel.do';
	
 	var app = angular.module('orderTable', []); // 第二个参数定义了Module依赖 

	app.config(function($httpProvider) {
	    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
	    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

	    // Override $http service's default transformRequest
	    $httpProvider.defaults.transformRequest = [function(data) {
	        /**
	         * The workhorse; converts an object to x-www-form-urlencoded serialization.
	         * @param {Object} obj
	         * @return {String}
	         */
	        var param = function(obj) {
	            var query = '';
	            var name, value, fullSubName, subName, subValue, innerObj, i;
	 
	            for (name in obj) {
	                value = obj[name];
	 
	                if (value instanceof Array) {
	                    for (i = 0; i < value.length; ++i) {
	                        subValue = value[i];
	                        fullSubName = name + '[' + i + ']';
	                        innerObj = {};
	                        innerObj[fullSubName] = subValue;
	                        query += param(innerObj) + '&';
	                    }
	                } else if (value instanceof Object) {
	                    for (subName in value) {
	                        subValue = value[subName];
	                        fullSubName = name + '[' + subName + ']';
	                        innerObj = {};
	                        innerObj[fullSubName] = subValue;
	                        query += param(innerObj) + '&';
	                    }
	                } else if (value !== undefined && value !== null) {
	                    query += encodeURIComponent(name) + '='
	                            + encodeURIComponent(value) + '&';
	                }
	            }
	 
	            return query.length ? query.substr(0, query.length - 1) : query;
	        };
	 
	        return angular.isObject(data) && String(data) !== '[object File]'
	                ? param(data)
	                : data;
	    }];
	});
	
	app.controller('tableController', ['$scope','$http', function($scope, $http) {
		
		var data = {name:'angular',password:'333',age:1};
		
		$http.post(tableController_url, data).success(function(result) { 
			$scope.cn_keys = result.key_cn;
			$scope.key_dataList = result.dataList;
		}).error(function(result) {  
			 alert("an unexpected error ocurred!");
		}); 
		
		// 下一页
		$scope.nextPage = function(nextPage){
			alert($scope.startTimess);
			var data = {"nextPage":nextPage};
			$http.post(tableController_url, data).success(function(result) { 
				$scope.cn_keys = result.key_cn; 
				$scope.key_dataList = result.dataList; 
			}).error(function(result) {  
				 alert("an unexpected error ocurred!");
			}); 
		}
		
		// 查询订单列表
		$scope.queryOrders = function(){
			var data = {"nextPage":3};
			$http.post(tableController_url, data).success(function(result) { 
				$scope.cn_keys = result.key_cn; 
				$scope.key_dataList = result.dataList; 
			}).error(function(result) {  
				 alert("an unexpected error ocurred!");
			});  	
		}
		
		$scope.excelExport = function(){
		
			$http.post(export_url, data).success(function(result) { 
				
			}).error(function(result) {  
				 alert("an unexpected error ocurred!");
			});  	
		}
		
		
	}]);
</script>
</body>
</html>