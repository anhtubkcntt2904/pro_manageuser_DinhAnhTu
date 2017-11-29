<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="./css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="./js/user.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<%@include file="header.jsp"%>
	<!-- End vung header -->

	<!-- Begin vung input-->
	<form action="AddUserInput.do?type=confirm" method="post"
		name="inputform">
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">会員情報編集</div>
				</th>
			</tr>
			
				<c:forEach var="errMess" items="${lstError}">
					<tr>
						<td class="errMsg" colspan="2" style="padding-left: 90px;"><font color="red"><c:out
									value="${errMess}" /></font></td>
					</tr>
				</c:forEach>
			
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td><input type="hidden" name="user_id"
									value="${userInfor.userId}" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> アカウント名:</td>
								<c:if test="${userInfor.userId != 0}">
									<td align="left"><input class="txBox" type="text"
										name="loginName" value="${fn:escapeXml(userInfor.loginName)}"
										size="15" onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" readonly /></td>
								</c:if>
								<c:if test="${userInfor.userId == 0}">
									<td align="left"><input class="txBox" type="text"
										name="loginName" value="${fn:escapeXml(userInfor.loginName)}"
										size="15" onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
								</c:if>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> グループ:</td>
								<td align="left"><select name="group_id">
										<option value="0">選択してください</option>
										<c:forEach var="group" items="${lstMstGroup}">
											<option value="${group.groupId}"
												${group.groupId == userInfor.groupId ? 'selected' : ''}>${group.groupName}</option>
										</c:forEach>
								</select> <span>&nbsp;&nbsp;&nbsp;</span></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="fullName" value="${fn:escapeXml(userInfor.fullName)}"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="fullNameKana"
									value="${fn:escapeXml(userInfor.fullNameKana)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 生年月日:</td>
								<td align="left"><select name="yearbirthday">
										<c:forEach var="year" items="${lstYear}">
											<option value="${year}"
												${year == userInfor.yearbirthday ? 'selected' : ''}>${year}</option>
										</c:forEach>
								</select>年 <select name="monthbirthday">
										<c:forEach var="month" items="${lstMonth}">
											<option value="${month}"
												${month == userInfor.monthbirthday ? 'selected' : ''}>${month}</option>
										</c:forEach>
								</select>月 <select name="daybirthday">
										<c:forEach var="day" items="${lstDay}">
											<option value="${day}"
												${day == userInfor.daybirthday ? 'selected' : ''}>${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> メールアドレス:</td>
								<td align="left"><input class="txBox" type="text"
									name="email" value="${fn:escapeXml(userInfor.email)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font>電話番号:</td>
								<td align="left"><input class="txBox" type="text"
									name="tel" value="${fn:escapeXml(userInfor.tel)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<c:if test="${userInfor.userId == 0}">
								<tr>
									<td class="lbl_left"><font color="red">*</font> パスワード:</td>
									<td align="left"><input class="txBox" type="password"
										name="password" value="" size="30"
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
							</c:if>
							<tr>
								<th align="left" colspan="2"><a
									href="javascript:myFunction()">日本語能力</a></th>
							</tr>
							<tbody id="showHide" style="display: none">
								<tr>
									<td class="lbl_left">資格:</td>
									<td align="left"><select name="code_level">
											<option value="0">選択してください</option>
											<c:forEach var="mstjapan" items="${lstMstJapan}">
												<option value="${mstjapan.code_level}"
													${mstjapan.code_level == userInfor.codeLevel ? 'selected' : ''}>${mstjapan.name_level}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<td class="lbl_left">資格交付日:</td>
									<td align="left"><select name="yearvalidate">
											<c:forEach var="year" items="${lstYear}">
												<option value="${year}"
													${year == userInfor.yearvalidate ? 'selected' : ''}>${year}</option>
											</c:forEach>
									</select>年<select name="monthvalidate">
											<c:forEach var="month" items="${lstMonth}">
												<option value="${month}"
													${month == userInfor.monthvalidate ? 'selected' : ''}>${month}</option>
											</c:forEach>
									</select>月 <select name="dayvalidate">
											<c:forEach var="day" items="${lstDay}">
												<option value="${day}"
													${day == userInfor.dayvalidate ? 'selected' : ''}>${day}</option>
											</c:forEach>
									</select>日</td>
								</tr>
								<tr>
									<td class="lbl_left">失効日:</td>
									<td align="left"><select name="yearinvalidate">
											<c:forEach var="year" items="${lstYearEnd}">
												<option value="${year}"
													${year == userInfor.yearinvalidate ? 'selected' : ''}>${year}</option>
											</c:forEach>
									</select>年 <select name="monthinvalidate">
											<c:forEach var="month" items="${lstMonth}">
												<option value="${month}"
													${month == userInfor.monthinvalidate ? 'selected' : ''}>${month}</option>
											</c:forEach>
									</select>月 <select name="dayinvalidate">
											<c:forEach var="day" items="${lstDay}">
												<option value="${day}"
													${day == userInfor.dayinvalidate ? 'selected' : ''}>${day}</option>
											</c:forEach>
									</select>日</td>
								</tr>
								<tr>
									<td class="lbl_left">点数:</td>
									<td align="left"><input class="txBox" type="text"
										name="total" value="${userInfor.total}" size="5"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
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
					<c:if test="${userInfor.userId == 0}">
						<td><input class="btn" type="button" value="戻る"
							onclick="location.href='/manage_user/ListUser.do?type=back';" /></td>
					</c:if>
					<c:if test="${userInfor.userId != 0}">
						<td><input class="btn" type="button" value="戻る"
							onclick="location.href='/manage_user/DetailUser.do?userid=${userInfor.userId}';" /></td>
					</c:if>
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