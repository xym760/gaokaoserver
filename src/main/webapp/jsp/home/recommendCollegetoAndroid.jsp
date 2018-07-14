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
		<style type="text/css">
			td{
				padding:5px;
			}
			body{
				height: 100%;  
                width: 99%;
			}
		</style>
	</head>
	<body>
		<div class="contentDiv">
			<table cellspacing='0' cellpadding='0' border='1' width='95%'>
				<th>学校名称</th><th>所在省</th><th>排名</th><th>学校类型</th><th>查看</th>
				<c:forEach items="${dataList}" var="listItem">
					<tr>
						<td nowrap="nowrap">${listItem.name}</td>
						<td>${listItem.province}</td>
						<td nowrap="nowrap">${listItem.ranking}</td>
						<td>
							${listItem.grade}
						</td>
						<td nowrap="nowrap" align="center">
							<a href="../home/collegeDetail?collegeName=${listItem.name}">
								<img  style="margin-left:10px" alt="查看" title="查看" width="20px" height="20px" src="../images/查看.png" />
							</a>
						</td>
					</tr>
			    </c:forEach>
			</table>
		</div>
	</body>
	<script>
		//点击查看
		function detail(collegeName){
			//$(window.parent.document).find("#rightContent").attr("src","../home/collegeDetail?collegeName="+collegeName);
		}
	</script>
</html>