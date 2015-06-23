/**
 * Method to generate page
 * Contains architecture pages as JSON object
 * @author Michael DUBUIS
 */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * VARIABLES * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


var tableItem = [
		{text:"Title", attributes:{"class":"rowTitle"}},
		{text:"Description", attributes:{"class":"rowDescription"}},
		{text:"", attributes:{"class":"rowActions"}}
];
var emptyForm = [
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_"}, inside:[
				{element:"text", value:""}
			]},
			{element:"input", attributes:{type:"", id:"", name:"", required:"required", placeholder:""}, inside:[]},
		]}
];
/* * * * * * * * * * * * * * * * * * * * * * *  MENU * * * * * * * * * * * * * * * * * * * * * * * * * */
var menu = [
    		{element:"ul", attributes:{}, inside:[
     			{element:"li", attributes:{"class":"homeButton", onclick:"includeHome();loadItems();"}, inside:[
                    	{element:"a", attributes:{}, inside:[
                            {element:"text", value:"Home"}
                        ]}
     			]},
    			{element:"li", attributes:{"class":"searchButton", onclick:"includeSearch();"}, inside:[
    				{element:"a", attributes:{}, inside:[
    					{element:"text", value:"Search Object"}
                	]}
    			]},
    			{element:"li", attributes:{"class":"contratButton", onclick:"includeContrat();"}, inside:[
    				{element:"a", attributes:{}, inside:[
    					{element:"text", value:"Contrats"}
    	        	]}
    			]},
    			{element:"li", attributes:{"class":"messageButton", onclick:"includeWebmail();"}, inside:[
    				{element:"a", attributes:{}, inside:[
    					{element:"text", value:"Messages"}
    	        	]}
    			]},
    			{element:"li", attributes:{"class":"favoritesButton", onclick:"switchFavorites();"}, inside:[
    				{element:"a", attributes:{}, inside:[
    					{element:"text", value:"Favorites"}
    	        	]}
    			]},
     		]}
    ];
