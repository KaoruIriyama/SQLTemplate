<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Person, java.util.List" %>
<%@ page import="model.Gender.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="bootstrap.jsp"></jsp:include>
<title>SQLテンプレート 削除リスト</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row justify-content-center">
			<div class="col-sm-12">
			<h1>削除画面</h1>
			</div>
		</div>
	<form method="post">
		<table class="table table-striped">
			<thead class="thead-dark">
				<tr>
				<th scope="col">ID</th>
				<th scope="col">名前</th>
				<th scope="col">年齢</th>
				<th scope="col">性別</th>
				</tr>
			</thead>
		<c:forEach var="person" items="${personList}">
		<tr>
		<%--hidden属性でidをサーブレットに送る --%>
		<%--name属性を配列にすることでcheckboxで複数選択された値をpost出来る --%>
		<td scope="row"><input type="hidden" name="id" value="${person.id }">
		<c:out value="${person.id}" /></td>
		<td scope="row"><c:out value="${person.name}" /></td>
		<td scope="row"><c:out value="${person.age}" /></td>
		<td scope="row"><c:out value="${person.gender.getName()}" /></td>
		</tr>
		</c:forEach>
		</table>
		<div class="col-12 m-3">
		<input type="submit" value="削除" class="btn btn-info" formaction="DeleteServlet" 
			onclick="return confirm('これらの人物レコードを削除してよろしいですか？');">
		<input type="submit" value="戻る" class="btn btn-info" formaction="TemplateServlet">
		</div>
	</form>
	</div>
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</body>
</html>