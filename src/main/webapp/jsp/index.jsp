<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>主页</title>
		<script src="../js/jquery-3.3.1.min.js"></script>
		<script src="../js/jquery/zTree/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="../js/jquery/zTree/zTreeStyle/zTreeStyle.css" />
		<style>
			.ztree * {
			    font-size: 16px;
			}
			.ztree li span.button.ico_close {
			    width: 15px;
			    height: 15px;
			}
			.ztree li span.button.ico_docu {
			    width: 15px;
			    height: 15px;
			}
			.ztree li span.button.ico_open {
			    width: 15px;
			    height: 15px;
			}
		</style>
		<style type="text/css">
			*{  
                margin: 0;  
                padding: 0;  
            }  
			html,body{  
                height: 100%;  
                width: 100%;
            }
            .topDiv{
            	height:15%;
            	width:15%;
            	background:url(../images/background/topbackground.gif) no-repeat;
            	float:left;
            }
            .topRightDiv{
            	height:15%;
            	width:100%;
            	background:url(../images/background/toprightbg.jpg);
            	background-size:cover;
            }
            h3{
            	font: 14px/1.5 'Microsoft YaHei',arial,tahoma,\5b8b\4f53,sans-serif;
            }
            .adminDiv{
            	height:100%;
            	width:30%;
            	float:right;
            }
            .adminDiv ul{
            	float:right;
            	padding-right:10px;
            	list-style-type:none;
            }
            .adminDiv ul li{
            	float: left;
				padding: 11px;
				border: 2px solid #3C9;
            }
            .adminDiv ul li a{
            	text-decoration: none;
            }
		</style>
	</head>
	<body>
		<div class="topDiv">
			<h3>账号，${user.userAccount}</h3>
			<h3>欢迎管理员：${user.userName}</h3>
		</div>
		<div class="topRightDiv">
			<div class="adminDiv">
				<ul>
					<li><a href="../admin/logout" onclick="return exitHint()">退出</a></li>
					<li><a href="#">修改密码</a></li>
					<li><a href="#">目前系统中存在的院校数：${collegeNumber}</a></li>
				</ul>
			</div>
		</div>
		<div style="height:85%;width:100%">
			<div style="height:99%;width:15%;overflow-y:scroll;float:left">
   				<ul id="tree" class="ztree" style="float:left"></ul>
   			</div>
   			<div style="height:99%;width:85%;overflow-y:scroll">
   				<iframe id="rightContent" src="../admin/index" width="100%" height="100%" frameborder="0">
   				</iframe>
   			</div>
		</div>	
	</body>
	<script>
		function showEffect(){
			$("#userInfoDiv").fadeOut(3000);
			$("#userInfoDiv").fadeIn(3000);
		}
		$(document).ready(function(){
			setInterval(showEffect,3000);
	    });
		//树菜单配置
		var setting = {  
            data: {  
                simpleData: {  
                    enable: true  
                }  
            },
            callback:{
            	onClick:clickMenu
            } 
        };
		var zNodes=[];
		zNodes.push({id:1,pId:0,name:"高考管理",open:true});
		zNodes.push({id:11,pId:1,name:"首页消息"});
		zNodes.push({id:12,pId:1,name:"学校管理"});
		zNodes.push({id:12,pId:1,name:"专业管理"});
		zNodes.push({id:13,pId:1,name:"省控线管理"});
		zNodes.push({id:14,pId:1,name:"学校分数管理"});
		zNodes.push({id:12,pId:1,name:"学校专业管理"});
		
		zNodes.push({id:2,pId:0,name:"考友圈管理"});
		zNodes.push({id:21,pId:2,name:"话题管理"});
		
		zNodes.push({id:3,pId:0,name:"个人中心管理"});
		zNodes.push({id:31,pId:3,name:"意见反馈"});
		zNodes.push({id:32,pId:3,name:"用户管理"});
		zNodes.push({id:33,pId:3,name:"用户设置管理"});
		zNodes.push({id:34,pId:3,name:"Android端版本管理"});
		$.fn.zTree.init($("#tree"), setting, zNodes);
		
		var menus=[
			{name:"首页消息",url:"../admin/messageManage"},
			{name:"意见反馈",url:"../admin/feedbackManage"},
			{name:"话题管理",url:"../admin/topicManage"},
			{name:"学校管理",url:"../admin/collegeManage"},
			{name:"用户管理",url:"../admin/userManage"},
			{name:"专业管理",url:"../admin/majorManage"}
		];
		
		//左侧菜单点击事件
		function clickMenu(){
			var zTree = $.fn.zTree.getZTreeObj("tree").getSelectedNodes();
			var clickName=zTree[0].name;
			if(zTree[0].isParent == false){//如果不是父类
				showRightContent(clickName);
			}
		}
		//显示右侧内容
		function showRightContent(clickItem){
			var url="../admin/index";
			for(i=0;i<menus.length;i++){
				if(menus[i].name==clickItem){
					url=menus[i].url;
				}	
			}
			$("#rightContent").attr("src",url);
		}
		function exitHint(){
			if(confirm("确认退出？")==true)
				return true;
			else
				return false;
		}
	</script>
</html>
