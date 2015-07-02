/**
 * JavaScript for managing contrats
 * @author Michael DUBUIS
 */
var contratList = "contratList";
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    QUERY FROM JAVASCRIPT TO MODEL									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function loadContrats(){
	sendEmptyQuery("loadContrats");
}
function loadContrat(id){
	var content = {"id":id};
	sendQuery("loadContrat", content);
}
function loadItemForContrat(itemKey){
	var content = {"itemKey":itemKey};
	sendQuery("loadItemForContrat", content);
}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 								    ANSWER FROM MODEL TO JAVASCRIPT									   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
