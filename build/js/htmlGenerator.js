/**
 * Method to generate page 
 */
function getLoginForm(){
	var div = document.createElement("div");
	div.setAttribute("id", "login");
	var h1 = document.createElement("h1");
	h1.appendChild(document.createTextNode("Sign In"));
	div.appendChild(h1);
	// Username
	var p1 = document.createElement("p");
	var label1 = document.createElement("label");
	label1.setAttribute("for", "username");
	label1.setAttribute("id","label_name");
	label1.appendChild(document.createTextNode("Username : "));
	var input1 = document.createElement("input");
	input1.setAttribute("type", "text");
	input1.setAttribute("name", "username");
	input1.setAttribute("id", "username");
	input1.setAttribute("required","required");
	input1.setAttribute("placeholder","Alice");
	p1.appendChild(label1);
	p1.appendChild(input1);
	div.appendChild(p1);
	// Password
	var p2 = document.createElement("p");
	var label2 = document.createElement("label");
	label2.setAttribute("for", "password");
	label2.setAttribute("id","label_password");
	label2.appendChild(document.createTextNode("Password : "));
	var input2 = document.createElement("input");
	input2.setAttribute("type", "password");
	input2.setAttribute("name", "password");
	input2.setAttribute("id", "password");
	input2.setAttribute("required","required");
	input2.setAttribute("placeholder","ex : p4$Sw0r6!");
	p2.appendChild(label2);
	p2.appendChild(input2);
	div.appendChild(p2);
	// Button SignIn
	var p3 = document.createElement("p");
	var input3 = document.createElement("input");
	input3.setAttribute("class", "button");
	input3.setAttribute("type", "submit");
	input3.setAttribute("onclick", "signIn();");
	input3.setAttribute("value","Sign In");
	p3.appendChild(input3);
	div.appendChild(p3);
	// Button Registration
	var p4 = document.createElement("p");
	var input4 = document.createElement("input");
	input4.setAttribute("class", "button");
	input4.setAttribute("type", "button");
	input4.setAttribute("onclick", "includeRegistration();");
	input4.setAttribute("value","Registration");
	p4.appendChild(input4);
	div.appendChild(p4);
	
	return div;
}

