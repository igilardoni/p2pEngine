function isEmail(email) {
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return regex.test(email);
}

function isPhone(phone) {
	var regex = /^[0-9-+ ]+$/;
	return regex.test(phone);
}

function textWithSlashNToBr(string) {
	return string.replace(/(?:\r\n|\r|\n)/g, '<br />');
}
function textWithBrToSlashN(string) {
	return string.replace(/<br *\/?>/gi, '\n');
}

function removePunctuation(string){
	return string.replace(/[\.,-\/#!$%\^&\*;:{}=\-_`~()]/g, "").trim();
}