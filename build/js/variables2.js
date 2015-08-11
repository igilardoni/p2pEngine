/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * VARIABLES * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
var contratList = "contratList";
var itemContratList = "itemContratList"
var favoritesList = "favoritesList";
var itemList = "itemList";
var itemSearchList = "itemSearchList";
var itemForm = "itemForm";
var messagesList = "messagesList";
var messageDisplay = "messageDisplay";


var emptyForm = [
		{element:"p", attributes:{}, inside:[
			{element:"label", attributes:{id:"label_"}, inside:[
				{element:"text", value:""}
			]},
			{element:"input", attributes:{type:"", id:"", name:"", required:"required", placeholder:""}, inside:[]},
		]}
];
/* * * * * * * * * * * * * * * * * * * * * * *  MENU * * * * * * * * * * * * * * * * * * * * * * * * * */
var menu =
		{element:"ul", attributes:{}, inside:[
			{element:"li", attributes:{"class":"menu homeButton", onclick:"includeHome();"}, inside:[
	            	{element:"a", attributes:{title:"Your objects manager"}, inside:[
	                    //{element:"text", value:"My objects"}
	                ]}
			]},
			{element:"li", attributes:{"class":"menu searchButton", onclick:"includeSearch();"}, inside:[
				{element:"a", attributes:{title:"Searching item interface"}, inside:[
					//{element:"text", value:"Search Object"}
	        	]}
			]},
			{element:"li", attributes:{"class":"menu contratButton", onclick:"includeContrat();"}, inside:[
				{element:"a", attributes:{title:"Your contract manager"}, inside:[
					//{element:"text", value:"Contracts"}
	        	]}
			]},
			{element:"li", attributes:{"class":"menu messageButton", onclick:"includeWebmail();"}, inside:[
				{element:"a", attributes:{title:"Your messages manager"}, inside:[
					//{element:"text", value:"Messages"}
	        	]}
			]},
			{element:"li", attributes:{"class":"menu favoritesButton", onclick:"switchFavorites();"}, inside:[
				{element:"a", attributes:{title:"Display/Hide favorites bar"}, inside:[
					//{element:"text", value:"Favorites"}
	        	]}
			]}
		]};
/* * * * * * * * * * * * * * * * * * * * * * * HEADER* * * * * * * * * * * * * * * * * * * * * * * * * */
var header = [
		{element:"div", attributes:{}, inside:[
			{element:"img", attributes:{src:"./img/sxpLogo.png", "class":"icon"}, inside:[]}
		]},
    	{element:"div", attributes:{"class":"dropDownMenu"}, inside:[
			{element:"ul", attributes:{"class":"dropMenu"}, inside:[
				{element:"li", attributes:{"class":"droper"}, inside:[
					{element:"label", attributes:{id:"usernameHead"}, inside:[]},
					{element:"img", attributes:{src:"./img/gears.png", alt:"Settings", "class":"setting"}, inside:[]}
				]},
				{element:"ul", attributes:{"class":"drop"}, inside:[
					{element:"li", attributes:{}, inside:[
						{element:"a", attributes:{title:"Bootstrap", "class":"dropButton buttonBootstrap", onclick:"includeBoostrapInvitation();"}, inside:[
							//{element:"text", value:"Bootstrap"}
						]}
					]},
					{element:"li", attributes:{}, inside:[
						{element:"a", attributes:{title:"Profile", "class":"dropButton buttonProfile", onclick:"includeAccount();loadAccount();"}, inside:[
							//{element:"text", value:"Profile"}
						]}
					]},
					{element:"li", attributes:{}, inside:[
						{element:"a", attributes:{title:"Logout", "class":"dropButton buttonLogout", onclick:"signOut();"}, inside:[
							//{element:"text", value:"Logout"}
						]}
					]}
				]}
			]}
		]}
];
/* * * * * * * * * * * * * * * * * * * * * * * BOOSTRAP* * * * * * * * * * * * * * * * * * * * * * * * */
var boostrapInvitation =
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
			{element:"p", attributes:{"class":"feedbackBox", onclick:"clearFeedback();"}, inside:[]}
		]};	
var boostrapSetting =
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
			{element:"p", attributes:{"class":"feedbackBox", onclick:"clearFeedback();"}, inside:[]}
		]};
