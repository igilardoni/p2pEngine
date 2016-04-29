package network.api;

public interface Service {
	
	/**
	 * Service's name
	 * @return
	 */
	public String getName();
	
	/**
	 * Search features on this service
	 * @param sl a search listener
	 */
	public void search(SearchListener<?> sl);
	
	/**
	 * Publish an advertisement on this service
	 * @param adv
	 */
	public void publishAdvertisement(Advertisement<?> adv);
}
