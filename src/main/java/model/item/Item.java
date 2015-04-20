package model.item;

import model.advertisement.AbstractAdvertisement;

/**
 * This class can be instantiated for contains an item.
 * This class extends AbstractAdvertisement and can be used like an advertisement.
 */
public class Item extends AbstractAdvertisement implements Comparable<Item>{
	
	/**
	 * TYPE is the list of exchange type permitted.
	 */
	public static enum TYPE{
		WISH,						// If object is needed
		PROPOSAL					// If object is proposed
	};
	
	private String owner;			// Owner of the object
	private String friendlyNick;	// Friendly-user Pseudo of owner
	private String title;			// Title of the object
	private Category category;		// Category of the object
	private String description;		// Big description of the object
	private String image;			// Image of the object (convert with Base64)
	private String country;			// Country of the object (TODO add city and more if needed)
	private String contact;			// Description of method for contact the owner
	private long date;				// Date of post/update
	private long lifeTime;			// LifeTime of the object (at the end of this, the object is delete)
	private TYPE type;				// Proposal/Wish
	
	/**
	 * Constructor of Item
	 * @param owner
	 * @param title
	 * @param category
	 * @param description
	 * @param image
	 * @param zone
	 * @param contact
	 * @param date
	 * @param lifeTime
	 * @param type
	 */
	public Item(String owner,String title,
			Category category, String description, String image,
			String country,String contact,long date,long lifeTime,TYPE type){
		super();
		this.owner = owner;
		this.title = title;
		this.category = category;
		this.description = description ;
		this.image = image;
		this.country = country;
		this.contact = contact;
		this.date = date;
		this.lifeTime = lifeTime;
		this.type = type;
		setKeys();
	}
	
	/**
	 * Empty Constructor
	 */
	public Item(){
	}
	
	/**
	 * Return the owner of this Item
	 * @return
	 */
	public String getOwner() {
		return owner;
	}
	
	/**
	 * Define the owner of this Item
	 * @param owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	/**
	 * Return the friendly-user nickname of the owner
	 * @return
	 */
	public String getFriendNick(){
		return friendlyNick;
	}
	
	/**
	 * Define the friendly-user nickname of the owner
	 * @param friendNick
	 */
	public void setFriendNick(String friendNick){
		this.friendlyNick = friendNick;
	}
	
	/**
	 * Return the title of this Item
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Define the title of this Item
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Return the Category of this Item
	 * @return Category class
	 */
	public Category getCategory() {
		return category;
	}
	
	/**
	 * Define the Category of this Item
	 * @param category - Category class
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * Return the description of this Item
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Define the description of this Item
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Return the image of this Item
	 * This image will be in Base64 encoding
	 * @return
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Define the image of this Item
	 * This image will be in Base64 encoding
	 * @param image
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * Return the location of this Item
	 * @return Locale class (Java)
	 */
	public String getZone() {
		return country;
	}
	
	/**
	 * Define the Country of this Item
	 * @param language
	 * @param country
	 */
	public void setCountry(String country){
		this.country = country;
	}
	
	/**
	 * Return the Country of this Item
	 * @return
	 */
	public String getCountry(){
		return country;
	}
	
	/**
	 * Return the owner contact method of this Item
	 * @return
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * Define the owner contact method of this Item
	 * @param contact
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	/**
	 * Return date creation of this Item
	 * @return long based of epoch time
	 */
	public long getDate() {
		return date;
	}
	
	/**
	 * Define the time creation of this Item
	 * @param date - long based of epoch time
	 */
	public void setDate(long date) {
		this.date = date;
	}

	/**
	 * Return the life time of this Item
	 * @return
	 */
	public long getLifeTime() {
		return lifeTime;
	}
	
	/**
	 * Define the life time of this Item
	 * @param lifeTime
	 */
	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}
	
	/**
	 * Return the type of this Item
	 * @return
	 */
	public TYPE getType() {
		return type;
	}
	
	/**
	 * Define the type of this Item
	 * @param type
	 */
	public void setType(TYPE type) {
		this.type = type;
	}
	
	/**
	 * Return this Item
	 * @return
	 */
	public Item getItem(){
		return this;
	}
	
	/**
	 * If the lifetime is exceeded return false, true else
	 * @return
	 */
	public boolean isAlive(){
		if((date + lifeTime)>=System.currentTimeMillis())
			return true;
		return false;
	}
	
	//////////////////////////////////////////////// ADVERTISEMENT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Used to define Keys
	 */
	@Override
	protected void setKeys() {
		this.addKey("owner", true);
		this.addKey("friendNick", false);
		this.addKey("title", true);
		this.addKey("category",true);
		this.addKey("description",false);
		this.addKey("image",false);
		this.addKey("zone",true);
		this.addKey("contact",false);
		this.addKey("date",true);
		this.addKey("lifeTime",false);
		this.addKey("type", true);
	}
	
	/**
	 * Used to add all keys
	 */
	@Override
	protected void putValues() {
		addValue("owner", this.getOwner());
		addValue("friendNick", this.getFriendNick());
		addValue("title", this.getTitle());
		addValue("category", category.getStringChoice());
		addValue("description", this.getDescription());
		addValue("image", this.getImage());
		addValue("zone", this.getCountry());
		addValue("contact", this.getContact());
		addValue("date", String.valueOf(this.getDate()));
		addValue("lifeTime", String.valueOf(this.getLifeTime()));
		addValue("type", this.getType().toString());
	}

	@Override
	protected String getAdvertisementName() {
		return this.getClass().getSimpleName();
	}

	@Override
	protected boolean handleElement(org.jdom2.Element e) {
		String val = e.getText();
		switch(e.getName()){
		case "owner":
			setOwner(val);
			return true;
		case "friendNick":
			setFriendNick(val);
			return true;
		case "title":
			setTitle(val);
			return true;
		case "category":
			setCategory(new Category(val));
			return true;
		case "description":
			setDescription(val);
			return true;
		case "image":
			setImage(val);
			return true;
		case "country":
			setCountry(val);
			return true;
		case "contact":
			setContact(val);
			return true;
		case "date":
			setDate(Long.parseLong(val));
			return true;
		case "lifeTime":
			setLifeTime(Long.parseLong(val));
			return true;
		case "type":
			if(val.equals(TYPE.PROPOSAL.toString().toLowerCase()))
				setType(TYPE.PROPOSAL);
			else if(val.equals(TYPE.WISH.toString().toLowerCase()))
				setType(TYPE.WISH);
			else
				return false;
			return true;
		default:
			return false;
		}
	}
	
	////////////////////////////////////////////////// COMPARABLE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	@Override
	public int compareTo(Item item) {
		if(!this.getOwner().equals(item.getOwner()) || 
				!this.getTitle().equals(item.getTitle()) ||
				!this.getCategory().toString().equals(item.getCategory().toString()) ||
				!this.getDescription().equals(item.getDescription()) ||
				!this.getImage().equals(item.getImage()) ||
				!this.getCountry().equals(item.getCountry()) ||
				!this.getContact().equals(item.getContact()) ||
				this.getDate() != item.getDate() ||
				this.getLifeTime() != item.getLifeTime() ||
				!this.getType().toString().equals(item.getType().toString())
				)
			return 1;
		return 0;
	}
}
