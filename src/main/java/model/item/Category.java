package model.item;

/**
 * This class contain a list of category permitted by the application.
 * This class can be instantiated for contain the category of an item.
 */
public class Category {
	
	/*
	 * This is just for translate text of categories.
	 */
	public static String linkText = "Category.CATEGORY.";
	
	/**
	 * CATEGORY is the list of Category permitted.
	 */
	public static enum CATEGORY {
		Appliances,
		Baby,
		Beauty,
		Books,
		ClothingAccessories,
		Clothing,
		Computers,
		FoodAndBeverages,
		Health,
		Home,
		Industrial,
		Jewelry,
		Music,
		MusicalInstruments,
		Office,
		OtherProducts,
		Photography,
		Shoes,
		Sports,
		Tools,
		Toys,
		Vehicles,
		Video,
		VideoGames,
		OtherServices,
		PersonalCaring,
		Teaching,
		Transport,
		NC
	}; 
	
	private CATEGORY choice;
	
	/**
	 * Constructor initialized by a CATEGORY chosen
	 * @param choice
	 */
	public Category(CATEGORY choice){
		setChoice(choice);
	}
	
	public Category(String choice){
		for(CATEGORY c: CATEGORY.values()) {
			String choiceLowCase = choice.toLowerCase();
			String catLowCase = c.toString().toLowerCase();
			if(catLowCase.equals(choiceLowCase)){
				this.choice = c;
				return;
			}
		}
		this.choice = CATEGORY.NC;
	}
	
	/**
	 * Return the choice
	 * @return
	 */
	public CATEGORY getChoice() {
		return choice;
	}
	
	/**
	 * Return the choice format String
	 * @return
	 */
	public String getStringChoice() {
		return choice.name();
	}
	
	/**
	 * Define the choice
	 * @param choice CATEGORY
	 */
	public void setChoice(CATEGORY choice) {
		this.choice = choice;
	}
}
