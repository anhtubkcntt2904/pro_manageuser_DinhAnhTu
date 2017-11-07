<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<!-- <script type="text/javascript" src="../js/user.js"></script> -->
<title>ユーザ管理</title>
<script type="text/javascript" language="javascript">
	function myFunction() {
		var x = document.getElementById("showHide");
		if (x.style.display === "none") {
			x.style.display = "table-row-group";
		} else {
			x.style.display = "none";
		}
	}
</script>
</head>
<body>
	<!-- Begin vung header -->	
	<%@include file="header.jsp"%>
	<!-- End vung header -->	

<!-- Begin vung input-->	
	<form action="ADM006.html" method="post" name="inputform">	
	<table  class="tbl_input" border="0" width="75%"  cellpadding="0" cellspacing="0" >			
		<tr>
			<th align="left">
				<div style="padding-left:100px;">
					情報確認<br>
					入力された情報をＯＫボタンクリックでＤＢへ保存してください
				</div>
				<div style="padding-left:100px;">&nbsp;</div>
			</th>			
		</tr>				
		<tr>
			<td align="left" >
				<div style="padding-left:100px;">
					<table border="1" width="70%" class="tbl_input" cellpadding="4" cellspacing="0" >					
					<tr>
						<td class="lbl_left">アカウント名:</td>
						<td align="left"><label for="loginName">${userInfor.loginName}</label></td>
					</tr>
					<tr>
						<td class="lbl_left">グループ:</td>
						<td align="left"><label for="groupID">${userInfor.groupId}</label></td>
					</tr>
					<tr>
						<td class="lbl_left">氏名:</td>
						<td align="left"><label for="fullName">${userInfor.fullName}</label></td>
					</tr>	
					<tr>
						<td class="lbl_left">カタカナ氏名:</td>
						<!-- <td align="left">名カナ</td> -->
						<td align="left"><label for="fullNameKana">${userInfor.fullNameKana}</label></td> 
					</tr>
					<tr>
						<td class="lbl_left">生年月日:</td>
						<td align="left"><label for="birthday"><fmt:formatDate pattern="yyyy/MM/dd"
									value="${userInfor.birthday}" /></label></td>
					</tr>				
					<tr>
						<td class="lbl_left">メールアドレス:</td>
						<td align="left"><label for="email">${userInfor.email}</label></td>
					</tr>
					<tr>
						<td class="lbl_left">電話番号:</td>
						<td align="left"><label for="tel">${userInfor.tel}</label></td>
					</tr>	
					<tr>
						<th colspan="2"><a
									href="javascript:myFunction()">日本語能力</a></th>
					</tr>
					<tbody id="showHide">
					<tr>
						<td class="lbl_left">資格:</td>
						<td align="left"><label for="codeLevel">${userInfor.codeLevel}</label></td>
					</tr>
					<tr>
						<td class="lbl_left">資格交付日:</td>
						<td align="left"><label for="startDate"><fmt:formatDate pattern="yyyy/MM/dd"
									value="${userInfor.startDate}" /></label></td>
					</tr>
					<tr>
						<td class="lbl_left">失効日:</td>
						<td align="left"><label for="endDate"><fmt:formatDate pattern="yyyy/MM/dd"
									value="${userInfor.endDate}" /></label></td>
					</tr>	
					<tr>
						<td class="lbl_left">点数:</td>
						<td align="left"><label for="total">${userInfor.total}</label></td>
					</tr>				
					</tbody>								
				</table>
				</div>				
			</td>		
		</tr>
	</table>
	<div style="padding-left:100px;">&nbsp;</div>
		<!-- Begin vung button -->
	<div style="padding-left:45px;">
	<input type="hidden" name="keyAdd" value="${keyAdd}">
	<table border="0" cellpadding="4" cellspacing="0" width="300px">	
		<tr>
			<th width="200px" align="center">&nbsp;</th>
				<td>
					<input class="btn" type="submit" value="OK" />					
				</td>	
				<td>
					<input class="btn" type="button" value="戻る" onclick="location.href='/manage_user/AddUserInput.do?type=back&keyAdd=${keyAdd}';"/>						
				</td>
		</tr>		
	</table>
	<!-- End vung button -->	
</form>
<!-- End vung input -->

<!-- Begin vung footer -->
<%@include file="footer.jsp"%>
<!-- End vung footer -->
</body>

</html>