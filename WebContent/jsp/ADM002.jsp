<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="./css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../jsp/ADM002.jsp"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<%@include file="header.jsp"%>
	<!-- End vung header -->

	<!-- Begin vung dieu kien tim kiem -->
	<form action="ListUser.do?type=search" method="post" name="mainform">
		<table class="tbl_input" border="0" width="90%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>会員名称で会員を検索します。検索条件無しの場合は全て表示されます。</td>
			</tr>
			<tr>
				<td width="100%">
					<table class="tbl_input" cellpadding="4" cellspacing="0">
						<tr>
							<td class="lbl_left">氏名:</td>
							<td align="left"><input class="txBox" type="text"
								name="name" value="${fn:escapeXml(name)}" size="20"
								onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" /></td>
							<td></td>
						</tr>
						<tr>
							<td class="lbl_left">グループ:</td>

							<td align="left" width="80px"><select name="group_id">
									<option value="0">全て</option>
									<c:forEach var="group" items="${lstGroup}">
										<option value="${group.groupId}"
											${group.groupId == group_id ? 'selected' : ''}>Nhóm ${group.groupId}</option>
									</c:forEach>
							</select></td>
							<td align="left"><input class="btn" type="submit" value="検索" />
								<input class="btn" type="button" value="新規追加"
								onclick="location.href='/manage_user/AddUserInput.do?type=default';" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!-- End vung dieu kien tim kiem -->
	</form>
	<!-- Begin vung hien thi danh sach user -->
	<table class="tbl_list" border="1" cellpadding="4" cellspacing="0"
		width="80%">

		<tr class="tr2">
			<th align="center" width="20px">ID</th>
			<th align="left">氏名 <c:choose>
					<c:when test="${sessionScope.sortByFullname == 'asc'}">
						<a href="ListUser.do?type=sort&sortType=full_name">▲▽</a>
					</c:when>
					<c:otherwise>
						<a href="ListUser.do?type=sort&sortType=full_name">△▼</a>
					</c:otherwise>
				</c:choose>
			</th>
			<th align="left">生年月日</th>
			<th align="left">グループ</th>
			<th align="left">メールアドレス</th>
			<th align="left" width="70px">電話番号</th>
			<th align="left">日本語能力 <c:choose>
					<c:when test="${sessionScope.sortByCodeLevel == 'asc'}">
						<a href="ListUser.do?type=sort&sortType=code_level">▲▽</a>
					</c:when>
					<c:otherwise>
						<a href="ListUser.do?type=sort&sortType=code_level">△▼</a>
					</c:otherwise>
				</c:choose>
			</th>
			<th align="left">失効日 <c:choose>
					<c:when test="${sessionScope.sortByEndDate == 'asc'}">
						<a href="ListUser.do?type=sort&sortType=end_date">▲▽</a>
					</c:when>
					<c:otherwise>
						<a href="ListUser.do?type=sort&sortType=end_date">△▼</a>
					</c:otherwise>
				</c:choose>
			</th>
			<th align="left">点数</th>
		</tr>
		<tr>
			<c:choose>
				<c:when test="${lstUserInfo.size() > 0}">
					<c:forEach var="userInfo" items="${lstUserInfo}">
						<tr>
							<td align="right"><a
								href="DetailUser.do?userid=${userInfo.userId}"><c:out
										value="${userInfo.userId}" /></a></td>
							<td><c:out value="${userInfo.fullName}" /></td>
							<td align="center"><fmt:formatDate pattern="yyyy/MM/dd"
									value="${userInfo.birthday}" /></td>
							<td><c:out value="${userInfo.groupName}" /></td>
							<td><c:out value="${userInfo.email}" /></td>
							<td><c:out value="${userInfo.tel}" /></td>
							<td><c:out value="${userInfo.nameLevel}" /></td>
							<td align="center"><c:out value="${userInfo.endDate}" /></td>
							<td align="right"><c:out value="${userInfo.total}" /></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="9" align="center"><font color="red"><c:out
									value="${message}" /></font></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tr>

	</table>
	<!-- End vung hien thi danh sach user -->

	<!-- Begin vung paging -->
	<table>
		<tr>
			<td class="lbl_paging"><c:if test="${totalPage > 1}">
					<c:if test="${lstPaging.get(lstPaging.size() - 1) > 3}">
						<c:if test="${lstPaging.size() == 3}">
							<a
								href="ListUser.do?type=paging&currentPage=${lstPaging.get(lstPaging.size() - 3) - 3}">
								<< </a>
						</c:if>
						<c:if test="${lstPaging.size() == 2}">
							<a
								href="ListUser.do?type=paging&currentPage=${lstPaging.get(lstPaging.size() - 2) - 3}">
								<< </a>
						</c:if>
						<c:if test="${lstPaging.size() == 1}">
							<a
								href="ListUser.do?type=paging&currentPage=${lstPaging.get(lstPaging.size() - 1) - 3}">
								<< </a>
						</c:if>
					</c:if>

					<c:forEach var="paging" items="${lstPaging}">
						<c:choose>
							<c:when test="${currentPage == paging}">
								<c:out value="${paging}" />
							</c:when>
							<c:otherwise>
								<a href="ListUser.do?type=paging&currentPage=${paging}"><c:out
										value="${paging}" /></a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if
						test="${lstPaging.get(lstPaging.size() - 1) + 1  <= totalPage && totalPage > 3}">
						<a
							href="ListUser.do?type=paging&currentPage=${lstPaging.get(lstPaging.size() - 1) + 1}">
							>> </a>
					</c:if>
				</c:if></td>
		</tr>
	</table>
	<!-- End vung paging -->

	<!-- Begin vung footer -->
	<%@include file="footer.jsp"%>
	<!-- End vung footer -->

</body>

</html>