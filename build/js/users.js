/**
 * JavaScript for managing users
 * @author Michael DUBUIS
 */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
// Ask to the model to logout
function signOut(){
	var content = {
			
	};
	var data = {"query":"signOut", "content":content};
	webSocket.send(JSON.stringify(data));
}

// Ask to the model to login
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

// Ask to the model to register
function register(){
	var password = $("#password").val();
	var passwordConfirm = $("#passwordConfirm").val();
	var username = $("#username").val();
	var name = $("#name").val();
	var firstname = $("#firstname").val();
	var email = $("#email").val();
	var phone = $("#phone").val();
	// TODO Fields verification
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

// Ask to the model to update current account
function updateAccount(){
	var oldPassword = $("#oldpassword").val();
	var password = $("#password").val();
	var passwordConfirm = $("#passwordConfirm").val();
	var username = $("#username").val();
	var name = $("#name").val();
	var firstname = $("#firstname").val();
	var email = $("#email").val();
	var phone = $("#phone").val();
	// TODO Fields verification
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

// Ask to the model data of current account
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
	if(content.ok == "ok"){
		includeHeader();
		includeMenu();
		includeHome();
	}else{
		alert(content.message);
	}// Ask loading all current user's item
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
	if(content.ok = "ok"){
		includeLogin();
		$("#username").val(content.username);
	}
}

// Update account answer
function accountUpdated(content){
	if(content.ok = "ok"){
		includeHeader();
		includeMenu();
		includeHome();
	}else{
		if(content.message == "wrong password")
			$("#oldpassword").css("color", "#FF0000");
		if(content.message == "not the same password"){
			$("password").css("color", "#FF0000");
			$("passwordConfirm").css("color", "#FF0000");
		}
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