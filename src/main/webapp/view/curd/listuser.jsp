<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示所有用户信息</title>
<style type="text/css">
</style>
<script type="text/javascript" src="${ctx}/js/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="${ctx}/js/CommonJS.js"></script>
<script type="text/javascript">
	//检测是否有未保存的数据
	var nosave = false;
	//编辑数据  
	function editUser(id, name, age, likes) {
		if (nosave) {
			if (!confirm('您有未保存的数据，继续操作将放弃数据')) {
				return;
			}
		}
		if (null == id || id == undefined || id.trim() == '') {
			alert('请在指定网页上操作!');
			return;
		}
		$('#objectid').val(id);
		$('#name').val(name);
		$('#age').val(parseFloat(age));
		$('#likes').val(likes.tostr(','));
		$('#inupdiv').show();
		nosave = true;
	}
	//添加数据
	function addUser() {
		if (nosave) {
			if (!confirm('您有未保存的数据，继续操作将放弃数据')) {
				return;
			}
		}
		$('#objectid').val(0);
		$('#name').val('');
		$('#age').val('');
		$('#likes').val('');
		$('#inupdiv').show();
		nosave = true;
	}

	//存储数据
	function saveUser() {
		var objectid=$('#objectid');
		var name=$('#name');
		//统一校验数据
		if (name == undefined || name.val().trim() == '') {
			alert('名称不能为空');
			return;
		}
		$('#age').val($('#age').val().onlyNum(0, 3, false));
		var age=$('#age');
		if (age == undefined || age.val() == '') {
			alert('年龄不能为空');
			return;
		}
		var likes=$('#likes');
		if (likes == undefined || likes.val().trim() == '') {
			alert('爱好不能为空');
			return;
		}

		//更新或新增
		$.ajax({
			type : 'post',
			url : '${ctx}/curd/saveOrUpdateUser.do?flag=' + Math.random(),
			data : 'objectid=' + objectid.val()+'&name='+name.val()+'&age='+age.val()+'&likes='+likes.val(),
			success : function(data) {
				if (data == 'saveupdate') {
					nosave = false;
					alert('操作成功');
					window.location.reload(true);
				} else {
					alert('本次操作失败,没有数据更改');
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert('操作异常，错误码为:' + XMLHttpRequest.status);
			}
		});

	}

	//删除数据
	function delUser(id, name) {
		if (null == id || id == undefined || id.trim() == '') {
			alert('请在指定网页上操作!');
			return;
		}
		//用户二次确认后删除
		if (confirm('您确定要删除   ' + name + '  的信息吗?')) {
			$.ajax({
				type : 'post',
				url : '${ctx}/curd/delUser.do?flag=' + Math.random(),
				data : 'id=' + id,
				success : function(data) {
					if (data == 'succ') {
						alert('删除成功');
						window.location.reload(true);
					} else {
						alert('本次删除失败');
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert('删除异常，错误码为:' + XMLHttpRequest.status);
				}
			});
		}
	}
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
									<c:if test="${str.count>1 }">,</c:if>${likes} 
					</c:forEach>
							</c:otherwise>
						</c:choose></td>
					<td>&nbsp;<a href="javascript:void(0);"
						onclick="editUser('${user._id}','${user.name }','${user.age }','${user.likes }')">编辑</a>&nbsp;
						<a href="javascript:void(0);"
						onclick="delUser('${user._id}','${user.name }')"
						style="color: grey">删除</a>&nbsp;
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<button style="margin: 10px 0px 0px 60%" onclick="addUser();">新增</button>
	<div id="inupdiv"
		style="display: none; padding-left: 40%; margin-top: 100px;">
		<input type="hidden" name="objectid" id="objectid" value="0" /> <label>姓名:</label>
		<input type="text" id="name" name="name" /><br /> <label>年龄:</label>
		<input type="text" id="age" name="age"
			onkeyup="this.value=value.onlyNum(0,3,false);" /><br /> <label>爱好:</label>
		<textarea rows="3" cols="20" id="likes" name="likes"></textarea>
		<br />
		<button style="margin: 10px 0px 0px 80px" onclick="saveUser();">提交</button>
	</div>
</body>
</html>