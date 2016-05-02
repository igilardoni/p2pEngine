package network.api;


import org.jdom2.Document;

import crypt.api.signatures.Signable;

/**
 * Advertise a feature on a service.
 * @param <Sign> signature's type
 * @author Julien Prudhomme
 *
 */
public interface Advertisement<Sign> extends Signable<Sign>{
	/**
	 * Get the advertisement name
	 * @return the advertisement's name
	 */
	public String getName();
	
	/**
	 * Get the advertisement type
	 * @return
	 */
	public String getAdvertisementType();
	
	/**
	 * Publish this advertisement on the network
	 * @param peer
	 */
	public void publish(Peer peer);
	
	/**
	 * Return the XML document representing this advertisement
	 * @return a DOM document
	 */
	public Document getDocument();
	
	/**
	 * Initialise this advertisement with the document
	 * @param doc
	 */
	public void initialize(Document doc);
	
}
