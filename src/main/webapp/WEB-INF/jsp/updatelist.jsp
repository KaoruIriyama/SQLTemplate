<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Person, java.util.List" %>
<%@ page import="model.Gender.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="bootstrap.jsp"></jsp:include>
<title>SQLテンプレート 更新リスト</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
 <%--ここで空欄のまま送信ボタンを押すと警告が出るようにしたい --%>
 <div class="container-fluid">
	<div class="row justify-content-center">
		<div class="col-sm-12">
			<h1>更新画面</h1>
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
				<td scope="row"><input type="hidden" class="form-control" name="id" value="${person.id }"><c:out value="${person.id}" /></td>
				<td scope="row"><input type="text" class="form-control" name="name" value="${person.name}" required></td>
				<td scope="row"><input type="number" class="form-control" name="age"value="${person.age}"required></td>
				<td scope="row">
				<%--ここでvalue="${person.gender}"の値によって初期値が入力された形にしたい --%>
				<c:choose>
				  <c:when test="${person.gender.ordinal() == 0}"><c:set var="man_checked">checked</c:set></c:when>
				  <c:when test="${person.gender.ordinal() == 1}"><c:set var="woman_checked">checked</c:set></c:when>
				  <c:when test="${person.gender.ordinal() == 2}"><c:set var="other_checked">checked</c:set></c:when>
				</c:choose>
				<select name="gender" class="form-control" required>
				<option value="Man" <c:out value="${man_checked}" />>男性</option>
				<option value="Woman" <c:out value="${woman_checked}" />>女性</option>
				<option value="Other" <c:out value="${other_checked}" />>その他</option>
				</select>
				</td>
				</tr>
			
			</c:forEach>
			
		</table>
		<div class="col-12 m-3">
			<div class="btn-group" role="group">
				<input type="submit" class="btn btn-info" value="更新" formaction="UpdateServlet" 
				onclick="return confirm('これらの人物レコードを更新してよろしいですか？');">
				<input type="submit" class="btn btn-info" value="リセット">
				<input type="submit" class="btn btn-info" value="戻る" formaction="TemplateServlet">
				
			</div>
		</div>
	</form>
</div>
<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</body>
</html>