function getRegistrationForm(){
	var div = document.createElement("div");
	div.setAttribute("id", "registration");
	var h1 = document.createElement("h1");
	h1.appendChild(document.createTextNode("Registration"));
	div.appendChild(h1);
	// Username
	var p1 = document.createElement("p");
	var label1 = document.createElement("label");
	label1.setAttribute("for", "username");
	label1.setAttribute("id","label_name");
	label1.appendChild(document.createTextNode("Username : "));
	var input1 = document.createElement("input");
	input1.setAttribute("type", "text");
	input1.setAttribute("name", "username");
	input1.setAttribute("id", "username");
	input1.setAttribute("required","required");
	input1.setAttribute("placeholder","LapinBlanc");
	p1.appendChild(label1);
	p1.appendChild(input1);
	div.appendChild(p1);
	// Name
	var p2 = document.createElement("p");
	var label2 = document.createElement("label");
	label2.setAttribute("for", "name");
	label2.setAttribute("id","label_name");
	label2.appendChild(document.createTextNode("Name : "));
	var input2 = document.createElement("input");
	input2.setAttribute("type", "text");
	input2.setAttribute("name", "name");
	input2.setAttribute("id", "name");
	input2.setAttribute("required","required");
	input2.setAttribute("placeholder","Smith");
	p2.appendChild(label2);
	p2.appendChild(input2);
	div.appendChild(p2);
	// First Name
	var p3 = document.createElement("p");
	var label3 = document.createElement("label");
	label3.setAttribute("for", "firstname");
	label3.setAttribute("id","label_firstname");
	label3.appendChild(document.createTextNode("First Name : "));
	var input3 = document.createElement("input");
	input3.setAttribute("type", "text");
	input3.setAttribute("name", "firstname");
	input3.setAttribute("id", "firstname");
	input3.setAttribute("required","required");
	input3.setAttribute("placeholder","Alice");
	p3.appendChild(label3);
	p3.appendChild(input3);
	div.appendChild(p3);
	// Email
	var p4 = document.createElement("p");
	var label4 = document.createElement("label");
	label4.setAttribute("for", "email");
	label4.setAttribute("id","label_email");
	label4.appendChild(document.createTextNode("Email : "));
	var input4 = document.createElement("input");
	input4.setAttribute("type", "text");
	input4.setAttribute("name", "email");
	input4.setAttribute("id", "email");
	input4.setAttribute("required","required");
	input4.setAttribute("placeholder","Alice@sxp.com");
	p4.appendChild(label4);
	p4.appendChild(input4);
	div.appendChild(p4);
	// Phone number
	var p5 = document.createElement("p");
	var label5 = document.createElement("label");
	label5.setAttribute("for", "phone");
	label5.setAttribute("id","label_phone");
	label5.appendChild(document.createTextNode("Phone number : "));
	var input5 = document.createElement("input");
	input5.setAttribute("type", "text");
	input5.setAttribute("name", "phone");
	input5.setAttribute("id", "phone");
	input5.setAttribute("required","required");
	input5.setAttribute("placeholder","06.05.04.03.02");
	p5.appendChild(label5);
	p5.appendChild(input5);
	div.appendChild(p5);
	// New Line
	div.appendChild(document.createElement("br"));
	// Password
	var p6 = document.createElement("p");
	var label6 = document.createElement("label");
	label6.setAttribute("for", "password");
	label6.setAttribute("id","label_password");
	label6.appendChild(document.createTextNode("Password : "));
	var input6 = document.createElement("input");
	input6.setAttribute("type", "password");
	input6.setAttribute("name", "password");
	input6.setAttribute("id", "password");
	input6.setAttribute("required","required");
	input6.setAttribute("placeholder","ex : p6$Sw0r7!");
	p6.appendChild(label6);
	p6.appendChild(input6);
	div.appendChild(p6);
	// Confirm Password
	var p7 = document.createElement("p");
	var label7 = document.createElement("label");
	label7.setAttribute("for", "confirmPassword");
	label7.setAttribute("id","label_confirmPassword");
	label7.appendChild(document.createTextNode("Confirm Password : "));
	var input7 = document.createElement("input");
	input7.setAttribute("type", "password");
	input7.setAttribute("name", "confirmPassword");
	input7.setAttribute("id", "confirmPassword");
	input7.setAttribute("required","required");
	input7.setAttribute("placeholder","ex : p6$Sw0r7!");
	p7.appendChild(label7);
	p7.appendChild(input7);
	div.appendChild(p7);
	// New Line
	div.appendChild(document.createElement("br"));
	// BUTTON Registration
	var p8 = document.createElement("p");
	var input8 = document.createElement("input");
	input8.setAttribute("class", "button");
	input8.setAttribute("type", "submit");
	input8.setAttribute("onclick", "register();");
	input8.setAttribute("value","Registration");
	p8.appendChild(input8);
	div.appendChild(p8);
	// New Line
	div.appendChild(document.createElement("br"));
	// BUTTON Sign In
	var p9 = document.createElement("p");
	p9.appendChild(document.createTextNode("Already a member ? "));
	var input9 = document.createElement("input");
	input9.setAttribute("class", "button");
	input9.setAttribute("type", "submit");
	input9.setAttribute("onclick", "includeLogin();");
	input9.setAttribute("value","Go and log in");
	p9.appendChild(input9);
	div.appendChild(p9);
	
	return div;
}

function dropMenuOn(){
	$(".drop").show();
}
function dropMenuOff(){
	$(".drop").hide();
}

function getHeader(){
	var div = document.createElement("div");
	div.appendChild(document.createTextNode("Secure eXchange Protocol Manager"));
	var divMenu = document.createElement("div");
	divMenu.setAttribute("class", "dropDownMenu");
	var ul = document.createElement("ul");
	ul.setAttribute("class", "menu");
	ul.setAttribute("onmouseover", "dropMenuOn();");
	ul.setAttribute("onmouseout", "dropMenuOff();");
	
	var li1 = document.createElement("li");
	li1.appendChild(document.createTextNode("Account Setting"));
	ul.appendChild(li1);
	
	var li2 = document.createElement("li");
	li2.setAttribute("class", "drop");
	li2.setAttribute("style", "display:none;")
	var a2 = document.createElement("a");
	a2.appendChild(document.createTextNode("Profile"));
	a2.setAttribute("onclick", "loadAccount();");
	li2.appendChild(a2);
	ul.appendChild(li2);

	var li3 = document.createElement("li");
	li3.setAttribute("class", "drop");
	li3.setAttribute("style", "display:none;")
	var a3 = document.createElement("a");
	a3.appendChild(document.createTextNode("Logout"));
	a3.setAttribute("onclick", "signOut();");
	li3.appendChild(a3);
	ul.appendChild(li3);
	
	divMenu.appendChild(ul);
	div.appendChild(divMenu);
	return div;
}