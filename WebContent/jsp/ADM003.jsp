<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<form action="AddUserInput.do?type=confirm" method="post" name="inputform">
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">会員情報編集</div>
				</th>
			</tr>
			<tr>
				<c:forEach var="errMess" items="${lstError}">
					<tr>
						<td class="errMsg" colspan="2"><font color="red"><c:out
									value="${errMess}" /></font></td>
					</tr>
				</c:forEach>			
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left"><font color="red">*</font> アカウント名:</td>
								<td align="left"><input class="txBox" type="text" name="loginName"
									value="${userInfor.loginName}" size="15"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> グループ:</td>
								<td align="left"><select name="group_id">
										<option value="0">選択してください</option>
										<c:forEach var="group" items="${lstMstGroup}">
											<option value="${group.groupId}"
												${group.groupId == group_id ? 'selected' : ''}>Nhóm ${group.groupId}</option>
										</c:forEach>
								</select> <span>&nbsp;&nbsp;&nbsp;</span></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="fullName" value="${userInfor.fullName}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="fullNameKana" value="${userInfor.fullNameKana}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 生年月日:</td>
								<td align="left"><select name="yearbirthday">
								<option value="2017">2017</option>
										<c:forEach var="year" items="${lstYear}">
											<option value="${year}"
												${year == yearbirthday ? 'selected' : ''}>${year}</option>
										</c:forEach>
								</select>年 <select name="monthbirthday">
								<option value="12">12</option>
										<c:forEach var="month" items="${lstMonth}">
											<option value="${month}"
												${month == monthbirthday ? 'selected' : ''}>${month}</option>
										</c:forEach>
								</select>月 <select name="daybirthday">
								<option value="31">31</option>
										<c:forEach var="day" items="${lstDay}">
											<option value="${day}"
												${day == daybirthday ? 'selected' : ''}>${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> メールアドレス:</td>
								<td align="left"><input class="txBox" type="text"
									name="email" value="${userInfor.email}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font>電話番号:</td>
								<td align="left"><input class="txBox" type="text"
									name="tel" value="${userInfor.tel}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> パスワード:</td>
								<td align="left"><input class="txBox" type="password"
									name="password" value="${userInfor.password}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left">パスワード（確認）:</td>
								<td align="left"><input class="txBox" type="password"
									name="confirmpass" value="" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<th align="left" colspan="2"><a
									href="javascript:myFunction()">日本語能力</a></th>
							</tr>
							<tbody id="showHide">
								<tr>
									<td class="lbl_left">資格:</td>
									<td align="left"><select name="code_level">
											<option value="0">選択してください</option>
											<c:forEach var="mstjapan" items="${lstMstJapan}">
												<option value="${mstjapan.code_level}"
													${mstjapan.code_level == code_level ? 'selected' : ''}>${mstjapan.code_level}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<td class="lbl_left">資格交付日:</td>
									<td align="left"><select name="yearvalidate">
									<option value="2017">2017</option>
											<c:forEach var="year" items="${lstYear}">
												<option value="${year}"
													${year == yearvalidate ? 'selected' : ''}>${year}</option>
											</c:forEach>
									</select>年<select name="monthvalidate">
									<option value="12">12</option>
											<c:forEach var="month" items="${lstMonth}">
												<option value="${month}"
													${month == monthvalidate ? 'selected' : ''}>${month}</option>
											</c:forEach>
									</select>月 <select name="dayvalidate">
									<option value="31">31</option>
											<c:forEach var="day" items="${lstDay}">
												<option value="${day}"
													${day == dayvalidate ? 'selected' : ''}>${day}</option>
											</c:forEach>
									</select>日</td>
								</tr>
								<tr>
									<td class="lbl_left">失効日:</td>
									<td align="left"><select name="yearinvalidate">
									<option value="2017">2017</option>
											<c:forEach var="year" items="${lstYear}">
												<option value="${year}"
													${year == yearinvalidate ? 'selected' : ''}>${year}</option>
											</c:forEach>
									</select>年 <select name="monthinvalidate">
									<option value="12">12</option>
											<c:forEach var="month" items="${lstMonth}">
												<option value="${month}"
													${month == monthinvalidate ? 'selected' : ''}>${month}</option>
											</c:forEach>
									</select>月 <select name="dayinvalidate">
									<option value="31">31</option>
											<c:forEach var="day" items="${lstDay}">
												<option value="${day}"
													${day == dayinvalidate ? 'selected' : ''}>${day}</option>
											</c:forEach>
									</select>日</td>
								</tr>
								<tr>
									<td class="lbl_left">点数:</td>
									<td align="left"><input class="txBox" type="text"
										name="total" value="${userInfor.total}" size="5"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
									<%-- <c:when test="${userInfor.total == 0}">
										<td align="left"><input class="txBox" type="text"
											name="total" value="" size="5"
											onfocus="this.style.borderColor='#0066ff';"
											onblur="this.style.borderColor='#aaaaaa';" /></td>
									</c:when>
									<c:otherwise>
										<td align="left"><input class="txBox" type="text"
											name="total" value="${userInfor.total}" size="5"
											onfocus="this.style.borderColor='#0066ff';"
											onblur="this.style.borderColor='#aaaaaa';" /></td>
									</c:otherwise> --%>
								</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<div style="padding-left: 100px;">&nbsp;</div>
		<!-- Begin vung button -->
		<div style="padding-left: 45px;">
			<table border="0" cellpadding="4" cellspacing="0" width="300px">
				<tr>
					<th width="200px" align="center">&nbsp;</th>
					<td><input class="btn" type="submit" value="確認" /></td>
					<td><input class="btn" type="button" value="戻る" /></td>
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