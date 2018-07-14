<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<script src="../js/jquery-3.3.1.min.js"></script>
		<style>
			td{
				padding:5px;
			}
		</style>
	</head>
	<body>
	<form action="../admin/collegeManageUpdateDispose" id="updateForm" method="post">
		<table cellspacing='0' cellpadding='0' border='1'>
			<tr>
				<td colspan="4">
					<h1>查看院校</h1>
				</td>
			</tr>
			<tr>
				<td>
					<p>院校名称：</p>
				</td>
				<td>
					<input type="text" name="collegeId" value="${college.collegeId}" style="display:none;">
					<input type="text" name="name" value="${college.name}">
				</td>
				<td>
					<p>院校官网：</p>
				</td>
				<td>
					<input type="text" name="website" value="${college.website}">
				</td>
			</tr>
			<tr>
				<td>
					<p>院校所在省份：</p>
				</td>
				<td>
					<select name="province" id="province">
					<c:choose>
					   <c:when test="${empty province}">
					   		<option value="" selected="selected">请选择省份</option>
					   </c:when>
					   <c:otherwise> 
					   		<option value="">请选择省份</option>
					   </c:otherwise>
					</c:choose>
					<c:forEach items="${pList}" var="p">
						<c:choose>
						   <c:when test="${province==p.provinceName}">
						   		<option value="${p.provinceName}" selected="selected">${p.provinceName}</option>
						   </c:when>
						   <c:otherwise> 
						   		<option value="${p.provinceName}">${p.provinceName}</option>
						   </c:otherwise>
						</c:choose>
			    	</c:forEach>
				</select>
				</td>
				<td>
					<p>院校类型：</p>
				</td>
				<td>
					<input type="text" name="grade" value="${college.grade}">
				</td>
			</tr>
			<tr>
				<td>
					<p>院校属性：</p>
				</td>
				<td>
					<input type="text" name="property" value="${college.property}">
				</td>
				<td>
					<p>是否教育部直属：</p>
				</td>
				<td>
					<input type="text" name="directlyUnder" value="${college.directlyUnder}">
				</td>
			</tr>
			<tr>
				<td>
					<p>办学性质：</p>
				</td>
				<td>
					<input type="text" name="runNature" value="${college.runNature}">
				</td>
				<td>
					<p>邮箱：</p>
				</td>
				<td>
					<input type="text" name="mailbox" value="${college.mailbox}">
				</td>
			</tr>
			<tr>
				<td>
					<p>院校编码：</p>
				</td>
				<td>
					<input type="text" name="code" value="${college.code}">
				</td>
				<td>
					<p>排名：</p>
				</td>
				<td>
					<input type="number" name="ranking" value="${college.ranking}">
				</td>
			</tr>
			<tr>
				<td>
					<p>院校曾用名：</p>
				</td>
				<td>
					<input type="text" name="formerName" value="${college.formerName}">
				</td>
				<td>
					<p>招办电话：</p>
				</td>
				<td>
					<textarea name="telephone" rows="10" cols="50" placeholder="请输入内容">${college.telephone}</textarea>
				</td>
			</tr>
			<tr>
				<td>
					<p>详细地址：</p>
				</td>
				<td colspan="3">
					<textarea name="address" rows="10" cols="50" placeholder="请输入内容">${college.address}</textarea>
				</td>
			</tr>
			<tr>
				<td>
					<p>院校简介：</p>
				</td>
				<td colspan="3">
					<textarea name="intro" rows="10" cols="50" placeholder="请输入内容">${college.intro}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="submit" value="提交" onclick="return update()" style="width:60px;height:30px;margin-right:10px" />
					<input type="button" value="返回" onclick="backHint()" style="width:60px;height:30px;margin-left:10px" />
				</td>
			</tr>
		</table>
	</form>
	</body>
	<script>
		function update(){
			if($("input[name='name']").val()==""){
				alert("请输入学校名！");
				return false;
			}
			if($("select[name='province']").val()==""){
				alert("请输入省份！");
				return false;
			}
			if($("input[name='code']").val()==""){
				alert("请输入院校编码！");
				return false;
			}
			if($("input[name='collegeId']").val()==""){
				alert("请不要用谷歌浏览器修改Id！");
				return false;
			}
			return true;
		}
		//返回时调用函数
	 	function backHint(){
			if(confirm("返回将丢失已修改信息，是否返回？")){
				//返回
				$(window.parent.document).find("#rightContent").attr("src","../admin/collegeManage");
		    }else{
		    	
		    }
	 	}
	</script>
</html>