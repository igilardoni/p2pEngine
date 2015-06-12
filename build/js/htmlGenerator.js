/**
 * Method to generate page 
 */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    			VARIABLES											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
var menu = [
		{type:"button",		onclick:"includeHome();",		value:"Home"},
		{type:"button",		onclick:"includeSearch()",		value:"Search object"},
		{type:"button",		onclick:"includeContrat();", 	value:"Contrat object" },
		{type:"button",		onclick:"includeWebmail();", 	value:"Messages" },
		{type:"button",		onclick:"includeFavorites();",	value:"Favorites" }
];
		
var formLogin = [
		{label:"Username : ", attributes:{type:"text", name:"username", id:"username", required:"required", placeholder:"AliceWonderland"}},
		{label:"Password : ", attributes:{type:"password", name:"password", id:"password", required:"required", placeholder:"ex : p4$Sw0r6!"}}
];

var buttonLogin = [
		{"class":"button", type:"submit", onclick:"signIn();", value:"Sign In"},
		{"class":"button", type:"button", onclick:"includeRegistration();", value:"Registration"}
];

var formRegistration = [
		{label:"Username : ", attributes:{type:"text", name:"username", id:"username", required:"required", placeholder:"AliceWonderland"}},
		{label:"Name : ", attributes:{type:"text", name:"name", id:"name", required:"required", placeholder:"Liddel"}},
		{label:"First name : ", attributes:{type:"text", name:"firstname", id:"firstname", required:"required", placeholder:"Alice"}},
		{label:"Email : ", attributes:{type:"text", name:"email", id:"email", required:"required", placeholder:"Alice@wonderland.com"}},
		{label:"Phone : ", attributes:{type:"text", name:"phone", id:"phone", required:"required", placeholder:"+336 05 04 03 02"}},
		{label:"Password : ", attributes:{type:"password", name:"password", id:"password", required:"required", placeholder:"ex : p4$Sw0r6!"}},
		{label:"Confirm password : ", attributes:{type:"password", name:"passwordConfirm", id:"passwordConfirm", required:"required", placeholder:"ex : p4$Sw0r6!"}}
];

var formItemAdd = [
		{label:"Title : ", element:"input", attributes:{type:"text", name:"title", id:"title"}},
		{label:"Type : ", element:"select", attributes:{name:"type", id:"type"}},
		{label:"Category : ", element:"select", attributes:{name:"category", id:"category"}},
		{label:"Description : ", element:"textarea", attributes:{name:"description", id:"description"}},
		{label:"Image : ", elment:"img", attributes:{name:"image", id:"image", src:""}},
		{label:"Contry : ", element:"select", attributes:{name:"country", id:"country"}},
		{label:"Contact : ", element:"textarea", attributes:{name:"contact", id:"contact"}}
];

var buttonRegistration = [
		{"class":"button", type:"submit", onclick:"register();", value:"Registration"},
		{"class":"button", type:"submit", onclick:"includeLogin();", value:"Already registered"}
];

var tableItem = [
		{text:"Unique ID", attributes:{}},
		{text:"Title", attributes:{}},
		{text:"Description", attributes:{}},
		{text:"Actions", attributes:{}},
];
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    			GENERATORS											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function getHome(){
	var content = document.createElement("div");
	content.setAttribute("id", "content");
	var div = document.createElement("div");
	div.setAttribute("id", "items");
	div.appendChild(getTableItem(itemList));
	content.appendChild(div);
	div.appendChild(getItemAddForm());
	content.appendChild(div);
	return content;
}

function getItemFormWithoutButton(){
	var div = document.createElement("div");
	div.setAttribute("id", "itemForm");
	for ( var i = 0 ; i < formItemAdd.length; i++) {
		var p = document.createElement("p");
		var element = document.createElement(formItemAdd[i].element);
		var label = document.createElement("label");
		label.appendChild(document.createTextNode(formItemAdd[i].label))
		label.setAttribute("for", formItemAdd[i].attributes.name);
		label.setAttribute("id", "label_"+formItemAdd[i].attributes.name);
		p.appendChild(label);
		$.each(formItemAdd[i].attributes, function(key, value){
			element.setAttribute(key, value);
		});
		p.appendChild(element);
		div.appendChild(p);
	}
	loadCategories();
	return div;
}

function getItemAddForm(){
	var div = getItemFormWithoutButton();
	// add buttons
	return div;
}

