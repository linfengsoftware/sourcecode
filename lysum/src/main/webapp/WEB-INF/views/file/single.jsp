<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>用户管理</title>
	
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#fileName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/file/${action}" method="post" class="form-horizontal" enctype="multipart/form-data">
		<fieldset>
			<legend><small>文件管理</small></legend>
			<input type="hidden" name="id" value="${file.id}">
			<div class="control-group">
				<label class="control-label">文件名:</label>
				<div class="controls">
					<input type="text" name="fileName" value="${file.fileName}" class="input-large"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">文件</label>
				<div class="controls">
					<input type="file" name="file" class="input-large"  />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">注册日期:</label>
				<div class="controls">
					<span class="help-inline" style="padding:5px 0px"><fmt:formatDate value="${file.uploadDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">描述:</label>
				<div class="controls">
					<input type="text" id="remark" name="remark" value="${file.remark}" class="input-large required"/>
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