/* * * * * * * * * * * * * * * * * * * * * * LOGIN FORM* * * * * * * * * * * * * * * * * * * * * * * * */
var loginForm = [
		{element:"h1", attributes:{}, inside:[
			{element:"text", value:"Sign In"}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_username"}, inside:[
				{element:"text", value:"Username : "}
			]},
			{element:"input", attributes:{type:"text", id:"username", name:"username", required:"required", placeholder:"AliceWonderland"}, inside:[]},
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_password"}, inside:[
				{element:"text", value:"Password : "}
			]},
			{element:"input", attributes:{type:"password", id:"password", name:"password", required:"required", placeholder:"ex : p4$Sw0r6!"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"input", attributes:{type:"button", id:"loginButton", onclick:"signIn();", value:"Sign In"}, inside:[
				/*{element:"text", value:"Sign In"}*/
			]}
		]},
		{element:"p", attributes:{}, inside:[
 			{element:"input", attributes:{type:"button", id:"registrationButton", onclick:"includeRegistration();", value:"Registration"}, inside:[
 				/*{element:"text", value:"Sign In"}*/
 			]}
 		]}
];
/* * * * * * * * * * * * * * * * * * * * REGISTRATION FORM * * * * * * * * * * * * * * * * * * * * * * */
var registrationForm = [
	{element:"h1", attributes:{}, inside:[
		{element:"text", value:"Registration"}
	]},
	{element:"p", attributes:{}, inside:[
		{element:"label", attributes:{id:"label_username"}, inside:[
			{element:"text", value:"Username : "}
		]},
		{element:"input", attributes:{type:"text", id:"username", name:"username", required:"required", placeholder:"AliceWonderland"}, inside:[]},
	]},
	{element:"p", attributes:{}, inside:[
		{element:"label", attributes:{id:"label_name"}, inside:[
			{element:"text", value:"Name : "}
		]},
		{element:"input", attributes:{type:"text", name:"name", id:"name", required:"required", placeholder:"Liddel"}, inside:[]}
	]},
	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_firstname"}, inside:[
 			{element:"text", value:"First Name : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"firstname", id:"firstname", required:"required", placeholder:"Alice"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_email"}, inside:[
 			{element:"text", value:"Email : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"email", id:"email", required:"required", placeholder:"Alice@wonderland.com"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_phone"}, inside:[
 			{element:"text", value:"Phone : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"phone", id:"phone", required:"required", placeholder:"+336 05 04 03 02"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_password"}, inside:[
 			{element:"text", value:"Password : "}
 		]},
 		{element:"input", attributes:{type:"password", name:"password", id:"password", required:"required", placeholder:"ex : p4$Sw0r6!"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_passwordConfirm"}, inside:[
 			{element:"text", value:"Confirm password : "}
 		]},
 		{element:"input", attributes:{type:"password", name:"passwordConfirm", id:"passwordConfirm", required:"required", placeholder:"ex : p4$Sw0r6!"}, inside:[]}
 	]},
	{element:"p", attributes:{}, inside:[
		{element:"input", attributes:{type:"button", id:"registrationButton", onclick:"register();", value:"Registration"}, inside:[
			/*{element:"text", value:"Sign In"}*/
		]}
	]},
	{element:"p", attributes:{}, inside:[
		{element:"input", attributes:{type:"button", id:"loginButton", onclick:"includeLogin();", value:"Go and log in"}, inside:[
			/*{element:"text", value:"Sign In"}*/
		]}
	]}
];
/* * * * * * * * * * * * * * * * * * * * ACCOUNT UPDATE FORM * * * * * * * * * * * * * * * * * * * * * */
var updateAccountForm = [
	{element:"h1", attributes:{}, inside:[
		{element:"text", value:"Account"}
	]},
	{element:"p", attributes:{}, inside:[
		{element:"label", attributes:{id:"label_username"}, inside:[
			{element:"text", value:"Username : "}
		]},
		{element:"input", attributes:{type:"text", id:"username", name:"username", required:"required", placeholder:"AliceWonderland"}, inside:[]},
	]},
	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_oldpassword"}, inside:[
 			{element:"text", value:"Old password : "}
 		]},
 		{element:"input", attributes:{type:"password", id:"oldpassword", name:"oldpassword", required:"required", placeholder:"ex : p4$Sw0r6!"}, inside:[]},
 	]},
	{element:"p", attributes:{}, inside:[
		{element:"label", attributes:{id:"label_name"}, inside:[
			{element:"text", value:"Name : "}
		]},
		{element:"input", attributes:{type:"text", name:"name", id:"name", required:"required", placeholder:"Liddel"}, inside:[]}
	]},
	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_firstname"}, inside:[
 			{element:"text", value:"First Name : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"firstname", id:"firstname", required:"required", placeholder:"Alice"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_email"}, inside:[
 			{element:"text", value:"Email : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"email", id:"email", required:"required", placeholder:"Alice@wonderland.com"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_phone"}, inside:[
 			{element:"text", value:"Phone : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"phone", id:"phone", required:"required", placeholder:"+336 05 04 03 02"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_password"}, inside:[
 			{element:"text", value:"Password : "}
 		]},
 		{element:"input", attributes:{type:"password", name:"password", id:"password", required:"required", placeholder:"ex : p4$Sw0r6!"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_passwordConfirm"}, inside:[
 			{element:"text", value:"Confirm password : "}
 		]},
 		{element:"input", attributes:{type:"password", name:"passwordConfirm", id:"passwordConfirm", required:"required", placeholder:"ex : p4$Sw0r6!"}, inside:[]}
 	]},
	{element:"p", attributes:{}, inside:[
		{element:"input", attributes:{type:"submit", onclick:"updateAccount();", value:"Update Account"}, inside:[
			/*{element:"text", value:"Sign In"}*/
		]}
	]},
	{element:"p", attributes:{}, inside:[
		{element:"input", attributes:{type:"button", onclick:"includeHome();", value:"Cancel"}, inside:[
			/*{element:"text", value:"Sign In"}*/
		]}
	]}
];
/* * * * * * * * * * * * * * * * * * * * * ITEM FORM * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemAddForm = [
		{element:"h1", attributes:{}, inside:[
  			{element:"text", value:"Item"}
  		]},
  		{element:"p", attributes:{}, inside:[
  			{element:"label", attributes:{id:"label_title"}, inside:[
  				{element:"text", value:"Title  : "}
  			]},
  			{element:"input", attributes:{type:"text", id:"title", name:"title", required:"required", placeholder:"Potatoes"}, inside:[]},
  		]},
  		{element:"p", attributes:{}, inside:[
  			{element:"label", attributes:{id:"label_type"}, inside:[
  				{element:"text", value:"Type : "}
  			]},
  			{element:"select", attributes:{id:"type", name:"type", required:"required", value:"PROPOSAL"}, inside:[]},
  		]},
  		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_lifetime"}, inside:[
				{element:"text", value:"Life time : "}
			]},
			{element:"input", attributes:{type:"text", id:"lifetime", name:"lifetime", required:"required", placeholder:"2015-12-25"}, inside:[]},
		]},
  		{element:"p", attributes:{}, inside:[
 			{element:"label", attributes:{id:"label_category"}, inside:[
 				{element:"text", value:"Category : "}
 			]},
 			{element:"select", attributes:{id:"category", name:"category", required:"required", value:"NC"}, inside:[]},
 		]},
  		{element:"p", attributes:{}, inside:[
 			{element:"label", attributes:{id:"label_description"}, inside:[
 				{element:"text", value:"Description : "}
 			]},
 			{element:"textarea", attributes:{id:"description", name:"description", required:"required", placeholder:"My potatoes are great !"}, inside:[]},
 		]},
  		{element:"p", attributes:{}, inside:[
 			{element:"label", attributes:{id:"label_image"}, inside:[
 				{element:"text", value:"Image : "}
 			]},
 			{element:"img", attributes:{id:"image", name:"image"}, inside:[]},
 		]},
  		{element:"p", attributes:{}, inside:[
 			{element:"label", attributes:{id:"label_country"}, inside:[
 				{element:"text", value:"Country : "}
 			]},
 			{element:"input", attributes:{type:"text", id:"country", name:"country", required:"required", placeholder:"FRANCE - Marseille"}, inside:[]},
 		]},
  		{element:"p", attributes:{}, inside:[
  			{element:"label", attributes:{id:"label_contact"}, inside:[
  				{element:"text", value:""}
  			]},
  			{element:"textarea", attributes:{id:"contact", name:"contact", required:"required", placeholder:"Please call me : +336 05 04 03 02"}, inside:[]},
  		]},
  		{element:"p", attributes:{}, inside:[
       		{element:"a", attributes:{onclick:"addItem();", id:"addButton"}, inside:[
       			{element:"text", value:"Add item"}
       		]},
       		{element:"a", attributes:{onclick:"cancelItem();", id:"cancelButton"}, inside:[
	  			{element:"text", value:"Cancel"}
	  		]}
       	]}
];
/* * * * * * * * * * * * * * * * * * * * * SEARCH FORM * * * * * * * * * * * * * * * * * * * * * * * * */
var searchForm = [             
		{element:"h1", attributes:{}, inside:[
			{element:"text", value:"Search"}
		]},
		{element:"label", attributes:{}, inside:[
		     {element:"text", value:"Search : "}
		]},
		{element:"input", attributes:{type:"text", name:"search", id:"search"}, inside:[]},
        {element:"p", attributes:{}, inside:[
			{element:"label", attributes:{}, inside:[
				{element:"text", value:"Helper : "}
			]},
			{element:"select", attributes:{name:"field", id:"field", onchange:"updateSearchField();"}, inside:[]},
			{element:"input", attributes:{type:"text", name:"searchField", id:"searchField"}, inside:[]},
			{element:"a", attributes:{onclick:"addSearch();", id:"addFieldButton"}, inside:[
				{element:"text", value:"+"}
			]},
		]},
  		{element:"p", attributes:{"class":"buttonsContainer"}, inside:[
      		{element:"a", attributes:{onclick:"searchItem();", id:"searchButton"}, inside:[
      			{element:"text", value:"Seach"}
      		]},
      		{element:"a", attributes:{onclick:"resetSearch();", id:"resetButton"}, inside:[
				{element:"text", value:"Reset"}
			]}
      	]}
];
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * PAGE GENERATORS * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function getElement(json) {
	if(json.element=="text"){
		var text = document.createTextNode(json.value);
		return text;
	}
	var element = document.createElement(json.element);
	$.each(json.attributes, function(key, value){
		element.setAttribute(key, value);
	});
	for( var i = 0 ; i < json.inside.length ; i++ ){
		element.appendChild(getElement(json.inside[i]));
	}
	return element;
}

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
	/*var content = document.createElement("section");
	content.setAttribute("id", "content");
	content.appendChild(getItemSearchForm());
	return content;*/
	var content = document.createElement("div");
	content.setAttribute("id", "content");
	var div = document.createElement("div");
	div.setAttribute("id", "searchForm");
	for ( var i = 0 ; i < searchForm.length ; i++ ) {
		div.appendChild(getElement(searchForm[i]));
	}
	content.appendChild(div);
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
	for ( var i = 0 ; i < loginForm.length ; i++ ) {
		div.appendChild(getElement(loginForm[i]));
	}
	return div;
}

function getRegistrationForm(){
	var div = document.createElement("div");
	div.setAttribute("id", "registration");
	for ( var i = 0 ; i < registrationForm.length ; i++ ) {
		div.appendChild(getElement(registrationForm[i]));
	}
	return div;
}

function getUpdateAccountForm(){
	var div = document.createElement("div");
	div.setAttribute("id", "accountUpdate");
	for ( var i = 0 ; i < updateAccountForm.length ; i++ ) {
		div.appendChild(getElement(updateAccountForm[i]));
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
	var nav = document.createElement("nav");
	for ( var i = 0 ; i < menu.length ; i++ ) {
		nav.appendChild(getElement(menu[i]));
	}
	return nav;
}