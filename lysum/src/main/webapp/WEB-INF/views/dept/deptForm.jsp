<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>部门管理</title>
	
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#dept_deptName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/dept/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${dept.id}"/>
		<fieldset>
			<legend><small>管理部门</small></legend>
			<div class="control-group">
				<label for="dept_deptName" class="control-label">部门名称:</label>
				<div class="controls">
					<input type="text" id="dept_deptName" name="deptName"  value="${dept.deptName}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="description" class="control-label">父部门:</label>
				<div class="controls">
					<select name="parentId">
						<c:forEach items="${depts}" var="childDept">
						    <c:choose>
							    <c:when test="${childDept.id == dept.parentId}">
							    	<option value="${childDept.id}" selected="selected">${childDept.deptName}</option>
							    </c:when>
							    <c:otherwise>
									<option value="${childDept.id}">${childDept.deptName}</option>
							    </c:otherwise>
						    </c:choose>
						</c:forEach>
					</select>
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