/* * * * * * * * * * * * * * * * * * * * * * LOGIN FORM* * * * * * * * * * * * * * * * * * * * * * * * */
var loginForm =
		{element:"div", attributes:{id:"login"}, inside:[
			{element:"input", attributes:{type:"button",id:"bootstrapSetting", value:"Bootstrap Settings", onclick:"includeBoostrapSetting();"}, inside:[]},
			{element:"h1", attributes:{}, inside:[
				{element:"text", value:"Sign In"}
			]},
			{element:"p", attributes:{"class":"feedbackBox", onclick:"clearFeedback();"}, inside:[]},
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
		]};
/* * * * * * * * * * * * * * * * * * * * REGISTRATION FORM * * * * * * * * * * * * * * * * * * * * * * */
var registrationForm =
		{element:"div", attributes:{id:"registration"}, inside:[
			{element:"h1", attributes:{}, inside:[
				{element:"text", value:"Registration"}
			]},
			{element:"p", attributes:{"class":"feedbackBox", onclick:"clearFeedback();"}, inside:[]},
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
		]};
/* * * * * * * * * * * * * * * * * * * * ACCOUNT UPDATE FORM * * * * * * * * * * * * * * * * * * * * * */
var updateAccountForm =
		{element:"div", attributes:{id:"accountUpdate"}, inside:[
			{element:"h1", attributes:{}, inside:[
				{element:"text", value:"Account"}
			]},
			{element:"p", attributes:{"class":"feedbackBox", onclick:"clearFeedback();"}, inside:[]},
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
		]};
/* * * * * * * * * * * * * * * * * * * * * ITEM FORM * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemAddForm = 
		{element:"div", attributes:{id:"itemForm"}, inside:[
			{element:"h1", attributes:{}, inside:[
	  			{element:"text", value:"Item"}
	  		]},
	  		{element:"p", attributes:{"class":"feedbackBox", onclick:"clearFeedback();"}, inside:[]},
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
 		]};
/* * * * * * * * * * * * * * * * * * * * * ITEMS TABLE * * * * * * * * * * * * * * * * * * * * * * * * */
var itemTable =
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
			]},
			{element:"tbutton", attributes:{}, inside:[
				{element:"a", attributes:{"class":"button buttonAdd", onclick:"itemFormComplet();"}, inside:[]}
			]}
		]};
/* * * * * * * * * * * * * * * * * * * * * SEARCH FORM * * * * * * * * * * * * * * * * * * * * * * * * */
var searchForm =          
		{element:"div", attributes:{id:"searchForm"}, inside:[
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
	      		{element:"a", attributes:{onclick:"searchItem();", id:"searchButton", "class":"button buttonSearch"}, inside:[
	      			{element:"text", value:"Seach"}
	      		]},
	      		{element:"a", attributes:{onclick:"resetSearch();", id:"resetButton","class":"button"}, inside:[
					{element:"text", value:"Reset"}
				]}
	      	]},
	      	{element:"p", attributes:{"class":"feedbackBox", onclick:"clearFeedback();"}, inside:[]}
		]};

var itemSearchTable =
		{element:"table", attributes:{id:itemSearchList}, inside:[
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
		]};
var itemSearchDisplayer =
		{element:"div", attributes:{"class":"searchDisplayer hidden"}, inside:[
			{element:"p", attributes:{}, inside:[
				{element:"label", attributes:{"class":"label"}, inside:[
					{element:"text", value:"Title : "}
				]},
				{element:"label", attributes:{id:"title"}, inside:[]}
			]},
			{element:"p", attributes:{}, inside:[
				{element:"label", attributes:{"class":"label"}, inside:[
					{element:"text", value:"Category : "}
				]},
				{element:"label", attributes:{id:"category"}, inside:[]}
			]},
			{element:"p", attributes:{}, inside:[
				{element:"label", attributes:{"class":"label"}, inside:[
					{element:"text", value:"Type : "}
				]},
				{element:"label", attributes:{id:"type"}, inside:[]}
			]},
			{element:"p", attributes:{}, inside:[
				{element:"label", attributes:{"class":"label"}, inside:[
					{element:"text", value:"Description : "}
				]},
				{element:"label", attributes:{id:"description"}, inside:[]}
			]},
			{element:"p", attributes:{}, inside:[
				{element:"label", attributes:{"class":"label"}, inside:[
					{element:"text", value:"Image : "}
				]},
				{element:"label", attributes:{id:"image"}, inside:[]}
			]},
			{element:"p", attributes:{}, inside:[
				{element:"label", attributes:{"class":"label"}, inside:[
					{element:"text", value:"Location : "}
				]},
				{element:"label", attributes:{id:"country"}, inside:[]}
			]},
			{element:"p", attributes:{}, inside:[
				{element:"label", attributes:{"class":"label"}, inside:[
					{element:"text", value:"Contact : "}
				]},
				{element:"label", attributes:{id:"contact"}, inside:[]}
			]},
		]};
