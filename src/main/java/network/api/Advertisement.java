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

	 * Initialize this advertisement with an xml document
	 * @param doc
	 */
	public void initialize(Document doc);
	
	/**
	 * Generate an xml document describing this advertisement
	 * @return
	 */
	public Document getDocument();
	
	/**
	 * Get a string array of field that are used for indexing this advertisement
	 * @return
	 */
	public String[] getIndexFields();
	
}
