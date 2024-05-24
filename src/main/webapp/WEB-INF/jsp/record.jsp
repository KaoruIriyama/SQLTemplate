<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="bootstrap.jsp"></jsp:include>
<title>SQLテンプレート 登録</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row justify-content-center">
			<div class="col-sm-12">
			<h1>登録画面</h1>
			</div>
		</div>
	<%--ここで空欄のまま送信ボタンを押すと警告が出るようにしたい --%>
		<form action="RecordServlet" method="post">
			<div class="form-group">
				<h3>名前</h3>
				<input type="text" class="form-control" name="name" required><br>
				<h3>年齢</h3>
				<input type="number" class="form-control" name="age" required><br>
			</div>
			<h3>性別</h3>
			<div class="form-check-inline">
				
				<input type="radio" id="man" name="gender" value="Man" required>
				<label for="man">男性</label>
				<input type="radio" id="woman" name="gender" value="Woman" required>
				<label for="woman">女性</label>
				<input type="radio" id="other" name="gender" value="Other" required>
				<label for="other">その他</label>
			</div><br>
			<div class="col-12 m-3">
				<div class="btn-group" role="group">
					<input type="submit" class="btn btn-info" value="登録">
					<input type="reset" class="btn btn-info" value="リセット">
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
</body>
</html>