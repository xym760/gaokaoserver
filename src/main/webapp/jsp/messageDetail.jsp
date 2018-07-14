<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<script src="../js/jquery-3.3.1.min.js"></script>
	</head>
	<body>
		<table>
			<tr>
				<td>
					<h1>查看消息</h1>
				</td>
			</tr>
			<tr>
				<td>
					<form action="../admin/messageManageAddDispose" id="addForm" method="post">
						<p>标题：</p><input type="text" name="title" id="title" placeholder="请输入标题" value="${message.title}">
						<p>内容：</p><textarea id="messageContent" name="content" rows="10" cols="50" placeholder="请输入内容">${message.content}</textarea>
						<p>图片：</p><img src="../admin/getMessagePicture/?picture=${message.messageId}_${message.picture}" width="150px" height="150px">
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" value="返回" onclick="backHint()" />
				</td>
			</tr>
		</table>
	</body>
	<script>
		$(document).ready(function(){
			$('input').attr("readonly","readonly");
			$('textarea').attr("readonly","readonly");
		});
		//返回时调用函数
	 	function backHint(){
			$(window.parent.document).find("#rightContent").attr("src","../admin/messageManage");
	 	}
	</script>
</html>