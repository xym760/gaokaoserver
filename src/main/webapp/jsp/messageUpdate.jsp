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
					<h1>修改消息</h1>
				</td>
			</tr>
			<tr>
				<td>
					<form action="../admin/messageManageAddDispose" id="addForm" method="post">
						<p>标题：</p><input type="text" name="title" id="title" placeholder="请输入标题" value="${message.title}">
						<p>内容：</p><textarea id="messageContent" name="content" rows="10" cols="50" placeholder="请输入内容">${message.content}</textarea>
						<p>数据库中图片：</p><img src="../admin/getMessagePicture/?picture=${message.messageId}_${message.picture}" width="150px" height="150px">
						<p>图片：</p><input type="text" name="picture" id="picture" readonly="true" style="background:gray">
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" id="hideMessageId" value="${message.messageId}" style="display:none" />
					<input type="text" id="hidePicture" value="${message.picture}" style="display:none" />
					<input type="file" name="picture1" id="picture1" value="图片">
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" value="确定" onClick="update()">
					<input type="button" value="返回" onclick="backHint()" />
				</td>
			</tr>
		</table>
	</body>
	<script>
		function update(){
			var messageId=$("#hideMessageId").val();
			var title=$("#title").val();
			if(title==""){
				alert("标题不能为空！");
				return false;
			}
			var content=$("#messageContent").val();
			if(content==""){
				alert("内容不能为空！");
				return false;
			}
			var fileName="";
			if($("#picture1").val()!=""){
				fileName=$("#picture1")[0].files[0].name;
			}
			else//如果未修改图片，则为数据库中原来的图片
				fileName=$("#hidePicture").val();
			$.ajax({
		        url: "../admin/messageManageUpdateDispose",
		        type: 'post',
		        data: {messageId:messageId,title:title,picture:fileName,content:content},
		        success: function (data) {
		        	if($("#picture1").val()!="")
		            	addPicture(data);
		        	else
		        		$(window.parent.document).find("#rightContent").attr("src","../admin/messageManage");
		        }
		    });
		}
		function addPicture(messageId){
			var formData = new FormData();//必须是new FormData后台才能接收到
		    formData.append("picture", $("#picture1")[0].files[0]);
		    formData.append("id",messageId);
		    $.ajax({
		        url: "../admin/uploadMessagePicture",
		        data: formData,
		        type: 'post',
		        datatype: "json",
		        contentType: false,//必须false才会自动加上正确的Content-Type
		        processData: false,//必须false才会避开jQuery对 formdata 的默认处理，XMLHttpRequest会对 formdata 进行正确的处理 
		        success: function (data) {
		            alert(data);
		            $(window.parent.document).find("#rightContent").attr("src","../admin/messageManage");
		        }
		    });
		}
		//返回时调用函数
	 	function backHint(){
			if(confirm("返回将丢失已修改信息，是否返回？")){
				//返回
				$(window.parent.document).find("#rightContent").attr("src","../admin/messageManage");
		    }else{
		    	
		    }
	 	}
	</script>
</html>