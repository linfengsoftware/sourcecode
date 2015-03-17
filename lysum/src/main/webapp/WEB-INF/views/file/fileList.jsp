<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>文件管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
			 	<label>文件名称：</label> <input type="text" name="search_LIKE_fileName" class="input-medium" value="${param.search_LIKE_fileName}"> 
			    <button type="submit" class="btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>文件</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${files.content}" var="file">
			<tr>
				<td><a href="${ctx}/file/update/${file.id}">${file.fileName}</a></td>
				<td><a href="${ctx}/file/delete/${file.id}">"删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${files}" paginationSize="10"/>

	<div><a class="btn" href="${ctx}/file/create">创建文件</a></div>
</body>
</html>
