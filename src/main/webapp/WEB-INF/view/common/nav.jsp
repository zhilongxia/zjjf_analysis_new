<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("root", request.getContextPath()); %>
<nav id="nav">
    <a href="${root}/toMainPage.do" target="mainiframe"><h1></h1></a>
    <ul>
        <c:forEach var="item" items="${menuTree}" varStatus="status">   
        	<shiro:hasAnyRoles name="${item.authString}">
	     		<li>
		            <div class="category"><a href="${root}${item.menuUrl}" target="mainiframe"><i class="icon icon-wallet"></i>${item.menuName }</a></div>
		        </li>
	        </shiro:hasAnyRoles>
		</c:forEach>  
        <li>
            <div class="category"><a href="${root}/gateway/graph_char.do" target="mainiframe"><i class="icon icon-wallet"></i>全局统计数据分析</a></div>
        </li>
<%--         <shiro:hasAnyRoles name="admin,abc">
   	    	<li>
	            <div class="category"><a href="${root}/api/sp_order/loadPage.do" target="mainiframe"><i class="icon icon-wallet"></i>交易订单数据分析</a></div>
	        </li>
		</shiro:hasAnyRoles> --%>
        <li>
            <div class="category"><a href="${root}/analysis/statis/toStatisPage.do" target="mainiframe"><i class="icon icon-wallet"></i>全局统计</a></div>
        </li>
        <li>
            <div class="category"><a href="${root}/analysis/product/toProductPage.do" target="mainiframe"><i class="icon icon-wallet"></i>商品数据分析</a></div>
        </li>
        <li>
            <div class="category"><a href="${root}/analysis/shop/toShopPage.do" target="mainiframe"><i class="icon icon-wallet"></i>商铺数据分析</a></div>
        </li>
        <li>
            <div class="category"><a href="${root}/analysis/supplier/toSupplierPage.do" target="mainiframe"><i class="icon icon-wallet"></i>合作商数据分析</a></div>
        </li>
        
        <li>
            <div class="category"><a href="${root}/analysis/order/toOrderPage.do" target="mainiframe"><i class="icon icon-wallet"></i>订单数据分析</a></div>
        </li>
        <c:if test="${logInVo.userName eq 'admin' or logInVo.userName eq 'qingqing' or logInVo.userName eq 'huawuque' or logInVo.userName eq 'jiangxiaoyu'}">
	        <li>
	            <div class="category"><a href="${root}/analysis/store/toStorePage.do" target="mainiframe"><i class="icon icon-wallet"></i>店铺信息查询</a></div>
	        </li>
	        <li>
	            <div class="category"><a href="${root}/analysis/orderList/toOrderListPage.do" target="mainiframe"><i class="icon icon-wallet"></i>订单明细查询</a></div>
	        </li>
        </c:if>
    </ul>
</nav>