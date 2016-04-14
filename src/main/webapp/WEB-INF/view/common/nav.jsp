<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setAttribute("root", request.getContextPath()); %>
<nav id="nav">
    <h1 class="logo"></h1>
    <ul>
    	<c:forEach var="item" items="${menuTree}" varStatus="status">   
        	<shiro:hasAnyRoles name="${item.authString}">
		        <li>
		            <div class="category" data-direction="down"><i class="icon icon-global"></i>全局统计<i class="icon-direction"></i></div>
		            <div class="subcategory">
		                <a href="${root}${item.menuUrl}" target="mainiframe">${item.menuName }</a>
		            </div>
		        </li>
	        </shiro:hasAnyRoles>
		</c:forEach>  
    </ul>
</nav>