             
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
                    // For reasons I can't determine, onopen gets called twice
                    // and the first time event.data is undefined.
                    // Leave a comment if you know the answer.
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
            	
            		webSocket.send("/new_objet:");
            
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
            
            function closeSocket(){
                webSocket.close();
            }
 
            function writeResponse(text){
            	messages.innerHTML += "<br/>" + text;
            	window.location.replace(text);
                
            }
            
            
            		