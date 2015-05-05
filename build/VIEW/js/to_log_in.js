             
            var webSocket;
           
            
            function openSocket(){
                // Ensures only one connection is open at a time
                if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
                   writeResponse("WebSocket is already opened.");
                    return;
                }
                // Create a new instance of the websocket
                webSocket = new WebSocket("ws://localhost:8080/EchoChamber/serv");
                 
                /**
                 * Binds functions to the listeners for the websocket.
                 */
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
            
            /**
             * Sends the value of the text input to the server
             */
            

 
            
            
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
                	 
                	
                	 
                	var media = "";
;                		if(Title == ""){
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
                		if(Picture != ""){
                			media = Picture;
                		}else if( URI != ""){
                			media = URI;
                		}else{
                			media = Picture;
                		}
                		
                		webSocket.send("/new_objet_add:"+Title+":"+Category+":"+Description+":"+media+":"+Country+":"+Contact_item+":"+Life_time+":"+Type_ob);
                		
                		alert(Title+":"+Category+":"+Description+":"+Country+":"+Contact_item+":"+Life_time+":"+Type_ob);
                		document.getElementById("succ").innerHTML = "your object was sent successfully";          	
                    	
                	}

                	
            }
            
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
            
            
            function redirection(text){
            	
            	webSocket.send(text);
            }
           
            
          
            
            
           
            function Test_adresse_email(email)
            
            {
                var reg = new RegExp('^[a-z0-9]+([_|\.|-]{1}[a-z0-9]+)*@[a-z0-9]+([_|\.|-]{1}[a-z0-9]+)*[\.]{1}[a-z]{2,6}$', 'i');
             
                if(reg.test(email))
                  {
            		return(true);
                  }
                else
                  {
            		return(false);
                  }
            }
            function validatePwd(password) {
            	if(password.length < 8){
            		return false;
            	}else{
            	
            	return true;
            }}
            
            
            function checknum(num){
                var valide = /^0[1-6]\d{8}$/;
                if(valide.test(num)){
                	return true;
                }
                else{
                	return false;
                }
            }
            
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
            
            
            
            function closeSocket(){
                webSocket.close();
            }
 
            //recuper les info utilisateur
            function load_user(){
            	
            	webSocket.send("/load_use:");
        	
            }
            
            //recuperee les tout les objet de l'user current
            function load_item(){
            	
            	webSocket.send("/load_item:");
        	
            }
            
            function zoom(text){
            	
           alert(text);
            	webSocket.send("/zoom_item:"+text);
            }
            
            
            function remo(text,r){
            	//alert(r);
            	document.getElementById("data_it").deleteRow(r);
            	webSocket.send("/remove_item:"+text);
            }
            
             
            
            var cmpt = 0;
  
            function writeResponse(text){
            	
            	
            	var text_tab=text.split(":");
            	//document.getElementById("use").innerHTML = text;
            	
            	
            	if(text_tab[0] == "index.html"){
            		window.location.replace(text_tab[0]);
            
            	}
            	if(text_tab[0] == "load_user"){
            		
            		  document.getElementById("use").innerHTML = text_tab[1];		
            	}
            	if(text_tab[0] == "Se_connecter.html#tologin")   {
            		window.location.replace(text_tab[0]);
            	}     
            	if(text_tab[0] == "new_objet.html"){
            		window.location.replace(text_tab[0]);
            	}
            	if(text_tab[0] == "chat.html"){
            		window.location.replace(text_tab[0]);
            	}
            	if(text_tab[0] == "load_item"){
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
            		colonne5.innerHTML += '<a class=\'btn btn-info\'  onclick=\'zoom("'+text_tab[1]+'");\'><i class=\'halflings-icon white edit\'></i></a>';
            		
            		
            		var colonne6 = ligne.insertCell(5);
            		colonne6.innerHTML += '<a class=\'btn btn-danger\'  onclick=\'remo("'+text_tab[1]+'","'+cmpt+'");\'><i class=\'halflings-icon white trash\'></i></a>';


            	}if(text_tab[0] == "zoom_item_result"){
            		
            		document.getElementById("ex_title").innerHTML = text_tab[3];
            	}
            	
            	
            }
          
           
         
            
            
            
            
            
            
        
            
            
            		