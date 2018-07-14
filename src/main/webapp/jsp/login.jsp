<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>登录</title>
		<script src="../js/jquery-3.3.1.min.js"></script>
		<style type="text/css">
			html,body{
				height: 100%;  
                width: 100%;  
				background:url(../images/background/login.jpg) no-repeat ;
				background-size:100%;
				overflow:hidden;
			}
			input{
				width:150px;
				height:30px;
				margin:5px;
				background:transparent;
				border:1px solid #ffffff"
			}
			label{
				font-size:20pt;
			}
		</style>
	</head>
	<body>
		<form action="../admin/adminLogin" id="loginForm" method="post" align="center" style="margin-top:200px">
			<h1>登录</h1>
			<label style="font-size:26">账号：</label>
			<input type="text" id="userAccount" name="userAccount" placeholder="请输入账号" height:40px" /></h2><br/>
			<label>密码：</label>
			<input type="password" id="password" name="password" placeholder="请输入密码" height:40px" /><br/>
			<input type="button" value="提交" style="width:100px; height:40px;background:#ffffff" onclick="loginCheck()"
				onmouseover="this.style.borderColor='black';this.style.backgroundColor='#FFEB3B'" 
				onmouseout="this.style.borderColor='#ffffff';this.style.backgroundColor='#ffffff'" />
			<input type="reset" value="重置" style="width:100px;height:40px;background:#ffffff"
			 	onmouseover="this.style.borderColor='black';this.style.backgroundColor='#FFEB3B'" 
				onmouseout="this.style.borderColor='#ffffff';this.style.backgroundColor='#ffffff'"/>
		</form>
	</body>
	<script>
	 function loginCheck(){
		 if($("#userAccount").val()==""){
			 alert("请输入用户名！");
			 return false;
		 }
		 if($("#password").val()==""){
			 alert("请输入密码！");
			 return false;
		 }
		 $("#loginForm").submit();
	 }
	</script>
</html>