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
			.item{
				clear:left;
				border: 1px solid #8ad650;
			}
			.item div{
				float:left;
				padding:5px;
				width:100px;
				height:35px;
				border-left: 2px solid #8ad650;
			}
			.item div img{
				border: 2px solid #8ad650;
			}
		</style>
	</head>
	<body>
		<div style="height:40px;">
			<h2 style="float:left;margin:0px;">话题查看</h2>
			<input type="button" value="返回" onclick="backHint()" style="width:60px;height:30px;margin-left:10px" />
		</div>
		<div class="item">
			<div style="height:100px;">用户名：</div>
			<div style="height:100px;">${TopicData.userName}</div>
			<div style="height:100px;"><img src="../exchange/getUserAvatar/?picture=${TopicData.userId}_${TopicData.userIcon}" width="100px" height="100px"></div>
		</div>
		<div class="item">
			<div>发表内容：</div>
			<div>${TopicData.content}</div>
		</div>
		<div class="item">
			<div style="height:150px;">图片：</div>
			<div style="float:left;width:80%;height:150px;">
				<c:forEach items="${pictureList}" var="listItem">
					<img src="../exchange/getPicture/?picture=${TopicData.topicId}_${listItem}" width="150px" height="150px">	
			    </c:forEach>
		    </div>
		</div>
		<div class="item">
			<div style="height:auto;">回复：</div>
			<div style="height:auto; width:50%;">
				<c:forEach items="${rList}" var="listItem">
					<div style="float:left;clear:left;border-top: 2px solid #8ad650;">回复用户：</div>
					<div style="border: 2px solid #8ad650;border-bottom:none;">${listItem.userId}</div>
					<div style="clear:left;float:left;">回复内容：</div>
					<div style="border: 2px solid #8ad650;border-top:none;border-bottom:none;">${listItem.replyContent}</div>
					<div style="clear:left;float:left;">回复日期：</div>
					<div style="border: 2px solid #8ad650;border-top:none;border-bottom:none;"><fmt:formatDate value="${listItem.replyDate}" pattern="yyyy-MM-dd HH:mm" /></div>
				</c:forEach>
		    </div>
		</div>
	</body>
	<script>
		$(document).ready(function(){
			$('input').attr("readonly","readonly");
			$('textarea').attr("readonly","readonly");
		});
		//返回时调用函数
	 	function backHint(){
			$(window.parent.document).find("#rightContent").attr("src","../admin/topicManage");
	 	}
	</script>
</html>