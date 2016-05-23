package model;

import net.jxta.document.Attributable;
import net.jxta.document.Document;
import net.jxta.document.Element;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredDocumentFactory;

public class DocumentGenerator {
	
	
	/**
	 * Génénère un document structuré (par exemple XML) 
	 * @param asMimeType 
	 * @param advType
	 * @param args key, value, key, value, ..., key, value
	 * @return
	 */
	
	public static Document getGeneratedDocument(MimeMediaType asMimeType, String advType, String ...args) {
		String[] keyval = args;
		
		StructuredDocument adv = StructuredDocumentFactory.newStructuredDocument(asMimeType, advType);
		
		//on verifie qu'on peut ajouter des attributs
		if (adv instanceof Attributable) {
            ((Attributable) adv).addAttribute("xmlns:jxta", "http://jxta.org");
        }
		
		if(keyval.length%2 != 0) throw new IllegalArgumentException("Nombre d'argument doit être pair !");
		
		Element e;
		for(int i = 0; i < keyval.length; i+=2) {
			e = adv.createElement(keyval[i], keyval[i+1]);
			adv.appendChild(e);
		}
		
		return adv;
	}
	
	public static void main(String[] args) {
		Document doc = getGeneratedDocument(MimeMediaType.XMLUTF8, "jxta:advexemple", 
				"titre", "Les bonnes patates", 
				"resume", "des patates fraiche", 
				"desc", "des patates francaise, peut servir a faire des frites");
		
		System.out.println(doc);
	}
	
	
}
