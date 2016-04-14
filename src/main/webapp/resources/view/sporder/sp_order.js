
$(function() {
    dateRangeSimple('.J_timeS', '.J_timeE');
});

var tableController_url = root + '/api/sp_order/spOrderList.do';
var export_url = root + '/report/excelExport/portExcel.do';
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
	
	var data = {};
	// load page
	$http.post(tableController_url, data).success(function(result) { 
		// option 
		$scope.citySelect = result.optionList[0];
		$scope.areaSelect = result.optionList[1];
		$scope.spGroupIdSelect = result.optionList[2];
		$scope.supportmethoSelect = result.optionList[3];
		$scope.supportStatusSelect = result.optionList[4];
		$scope.statusSelect = result.optionList[5];
		// table
		$scope.cn_keys = result.key_cn;
		$scope.key_dataList = result.dataList;
	}).error(function(result) {  
		 alert("an unexpected error ocurred!");
	}); 
	
	// 下一页
	$scope.nextPage = function(nextPage){

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
		
		var data = {};
		$http.post(tableController_url, data).success(function(result) { 
			$scope.cn_keys = result.key_cn; 
			$scope.key_dataList = result.dataList; 
		}).error(function(result) {  
			 alert("an unexpected error ocurred!");
		});  	
	}
	
	// 导出Excel
	$scope.excelExport = function(){
	
		$http.post(export_url, data).success(function(result) { 
			
		}).error(function(result) {  
			 alert("an unexpected error ocurred!");
		});  	
	}
}]);
