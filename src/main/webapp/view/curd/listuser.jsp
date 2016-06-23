<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示所有用户信息</title>
<style type="text/css">

</style>
<script type="text/javascript">


</script>
</head>
<body>
	<h2 align="center">显示用户的详细信息</h2>
	<table style="margin: 0 auto; border-collapse: collapse;" border="1">
		<tr>
			<th>姓名</th>
			<th>年龄</th>
			<th>爱好</th>
			<th>操作</th>
		</tr>
		<tbody>
			<c:forEach items="${listu}" var="user">
				<tr>
					<td>${user.name}</td>
					<td><fmt:formatNumber type="number">${user.age==null?'':user.age }</fmt:formatNumber>
					</td>
					<td><c:choose>
							<c:when test="${empty user.likes }">无</c:when>
							<c:otherwise>
								<c:forEach items="${user.likes }" var="likes" varStatus="str">
									<c:if test="${str.count>1 }">| </c:if>${likes} 
					</c:forEach>
							</c:otherwise>
						</c:choose></td>
					<td>&nbsp;<a href="javascript:void(0);" onclick="editUser('${user._id}','${user.name }','${user.age }','${user.likes }')">编辑</a>&nbsp;
					<a href="javascript:void(0);" onclick="delUser('${user._id}','${user.name }')" style="color: grey">删除</a>&nbsp;</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>