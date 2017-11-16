function myFunction() {
	var x = document.getElementById("showHide");
	if (x.style.display === "none") {
		x.style.display = "table-row-group";
	} else {
		x.style.display = "none";
	}
}

function showDialog(link) {
	if (confirm('削除しますが、よろしいでしょうか。?')) {
		location.href = link;
	} else {
		return false;
	}
}