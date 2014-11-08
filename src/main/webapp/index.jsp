<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>jQuery.EasyUI- 1.2.6 后台经典框架使用DEMO</title>
<link href="css/default.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css"
	href="js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/themes/icon.css" />

<style type="text/css">
#user_info {
	background-image: url(images/manage_r2_c1.jpg);
	background-repeat: no-repeat;
	height: 55px;
	border: 0;
	padding: 10px 0px 0 55px;
	margin: 5px 0px 0px 0px;
}
</style>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src='js/XiuCai.index.js'>
	
</script>

<script type="text/javascript">
	var _menus = {"menus":[]};
	
	<c:forEach items="${modules}" var="m" varStatus="status">
 	<c:if test="${m.pid == 0}">
 		p_menu = {"menuid":"${m.id}","icon":"icon-sys","menuname":"${m.name}","menus":[]};
 		_menus.menus.push(p_menu);
		<c:forEach items="${modules}" var="s">
		 	<c:if test="${s.pid == m.id}">
		 		s_menu = {"menuid" : "${s.id}",	"icon" : "layout-button-right","menuname" : "${s.name}","url":"${s.url}"};
		 		p_menu.menus.push(s_menu);
			</c:if>
				</c:forEach>
		 	</c:if>
		</c:forEach>

	//设置登录窗口
	function openPwd() {
		$('#w').window({
			title : '修改密码',
			width : 300,
			modal : true,
			shadow : true,
			closed : true,
			height : 160,
			resizable : false
		});
	}
	//关闭登录窗口
	function closePwd() {
		$('#w').window('close');
	}

	//修改密码
	function serverLogin() {
		var $newpass = $('#txtNewPass');
		var $rePass = $('#txtRePass');

		if ($newpass.val() == '') {
			msgShow('系统提示', '请输入密码！', 'warning');
			return false;
		}
		if ($rePass.val() == '') {
			msgShow('系统提示', '请在一次输入密码！', 'warning');
			return false;
		}

		if ($newpass.val() != $rePass.val()) {
			msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
			return false;
		}

		$.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(
				msg) {
			msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
			$newpass.val('');
			$rePass.val('');
			close();
		})

	}

	$(function() {

		openPwd();

		$('#editpass').click(function() {
			$('#w').window('open');
		});

		$('#btnEp').click(function() {
			serverLogin();
		})

		$('#btnCancel').click(function() {
			closePwd();
		})

		$('#loginOut').click(function() {
			$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

				if (r) {
					location.href = '/ajax/loginout.ashx';
				}
			});
		})
	});
</script>

</head>
<body class="easyui-layout" style="overflow-y: hidden" fit="true"
	scroll="no">
	<noscript>
		<div
			style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>

	<div id="loading-mask"
		style="position: absolute; top: 0px; left: 0px; width: 100%; height: 100%; background: #D2E0F2; z-index: 20000">
		<div id="pageloading"
			style="position: absolute; top: 50%; left: 50%; margin: -120px 0px 0px -120px; text-align: center; border: 2px solid #8DB2E3; width: 200px; height: 40px; font-size: 14px; padding: 10px; font-weight: bold; background: #fff; color: #15428B;">
			<img src="images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...
		</div>
	</div>

	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 30px; background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%; line-height: 20px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">
		<span style="float: right; padding-right: 20px;" class="head">欢迎
			Allen <a href="#" id="editpass">修改密码</a> <a href="#" id="loginOut">安全退出</a>
		</span> <span style="padding-left: 10px; font-size: 16px;"><img
			src="images/TableDrawTable.png" width="20" height="20"
			align="absmiddle" /> &nbsp;&nbsp;&nbsp;Game One后台管理系统</span>
	</div>
	<div region="south" split="true"
		style="height: 30px; background: #D2E0F2;">
		<div class="footer">By Allen(QQ:429300664) EMAIL：
			allen.ime@gmail.com</div>
	</div>
	<div region="west" split="true" title="导航菜单" style="width: 208px;"
		id="west">
		<div class="easyui-layout" style="overflow-y: hidden" fit="true"
			scroll="no">
			<div id="user_info" region="north">
				欢迎您， <strong>Allen</strong> <br /> [ <a href="#">系统管理员</a>， <a
					href="#">退出</a>]
			</div>

			<div id="nav" region="west">
				<!--  导航内容 -->

			</div>
		</div>

	</div>
	<div id="mainPanle" region="center"
		style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎使用"
				style="padding: 20px; overflow: hidden; color: red;">
				<h1 style="font-size: 24px;">* 作者：Allen QQ：429300664</h1>
				<h1 style="font-size: 24px;">
					* EMAIL: <a style="font-size: 24px; color: green;"
						href="http://hxling.cnblogs.com">allen.ime@gmail.com</a>
				</h1>
				<h1 style="font-size: 24px;">* 讨论群：</h1>
				<h1 style="font-size: 24px;">*
<!-- 					广告：本人承接各类大中小型管理系统的软件的设计与开发，有需要的朋友联系我啦~~~~</h1> -->
			</div>
		</div>
	</div>


	<!--修改密码窗口-->
	<div id="w" class="easyui-window" title="修改密码" collapsible="false"
		minimizable="false" maximizable="false" icon="icon-save"
		style="width: 300px; height: 150px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding: 10px; background: #fff; border: 1px solid #ccc;">
				<table cellpadding=3>
					<tr>
						<td>新密码：</td>
						<td><input id="txtNewPass" type="password" class="txt01" /></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input id="txtRePass" type="password" class="txt01" /></td>
					</tr>
				</table>
			</div>
			<div region="south" border="false"
				style="text-align: right; height: 30px; line-height: 30px;">
				<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"
					href="javascript:void(0)"> 确定</a> <a id="btnCancel"
					class="easyui-linkbutton" icon="icon-cancel"
					href="javascript:void(0)">取消</a>
			</div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="close">关闭</div>
		<div id="closeall">全部关闭</div>
		<div id="closeother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="closeright">当前页右侧全部关闭</div>
		<div id="closeleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="exit">退出</div>
	</div>


</body>
</html>