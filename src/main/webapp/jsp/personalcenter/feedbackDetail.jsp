<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<script src="../js/jquery-3.3.1.min.js"></script>
		<style type="text/css">
			td{
				padding:5px;
			}
		</style>
	</head>
	<body>
		<table cellspacing='0' cellpadding='0' border='1'>
			<tr>
				<td colspan="4">
					<h1>查看反馈</h1>
				</td>
			</tr>
			<tr>
				<td>
					<p>反馈用户：</p>
				</td>
				<td>
					<input type="text" name="userName" value="${feedbackCustom.userName}">
				</td>
				<td>
					<p>内容：</p>
				</td>
				<td>
					<textarea name="content" rows="10" cols="50" placeholder="请输入内容">${feedbackCustom.content}</textarea>
				</td>
			</tr>
			<tr>
				<td>
					<p>图片：</p>
				</td>
				<td>
					<c:if test="${not empty feedbackCustom.picture}">
						<img src="../admin/getfeedbackPicture/?picture=${feedbackCustom.feedbackId}_${feedbackCustom.picture}" width="400px">
					</c:if>
					<c:if test="${empty feedbackCustom.picture}">
						<p>无</p>
					</c:if>
				</td>
				<td>
					<p>联系方式：</p>
				</td>
				<td>
					<input type="text" name="contact" value="${feedbackCustom.contact}">
				</td>
			</tr>
			<tr>
				<td>
					<p>反馈日期：</p>
				</td>
				<td>
					<input type="text" name="date" value='<fmt:formatDate value="${feedbackCustom.feedbackDate}" pattern="yyyy-MM-dd HH:mm" />'>
				</td>
				<td>
					<p>处理状态：</p>
				</td>
				<td>
					<c:if test="${feedbackCustom.status == false}">
						<p style="color:red">未处理</p>
					</c:if>
					<c:if test="${feedbackCustom.status == true}">
						<p style="color:black">已处理</p>
					</c:if>
					<c:if test="${empty feedbackCustom.status}">
						<p style="color:red">未处理</p>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
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
			$(window.parent.document).find("#rightContent").attr("src","../admin/feedbackManage");
	 	}
	</script>
</html>