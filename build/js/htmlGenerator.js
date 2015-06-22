/**
 * Method to generate page
 * Contains architecture pages as JSON object
 * @author Michael DUBUIS
 */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    			VARIABLES											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
var menu = [
		{text:"Home",			attributes:{onclick:"includeHome();loadItems();","class":"homeButton"}},
		{text:"Search object",	attributes:{onclick:"includeSearch();",			"class":"searchButton"}},
		{text:"Contrat object", attributes:{onclick:"includeContrat();", 		"class":"contratButton"}},
		{text:"Messages",		attributes:{onclick:"includeWebmail();", 		"class":"messageButton"}},
		{text:"Favorites", 		attributes:{onclick:"switchFavorites();",		"class":"favoritesButton"}}
];
	// Login
var formLogin = [
		{label:"Username : ", attributes:{type:"text", name:"username", id:"username", required:"required", placeholder:"AliceWonderland"}},
		{label:"Password : ", attributes:{type:"password", name:"password", id:"password", required:"required", placeholder:"ex : p4$Sw0r6!"}}
];
var buttonLogin = [
		{"class":"button", id:"loginButton", type:"submit", onclick:"signIn();", value:"Sign In"},
		{"class":"button", id:"registrationButton", type:"button", onclick:"includeRegistration();", value:"Registration"}
];
	// Registration
var formRegistration = [
		{label:"Username : ", attributes:{type:"text", name:"username", id:"username", required:"required", placeholder:"AliceWonderland"}},
		{label:"Name : ", attributes:{type:"text", name:"name", id:"name", required:"required", placeholder:"Liddel"}},
		{label:"First name : ", attributes:{type:"text", name:"firstname", id:"firstname", required:"required", placeholder:"Alice"}},
		{label:"Email : ", attributes:{type:"text", name:"email", id:"email", required:"required", placeholder:"Alice@wonderland.com"}},
		{label:"Phone : ", attributes:{type:"text", name:"phone", id:"phone", required:"required", placeholder:"+336 05 04 03 02"}},
		{label:"Password : ", attributes:{type:"password", name:"password", id:"password", required:"required", placeholder:"ex : p4$Sw0r6!"}},
		{label:"Confirm password : ", attributes:{type:"password", name:"passwordConfirm", id:"passwordConfirm", required:"required", placeholder:"ex : p4$Sw0r6!"}}
];
var buttonRegistration = [
		{"class":"button", id:"registrationButton", type:"submit", onclick:"register();", value:"Registration"},
		{"class":"button", id:"loginButton", type:"button", onclick:"includeLogin();", value:"Go and log in"}
];
var buttonUpdateAccount = [
		{"class":"button", type:"submit", onclick:"updateAccount();", value:"Update Account"},
		{"class":"button", type:"button", onclick:"includeHome();", value:"Cancel"}
];
	// Account Update
var formUpdateAccount = [
		{label:"Username : ", attributes:{type:"text", name:"username", id:"username", required:"required", placeholder:"AliceWonderland"}},
		{label:"Old Password : ", attributes:{type:"password", name:"oldpassword", id:"oldpassword", required:"required", placeholder:"ex : p4$Sw0r6!"}},
		{label:"Name : ", attributes:{type:"text", name:"name", id:"name", required:"required", placeholder:"Liddel"}},
		{label:"First name : ", attributes:{type:"text", name:"firstname", id:"firstname", required:"required", placeholder:"Alice"}},
		{label:"Email : ", attributes:{type:"text", name:"email", id:"email", required:"required", placeholder:"Alice@wonderland.com"}},
		{label:"Phone : ", attributes:{type:"text", name:"phone", id:"phone", required:"required", placeholder:"+336 05 04 03 02"}},
		{label:"Password : ", attributes:{type:"password", name:"password", id:"password", required:"required", placeholder:"ex : p4$Sw0r6!"}},
		{label:"Confirm password : ", attributes:{type:"password", name:"passwordConfirm", id:"passwordConfirm", required:"required", placeholder:"ex : p4$Sw0r6!"}}
];
	// Item add
