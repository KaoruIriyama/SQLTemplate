<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="WEB-INF/jsp/bootstrap.jsp"></jsp:include>
<title>SQLテンプレート トップ</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
<div class="container-fluid">
		
		<div class="jumbotron mt-4"><%--画像を入れたい --%>
			<h1 class="text-center">SQL Template</h1>
			<h2>これはWebアプリのテンプレートです。</h2>
			<p class="lead">
			Java ServletとH2databaseを使用して、<br>
			名前・年齢・性別を持つ人物データを
			登録・閲覧・更新・削除できます。</p>
			<div class="btn btn-info">
				<a href="TemplateServlet" class="text-light">
				リストへ</a>	
			</div>
			<div class="btn btn-info">
				<a href="RecordServlet" class="text-light"
				>新規登録</a>
			</div>
		</div>
	
</div>
</body>
</html>