/* * * * * * * * * * * * * * * * * * * * * MESSAGES FORM * * * * * * * * * * * * * * * * * * * * * * * */
var webmailForm =
		{element:"div", attributes:{id:"webmail"}, inside:[
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
  							{element:"text", value:"Read"}
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
				{element:"p", attributes:{"class":"feedbackBox", onclick:"clearFeedback();"}, inside:[]},
				{element:"div", attributes:{id:"messageDisplay"}, inside:[]}
			]}
		]};
var writeMessage =
		{element:"div", attributes:{id:"messageDisplay"}, inside:[
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
						{element:"input", attributes:{type:"text", id:"receiver", name:"receiver"}, inside:[]}
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
				{element:"a", attributes:{onclick:"sendMessage();", "class":"button buttonSend"}, inside:[
					{element:"text", value:"Send"}
				]},
				{element:"a", attributes:{onclick:"cancelMessage();", "class":"button buttonCancel"}, inside:[
					{element:"text", value:"Cancel"}
				]},
			]},
			{element:"p", attributes:{id:"feedbackBox", onclick:"clearFeedback();"}, inside:[]}
		]};
var messageRow = 
		{element:"tr", attributes:{}, inside:[
			{element:"td", attributes:{"class":"rowDate"}, inside:[]},
			{element:"td", attributes:{"class":"rowSubject"}, inside:[]},
			{element:"td", attributes:{"class":"rowFrom"}, inside:[]},
			{element:"td", attributes:{"class":"rowActions"}, inside:[
				{element:"a", attributes:{"class":"button buttonRemove"}, inside:[]}
			]}
		]};
