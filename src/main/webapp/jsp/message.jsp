<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="../css/main.css" />
		<script src="../js/jquery-3.3.1.min.js"></script>
		<style>
			td{
				padding:5px;
			}
		</style>
	</head>
	<body>
		<input type="button" id="add" value="新增消息" onclick="add()" style="width:150px;height:50px;margin:5px">
		<table cellspacing='0' cellpadding='0' border='5' width='100%'>
			<th>消息标题</th><th>图片</th><th>内容</th><th>创建日期</th><th>操作</th>
			<c:forEach items="${dataList}" var="listItem">
				<tr>
					<td nowrap="nowrap">${listItem.title}</td>
					<td><img src="../admin/getMessagePicture/?picture=${listItem.messageId}_${listItem.picture}" width="150px" height="150px"></td>
					<td>${listItem.content}</td>
					<td nowrap="nowrap"><fmt:formatDate value="${listItem.messageDate}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td nowrap="nowrap" align="center">
						<a href="#" onclick="javascript:detailMessage('${listItem.messageId}');">
							<img  style="margin-left:10px" alt="查看" title="查看" width="20px" height="20px" src="../images/查看.png" />
						</a>
						<a href="#" onclick="javascript:updateMessage('${listItem.messageId}');">
							<img  style="margin-left:10px" alt="修改" title="修改" width="20px" height="20px" src="../images/修改.png" />
						</a>
						<a href="#" onclick="javascript:deleteMessage('${listItem.messageId}','${listItem.title}');">
							<img  style="margin-left:10px" alt="删除" title="删除" width="20px" height="20px" src="../images/删除.png" />
						</a>
					</td>
				</tr>
		    </c:forEach>
		</table>
	</body>
	<script>
		//点击添加按钮
		function add(){
			$(window.parent.document).find("#rightContent").attr("src","../admin/messageManageAdd");
		}
		//点击查看
		function detailMessage(messageId){
			$(window.parent.document).find("#rightContent").attr("src","../admin/messageDetail?messageId="+messageId);
		}
		//点击修改
		function updateMessage(messageId){
			$(window.parent.document).find("#rightContent").attr("src","../admin/messageUpdate?messageId="+messageId);
		}
		//点击删除
		function deleteMessage(messageId,title){
			var r=confirm("是否要删除'"+title+"'该条消息？");
			if(r==true){
				$(window.parent.document).find("#rightContent").attr("src","../admin/messageDelete?messageId="+messageId);
			}
		}
	</script>
</html>