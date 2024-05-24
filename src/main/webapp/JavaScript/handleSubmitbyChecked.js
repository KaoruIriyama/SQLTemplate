function handleSubmitbyChecked(){
	const checks = document.querySelectorAll(".checks");
		 if (checks.length < 1) {
      		alert("人物が選択されていません");return false;
    	}else{
      		return true;
    	}
	
}