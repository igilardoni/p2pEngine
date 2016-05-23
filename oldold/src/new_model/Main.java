package new_model;

import java.math.BigInteger;

public class Main {
	
	
	public static void main(String[] args){
		new_user user =  new new_user("mehdi", "chtiwi", "nom", "prenom", "email", "tel", new BigInteger("0"), new BigInteger("0"), "id per");
		MakerXML.affichage(MakerXML.toXML(user,"nom","cle_public","tel","password"));
		
	}
	
	
	

}