function getLoginForm(){
	var div = document.createElement("div");
	div.setAttribute("id", "login");
	var h1 = document.createElement("h1");
	h1.appendChild(document.createTextNode("Sign In"));
	div.appendChild(h1);
	
	for ( var i = 0 ; i < formLogin.length; i++) {
		var p = document.createElement("p");
		var input = document.createElement("input");
		var label = document.createElement("label");
		label.appendChild(document.createTextNode(formLogin[i].label))
		label.setAttribute("for", formLogin[i].attributes.name);
		label.setAttribute("id", "label_"+formLogin[i].attributes.name);
		p.appendChild(label);
		$.each(formLogin[i].attributes, function(key, value){
			input.setAttribute(key, value);
		});
		p.appendChild(input);
		div.appendChild(p);
	}
	for( var i = 0 ; i < buttonLogin.length; i++){
		var p = document.createElement("p");
		var input = document.createElement("input");
		$.each(buttonLogin[i], function(key, value){
			input.setAttribute(key, value);
		});
		p.appendChild(input);
		div.appendChild(p);
	}
	return div;
}

function getRegistrationForm(){
	var div = document.createElement("div");
	div.setAttribute("id", "registration");
	var h1 = document.createElement("h1");
	h1.appendChild(document.createTextNode("Registration"));
	div.appendChild(h1);
	for ( var i = 0 ; i < formRegistration.length; i++) {
		var p = document.createElement("p");
		var input = document.createElement("input");
		var label = document.createElement("label");
		label.appendChild(document.createTextNode(formRegistration[i].label))
		label.setAttribute("for", formRegistration[i].attributes.name);
		label.setAttribute("id", "label_"+formRegistration[i].attributes.name);
		p.appendChild(label);
		$.each(formRegistration[i].attributes, function(key, value){
			input.setAttribute(key, value);
		});
		p.appendChild(input);
		div.appendChild(p);
	}
	for( var i = 0 ; i < buttonRegistration.length; i++){
		var p = document.createElement("p");
		var input = document.createElement("input");
		$.each(buttonRegistration[i], function(key, value){
			input.setAttribute(key, value);
		});
		p.appendChild(input);
		div.appendChild(p);
	}
	return div;
}

function getUpdateAccountForm(){
	var div = getRegistrationForm();
	var p = document.createElement("p");
	var input = document.createElement("input");
	var label = document.createElement("label");
	label.appendChild(document.createTextNode("Old password : "));
	label.setAttribute("for", "oldpassword");
	label.setAttribute("id", "label_oldpassword");
	input.setAttribute("id", "oldpassword");
	input.setAttribute("type","password");
	input.setAttribute("name","password");
	input.setAttribute("id","password");
	input.setAttribute("required","required");
	input.setAttribute("placeholder","ex : p4$Sw0r6!");
	p.appendChild(label);
	p.appendChild(input);
	$(p).insertAfter($(div).find("#username"));
	var h1 = document.createElement("h1");
	h1.appendChild(document.createTextNode("Account"));
	$(div).find("h1").replaceWith(h1);
	
	return div;
}

function dropMenuOn(){
	$(".drop").show();
}
function dropMenuOff(){
	$(".drop").hide();
}

function getTableItem(id){
	var table = document.createElement("table");
	table.setAttribute("id", id);
	for( var i = 0 ; i < tableItem.length; i++){
		var th = document.createElement("th");
		$.each(tableItem[i].attributes, function(key, value){
			th.setAttribute(key, value);
		});
		th.appendChild(document.createTextNode(tableItem[i].text));
		table.appendChild(th);
	}
	return table;
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
	a2.setAttribute("href", "#");
	a2.setAttribute("onclick", "loadAccount();");
	li2.appendChild(a2);
	ul.appendChild(li2);

	var li3 = document.createElement("li");
	li3.setAttribute("class", "drop");
	li3.setAttribute("style", "display:none;")
	var a3 = document.createElement("a");
	a3.appendChild(document.createTextNode("Logout"));
	a3.setAttribute("href", "#");
	a3.setAttribute("onclick", "signOut();");
	li3.appendChild(a3);
	ul.appendChild(li3);
	
	divMenu.appendChild(ul);
	div.appendChild(divMenu);
	return div;
}

function getMenu(){
	var ul = document.createElement("ul");
	for ( var i = 0 ; i < menu.length; i++) {
		var li = document.createElement("li");
		var input = document.createElement("input");
		$.each(menu[i], function(key, value){
			input.setAttribute(key, value);
		});
		li.appendChild(input);
		ul.appendChild(li);
	}
	return ul;
}