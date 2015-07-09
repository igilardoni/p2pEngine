/**
 * Method to generate page
 * Contains architecture pages as JSON object
 * @author Michael DUBUIS
 */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * VARIABLES * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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
			{element:"li", attributes:{"class":"homeButton", onclick:"includeHome();"}, inside:[
	            	{element:"a", attributes:{}, inside:[
	                    //{element:"text", value:"My objects"}
	                ]}
			]},
			{element:"li", attributes:{"class":"searchButton", onclick:"includeSearch();"}, inside:[
				{element:"a", attributes:{}, inside:[
					//{element:"text", value:"Search Object"}
	        	]}
			]},
			{element:"li", attributes:{"class":"contratButton", onclick:"includeContrat();"}, inside:[
				{element:"a", attributes:{}, inside:[
					//{element:"text", value:"Contrats"}
	        	]}
			]},
			{element:"li", attributes:{"class":"messageButton", onclick:"includeWebmail();"}, inside:[
				{element:"a", attributes:{}, inside:[
					//{element:"text", value:"Messages"}
	        	]}
			]},
			{element:"li", attributes:{"class":"favoritesButton", onclick:"switchFavorites();"}, inside:[
				{element:"a", attributes:{}, inside:[
					//{element:"text", value:"Favorites"}
	        	]}
			]}
		]}
];
/* * * * * * * * * * * * * * * * * * * * * * * HEADER* * * * * * * * * * * * * * * * * * * * * * * * * */
var header = [
		{element:"div", attributes:{}, inside:[
			{element:"img", attributes:{src:"./img/sxpLogo.png"}, inside:[]}
		]},
    	{element:"div", attributes:{"class":"dropDownMenu"}, inside:[
			{element:"ul", attributes:{"class":"menu"}, inside:[
				{element:"li", attributes:{"class":"droper"}, inside:[
					{element:"text", value:'⚙'}
				]},
				{element:"li", attributes:{"class":"drop"}, inside:[
					{element:"input", attributes:{type:"button", value:"Bootstrap", "class":"headerButton", onclick:"includeBoostrapInvitation();"}, inside:[]}
				]},
				{element:"li", attributes:{"class":"drop"}, inside:[
					{element:"input", attributes:{type:"button", value:"Profile", "class":"headerButton", onclick:"includeAccount();loadAccount();"}, inside:[]}
				]},
				{element:"li", attributes:{"class":"drop"}, inside:[
					{element:"input", attributes:{type:"button", value:"Logout", "class":"headerButton", onclick:"signOut();"}, inside:[]}
				]}
			]}
		]}
];
/* * * * * * * * * * * * * * * * * * * * * * * BOOSTRAP* * * * * * * * * * * * * * * * * * * * * * * * */
var boostrapInvitation = [
		{element:"div", attributes:{id:"bootstrapInvitation"}, inside:[]}
];
var boostrapSetting = [
		{element:"div", attributes:{id:"boostrapPreference"}, inside:[]}
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
		{element:"a", attributes:{id:"itemFormCompletButton", onclick:"itemFormComplet();", value:"Create new object"}, inside:[
			{element:"text", value:"Create new Object"}
		]},
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
			{element:"input", attributes:{type:"text", id:"lifetime", name:"lifetime", required:"required", placeholder:"2015-12-25", pattern:"[0-9]{4}\-[0-9]{2}\-[0-9]{2}"}, inside:[]},
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
			{element:"div", attributes:{id:"fileDiv"}, inside:[
				{element:"input", attributes:{type:"file", id:"fileImage", accept:"image/*", onchange:"previewFile(this);"}, inside:[]},
			]},
			{element:"div", attributes:{id:"webcamDiv", style:"display:none;"}, inside:[
				{element:"div", attributes:{"class":"camera", onload:"startup();"}, inside:[
					{element:"video", attributes:{}, inside:[
						{element:"text", value:"Video stream not available."}
					]},
					{element:"button", attributes:{id:"startbutton"}, inside:[
						{element:"text", value:"Take photo"}
					]}
				]}
			]},
			{element:"img", attributes:{id:"image", style:"max-width:30%;"}, inside:[]}
		]},
 		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_country"}, inside:[
				{element:"text", value:"Location : "}
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
/* * * * * * * * * * * * * * * * * * * * * ITEMS TABLE * * * * * * * * * * * * * * * * * * * * * * * * */
var itemTable = [
		{element:"table", attributes:{}, inside:[
			{element:"thead", attributes:{}, inside:[
				{element:"tr", attributes:{}, inside:[
					{element:"th", attributes:{"class":"rowTitle"}, inside:[
						{element:"text", value:"Title"}
					]},
					{element:"th", attributes:{"class":"rowDescription"}, inside:[
						{element:"text", value:"Description"}
					]},
					{element:"th", attributes:{"class":"rowActions"}, inside:[
						{element:"text", value:""}
					]},
				]}
			]},
			{element:"tbody", attributes:{}, inside:[]},
			{element:"tfoot", attributes:{}, inside:[
				{element:"tr", attributes:{}, inside:[
					{element:"td", attributes:{colspan:"3"}, inside:[
						{element:"text", value:"EMPTY"}
					]}
				]}
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
							{element:"text", value:"Unread"}
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
					]},
					{element:"tbody", attributes:{}, inside:[]},
					{element:"tfoot", attributes:{}, inside:[
						{element:"tr", attributes:{}, inside:[
							{element:"td", attributes:{colspan:"4"}, inside:[
          						{element:"text", value:"EMPTY"}
      						]}
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
			{element:"li", attributes:{}, inside:[
				{element:"p", attributes:{}, inside:[
					{element:"label", attributes:{}, inside:[
						{element:"text", value:"Subject "}
					]},
					{element:"input", attributes:{type:"text", id:"subject", name:"subject"}, inside:[]}
				]}
			]},
			{element:"li", attributes:{}, inside:[
				{element:"p", attributes:{}, inside:[
					{element:"label", attributes:{}, inside:[
						{element:"text", value:"Receiver "}
					]},
					{element:"input", attributes:{type:"text", id:"receiver", name:"receiver"}, inside:[]},
					{element:"select", attributes:{id:"typeReceiver", name:"typeReceiver"}, inside:[
						{element:"option", attributes:{value:"PublicKey"}, inside:[
							{element:"text", value:"PublicKey"}
						]},
						{element:"option", attributes:{value:"Username"}, inside:[
  							{element:"text", value:"Username"}
						]},
					]}
				]}
			]}
		]},
		{element:"p", attributes:{"class":"messageContent"}, inside:[
			{element:"label", attributes:{}, inside:[
				{element:"text", value:"Message : "}
			]},
			{element:"textarea", attributes:{id:"message", name:"message"}, inside:[]}
		]},
		{element:"p", attributes:{"class":"newMessageFooter"}, inside:[
			{element:"a", attributes:{onclick:"sendMessage();", id:"sendButton"}, inside:[
				{element:"text", value:"Send"}
			]},
			{element:"label", attributes:{id:"webmailErrorBox"}, inside:[]}
		]}
];
/* * * * * * * * * * * * * * * * * * * * * FAVORITES * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemFavoritesDisplayer = [
		{element:"h1", attributes:{}, inside:[
			{element:"text", value:"Favorite item"}
		]},
 		{element:"label", attributes:{id:"itemKey", "class":"hidden"}, inside:[]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Title : "}
			]},
			{element:"label", attributes:{id:"title", "class":"content"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
 			{element:"label", attributes:{"class":"label"}, inside:[
 				{element:"text", value:"Owner : "}
 			]},
 			{element:"label", attributes:{id:"friendlyNick", "class":"content"}, inside:[]}
 		]},
 		{element:"p", attributes:{style:"display:none;"}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[]},
			{element:"label", attributes:{id:"owner", "class":"content"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Type : "}
			]},
			{element:"label", attributes:{id:"type", "class":"content"}, inside:[]},
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Category : "}
			]},
			{element:"label", attributes:{id:"category", "class":"content"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Image : "}
			]},
			{element:"img", attributes:{id:"image", "class":"content", style:"max-width:20%;"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Description : "}
			]},
			{element:"label", attributes:{id:"description", "class":"content"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Location : "}
			]},
			{element:"label", attributes:{id:"country", "class":"content"}, inside:[]}
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"Contact : "}
			]},
			{element:"label", attributes:{id:"contact", "class":"content"}, inside:[]}
		]},
		{element:"p", attributes:{style:"text-align:center;"}, inside:[
			{element:"input", attributes:{type:"button", value:"Send Message", onclick:"sendMessageTo();"}, inside:[]},
			{element:"input", attributes:{type:"button", value:"Add to contrat", onclick:"loadItemForContrat();"}, inside:[]}
		]}
];
/* * * * * * * * * * * * * * * * * * * * * CONTRATS* * * * * * * * * * * * * * * * * * * * * * * * * * */
var contratDisplay = [
		{element:"h1", attributes:{}, inside:[
			{element:"text", value:"Contrats"}
		]},
		{element:"div", attributes:{}, inside:[
			{element:"input", attributes:{type:"text", id:"titleNewContrat", name:"titleNewContrat", required:"required"}, inside:[]},
			{element:"input", attributes:{type:"button", onclick:"newContrat();", value:"New Contrat"}, inside:[]}
		]}
];
var contratTable = [
		{element:"table", attributes:{id:contratList}, inside:[
			{element:"thead", attributes:{}, inside:[
				{element:"tr", attributes:{}, inside:[
					{element:"th", attributes:{"class":"rowTitle"}, inside:[
						{element:"text", value:"Title"}
					]},
					{element:"th", attributes:{"class":"rowState"}, inside:[
  						{element:"text", value:"State"}
  					]},
  					{element:"th", attributes:{"class":"rowActions"}, inside:[]}
				]}
			]},
			{element:"tbody", attributes:{}, inside:[]},
			{element:"tfoot", attributes:{}, inside:[]}
		]}
];
var contratForm =[
		{element:"div", attributes:{id:"contratForm"}, inside:[
			{element:"h1", attributes:{}, inside:[
				{element:"text", value:"Contrat"}
			]},
			{element:"label", attributes:{"class":"hidden", id:"contratID"}, inside:[]},
			{element:"div", attributes:{id:"objects"}, inside:[]},
			{element:"div", attributes:{}, inside:[
				{element:"table", attributes:{id:"signatories"}, inside:[
					{element:"thead", attributes:{}, inside:[
						{element:"tr", attributes:{}, inside:[
							{element:"th", attributes:{}, inside:[
								{element:"text", value:"friendlyNick"}
							]},
							{element:"th", attributes:{style:"display:none;"}, inside:[
  								{element:"text", value:"publicKey"}
  							]},
						]}
					]},
					{element:"tbody", attributes:{}, inside:[]},
					{element:"tfoot", attributes:{}, inside:[]}
				]}
			]},
			{element:"div", attributes:{id:"rules"}, inside:[
				{element:"table", attributes:{}, inside:[
					{element:"thead", attributes:{}, inside:[
						{element:"tr", attributes:{}, inside:[
							{element:"th", attributes:{"class":"rowItem"}, inside:[
								{element:"text", value:"Item"}
							]},
							{element:"th", attributes:{"class":"rowFrom"}, inside:[
  								{element:"text", value:"From"}
  							]},
  							{element:"th", attributes:{"class":"rowTo"}, inside:[
								{element:"text", value:"To"}
							]},
						]}
					]},
					{element:"tbody", attributes:{}, inside:[]},
					{element:"tfoot", attributes:{}, inside:[
         				{element:"tr", attributes:{}, inside:[
         					{element:"td", attributes:{colspan:"3"}, inside:[
         						{element:"text", value:"EMPTY"}
         					]}
         				]}
         			]},
					{element:"tbutton", attributes:{}, inside:[
						{element:"tr", attributes:{}, inside:[
							{element:"td", attributes:{colspan:"3"}, inside:[
								{element:"input", attributes:{type:"button", onclick:"addRules();", value:"Add exchange rule"}, inside:[]}
							]}
						]}
					]}
				]}
			]},
			{element:"div", attributes:{id:"clauses"}, inside:[
				{element:"input", attributes:{type:"button", value:"+", onclick:"addClauses();"}, inside:[]}
			]},
			{element:"div", attributes:{id:"actions"}, inside:[
				{element:"input", attributes:{type:"submit", value:"Lauch Signature Protocol", onclick:"signContrat();"}, inside:[]},
				{element:"input", attributes:{type:"button", value:"Save as draft", onclick:"saveDraftContrat();"}, inside:[]},
				{element:"input", attributes:{type:"button", value:"Reset", onclick:"resetContrat();"}, inside:[]}
			]},
		]}
];
var ruleForm = [
		{element:"tr", attributes:{}, inside:[
			{element:"td", attributes:{"class":"item"}, inside:[
				{element:"label", attributes:{"class":"item"}, inside:[]}
			]},
			{element:"td", attributes:{"class":"from"}, inside:[
				{element:"label", attributes:{"class":"from"}, inside:[]},
				{element:"label", attributes:{"class":"fromItemKey", style:"display:none;"}, inside:[]}
			]},
			{element:"td", attributes:{"class":"userSelect"}, inside:[
  				{element:"select", attributes:{"class":"userSelect"}, inside:[]}
  			]},
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
	if($("aside").text().length == 0){
		includeFavorites();
		$("nav .favoritesButton").addClass("favoriteSelected");
	} else {
		removeFavorites();
		$("nav .favoritesButton").removeClass("favoriteSelected");
	}
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
		$("#fileDiv").show();
		$("#webcamDiv").hide();
		previewFile();
	} else if($("#paraImage input[type=radio]:checked").val()=="Webcam") {
		$("#fileDiv").hide();
		$("#webcamDiv").show();
		$("#paraImage img").attr("src", "");
	}
}