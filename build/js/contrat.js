/**
 * JavaScript for managing contrats
 * @author Michael DUBUIS
 */
var contratList = "contratList";
var itemContratList = "itemContratList"
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

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 											HTML GENERATOR											   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
function getContrat(){
	var div = document.createElement("div");
	div.setAttribute("id", "content");
	div.appendChild(getElement(contratDisplay[0]));
	div.appendChild(getElement(contratTable[0]));
	div.appendChild(getElement(contratForm[0]));
	$(div).find("#objects").append(getTableItem(itemContratList));
	return div;
}