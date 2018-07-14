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
				<td colspan="2">
					<h3>${major.majorName}</h3>
				</td>
			</tr>
			<tr>
				<td>
					<p>专业代码：</p>
				</td>
				<td>
					<p>${major.majorCode}</p>
				</td>
			</tr>
			<tr>
				<td>
					<p>门类：</p>
				</td>
				<td>
					<p>${major.category}</p>
				</td>
			</tr>
			<tr>
				<td>
					<p>专业简介：</p>
				</td>
				<td>
					<p>${major.intro}</p>
				</td>
			</tr>
		</table>
	</body>
	<script>
		$(document).ready(function(){
			$('input').attr("readonly","readonly");
			$('textarea').attr("readonly","readonly");
		});
	</script>
</html>