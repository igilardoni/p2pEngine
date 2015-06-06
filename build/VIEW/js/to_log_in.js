
var webSocket;
var date_objet;
var image_objet;
var user_nick;
var media;
var cmpt = 0;

//fonction principal qui gére la connexion avec le serveur
function openSocket(){
	if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
		writeResponse("WebSocket OK.");
		return;
	}
	webSocket = new WebSocket("ws://localhost:8080/EchoChamber/serv");
	webSocket.onopen = function(event){
		load_user();
		load_item();
		if(event.data === undefined)
			return;
		writeResponse(event.data);
	};
	webSocket.onmessage = function(event){
		writeResponse(event.data);
	};
	webSocket.onclose = function(event){
		writeResponse("Connection closed");
	};
} 

// new_objet permet d'ajouter un nouveau objet dans la liste
function new_objet(){
	var Title = document.getElementById("Title").value;
	var Category = document.getElementById("Category").value;
	var Country = document.getElementById("Country").value;
	var Type_ob = document.getElementById("Type_ob").value;
	var Life_time = document.getElementById("Life_time").value;
	var Picture = document.getElementById("Picture").value;
	var Description = document.getElementById("Description").value;
	var URI = document.getElementById("uri_cam").value;
	var Contact_item = document.getElementById("Contact_item").value;

	if(Title == ""){
		document.getElementById("Title_label").style.color = "#ff0000";
	} else if(Category == ""){
		document.getElementById("Title_label").style.color = "#2E1C08";
		document.getElementById("Category_label").style.color = "#ff0000";
	} else  if(Country == ""){
		document.getElementById("Title_label").style.color = "#2E1C08";
		document.getElementById("Category_label").style.color = "#2E1C08";
		document.getElementById("Country_label").style.color = "#ff0000";
	} else if(Life_time == ""){
		document.getElementById("Title_label").style.color = "#2E1C08";
		document.getElementById("Category_label").style.color = "#2E1C08";
		document.getElementById("Country_label").style.color = "#2E1C08";
		document.getElementById("Life_time_label").style.color = "#ff0000";
	} else if(Picture == "" && URI ==""){
		document.getElementById("Title_label").style.color = "#2E1C08";
		document.getElementById("Category_label").style.color = "#2E1C08";
		document.getElementById("Country_label").style.color = "#2E1C08";
		document.getElementById("Life_time_label").style.color = "#2E1C08";
		document.getElementById("picture_label").style.color = "#ff0000";               	
	} else if(Description == ""){
		document.getElementById("Title_label").style.color = "#2E1C08";
		document.getElementById("Category_label").style.color = "#2E1C08";
		document.getElementById("Country_label").style.color = "#2E1C08";
		document.getElementById("Life_time_label").style.color = "#2E1C08";
		document.getElementById("picture_label").style.color = "#2E1C08";   
		document.getElementById("description_label").style.color = "#ff0000";          	
	}else if(Type_ob == ""){
		document.getElementById("type_label").style.color = "#ff0000";                  	
		document.getElementById("Title_label").style.color = "#2E1C08";
		document.getElementById("Category_label").style.color = "#2E1C08";
		document.getElementById("Country_label").style.color = "#2E1C08";
		document.getElementById("Life_time_label").style.color = "#2E1C08";
		document.getElementById("picture_label").style.color = "#2E1C08";   
		document.getElementById("description_label").style.color = "#2E1C08";
	}else if(Contact_item == ""){
		document.getElementById("Contact_label").style.color = "#ff0000";  
		document.getElementById("type_label").style.color = "#2E1C08";
		document.getElementById("Title_label").style.color = "#2E1C08";
		document.getElementById("Category_label").style.color = "#2E1C08";
		document.getElementById("Country_label").style.color = "#2E1C08";
		document.getElementById("Life_time_label").style.color = "#2E1C08";
		document.getElementById("picture_label").style.color = "#2E1C08";   
		document.getElementById("description_label").style.color = "#2E1C08";
	}else{
		if(document.getElementById('optionsRadios2').checked) {
			var filesSelected = document.getElementById("Picture").files;
			var fileToLoad = filesSelected[0];
			var fileReader = new FileReader();

			fileReader.onload = function(fileLoadedEvent) {
				media = fileLoadedEvent.target.result; // <--- data: base64
				webSocket.send("/new_objet_add:"+Title+":"+Category+":"+Description+":"+media+":"+Country+":"+Contact_item+":"+Life_time+":"+Type_ob);
			}
			fileReader.readAsDataURL(fileToLoad);
		} if(document.getElementById('optionsRadios1').checked) {//Female radio button is checked
			media = URI;
			webSocket.send("/new_objet_add:"+Title+":"+Category+":"+Description+":"+media+":"+Country+":"+Contact_item+":"+Life_time+":"+Type_ob);
		}
		document.getElementById("succ").innerHTML = "your object was sent successfully";          	
	}
}
// verification du login et du mot de passe
function connexion(){
	var password = document.getElementById("password").value;
	var nickname = document.getElementById("username").value;
	if(nickname == ""){
		document.getElementById("label_name").style.color = "#ff0000";
		document.getElementById("label_password").style.color = "#2E1C08";
	}else if(password == "" ){
		document.getElementById("label_password").style.color = "#ff0000";
		document.getElementById("label_name").style.color = "#2E1C08";
	}else{
		document.getElementById("label_password").style.color = "#2E1C08";
		document.getElementById("label_name").style.color = "#2E1C08";
		webSocket.send("/index:"+password+":"+nickname);
	}
}
//inscription noveau utilisateur
function to_register(){
	var nick = document.getElementById("nick").value;
	var name = document.getElementById("name").value;
	var firstName = document.getElementById("firstName").value;
	var email = document.getElementById("email").value;
	var passwordsignup = document.getElementById("passwordsignup").value;
	var passwordsignup_confirm = document.getElementById("passwordsignup_confirm").value;
	var phone = document.getElementById("phone").value;

	if(nick == ""){
		document.getElementById("label_nick").innerHTML = " Please enter your nickname"; 
		document.getElementById("label_nick").style.color = "#ff0000";
	}else if(name == "" ){
		document.getElementById("name_label").innerHTML = " Please enter your name";
		document.getElementById("label_nick").innerHTML = "Your nick name";
		document.getElementById("label_nick").style.color = "#2E1C08";
		document.getElementById("name_label").style.color = "#ff0000";
	}else if(firstName == "" ){
		document.getElementById("label_firstName").innerHTML = "Please enter your firstName";
		document.getElementById("name_label").innerHTML = "Your name";
		document.getElementById("name_label").style.color = "#2E1C08";
		document.getElementById("label_firstName").style.color = "#ff0000";
	}else if(!Test_adresse_email(email)){
		document.getElementById("label_email").innerHTML = "Please enter your email";
		document.getElementById("label_firstName").innerHTML = "Your firstName";
		document.getElementById("label_firstName").style.color = "#2E1C08";
		document.getElementById("label_email").style.color = "#ff0000";
	}else if(!validatePwd(passwordsignup)){
		document.getElementById("label_pw1").innerHTML = "password does not meet the safety criteria";
		document.getElementById("label_email").innerHTML = "Your email";
		document.getElementById("label_email").style.color = "#2E1C08";
		document.getElementById("label_pw1").style.color = "#ff0000";
	}else if(passwordsignup_confirm == "" || passwordsignup_confirm != passwordsignup){
		document.getElementById("label_pw2").innerHTML = "past words are not identical";
		document.getElementById("label_pw1").innerHTML = "Your password";
		document.getElementById("label_pw1").style.color = "#2E1C08";
		document.getElementById("label_pw2").style.color = "#ff0000";
	}else if(!checknum(phone)){
		document.getElementById("label_phone").style.color = "#ff0000";
		document.getElementById("label_phone").innerHTML = "Please enter your phone number";    		
		document.getElementById("label_pw2").innerHTML = "Please confirm your password ";
		document.getElementById("label_pw2").style.color = "#2E1C08";
	}else {
		webSocket.send("/register:"+nick+":"+passwordsignup+":"+name+":"+firstName+":"+email+":"+phone);
	}
}
//modification d'un objet
function to_update(){
	var title = document.getElementById("title_update_for").value;
	var categorie = document.getElementById("Category_update_select").value;
	var country = document.getElementById("Country_update_af").value;
	var life_time = document.getElementById("Life_time_update").value;
	var type_update = document.getElementById("Type_update").value;
	var description = document.getElementById("update_desc").value;
	var contact = document.getElementById("Contact_item_upd").value;

	if(title  == ""){
		document.getElementById("update_title_err").style.color = "#ff0000";
	}else if(categorie  == ""){
		document.getElementById("Category_update").style.color = "#ff0000";
		document.getElementById("update_title_err").style.color = "#2E1C08";
	}else if(country  == ""){
		document.getElementById("Country_update").style.color = "#ff0000";
		document.getElementById("Category_update").style.color = "#2E1C08";
		document.getElementById("update_title_err").style.color = "#2E1C08";
	}else if(life_time  == ""){
		document.getElementById("Life_time_label").style.color = "#ff0000";
		document.getElementById("Country_update").style.color = "#2E1C08";
		document.getElementById("Category_update").style.color = "#2E1C08";
		document.getElementById("update_title_err").style.color = "#2E1C08";
	}else if(type_update  == ""){
		document.getElementById("type_label_update").style.color = "#ff0000";
		document.getElementById("Life_time_label").style.color = "#2E1C08";
		document.getElementById("Country_update").style.color = "#2E1C08";
		document.getElementById("Category_update").style.color = "#2E1C08";
		document.getElementById("update_title_err").style.color = "#2E1C08";
	}else if(description  == ""){
		document.getElementById("description_label").style.color = "#ff0000";         		
		document.getElementById("type_label_update").style.color = "#2E1C08";
		document.getElementById("Life_time_label").style.color = "#2E1C08";
		document.getElementById("Country_update").style.color = "#2E1C08";
		document.getElementById("Category_update").style.color = "#2E1C08";
		document.getElementById("update_title_err").style.color = "#2E1C08";
	}else if(contact  == ""){
		document.getElementById("Contact_labe_ul").style.color = "#ff0000";  
		document.getElementById("description_label").style.color = "#2E1C08"; 
		document.getElementById("type_label_update").style.color = "#2E1C08";
		document.getElementById("Life_time_label").style.color = "#2E1C08";
		document.getElementById("Country_update").style.color = "#2E1C08";
		document.getElementById("Category_update").style.color = "#2E1C08";
		document.getElementById("update_title_err").style.color = "#2E1C08";
	}else{
		webSocket.send("/new_objet_update:"+title+":"+categorie+":"+description+":"+image_objet+":"+country+":"+contact+":"+life_time+":"+type_update+":"+date_objet);
	}
}
//fonction qui gére tout les redirection entre page
function redirection(text){
	webSocket.send(text);
}
//verification mel
function Test_adresse_email(email){
	var reg = new RegExp('^[a-z0-9]+([_|\.|-]{1}[a-z0-9]+)*@[a-z0-9]+([_|\.|-]{1}[a-z0-9]+)*[\.]{1}[a-z]{2,6}$', 'i');

	if(reg.test(email)){
		return(true);
	}else{
		return(false);
	}
}
//verification password
function validatePwd(password) {
	if(password.length < 8){
		return false;
	}else{
		return true;
	}
}
//verification format tel
function checknum(num){
	var valide = /^0[1-6]\d{8}$/;
	if(valide.test(num)){
		return true;
	}else{
		return false;
	}
}
//convert image to URI (c'est elementStyle qui contient le resultat)
function fulltype_picture(text) {
	if(text == "picture_t2"){
		document.getElementById("picture_t2").style.visibility = "hidden";
		document.getElementById("picture_t1").style.visibility = "visible";
		var elementStyle = document.getElementById("picture_t3").style;
		elementStyle.position = "relative";
		elementStyle.top = elementStyle.top = "-400px";
	}else{
		document.getElementById("picture_t2").style.visibility = "visible";
		document.getElementById("picture_t1").style.visibility = "hidden";
		var elementStyle = document.getElementById("picture_t3").style;
		elementStyle.position = "relative";
		elementStyle.top = elementStyle.top = "00px";
	}
}
//fermuture de la socket
function closeSocket(){
	webSocket.close();
}
//load_user charge tout les information de l'utilisateur 
function load_user(){
	webSocket.send("/load_use:");
}
//load_item charge tout les objet de l'utilisateur 
function load_item(){
	webSocket.send("/load_item:");
}
//pour afficher les information cacher
function zoom(text){
	webSocket.send("/zoom_item:"+text);
}
//pour activer la modification
function update(){
	document.getElementById("gestion_but_aft").style.visibility="visible";
	document.getElementById('Life_time_update').disabled=false;      		
	document.getElementById("update_desc").disabled=false;
	document.getElementById("Picture_update").disabled=false;
}
//pour supprimer un objet
function remo(text,r){
	webSocket.send("/remove_item:"+text);
	document.getElementById("data_it").deleteRow(r);
	document.getElementById("title_update_for").value = "";
	document.getElementById("Life_time_update").value = "";
	document.getElementById("update_desc").innerHTML = "";
	document.getElementById("image_object_u").src = "VIEW/img/sxpLogo.png";
	document.getElementById('Life_time_update').disabled=true;
	document.getElementById("title_update_for").disabled=true;
	document.getElementById("update_desc").disabled=true;
	document.getElementById("Picture_update").disabled=true;
	document.getElementById("gestion_but_after").style.visibility="hidden";
}
function send_message(){
	//A completer
	var nick = document.getElementById("user_recv").innerHTML;
	var message_env = document.getElementById("message_send").value;
	webSocket.send("/send_message:"+nick+":"+message_env);
}
//fonction par encore fini, pour la recherche dans le réseau
function to_search(){
	var title_search = document.getElementById("title_search_input").value;
	var categorie_search = document.getElementById("Category_search").value;
	var country_search = document.getElementById("Country_search").value;
	var type_search = document.getElementById("Type_search_").value;
	webSocket.send("/search_itme:"+title_search);
}
//deconnexion
function logout(){
	webSocket.send("/log_out:");
}
//modification des information perso
function update_compte(){
	var nick = document.getElementById("nick_compte_input").value;
	var name = document.getElementById("name_compte_input").value;
	var firstname = document.getElementById("firstname_compte_input").value;
	var email = document.getElementById("email_compte_input").value;
	var passe_update = document.getElementById("password_input_compte").value;
	var phone = document.getElementById("phone_compte_input").value;
	var passe_verif = document.getElementById("password_verif").value;

	if(nick  == ""){
		document.getElementById("update_compte_label").style.color = "#ff0000";
	}else if(name == ""){
		document.getElementById("names_labe_ul").style.color = "#ff0000";
		document.getElementById("update_compte_label").style.color = "#2E1C08";
	}else if(firstname == ""){
		document.getElementById("firstes_labe_ul").style.color = "#ff0000";
		document.getElementById("names_labe_ul").style.color = "#2E1C08";
		document.getElementById("update_compte_label").style.color = "#2E1C08";
	}else if(!Test_adresse_email(email)){
		document.getElementById("emailes_labe_ul").style.color = "#ff0000";
		document.getElementById("firstes_labe_ul").style.color = "#2E1C08";
		document.getElementById("names_labe_ul").style.color = "#2E1C08";
		document.getElementById("update_compte_label").style.color = "#2E1C08";
	}else if(!validatePwd(passe_update)){
		document.getElementById("passwordss_labe_ul").style.color = "#ff0000";
		document.getElementById("emailes_labe_ul").style.color = "#2E1C08";
		document.getElementById("firstes_labe_ul").style.color = "#2E1C08";
		document.getElementById("names_labe_ul").style.color = "#2E1C08";
		document.getElementById("update_compte_label").style.color = "#2E1C08";
	}else if(!checknum(phone)){
		document.getElementById("iphoe_labe_ul").style.color = "#ff0000";
		document.getElementById("passwordss_labe_ul").style.color = "#2E1C08";
		document.getElementById("emailes_labe_ul").style.color = "#2E1C08";
		document.getElementById("firstes_labe_ul").style.color = "#2E1C08";
		document.getElementById("names_labe_ul").style.color = "#2E1C08";
		document.getElementById("update_compte_label").style.color = "#2E1C08";
	}else{
		webSocket.send("/update_compte_user:"+nick+":"+name+":"+firstname+":"+email+":"+passe_update+":"+phone+":"+passe_verif);
	}
}
//charge tout les categorie dans la liste
function loadCategories(){
	webSocket.send("/load_categories");
}
//fonction qui recoi tout les message du serveur ( c'est la ou on peu gérer l'affichage dans l'HTML)
function writeResponse(text){
	var text_tab=text.split(":");
	// Redirection
	if(text_tab[0] == "index.html"){
		window.location.replace(text_tab[0]);
	}if(text_tab[0] == "Se_connecter.html#tologin")   {
		window.location.replace(text_tab[0]);
	}if(text_tab[0] == "new_objet.html"){
		window.location.replace(text_tab[0]);
	}if(text_tab[0] == "Search.html"){
		window.location.replace(text_tab[0]);
	}if(text_tab[0] == "Message.html"){
		window.location.replace(text_tab[0]);
	}if(text_tab[0] == "Contrat.html"){
		window.location.replace(text_tab[0]);
	}if(text_tab[0] == "User_compte.html"){
		window.location.replace(text_tab[0]);
	}
	
	if(text_tab[0] == "resultCategories"){
		// HERE CHANGE Value of SelectHTMLElement Category
	}if(text_tab[0] == "load_user"){
		document.getElementById("use").innerHTML = text_tab[1];	
		document.getElementById("nick_compte_input").value = text_tab[1];	
		document.getElementById("name_compte_input").value = text_tab[2];	
		document.getElementById("firstname_compte_input").value = text_tab[3];	
		document.getElementById("email_compte_input").value = text_tab[4];	
		document.getElementById("phone_compte_input").value = text_tab[5];	
		document.getElementById("name_compte_verid").value = text_tab[1];
	}if(text_tab[0] == "load_update_user"){
		document.getElementById("invalide_pass").innerHTML = "Account successfully change";
		document.getElementById("invalide_pass").style.color = "#228B22";
		load_user();
	}if(text_tab[0] == "update_user_false"){
		document.getElementById("invalide_pass").innerHTML = "Please verify your password";
		document.getElementById("invalide_pass").style.color = "#ff0000";
	}if(text_tab[0] == "load_item"){
		cmpt = cmpt +1;
		var tableau = document.getElementById("data_it");
		var ligne = tableau.insertRow(-1);
		var colonne1 = ligne.insertCell(0);
		colonne1.innerHTML += text_tab[1]
		var colonne2 = ligne.insertCell(1);
		colonne2.innerHTML += text_tab[2]
		var colonne3 = ligne.insertCell(2);
		colonne3.innerHTML += text_tab[3]
		var colonne4 = ligne.insertCell(3);  	
		colonne4.innerHTML += '<a class=\'btn btn-success\'  onclick=\'zoom("'+text_tab[1]+'");\'><i class=\'halflings-icon white zoom-in\'></i></a>';
		var colonne5 = ligne.insertCell(4);
		colonne5.innerHTML += '<a class=\'btn btn-info\'  onclick=\'update("'+text_tab[1]+'");\'><i class=\'halflings-icon white edit\'></i></a>';
		var colonne6 = ligne.insertCell(5);
		colonne6.innerHTML += '<a class=\'btn btn-danger\'  onclick=\'remo("'+text_tab[1]+'","'+cmpt+'");\'><i class=\'halflings-icon white trash\'></i></a>';
	}if(text_tab[0] == "zoom_item_result"){
		document.getElementById("title_update_for").value = text_tab[1];
		document.getElementById("Life_time_update").value = text_tab[4];
		document.getElementById("update_desc").innerHTML = text_tab[6];
		document.getElementById("image_object_u").src = text_tab[7]+":"+text_tab[8];
		document.getElementById("Contact_item_upd").value= text_tab[10];
		date_objet = text_tab[9];
		image_objet = text_tab[7]+":"+text_tab[8];
	}if(text_tab[0] == "update_objet"){
		document.getElementById("pourteste").innerHTML = "objet modifier";
		var element = document.getElementById("data_it");
		while (element.firstChild) {
			element.removeChild(element.firstChild);
		}
		load_item();
		var tableau = document.getElementById("data_it");
		var ligne = tableau.insertRow(-1);
		var colonne1 = ligne.insertCell(0);
		colonne1.innerHTML += "Title";
		var colonne2 = ligne.insertCell(1);
		colonne2.innerHTML += "Country";
		var colonne3 = ligne.insertCell(2);
		colonne3.innerHTML += "Description";
		var colonne3 = ligne.insertCell(3);
		colonne3.innerHTML += "Option";
	}if(text_tab[0] == "log_index"){
		window.location.replace("Se_connecter.html");
	}if(text_tab[0] == "result_search_item"){
		var tableau = document.getElementById("data_it_search");
		var ligne = tableau.insertRow(-1);
		var colonne1 = ligne.insertCell(0);
		colonne1.innerHTML += text_tab[1]
		var colonne2 = ligne.insertCell(1);
		colonne2.innerHTML += text_tab[2]
		var colonne3 = ligne.insertCell(2);
		colonne3.innerHTML += text_tab[3]
	}if(text_tab[0] == "result_sendMessage"){
		if(text_tab[1] == "sendt"){
			document.getElementById("bkg").style.backgroundColor="#FF0040";
		}else{
			document.getElementById("bkg").style.backgroundColor="#FF0040";
		}
	}
}