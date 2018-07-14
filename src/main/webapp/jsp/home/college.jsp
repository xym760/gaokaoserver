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
                float:left;
                position:relative;
			}
			.contentDiv{
				margin-bottom:60px;
			}
			.bottomDiv{
				width:100%;
				height:50px;
				position:fixed;
				bottom:0;
				background-color:#44baff;
			}
			.topDiv{
				height:15%;
			}
			.topDiv div{
				width:20%;
				text-align:center;
				float:left;
				margin:0 auto;
			}
			.topDiv div p,input,select{
				height:30px;
				padding:2;
				margin:0;
				float:left;
			}
		</style>
	</head>
	<body>
		<div class="topDiv">
			<div>
				<p style="margin-left:10;">学校：</p>
				<input type="text" name="collegeName" id="collegeName" placeholder="学校" value="${collegeName}" />
			</div>
			<div>
				<p >省份：</p>
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
				<input type="button" value="查询" onClick="searchCollege()" style="width:60px;margin-left:10px;" />
			</div>
		</div>
		<div class="contentDiv">
			<table cellspacing='0' cellpadding='0' border='1' width='100%'>
				<th>学校名称</th><th>所在省</th><th>类型</th><th>办学性质</th><th>排名</th><th>学校类型</th><th>操作</th>
				<c:forEach items="${dataList}" var="listItem">
					<tr>
						<td nowrap="nowrap">${listItem.name}</td>
						<td>${listItem.province}</td>
						<td>
							${listItem.property}
						</td>
						<td>${listItem.runNature}</td>
						<td nowrap="nowrap">${listItem.ranking}</td>
						<td>
							${listItem.grade}
						</td>
						<td nowrap="nowrap" align="center">
							<a href="#" onclick="javascript:detail('${listItem.name}');">
								<img  style="margin-left:10px" alt="查看" title="查看" width="20px" height="20px" src="../images/查看.png" />
							</a>
							<a href="#" onclick="javascript:updateCollege('${listItem.collegeId}');">
								<img  style="margin-left:10px" alt="修改" title="修改" width="20px" height="20px" src="../images/修改.png" />
							</a>
							<a href="#" onclick="javascript:deleteCollege('${listItem.collegeId}','${listItem.name}');">
								<img  style="margin-left:10px" alt="删除" title="删除" width="20px" height="20px" src="../images/删除.png" />
							</a>
						</td>
					</tr>
			    </c:forEach>
			</table>
		</div>
		<div class="bottomDiv">
			<p style="float:left">总共有<span style="color:red">${recordCount}</span>条记录，每页显示<span style="color:red">${pageSize}</span>条，当前是第<span style="color:red">${pageNo}</span>页</p>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<c:choose>
					   <c:when test="${pageNo==1 || recordCount==0}">
					   		<td nowrap="nowrap">
								<button >首页</button>
							</td>
							<td nowrap="nowrap">
								<button>上页</button>	
							</td>
					   </c:when>
					   <c:otherwise> 
					   		<td nowrap="nowrap">
								<button onclick="turnOverPage('1')">首页</button>									
							</td>
							<td nowrap="nowrap">
								<button onclick="turnOverPage(${pageNo - 1})">上页</button>
							</td>
					   </c:otherwise>
					</c:choose>
					<c:choose>
					   <c:when test="${pageNo == pageCount || recordCount<=0}">
					   		<td nowrap="nowrap">
								<button>下页</button>
							</td>
							<td nowrap="nowrap">
								<button>尾页</button>
							</td>
					   </c:when>
					   <c:otherwise> 
					   		<td nowrap="nowrap">
								<button class="enable" onclick="turnOverPage(${pageNo + 1})">下页</button>								
							</td>
							<td nowrap="nowrap">
								<button class="enable" onclick="turnOverPage(${pageCount})">尾页</button>
							</td>
					   </c:otherwise>
					</c:choose>
					<td nowrap="nowrap">
						&nbsp;转到第&nbsp;
					</td>
					<td nowrap="nowrap">
						<input id="pageNum" name="pageNum"  onkeypress="IsIntegerPager()" onkeyup="IsChsPager()"/>
					</td>
					<td nowrap="nowrap">
						&nbsp;页&nbsp;
					</td>
					<td nowrap="nowrap">
						<c:choose>
						   <c:when test="${recordCount<=0}">
						   		<button>转</button>
						   </c:when>
						   <c:otherwise> 
						   		<button class="enable" onclick="turnOverPage(document.getElementById('pageNum').value)">转</button>
						   </c:otherwise>
						</c:choose>							
					</td>
				</tr>
			</table>
		</div>
	</body>
	<script>
		$(document).ready(function(){
			$('select').change(function(){
				searchCollege(${pageNo});
			});
	    });
		//点击查看
		function detail(collegeName){
			$(window.parent.document).find("#rightContent").attr("src","../admin/collegeDetail?collegeName="+collegeName);
		}
		//点击修改
		function updateCollege(collegeId){
			$(window.parent.document).find("#rightContent").attr("src","../admin/updateCollege?collegeId="+collegeId);
		}
		//点击删除
		function deleteCollege(collegeId,name){
			var r=confirm("是否要删除学校'"+name+"'？");
			if(r==true){
				$(window.parent.document).find("#rightContent").attr("src","../admin/deleteCollege?collegeId="+collegeId);
			}
		}
		//查询院校
		function searchCollege(no){
			var province=$("#province option:selected").val();
			var collegeName=$("#collegeName").val();
			if(no==undefined)
				no="";
			if(no=="")//如果不是点击底部翻页操作
				$(window.parent.document).find("#rightContent").attr("src","../admin/collegeManage?pageNo="+${pageNo}+"&province="+province+"&collegeName="+collegeName);
			else
				$(window.parent.document).find("#rightContent").attr("src","../admin/collegeManage?pageNo="+no+"&province="+province+"&collegeName="+collegeName);
		}
	</script>
	<script language="javascript">
		function turnOverPage(no){				
			if(no== null || no=="" || parseInt(no)<=0 || parseInt(no)>'${pageCount}'){
				return ;
			}
		    //var qForm=document.getElementById('${formName}');
			//qForm.reset();    
			if(no>${pageCount}){
				no=${pageCount};
			}
		    if(no<1){
		    	no=1;
		    }
		    //qForm.pageNo.value=no;    
		    //qForm.submit();
		    searchCollege(no);
		}
		function IsChsPager()
		{
		    var e = document.activeElement;
		    var objValue = e.value.replace(/[^\.\d]/g,'') ;
		    if ( e.value != objValue )
		    {
		        e.value = objValue ;
		    }
		}
		
		function IsIntegerPager()
		{
			var e = document.activeElement;
				
			if(window.event.keyCode<48 || window.event.keyCode>57)
			{				
				window.event.keyCode= 0;
				window.event.returnValue= false;
				return ;
			}
		}
	</script>
</html>