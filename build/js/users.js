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
	var firstname = $("#firstName").val();
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
function updaterAccount(){
	var oldPassword = $("#oldpassword").val();
	var password = $("#password").val();
	var passwordConfirm = $("#passwordConfirm").val();
	var username = $("#username").val();
	var name = $("#name").val();
	var firstname = $("#firstname").val();
	var email = $("#email").val();
	var phone = $("#pĥone").val();
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
	}
	loadItems(); // Ask loading all current user's item
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
		includeHome();
	}
}