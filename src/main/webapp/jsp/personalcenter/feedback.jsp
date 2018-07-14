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
		</style>
	</head>
	<body>
		<div class="contentDiv">
			<table cellspacing='0' cellpadding='0' border='1' width='100%'>
				<th>反馈用户</th><th>内容</th><th>图片</th><th>联系方式</th><th>反馈日期</th><th>状态</th><th>操作</th>
				<c:forEach items="${dataList}" var="listItem">
					<tr>
						<td nowrap="nowrap">${listItem.userName}</td>
						<td>${listItem.content}</td>
						<td>
							<c:if test="${not empty listItem.picture}">
								<img src="../admin/getfeedbackPicture/?picture=${listItem.feedbackId}_${listItem.picture}" width="150px" height="150px">
							</c:if>
							<c:if test="${empty listItem.picture}">
								<p>无</p>
							</c:if>
						</td>
						<td>${listItem.contact}</td>
						<td nowrap="nowrap"><fmt:formatDate value="${listItem.feedbackDate}" pattern="yyyy-MM-dd HH:mm" /></td>
						<td>
							<c:if test="${listItem.status == false}">
								<p style="color:red">未处理</p>
							</c:if>
							<c:if test="${listItem.status == true}">
								<p style="color:black">已处理</p>
							</c:if>
							<c:if test="${empty listItem.status}">
								<p style="color:red">未处理</p>
							</c:if>
						</td>
						<td nowrap="nowrap" align="center">
							<a href="#" onclick="javascript:detail('${listItem.feedbackId}');">
								<img  style="margin-left:10px" alt="查看" title="查看" width="20px" height="20px" src="../images/查看.png" />
							</a>
							<c:if test="${listItem.status == false}">
								<a href="#" onclick="javascript:audit('${listItem.feedbackId}','${listItem.userName}');">
									<img  style="margin-left:10px" alt="审核" title="审核" width="20px" height="20px" src="../images/审核.png" />
								</a>
							</c:if>
							<c:if test="${listItem.status == true}">
								<a href="#" onclick="javascript:cancelAudit('${listItem.feedbackId}','${listItem.userName}');">
									<img  style="margin-left:10px" alt="撤销" title="撤销" width="20px" height="20px" src="../images/撤销.png" />
								</a>
							</c:if>
							<c:if test="${empty listItem.status}">
								<a href="#" onclick="javascript:audit('${listItem.feedbackId}','${listItem.userName}');">
									<img  style="margin-left:10px" alt="审核" title="审核" width="20px" height="20px" src="../images/审核.png" />
								</a>
							</c:if>
							
							<a href="#" onclick="javascript:deleteFeedback('${listItem.feedbackId}','${listItem.userName}');">
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
		function detail(feedbackId){
			$(window.parent.document).find("#rightContent").attr("src","../admin/feedbackDetail?feedbackId="+feedbackId);
		}
		//点击审核
		function audit(feedbackId,userName){
			var r=confirm("是否要审核用户'"+userName+"'的意见反馈？");
			if(r==true){
				$(window.parent.document).find("#rightContent").attr("src","../admin/feedbackAudit?feedbackId="+feedbackId);
			}
		}
		//点击撤销
		function cancelAudit(feedbackId,userName){
			var r=confirm("是否要撤销用户'"+userName+"'的意见反馈？");
			if(r==true){
				$(window.parent.document).find("#rightContent").attr("src","../admin/feedbackCancelAudit?feedbackId="+feedbackId);
			}
		}
		//点击删除
		function deleteFeedback(feedbackId,userName){
			var r=confirm("是否要删除用户'"+userName+"'的意见反馈？");
			if(r==true){
				$(window.parent.document).find("#rightContent").attr("src","../admin/feedbackDelete?feedbackId="+feedbackId);
			}
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
		    $(window.parent.document).find("#rightContent").attr("src","../admin/feedbackManage?pageNo="+no);
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