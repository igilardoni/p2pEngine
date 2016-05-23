package network.impl.jxta;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import org.jdom2.Element;

import net.jxta.document.Advertisement;
import net.jxta.document.Attributable;
import net.jxta.document.Document;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredDocumentFactory;
import net.jxta.document.TextElement;
import net.jxta.id.ID;
import protocol.impl.sigma.ElGamalSign;

public class AdvertisementBridge extends Advertisement{

	private network.api.Advertisement<ElGamalSign> adv;
	
	public AdvertisementBridge(network.api.Advertisement<ElGamalSign> adv) {
		this.adv = adv;
	}
	
	/**
	 * Create a new AdvertisementBridge instance initialized with a Jxta xml root element.
	 * @param root
	 */
	@SuppressWarnings("unchecked")
	public AdvertisementBridge(@SuppressWarnings("rawtypes") net.jxta.document.Element root) {
		@SuppressWarnings("rawtypes")
		TextElement doc = (TextElement) root;
		@SuppressWarnings("rawtypes")
		TextElement className = (TextElement) doc.getChildren("advertisementClass").nextElement();
		try {
			//try to find the class used for this advertisement
			Class<?> adv = Class.forName(className.getValue());
			Constructor<?> cons = adv.getConstructor();
			this.adv = (network.api.Advertisement<ElGamalSign>) cons.newInstance();
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		Element rootElement = new Element("root");
		@SuppressWarnings("rawtypes")
		Enumeration elements = doc.getChildren();
        while(elements.hasMoreElements()) {
        	@SuppressWarnings("rawtypes")
			TextElement elem = (TextElement) elements.nextElement();
        	if(elem.getName().equals("advertisementClass")) {
        		continue;
        	}
        	Element e = new Element(elem.getName()); //convert into a Jdom element.
        	e.addContent(elem.getValue());
        	rootElement.addContent(e);
        }
        this.adv.initialize(new org.jdom2.Document(rootElement));
	}
	
	/**
	 * {@inheritDoc}
	 * @param asMimeType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Document getDocument(MimeMediaType asMimeType) {
		@SuppressWarnings("rawtypes")
		StructuredDocument adv = StructuredDocumentFactory.newStructuredDocument(asMimeType, getAdvType());
		if (adv instanceof Attributable) {
            ((Attributable) adv).addAttribute("xmlns:jxta", "http://jxta.org");
        }
		
		@SuppressWarnings("rawtypes")
		net.jxta.document.Element cn = adv.createElement("advertisementClass", this.adv.getClass().getCanonicalName());
		adv.appendChild(cn);
		
		for(Element e : this.adv.getDocument().getRootElement().getChildren()) {
			@SuppressWarnings("rawtypes")
			net.jxta.document.Element e1 = adv.createElement(e.getName(), e.getValue());
			adv.appendChild(e1);
		}
		return adv;
	}

	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public ID getID() {
		// TODO voir si on génère un ID pour les adv
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getIndexFields() {
		if(adv == null) {
			throw new RuntimeException("Adv is null");
		}
		return adv.getIndexFields();
	}
	
	@Override
	public String getAdvType() {
		return "jxta:" + this.getClass().getName();
	}

	public network.api.Advertisement<ElGamalSign> getAdvertisement() {
		return adv;
	}
	
}
