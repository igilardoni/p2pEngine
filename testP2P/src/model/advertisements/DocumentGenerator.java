package model.advertisements;

import net.jxta.document.Attributable;
import net.jxta.document.Document;
import net.jxta.document.Element;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredDocumentFactory;

public class DocumentGenerator {
	
	
	/**
	 * Génère un document structuré (par exemple XML) à partir d'une liste de clé/valeur
	 * @param asMimeType le type de sortie voulu (typiquement XML)
	 * @param advType le type de l'advertisement
	 * @param args key, value, key, value, ..., key, value
	 * @return Un Document conforme pour JXTA :)
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
}