/* * * * * * * * * * * * * * * * * * * * * FAVORITES * * * * * * * * * * * * * * * * * * * * * * * * * */
var itemFavoritesDisplayer =
		{element:"div", attributes:{id:"itemFavoritesDisplayer"}, inside:[
			{element:"a", attributes:{"class":"button buttonCancel toprightButton", title:"hide", onclick:"removeDisplayItemFavorites();"}, inside:[]},
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
	 		{element:"p", attributes:{"class":"hidden"}, inside:[
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
		]};
var itemFavoritesTable =
		{element:"table", attributes:{id:favoritesList}, inside:[
			{element:"thead", attributes:{}, inside:[
				{element:"tr", attributes:{}, inside:[
					{element:"th", attributes:{"class":"rowTitle"}, inside:[
						{element:"text", value:"Title"}
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
		]};
/* * * * * * * * * * * * * * * * * * * * * CONTRACTS * * * * * * * * * * * * * * * * * * * * * * * * * */
var contratDisplay =
		{element:"div", attributes:{id:"newContratForm"}, inside:[
			/*{element:"label", attributes:{"class":"label"}, inside:[
				{element:"text", value:"New contrat's title : "}
			]},
			{element:"input", attributes:{type:"text", id:"titleNewContrat", name:"titleNewContrat", title:"If empty title will be generated"}, inside:[]},
			{element:"a", attributes:{"class":"button buttonAdd", onclick:"newContrat();"}, inside:[]},*/
			{element:"p", attributes:{"class":"feedbackBox", onclick:"clearFeedback();"}, inside:[]}
		]};
var contratTable =
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
			{element:"tfoot", attributes:{}, inside:[]},
			{element:"tbutton", attributes:{}, inside:[
				{element:"input", attributes:{type:"text", id:"titleNewContrat", name:"titleNewContrat", title:"If empty title will be generated"}, inside:[]},
				{element:"a", attributes:{"class":"button buttonAdd", onclick:"newContrat();"}, inside:[]},
			]}
		]};
var contratForm =
		{element:"div", attributes:{id:"contratForm"}, inside:[
			{element:"h1", attributes:{}, inside:[
				{element:"text", value:"Contrat"},
				{element:"a", attributes:{"class":"button buttonEdit", onclick:"renameContractForm()", title:"Rename"}, inside:[]}
			]},
			{element:"p", attributes:{"class":"feedbackBox", onclick:"clearFeedback();"}, inside:[]},
			{element:"label", attributes:{"class":"hidden", id:"contratID"}, inside:[]},
			{element:"div", attributes:{id:"objects"}, inside:[]},
			{element:"div", attributes:{}, inside:[
				{element:"table", attributes:{id:"signatories"}, inside:[
					{element:"thead", attributes:{}, inside:[
						{element:"tr", attributes:{}, inside:[
							{element:"th", attributes:{}, inside:[
								{element:"text", value:"Signatories"}
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
					/*{element:"tbutton", attributes:{}, inside:[
						{element:"tr", attributes:{}, inside:[
							{element:"td", attributes:{colspan:"3"}, inside:[
								{element:"a", attributes:{"class":"button buttonAdd", onclick:"addRules();", alt:"Add exchange rule"}, inside:[]}
							]}
						]}
					]}*/
				]}
			]},
			{element:"div", attributes:{id:"clauses"}, inside:[
				{element:"a", attributes:{"class":"button buttonAdd", title:"Add a clause", onclick:"addClause();"}, inside:[]}
			]},
			{element:"div", attributes:{id:"actions"}, inside:[
				{element:"a", attributes:{"class":"button buttonStart", onclick:"signContrat();"}, inside:[
					{element:"text", value:"Lauch Signature Protocol"}
				]},
				/*{element:"a", attributes:{"class":"button buttonValidate", onclick:"saveDraftContrat();"}, inside:[
					{element:"text", value:"Save"}
				]},*/
				{element:"a", attributes:{"class":"button buttonCancel", onclick:"deleteContrat();"}, inside:[
					{element:"text", value:"Delete"}
				]}
			]}
		]};
var itemContratTable =
		{element:"table", attributes:{id:itemContratList}, inside:[
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
		]};
var clauseContratForm =
		{element:"div", attributes:{}, inside:[
			{element:"p", attributes:{}, inside:[
				{element:"label", attributes:{"class":"label"}, inside:[
					{element:"text", value:"Title : "}
				]},
				{element:"input", attributes:{type:"text", id:"title", name:"title"}, inside:[]}
			]},
			{element:"p", attributes:{}, inside:[
				{element:"textarea", attributes:{id:"value", name:"value"}, inside:[]}
			]},
			{element:"p", attributes:{style:"text-align:right;"}, inside:[
				{element:"a", attributes:{"class":"button buttonValidate"}, inside:[
					{element:"text", value:"Save Clause"}
				]},
				{element:"a", attributes:{"class":"button buttonRemove", title:"Remove clause"}, inside:[
					{element:"text", value:"Remove Clause"}
				]}
			]}
		]};
var clauseContratDisplay =
		{element:"div", attributes:{}, inside:[
   			{element:"p", attributes:{}, inside:[
   				{element:"label", attributes:{"class":"label"}, inside:[
   					{element:"text", value:"Title : "}
   				]},
   				{element:"label", attributes:{id:"title"}, inside:[]},
   				{element:"a", attributes:{"class":"button buttonRemove", title:"Remove clause"}, inside:[]},
   				{element:"a", attributes:{"class":"button buttonEdit", title:"Edit clause"}, inside:[]}
   			]},
   			{element:"p", attributes:{}, inside:[
   				{element:"label", attributes:{id:"value"}, inside:[]}
   			]}
   		]};
var contratRow =
		{element:"tr", attributes:{}, inside:[
			{element:"td", attributes:{"class":"rowTitle"}, inside:[]},
			{element:"td", attributes:{"class":"rowState"}, inside:[]},
			{element:"td", attributes:{"class":"rowActions"}, inside:[
				{element:"a", attributes:{"class":"button buttonRemove"}, inside:{}}
			]}
		]};
var ruleRow =
		{element:"tr", attributes:{}, inside:[
			{element:"td", attributes:{"class":"item"}, inside:[
				{element:"label", attributes:{"class":"labelItem"}, inside:[]},
				{element:"label", attributes:{"class":"itemKey hidden"}, inside:[]}
			]},
			{element:"td", attributes:{"class":"from"}, inside:[
				{element:"label", attributes:{"class":"labelFrom"}, inside:[]},
				{element:"label", attributes:{"class":"publicKey hidden"}, inside:[]}
			]},
			{element:"td", attributes:{"class":"to"}, inside:[
  				{element:"select", attributes:{"class":"userSelect"}, inside:[]}
  			]}
		]};
var itemContratRow = 
		{element:"tr", attributes:{}, inside:[
			{element:"td", attributes:{"class":"rowTitle"}, inside:[]},
			{element:"td", attributes:{"class":"rowDescription"}, inside:[]},
			{element:"td", attributes:{"class":"rowActions"}, inside:[
				{element:"a", attributes:{"class":"button buttonRemove"}, inside:[]}
			]},
		]};