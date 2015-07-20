package model.network.search.extended;

import model.data.item.Item;

/**
 * Filter items
 * @author Julien Prudhomme
 *
 */
public class ItemFilter extends BaseExtendedListener<Item>{

	private String user = null;
	private String title = null;
	private String country = null;
	private Item.TYPE type = null;
	
	/**
	 * Create a new Item filter instance
	 * Let an arg null -> no filter on this arg
	 * @param user will keep item only if user is equal
	 * @param title will keep item only if title is equal
	 * @param country
	 * @param type
	 */
	public ItemFilter(String user, String title, String country, Item.TYPE type) {
		this.user = user;
		this.title = title;
		this.country = country;
		this.type = type;
	}
	
	@Override
	public boolean filter(Item event) {
		if(!(user != null && event.getFriendNick().equals(user))) {
			return false;
		}
			
		if(!(title != null && event.getTitle().equals(title))) {
			return false;
		}
		
		if(!(country != null && event.getCountry().equals(country))) {
			return false;
		}
		
		if(!(type != null && event.getType().equals(type))) {
			return false;
		}
		return true;
	}

	

}