var formItemAdd = [
		{label:"Title : ", element:"input", attributes:{type:"text", name:"title", id:"title"}},
		{label:"Type : ", element:"select", attributes:{name:"type", id:"type"}},
		{label:"Life Time :", element:"input", attributes:{type:"text", name:"lifetime", id:"lifetime"}},
		{label:"Category : ", element:"select", attributes:{name:"category", id:"category"}},
		{label:"Description : ", element:"textarea", attributes:{name:"description", id:"description"}},
		{label:"Image : ", elment:"img", attributes:{name:"image", id:"image", src:""}},
		{label:"Contry : ", element:"input", attributes:{type:"text", name:"country", id:"country"}},
		{label:"Contact : ", element:"textarea", attributes:{name:"contact", id:"contact"}}
];
var buttonItemAdd = [
		{"class":"button", type:"submit", onclick:"addItem();", value:"Add", id:"addButton"},
		{"class":"button", type:"button", onclick:"cancelItem();", value:"Cancel", id:"cancelButton"}
];
	// Item Display (for search list, items list, ...)
var tableItem = [
		{text:"Title", attributes:{"class":"rowTitle"}},
		{text:"Description", attributes:{"class":"rowDescription"}},
		{text:"Actions", attributes:{"class":"rowActions"}}
];
	// Item Search
var formSearchItem = [
		{label:"Search : ", element:"input", attributes:{type:"text", name:"searchField", id:"searchField"}},
		{label:"Field to search : ", element:"select", attributes:{name:"field", id:"field", onchange:"updateSearchField();"}}
];
var buttonSearchItem = [
		{"class":"button", type:"button", onclick:"searchItem();", value:"Search"}
];
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    		PAGE GENERATORS											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function getHome(){
	var content = document.createElement("section");
	content.setAttribute("id", "content");
	var div = document.createElement("div");
	div.setAttribute("id", "items");
	div.appendChild(getTableItem(itemList));
	content.appendChild(div);
	div.appendChild(getItemAddForm());
	content.appendChild(div);
	return content;
}

function getSearchItem(){
	var content = document.createElement("section");
	content.setAttribute("id", "content");
	content.appendChild(getItemSearchForm());
	content.appendChild(getTableItem(itemSearchList));
	return content;
} 

function getFavoritesDisplay(){
	return getTableItem(favoritesList);
}

function switchFavorites(){
	if($("aside").text().length == 0)
		includeFavorites();
	else
		removeFavorites();
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
	var div = document.createElement("div");
	div.setAttribute("id", "accountUpdate");
	var h1 = document.createElement("h1");
	h1.appendChild(document.createTextNode("Account"));
	div.appendChild(h1);
	for ( var i = 0 ; i < formUpdateAccount.length; i++) {
		var p = document.createElement("p");
		var input = document.createElement("input");
		var label = document.createElement("label");
		label.appendChild(document.createTextNode(formUpdateAccount[i].label))
		label.setAttribute("for", formUpdateAccount[i].attributes.name);
		label.setAttribute("id", "label_"+formUpdateAccount[i].attributes.name);
		p.appendChild(label);
		$.each(formUpdateAccount[i].attributes, function(key, value){
			input.setAttribute(key, value);
		});
		p.appendChild(input);
		div.appendChild(p);
	}
	for( var i = 0 ; i < buttonUpdateAccount.length; i++){
		var p = document.createElement("p");
		var input = document.createElement("input");
		$.each(buttonUpdateAccount[i], function(key, value){
			input.setAttribute(key, value);
		});
		p.appendChild(input);
		div.appendChild(p);
	}
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
	var input1 = document.createElement("input");
	input1.setAttribute("type", "button");
	input1.setAttribute("value", "Profile");
	input1.setAttribute("class", "headerButton");
	input1.setAttribute("onclick", "includeAccount();loadAccount();");
	li2.appendChild(input1);
	ul.appendChild(li2);

	var li3 = document.createElement("li");
	li3.setAttribute("class", "drop");
	li3.setAttribute("style", "display:none;")
	var input2 = document.createElement("input");
	input2.setAttribute("type", "button");
	input2.setAttribute("value", "Logout");
	input2.setAttribute("class", "headerButton");
	input2.setAttribute("onclick", "signOut();");
	li3.appendChild(input2);
	ul.appendChild(li3);
	
	divMenu.appendChild(ul);
	div.appendChild(divMenu);
	return div;
}

function getMenu(){
	var ul = document.createElement("ul");
	for ( var i = 0 ; i < menu.length; i++) {
		var li = document.createElement("li");
		var a = document.createElement("a");
		$.each(menu[i].attributes, function(key, value){
			li.setAttribute(key, value);
		});
		a.appendChild(document.createTextNode(menu[i].text));
		li.appendChild(a);
		ul.appendChild(li);
	}
	return ul;
}