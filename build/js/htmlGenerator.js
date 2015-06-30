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
	                    {element:"text", value:"My objects"}
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
			]}
		]}
];
/* * * * * * * * * * * * * * * * * * * * * * * HEADER* * * * * * * * * * * * * * * * * * * * * * * * * */
var header = [
		{element:"div", attributes:{}, inside:[
        	{element:"text", value:"Secure eXchange Protocol Manager"}
    	]},
    	{element:"div", attributes:{"class":"dropDownMenu"}, inside:[
			{element:"ul", attributes:{"class":"menu", onmouseover:"dropMenuOn();", onmouseout:"dropMenuOff();"}, inside:[
				{element:"li", attributes:{}, inside:[
					{element:"text", value:"Account Setting"}
				]},
				{element:"li", attributes:{"class":"drop", style:"display:none;"}, inside:[
					{element:"input", attributes:{type:"button", value:"Profile", "class":"headerButton", onclick:"includeAccount();loadAccount();"}, inside:[]}
				]},
				{element:"li", attributes:{"class":"drop", style:"display:none;"}, inside:[
					{element:"input", attributes:{type:"button", value:"Logout", "class":"headerButton", onclick:"signOut();"}, inside:[]}
				]}
			]}
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
 		{element:"p", attributes:{id:"paraImage"}, inside:[
			{element:"label", attributes:{id:"label_image"}, inside:[
				{element:"text", value:"Image : "}
			]},
			{element:"ul", attributes:{"class":"radio"}, inside:[
				{element:"li", attributes:{}, inside:[
					{element:"input", attributes:{type:"radio", name:"typeImage", value:"File", onchange:"typeImageChanged();", checked:"checked"}, inside:[]},
					{element:"text", value:"File"}
				]},
				{element:"li", attributes:{}, inside:[
					{element:"input", attributes:{type:"radio", name:"typeImage", value:"Webcam", onchange:"typeImageChanged();"}, inside:[]},
					{element:"text", value:"Webcam"}
				]},
			]},
			{element:"div", attributes:{id:"fileImageDisplay"}, inside:[
				{element:"input", attributes:{type:"file", id:"fileImage", accept:"image/*", onchange:"previewFile(this);"}, inside:[]},
			]},
			{element:"div", attributes:{id:"webcam", style:"display:none;"}, inside:[
				{element:"input", attributes:{type:"button", value:"Capture"}, inside:[]},
				{element:"img", attributes:{alt:"Webcam"}, inside:[]}
			]},
			{element:"img", attributes:{id:"image", style:"max-width:30%;"}, inside:[]}
		]},
 		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_country"}, inside:[
				{element:"text", value:"Country : "}
			]},
			{element:"input", attributes:{type:"text", id:"country", name:"country", required:"required", placeholder:"FRANCE - Marseille"}, inside:[]},
		]},
 		{element:"p", attributes:{}, inside:[
 			{element:"label", attributes:{id:"label_contact"}, inside:[
 				{element:"text", value:"Contact : "}
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
/* * * * * * * * * * * * * * * * * * * * * MESSAGES FORM * * * * * * * * * * * * * * * * * * * * * * * */
var webmailForm = [
		{element:"div", attributes:{id:"webmail"}, inside:[
			{element:"div", attributes:{id:"webmailMenu"}, inside:[
				{element:"ul", attributes:{}, inside:[
					{element:"li", attributes:{id:"unread", onclick:"newMessage();"}, inside:[
						{element:"a", attributes:{}, inside:[
							{element:"text", value:"Write"}
						]}
					]},
					{element:"li", attributes:{id:"unread", onclick:"loadMessages();"}, inside:[
						{element:"a", attributes:{}, inside:[
							{element:"text", value:"Unreaded"}
						]}
					]},
					{element:"li", attributes:{id:"conversation", onclick:"loadConversation();"}, inside:[
  						{element:"a", attributes:{}, inside:[
  							{element:"text", value:"Archives"}
  						]}
  					]},
				]}
			]},
			{element:"div", attributes:{id:"webmailDisplay"}, inside:[
				{element:"table", attributes:{id:"messagesList"}, inside:[
					{element:"thead", attributes:{}, inside:[
						{element:"tr", attributes:{}, inside:[
							{element:"th", attributes:{"class":"rowDate"}, inside:[
								{element:"text", value:"Date"}
							]},
							{element:"th", attributes:{"class":"rowSubject"}, inside:[
								{element:"text", value:"Subject"}
							]},
							{element:"th", attributes:{"class":"rowFrom"}, inside:[
								{element:"text", value:"From"}
							]},
							{element:"th", attributes:{"class":"rowActions"}, inside:[]}
						]}
					]}
				]},
				{element:"div", attributes:{id:"messageDisplay"}, inside:[
					{element:"text", value:"My super message !!!!"}
				]}
			]}
		]}
];
var writeMessage = [
		{element:"ul", attributes:{"class":"newMessageHeader"}, inside:[
			{element:"li", attributes:{onclick:"sendMessage();"}, inside:[
				{element:"a", attributes:{}, inside:[
					{element:"text", value:"Send"}
				]}
			]},
			{element:"li", attributes:{}, inside:[
				{element:"p", attributes:{}, inside:[
					{element:"label", attributes:{}, inside:[
						{element:"text", value:"Receiver : "}
					]},
					{element:"input", attributes:{}, inside:[]}
				]}
			]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{}, inside:[
				{element:"text", value:"Message : "}
			]},
			{element:"textarea", attributes:{id:"message", name:"message"}, inside:[]}
		]}
];

var itemFavoritesDisplayer = [
		{element:"h1", attributes:{id:"itemKey"}, inside:[]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Title : "}
			]},
			{element:"label", attributes:{id:"title"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Type : "}
			]},
			{element:"label", attributes:{id:"type"}, inside:[]},
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Category : "}
			]},
			{element:"label", attributes:{id:"category"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Image : "}
			]},
			{element:"img", attributes:{id:"image", style:"max-width:20%;"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Description : "}
			]},
			{element:"label", attributes:{id:"description"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Contry : "}
			]},
			{element:"label", attributes:{id:"country"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Contact : "}
			]},
			{element:"label", attributes:{id:"contact"}, inside:[]}
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
	if(json.element == "textarea"){
		element.setAttribute("onkeyup", "textAreaAdjust(this);");
		element.setAttribute("onfocus", "textAreaAdjust(this);");
		element.setAttribute("onscroll", "textAreaAdjust(this);");
	}
	$.each(json.attributes, function(key, value){
		element.setAttribute(key, value);
	});
	for( var i = 0 ; i < json.inside.length ; i++ ){
		element.appendChild(getElement(json.inside[i]));
	}
	return element;
}

function textAreaAdjust(o) {
    o.style.height = "1px";
    o.style.height = (20+o.scrollHeight)+"px";
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

function getItemFavoritesDisplay(){
	var div = document.createElement("div");
	div.setAttribute("id", "itemFavoritesDisplayer");
	for ( var i = 0 ; i < itemFavoritesDisplayer.length ; i++ ) {
		div.appendChild(getElement(itemFavoritesDisplayer[i]));
	}
	return div;
}

function getFavoritesDisplay(){
	var div = document.createElement("div");
	div.appendChild(getTableItem(favoritesList));
	var loading = document.createElement("div");
	loading.setAttribute("id", "loading");
	div.appendChild(loading);
	return div;
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

function getWebmail(){
	var div = document.createElement("div");
	div.setAttribute("id", "content");
	for( var i = 0 ; i < webmailForm.length ; i++ ) {
		div.appendChild(getElement(webmailForm[i]));
	}
	return div;
}

function getHeader(){
	var div = document.createElement("div");
	for ( var i = 0 ; i < header.length ; i++ ) {
		div.appendChild(getElement(header[i]));
	}
	return div;
}

function getMenu(){
	var nav = document.createElement("nav");
	for ( var i = 0 ; i < menu.length ; i++ ) {
		nav.appendChild(getElement(menu[i]));
	}
	return nav;
}

function typeImageChanged(){
	if($("#paraImage input[type=radio]:checked").val()=="File"){
		$("#paraImage input[type=file]").show();
		$("#webcam").hide();
		previewFile();
	}else if($("#paraImage input[type=radio]:checked").val()=="Webcam"){
		$("#paraImage input[type=file]").hide();
		$("#webcam").show();
		$("#paraImage img").attr("src", "");
	}
}