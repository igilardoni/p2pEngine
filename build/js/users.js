/**
 * JavaScript for managing users
 * @author Michael DUBUIS
 */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/**
 * Login query
 */
function signIn(){
	var username = $("#username").val();
	var password = $("#password").val();
	var content = {
			"username":username,
			"password": password
	};
	var data = {"query":"signIn", "content":content};
	webSocket.send(JSON.stringify(data));
}

/**
 * Logout query
 */
function signOut(){
	var content = {
			
	};
	var data = {"query":"signOut", "content":content};
	webSocket.send(JSON.stringify(data));
}

/**
 * Registration query
 */
function register(){
	$("input").removeClass("inputWrong");
	var error = false;
	var password = $("#password").val();
	var passwordConfirm = $("#passwordConfirm").val();
	var username = $("#username").val();
	var name = $("#name").val();
	var firstname = $("#firstname").val();
	var email = $("#email").val();
	var phone = $("#phone").val();
	if(password != passwordConfirm || password == "") {
		$("#password").addClass("inputWrong");
		$("#passwordConfirm").addClass("inputWrong");
		error = true;
	}
	if(!isSecure(password)){
		if(!confirm("This password isn't secure enough.\nAre you sure you want to use it anyway?")){
			return;
		}
	}
	if(username == "") {
		$("#username").addClass("inputWrong");
		error = true;
	}
	if(name == "") {
		$("#name").addClass("inputWrong");
		error = true;
	}
	if(firstname == "") {
		$("#firstname").addClass("inputWrong");
		error = true;
	}
	if(email == "" || !isEmail(email)) {
		$("#email").addClass("inputWrong");
		error = true;
	}
	if(phone == "" || !isPhone(phone)) {
		$("#phone").addClass("inputWrong");
		error = true;
	}
	if(error) return;
	var content = {
			"password":password,
			"username":username,
			"name":name,
			"firstname":firstname,
			"email":email,
			"phone":phone
	};
	var data = {"query":"register", "content":content};
	webSocket.send(JSON.stringify(data));
}

/**
 * Updating account query
 */
function updateAccount(){
	$(".inputWrong").removeClass("inputWrong");
	var oldPassword = $("#oldpassword").val();
	var password = $("#password").val();
	var passwordConfirm = $("#passwordConfirm").val();
	var username = $("#username").val();
	var name = $("#name").val();
	var firstname = $("#firstname").val();
	var email = $("#email").val();
	var phone = $("#phone").val();
	
	var error = false;
	if(name == "") {
		$("#name").addClass("inputWrong");
		error = true;
	}
	if(firstname == "") {
		$("#firstname").addClass("inputWrong");
		error = true;
	}
	if(password == "" || passwordConfirm == "" || password != passwordConfirm){
		$("#password").addClass("inputWrong");
		$("#passwordConfirm").addClass("inputWrong");
		error = true;
	}
	if(oldPassword == "") {
		$("#oldPassword").addClass("inputWrong");
		error = true;
	}
	if(!isEmail(email)){
		$("#email").addClass("inputWrong");
		error = true;
	}
	if(!isPhone(phone)){
		$("#phone").addClass("inputWrong");
		error = true;
	}
	if(!isSecure(password)){
		if(!confirm("This password isn't secure enough.\nAre you sure you want to use it anyway?")){
			return;
		}
	}
	if(error)
		return;
	
	var content = {
			"oldpassword":oldPassword,
			"password":password,
			"username":username,
			"name":name,
			"firstname":firstname,
			"email":email,
			"phone":phone
	};
	var data = {"query":"updateAccount", "content":content};
	webSocket.send(JSON.stringify(data));
}

/**
 * Loading account query
 */
function loadAccount(){
	var content = {};
	var data = {"query":"loadAccount", "content":content};
	webSocket.send(JSON.stringify(data));
}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
// Login answer
function login(content){
	if(content.feedbackOk == "true" || content.feedbackOk){
		includeHeader();
		includeMenu();
		includeHome();
		includeFavorites();
		$("#usernameHead").text(content.username);
	}else{
	}
}

// Logout answer
function logout(content){
	removeHeader();
	removeMenu();
	includeLogin();
	$("#username").val(content.username);
}

// Registration answer
function registration(content){
	if(content.feedbackOk == "true" || content.feedbackOk){
		includeLogin();
		$("#username").val(content.username);
	}
}

// Update account answer
function accountUpdated(content){
	if(content.feedbackOk == "true" || content.feedbackOk){
		includeHeader();
		includeMenu();
		includeHome();
	}
}

// Display account
function accountLoaded(content){
	$("#username").val(content.username);
	$("#name").val(content.name);
	$("#firstname").val(content.firstname);
	$("#email").val(content.email);
	$("#phone").val(content.phone);
}