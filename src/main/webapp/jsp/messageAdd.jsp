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
					<h1>添加消息</h1>
				</td>
			</tr>
			<tr>
				<td>
					<form action="../admin/messageManageAddDispose" id="addForm" method="post">
						<input type="text" name="title" id="title" placeholder="请输入标题">
						<input type="text" name="picture" id="picture" readonly="true" value="图片" style="background:gray">
						<textarea id="messageContent" name="content" rows="3" cols="20"></textarea>
						<input type="button" value="确定" onClick="add()">
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<input type="file" name="picture1" id="picture1" value="图片">
				</td>
			</tr>
		</table>
	</body>
	<script>
		function add(){
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
			var fileName;
			if($("#picture1").val()==""){
				alert("请选择一张图片！");
				return false;
			}
			else{
				fileName=$("#picture1")[0].files[0].name;
				$("#picture").val(fileName);
			}
			$.ajax({
		        url: "../admin/messageManageAddDispose",
		        type: 'post',
		        data: {title:title,picture:fileName,content:content},
		        success: function (data) {
		            addPicture(data);
		        }
		    });
		}
		function addPicture(messageId){
			var formData = new FormData();//必须是new FormData后台才能接收到
		    formData.append("picture", $("#picture1")[0].files[0]);
		    formData.append("id",messageId);debugger;
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
	</script>
</html>