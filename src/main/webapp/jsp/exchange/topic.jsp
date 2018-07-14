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
			.searchDiv div{
				width:25%;
				text-align:center;
				float:left;
				margin:0 auto;
			}
			.searchDiv div p,input,select{
				height:30px;
				padding:2;
				margin:0;
				float:left;
			}
		</style>
	</head>
	<body>
		<div class="searchDiv">
			<div>
				<p style="margin-left:10;">用户名：</p>
				<input type="text" name="userName" id="userName" placeholder="用户名" value="${userName}" />
			</div>
			<div>
				<p >时间：</p>
				<input type="date" name="startDate" id="startDate" placeholder="学校" value="${startDate}" />
				<input type="button" value="查询" onClick="search(${pageNo})" style="width:60px;margin-left:10px;" />
			</div>
		</div>
		<div class="contentDiv">
			<table cellspacing='0' cellpadding='0' border='1' width='100%'>
				<th>用户名</th><th>内容</th><th>点赞数</th><th>回复数</th><th>日期</th><th>操作</th>
				<c:forEach items="${dataList}" var="listItem">
					<tr>
						<td nowrap="nowrap">${listItem.userName}</td>
						<td>${listItem.content}</td>
						<td>
							${listItem.likeNumber}
						</td>
						<td>${listItem.replyNumber}</td>
						<td>
							${listItem.date}
						</td>
						<td nowrap="nowrap" align="center">
							<a href="#" onclick="javascript:detail('${listItem.topicId}');">
								<img  style="margin-left:10px" alt="查看" title="查看" width="20px" height="20px" src="../images/查看.png" />
							</a>
							<a href="#" onclick="javascript:deleteTopic('${listItem.topicId}','${listItem.userName}');">
								<img  style="margin-left:10px" alt="删除" title="删除" width="20px" height="20px" src="../images/删除.png" />
							</a>
						</td>
					</tr>
			    </c:forEach>
			</table>
		</div>
		<div class="bottomDiv">
			<p style="float:left">总共有${recordCount}条记录，每页显示${pageSize}条，当前是第${pageNo}页</p>
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
		//点击查看
		function detail(topicId){
			$(window.parent.document).find("#rightContent").attr("src","../admin/topicDetail?topicId="+topicId);
		}
		//点击删除
		function deleteTopic(topicId,userName){
			var r=confirm("是否要删除用户'"+userName+"'发表的话题？");
			if(r==true){
				$(window.parent.document).find("#rightContent").attr("src","../admin/topicDelete?topicId="+topicId);
			}
		}
		function search(no){
			var userName=$("#userName").val();
			var startDate=$("#startDate").val();
			if(no==undefined)
				no="";
			if(no=="")//如果不是点击底部翻页操作
				$(window.parent.document).find("#rightContent").attr("src","../admin/topicManage?pageNo="+${pageNo}+"&userName="+userName+"&startDate="+startDate);
			else
				$(window.parent.document).find("#rightContent").attr("src","../admin/topicManage?pageNo="+no+"&userName="+userName+"&startDate="+startDate);
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
		    search(no);
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