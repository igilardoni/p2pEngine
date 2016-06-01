package network.api;

public interface Messages {
	/**
	 * Get message content by this name (field)
	 * @param name
	 * @return
	 */
	public String getMessage(String name);
	
	/**
	 * Get all the messages fields
	 * @return
	 */
	public String[] getNames();
}
