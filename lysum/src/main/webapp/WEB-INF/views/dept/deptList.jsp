<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>部门管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
			 	<label>名称：</label> <input type="text" name="search_LIKE_deptName" class="input-medium" value="${param.search_LIKE_deptName}"> 
			    <button type="submit" class="btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>部门</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${depts.content}" var="dept">
			<tr>
				<td><a href="${ctx}/dept/update/${dept.id}">${dept.deptName}</a></td>
				<td><a href="${ctx}/dept/delete/${dept.id}">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${depts}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/dept/create">创建部门</a></div>
</body>
</html>
