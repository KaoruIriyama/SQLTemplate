<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Person, java.util.List" %>
<%@ page import="model.Gender.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="bootstrap.jsp"></jsp:include>
<title>SQLテンプレート メイン</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
<div class="container-fluid">
	<div class="row justify-content-center">
		<div class="col-sm-12">
		<h1>リスト画面</h1>
		</div>
	</div>
	<h2><c:out value="${Msg}"/></h2>
	<form method="get">
	<table class="table table-striped">
		<thead class="thead-dark">
			<tr>
			<th scope="col">全選択<br>
			<input type="checkbox" id="checksAll" name="person" ></th>
			<th scope="col">ID</th>
			<th scope="col">名前</th>
			<th scope="col">年齢</th>
			<th scope="col">性別</th>
			</tr>
		</thead>
		<c:forEach var="person" items="${personList}">
			<tr>
			<td scope="row"><input type="checkbox" class="checks" name="person" value="${person.id}"
			onclick="checkAllorchecks()"></td>
			<%--name属性を配列にすることでcheckboxで複数選択された値をpost出来る --%>
			<td><c:out value="${person.id}" /></td>
			<td><c:out value="${person.name}" /></td>
			<td><c:out value="${person.age}" /></td>
			<td><c:out value="${person.gender.getName()}" /></td>
			</tr>
		</c:forEach>
	</table>
		<div class="col-12 m-3">
			<div class="btn-group" role="group">
			<input type="submit" class="btn btn-info" value="更新" formaction="UpdateServlet" 
			onsubmit="return handleSubmitbyChecked()"
			/>
			<input type="submit" class="btn btn-info" value="削除" formaction="DeleteServlet" 
			<%--onsubmit="return handleSubmitbyChecked()"--%>
			onsubmit="return handleSubmitbyChecked()"
			/>
			</div>
		</div>
	</form>
	<jsp:include page="footer.jsp"></jsp:include>
	<script src="${pageContext.request.contextPath}/JavaScript/checkbox.js"></script>
	<script src="${pageContext.request.contextPath}/JavaScript/handleSubmitbyChecked.js"></script>
	<%--JavaScriptフォルダをwepapp直下におく＆パスを正しく記述する(EL式を使ったルート相対パスが比較的楽か) --%>
</div>
</body>
</html>