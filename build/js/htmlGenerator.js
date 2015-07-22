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
			{element:"li", attributes:{"class":"menu homeButton", onclick:"includeHome();"}, inside:[
	            	{element:"a", attributes:{}, inside:[
	                    //{element:"text", value:"My objects"}
	                ]}
			]},
			{element:"li", attributes:{"class":"menu searchButton", onclick:"includeSearch();"}, inside:[
				{element:"a", attributes:{}, inside:[
					//{element:"text", value:"Search Object"}
	        	]}
			]},
			{element:"li", attributes:{"class":"menu contratButton", onclick:"includeContrat();"}, inside:[
				{element:"a", attributes:{}, inside:[
					//{element:"text", value:"Contrats"}
	        	]}
			]},
			{element:"li", attributes:{"class":"menu messageButton", onclick:"includeWebmail();"}, inside:[
				{element:"a", attributes:{}, inside:[
					//{element:"text", value:"Messages"}
	        	]}
			]},
			{element:"li", attributes:{"class":"menu favoritesButton", onclick:"switchFavorites();"}, inside:[
				{element:"a", attributes:{}, inside:[
					//{element:"text", value:"Favorites"}
	        	]}
			]}
		]}
];
/* * * * * * * * * * * * * * * * * * * * * * * HEADER* * * * * * * * * * * * * * * * * * * * * * * * * */
var header = [
		{element:"div", attributes:{}, inside:[
			{element:"img", attributes:{src:"./img/sxpLogo.png", "class":"icon"}, inside:[]}
		]},
    	{element:"div", attributes:{"class":"dropDownMenu"}, inside:[
			{element:"ul", attributes:{"class":"dropMenu"}, inside:[
				{element:"li", attributes:{"class":"droper"}, inside:[
					{element:"img", attributes:{src:"./img/gears.png", alt:"Settings", "class":"setting"}, inside:[]}
				]},
				{element:"li", attributes:{"class":"drop"}, inside:[
					{element:"input", attributes:{type:"button", value:"Bootstrap", "class":"dropButton", onclick:"includeBoostrapInvitation();"}, inside:[]}
				]},
				{element:"li", attributes:{"class":"drop"}, inside:[
					{element:"input", attributes:{type:"button", value:"Profile", "class":"dropButton", onclick:"includeAccount();loadAccount();"}, inside:[]}
				]},
				{element:"li", attributes:{"class":"drop"}, inside:[
					{element:"input", attributes:{type:"button", value:"Logout", "class":"dropButton", onclick:"signOut();"}, inside:[]}
				]}
			]}
		]}
];
/* * * * * * * * * * * * * * * * * * * * * * * BOOSTRAP* * * * * * * * * * * * * * * * * * * * * * * * */
var boostrapInvitation = [
		{element:"div", attributes:{id:"bootstrapInvitation"}, inside:[
			{element:"label", attributes:{}, inside:[
				{element:"text", value:""}
			]},
			{element:"table", attributes:{id:"IP"}, inside:[
				{element:"thead", attributes:{}, inside:[
					{element:"tr", attributes:{}, inside:[
						{element:"th", attributes:{}, inside:[
							{element:"text", value:"IP Bootstrap"}
						]}
					]}
				]},
				{element:"tbody", attributes:{}, inside:[]},
				{element:"tfoot", attributes:{}, inside:[]}
			]},
			{element:"p", attributes:{}, inside:[
				{element:"label", attributes:{"class":"label"}, inside:[
					{element:"text", value:"Send to : "}
				]},
				{element:"input", attributes:{type:"text", id:"emailReceiver", name:"emailReceiver", required:"required", placeholder:"Alice@wonderland.com"}, inside:[]}
			]},
			{element:"p", attributes:{}, inside:[
				{element:"input", attributes:{type:"button", onclick:"sendBootstrap();", value:"Send Invitation"}, inside:[]}
			]},
			{element:"p", attributes:{"class":"feedbackBox hidden"}, inside:[]}
		]}
];	
var boostrapSetting = [
		{element:"div", attributes:{id:"boostrapPreference"}, inside:[
			{element:"h1", attributes:{}, inside:[
				{element:"text", value:"Bootstrap Settings"}
			]},
			{element:"p", attributes:{}, inside:[
				/*{element:"label", attributes:{"class":"label"}, inside:[
					{element:"text", value:"File sent by your sponsor : "}
				]},
				{element:"input", attributes:{type:"file", id:"bootstrapFile"}, inside:[]}*/
				{element:"label", attributes:{"class":"label"}, inside:[
					{element:"text", value:"Copy here all IP sent in your invitation :"}
				]},
				{element:"textarea", attributes:{id:"bootstrapIP"}, inside:[]}
			]},
			{element:"p", attributes:{"class":"actions"}, inside:[
				{element:"input", attributes:{type:"button", onclick:"sponsorBootstrap();", value:"Save Setting"}, inside:[]},
				{element:"input", attributes:{type:"button", onclick:"includeLogin();", value:"Back and log in"}, inside:[]}
			]},
			/*{element:"p", attributes:{}, inside:[
				{element:"input", attributes:{type:"button", onclick:"includeLogin();", value:"Back and log in"}, inside:[]}
			]},*/
			{element:"p", attributes:{"class":"feedbackBox hidden"}, inside:[]}
		]}
];
/* * * * * * * * * * * * * * * * * * * * * * LOGIN FORM* * * * * * * * * * * * * * * * * * * * * * * * */
var loginForm = [
		{element:"input", attributes:{type:"button",id:"bootstrapSetting", value:"Bootstrap Settings", onclick:"includeBoostrapSetting();"}, inside:[]},
		{element:"h1", attributes:{}, inside:[
			{element:"text", value:"Sign In"}
		]},
		{element:"p", attributes:{"class":"feedbackBox hidden"}, inside:[]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_username", "class":"label"}, inside:[
				{element:"text", value:"Username : "}
			]},
			{element:"input", attributes:{type:"text", id:"username", name:"username", required:"required", placeholder:"AliceWonderland"}, inside:[]},
		]},
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_password", "class":"label"}, inside:[
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
	{element:"p", attributes:{"class":"feedbackBox hidden"}, inside:[]},
	{element:"p", attributes:{}, inside:[
		{element:"label", attributes:{id:"label_username", "class":"label"}, inside:[
			{element:"text", value:"Username : "}
		]},
		{element:"input", attributes:{type:"text", id:"username", name:"username", required:"required", placeholder:"AliceWonderland"}, inside:[]},
	]},
	{element:"p", attributes:{}, inside:[
		{element:"label", attributes:{id:"label_name", "class":"label"}, inside:[
			{element:"text", value:"Name : "}
		]},
		{element:"input", attributes:{type:"text", name:"name", id:"name", required:"required", placeholder:"Liddel"}, inside:[]}
	]},
	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_firstname", "class":"label"}, inside:[
 			{element:"text", value:"First Name : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"firstname", id:"firstname", required:"required", placeholder:"Alice"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_email", "class":"label"}, inside:[
 			{element:"text", value:"Email : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"email", id:"email", required:"required", placeholder:"Alice@wonderland.com"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_phone", "class":"label"}, inside:[
 			{element:"text", value:"Phone : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"phone", id:"phone", required:"required", placeholder:"+336 05 04 03 02"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_password", "class":"label"}, inside:[
 			{element:"text", value:"Password : "}
 		]},
 		{element:"input", attributes:{type:"password", name:"password", id:"password", required:"required", placeholder:"ex : p4$Sw0r6!"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_passwordConfirm", "class":"label"}, inside:[
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
		{element:"label", attributes:{id:"label_username", "class":"label"}, inside:[
			{element:"text", value:"Username : "}
		]},
		{element:"input", attributes:{type:"text", id:"username", name:"username", required:"required", placeholder:"AliceWonderland"}, inside:[]},
	]},
	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_oldpassword", "class":"label"}, inside:[
 			{element:"text", value:"Old password : "}
 		]},
 		{element:"input", attributes:{type:"password", id:"oldpassword", name:"oldpassword", required:"required", placeholder:"ex : p4$Sw0r6!"}, inside:[]},
 	]},
	{element:"p", attributes:{}, inside:[
		{element:"label", attributes:{id:"label_name", "class":"label"}, inside:[
			{element:"text", value:"Name : "}
		]},
		{element:"input", attributes:{type:"text", name:"name", id:"name", required:"required", placeholder:"Liddel"}, inside:[]}
	]},
	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_firstname", "class":"label"}, inside:[
 			{element:"text", value:"First Name : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"firstname", id:"firstname", required:"required", placeholder:"Alice"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_email", "class":"label"}, inside:[
 			{element:"text", value:"Email : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"email", id:"email", required:"required", placeholder:"Alice@wonderland.com"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_phone", "class":"label"}, inside:[
 			{element:"text", value:"Phone : "}
 		]},
 		{element:"input", attributes:{type:"text", name:"phone", id:"phone", required:"required", placeholder:"+336 05 04 03 02"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_password", "class":"label"}, inside:[
 			{element:"text", value:"Password : "}
 		]},
 		{element:"input", attributes:{type:"password", name:"password", id:"password", required:"required", placeholder:"ex : p4$Sw0r6!"}, inside:[]}
 	]},
 	{element:"p", attributes:{}, inside:[
 		{element:"label", attributes:{id:"label_passwordConfirm", "class":"label"}, inside:[
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
			/*{element:"text", value:"Cancel"}*/
		]}
	]}
];
/* * * * * * * * * * * * * * * * * * * * * ITEM FORM * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemAddForm = [
		{element:"a", attributes:{id:"itemFormCompletButton", onclick:"itemFormComplet();", value:"Create new object", "class":"button"}, inside:[
			{element:"text", value:"Create new Object"}
		]},
		{element:"h1", attributes:{}, inside:[
  			{element:"text", value:"Item"}
  		]},
  		{element:"p", attributes:{}, inside:[
  			{element:"label", attributes:{id:"label_title", "class":"label"}, inside:[
  				{element:"text", value:"Title  : "}
  			]},
  			{element:"input", attributes:{type:"text", id:"title", name:"title", required:"required", placeholder:"Potatoes"}, inside:[]},
  		]},
  		{element:"p", attributes:{}, inside:[
  			{element:"label", attributes:{id:"label_type", "class":"label"}, inside:[
  				{element:"text", value:"Type : "}
  			]},
  			{element:"select", attributes:{id:"type", name:"type", required:"required", value:"PROPOSAL"}, inside:[]},
  		]},
  		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_lifetime", "class":"label"}, inside:[
				{element:"text", value:"Life time : "}
			]},
			{element:"input", attributes:{type:"text", id:"lifetime", name:"lifetime", required:"required", placeholder:"2015-12-25", pattern:"[0-9]{4}\-[0-9]{2}\-[0-9]{2}"}, inside:[]},
		]},
 		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_category", "class":"label"}, inside:[
				{element:"text", value:"Category : "}
			]},
			{element:"select", attributes:{id:"category", name:"category", required:"required", value:"NC"}, inside:[]},
		]},
 		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_description", "class":"label"}, inside:[
				{element:"text", value:"Description : "}
			]},
			{element:"textarea", attributes:{id:"description", name:"description", required:"required", placeholder:"My potatoes are great !"}, inside:[]},
		]},
 		{element:"p", attributes:{id:"paraImage"}, inside:[
			{element:"label", attributes:{id:"label_image", "class":"label"}, inside:[
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
					{element:"video", attributes:{id:"video"}, inside:[
						{element:"text", value:"Video stream not available."}
					]},
					{element:"canvas", attributes:{id:"canvas", "class":"hidden"}, inside:[]},
					{element:"input", attributes:{type:"button", id:"startbutton", value:"Take photo"}, inside:[]},
					{element:"input", attributes:{type:"button", id:"stopbutton", value:"Reset photo", "class":"hidden"}, inside:[]}
				]}
			]},
			{element:"img", attributes:{id:"image", style:"max-width:30%;"}, inside:[]}
		]},
 		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_country", "class":"label"}, inside:[
				{element:"text", value:"Location : "}
			]},
			{element:"input", attributes:{type:"text", id:"country", name:"country", required:"required", placeholder:"FRANCE - Marseille"}, inside:[]},
		]},
 		{element:"p", attributes:{}, inside:[
 			{element:"label", attributes:{id:"label_contact", "class":"label"}, inside:[
 				{element:"text", value:"Contact : "}
 			]},
 			{element:"textarea", attributes:{id:"contact", name:"contact", required:"required", placeholder:"Please call me : +336 05 04 03 02"}, inside:[]},
 		]},
 		{element:"p", attributes:{}, inside:[
      		{element:"a", attributes:{onclick:"addItem();", id:"addButton", "class":"button buttonValidate"}, inside:[
      			{element:"text", value:"Add item"}
      		]},
      		{element:"a", attributes:{onclick:"cancelItem();", "class":"button buttonCancel"}, inside:[
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
		/*{element:"h1", attributes:{}, inside:[
			{element:"text", value:"Search"}
		]},*/
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
			{element:"a", attributes:{onclick:"addSearch();", id:"addFieldButton", "class":"button buttonAdd"}, inside:[]},
		]},
  		{element:"p", attributes:{"class":"buttonsContainer"}, inside:[
      		{element:"a", attributes:{onclick:"searchItem();", id:"searchButton", "class":"button"}, inside:[
      			{element:"text", value:"Seach"}
      		]},
      		{element:"a", attributes:{onclick:"resetSearch();", id:"resetButton","class":"button"}, inside:[
				{element:"text", value:"Reset"}
			]}
      	]}
];
/* * * * * * * * * * * * * * * * * * * * * MESSAGES FORM * * * * * * * * * * * * * * * * * * * * * * * */
var webmailForm = [
		{element:"div", attributes:{id:"webmail"}, inside:[
			/*{element:"h1", attributes:{}, inside:[
				{element:"text", value:"Messages"}
			]},*/
			{element:"div", attributes:{id:"webmailMenu"}, inside:[
				{element:"ul", attributes:{}, inside:[
					{element:"li", attributes:{id:"unread", onclick:"newMessage();", "class":"menu"}, inside:[
						{element:"a", attributes:{}, inside:[
							{element:"text", value:"Write"}
						]}
					]},
					{element:"li", attributes:{id:"unread", onclick:"loadMessages();", "class":"menu"}, inside:[
						{element:"a", attributes:{}, inside:[
							{element:"text", value:"Unread"}
						]}
					]},
					{element:"li", attributes:{id:"conversation", onclick:"loadConversation();", "class":"menu"}, inside:[
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
			{element:"a", attributes:{onclick:"sendMessage();", id:"sendButton", "class":"button"}, inside:[
				{element:"text", value:"Send"}
			]},
			{element:"label", attributes:{id:"webmailErrorBox"}, inside:[]}
		]}
];
/* * * * * * * * * * * * * * * * * * * * * FAVORITES * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemFavoritesDisplayer = [
		/*{element:"h1", attributes:{}, inside:[
			{element:"text", value:"Favorite item"}
		]},*/
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
		/*{element:"h1", attributes:{}, inside:[
			{element:"text", value:"Contrats"}
		]},*/
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
				{element:"a", attributes:{"class":"button buttonStart", onclick:"signContrat();"}, inside:[
					{element:"text", value:"Lauch Signature Protocol"}
				]},
				{element:"a", attributes:{"class":"button buttonValidate", onclick:"saveDraftContrat();"}, inside:[
					{element:"text", value:"Save"}
				]},
				{element:"a", attributes:{"class":"button buttonCancel", onclick:"deleteContrat();"}, inside:[
					{element:"text", value:"Delete"}
				]}
			]},
		]}
];
var ruleForm = [
		{element:"tr", attributes:{}, inside:[
			{element:"td", attributes:{"class":"item"}, inside:[
				{element:"label", attributes:{"class":"label item"}, inside:[]}
			]},
			{element:"td", attributes:{"class":"from"}, inside:[
				{element:"label", attributes:{"class":"label from"}, inside:[]},
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
		$(element).attr("onkeyup", "textAreaAdjust(this);");
		$(element).attr("onfocus", "textAreaAdjust(this);");
		$(element).attr("onscroll", "textAreaAdjust(this);");
	}
	$.each(json.attributes, function(key, value){
		$(element).attr(key, value);
	});
	for( var i = 0 ; i < json.inside.length ; i++ ){
		$(element).append(getElement(json.inside[i]));
	}
	return element;
}

function textAreaAdjust(o) {
    o.style.height = "1px";
    o.style.height = (20+o.scrollHeight)+"px";
}

function getHeader(){
	var div = document.createElement("div");
	for ( var i = 0 ; i < header.length ; i++ ) {
		$(div).append(getElement(header[i]));
	}
	return div;
}

function getMenu(){
	var nav = document.createElement("nav");
	for ( var i = 0 ; i < menu.length ; i++ ) {
		$(nav).append(getElement(menu[i]));
	}
	return nav;
}

function printFeedback(feedback, isOk) {
	$("p.feedbackBox").removeClass("feedbackOk");
	if(feedback.length > 0) {
		$("p.feedbackBox").empty();
		$("p.feedbackBox").append(feedback);
		$("p.feedbackBox").removeClass("hidden");
	} else {
		$("p.feedbackBox").empty();
		$("p.feedbackBox").addClass("hidden");
	}
	if(isOk) {
		$("p.feedbackBox").addClass("feedbackOk");
	}
}