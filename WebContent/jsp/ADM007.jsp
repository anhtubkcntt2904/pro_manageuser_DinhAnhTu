<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="./css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/user.js"></script>
<title>ユーザ管理</title>

</head>
<body>
	<!-- Begin vung header -->
	<%@include file="header.jsp"%>
	<!-- End vung header -->

	<form action="./ChangePass.do" method="post">
		<center>
			<table class="tbl_input" cellpadding="4" cellspacing="0"
				width="400px">
				<tr>
					<th width="120px">&nbsp;</th>
					<th></th>
				</tr>
				<tr>
					<th colspan="2" align="left">CHANGE PASS</th>
				</tr>

				<c:forEach var="errMess" items="${lstErr}">
					<tr>
						<td class="errMsg" colspan="2"><font color="red"><c:out
									value="${errMess}" /></font></td>
					</tr>
				</c:forEach>

				<tr>
					<td class="lbl_left">New Pass:</td>
					<td align="left"><input class="txBox" type="password"
						maxlength="15" name="newpass"
						value="<c:out  value=""  escapeXml="true"/>" size="20"
						onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';" /></td>
				</tr>
				<tr>
					<td class="lbl_left">Confirm Pass:</td>
					<td align="left"><input class="txBox" type="password"
						maxlength="15" name="confirmpass" size="20"
						value="<c:out  value=""  escapeXml="true"/>"
						onfocus="this.style.borderColor='#0066ff';"
						onblur="this.style.borderColor='#aaaaaa';" /></td>
				</tr>
				<table>
					<tr>

						<td align="left"><input class="btn btn_wider" type="submit"
							value="OK" /></td>
						<td><input class="btn" type="button" value="戻る"
							onclick="location.href='DetailUser.do?userid=${userid}';" /></td>
					</tr>
				</table>
			</table>
			<table border="0">
				<tr>
					<td><input type="hidden" name="user_id" value="${userid}" /></td>
					<td><input type="hidden" name="type" value="edit" /></td>
				</tr>
			</table>
		</center>
	</form>

	<!-- Begin vung footer -->
	<div class="lbl_footer">
		<br><br><br><br> Copyright © 2010 ルビナソフトウエア株式会社.
						All rights reserved. 
	</div>
	<!-- End vung footer -->
</body>
</html>