package model.data.item;

import model.advertisement.AbstractAdvertisement;
import model.advertisement.AdvertisementInstaciator;
import model.data.item.Category.CATEGORY;
import model.data.user.User;
import net.jxta.document.AdvertisementFactory;

import org.jdom2.Element;

import util.VARIABLES;
import util.secure.AsymKeysImpl;

/**
 * This class can be instantiated for contains an item.
 * This class extends AbstractAdvertisement and can be used like an advertisement.
 * @author Michael Dubuis
 * @author Julien Prudhomme
 */
public class Item extends AbstractAdvertisement implements Comparable<Item>{
	
	/**
	 * TYPE is the list of exchange type permitted.
	 */
	public static enum TYPE{
		OFFER,					// If object is proposed
		DEMAND						// If object is needed
	};
	
	private String friendlyNick;	// Friendly-user Pseudo of owner
	private String title;			// Title of the object
	private Category category;		// Category of the object
	private String description;		// Big description of the object
	private String image;			// Image of the object (convert with Base64)
	private String country;			// Country of the object
	private String contact;			// Description of method for contact the owner
	private long date;				// Date of post/update
	private long lifeTime;			// LifeTime of the object (at the end of this, the object is delete)
	private TYPE type;				// Proposal/Wish
	
	/**
	 * Constructor of Item.
	 * This constructor will generated an Id based on hashed random number and owner concatenation.
	 * @param owner
	 * @param title
	 * @param category
	 * @param description
	 * @param image
	 * @param zone
	 * @param contact
	 * @param date - if equals zero date will be current Time
	 * @param lifeTime
	 * @param type
	 */
	public Item(AsymKeysImpl owner, String friendlyNick,
			String title, Category category, String description, 
			String image, String country, String contact, 
			long date, long lifeTime,TYPE type){
		super();
		this.setKeys(owner); //TODO ENCRYPT
		this.setFriendlyNick(friendlyNick);
		this.setTitle(title);
		this.setCategory(category);
		this.setDescription(description);
		this.setImage(image);
		this.setCountry(country);
		this.setContact(contact);
		if(date == 0)
			this.setDate(System.currentTimeMillis());
		else
			this.setDate(date);
		this.setLifeTime(lifeTime);
		this.setType(type);
		setKeys();
		setType(type);
		setKeys();
	}
	
	/**
	 * Constructor in case of User Object is passed in parameter
	 * @param owner
	 * @param title
	 * @param category
	 * @param description
	 * @param image
	 * @param country
	 * @param contact
	 * @param date
	 * @param lifeTime
	 * @param type
	 */
	public Item(User owner, String title,
			Category category, String description, String image,
			String country,String contact,long date,long lifeTime,TYPE type){
		this(owner.getKeys(), owner.getNick(),title, 
				category, description, image, country, contact, date, lifeTime, type);
	}
	
	/**
	 * Empty Constructor
	 */
	public Item(){
		super();
	}
	
	/**
	 * Construct an Item based on a XML, well and known formated string.
	 * @param XML
	 */
	public Item(String XML) {
		super(XML);
	}
	
	public Item(Element i) {
		super(i);
	}
	
	@SuppressWarnings("rawtypes")
	public Item(net.jxta.document.Element e) {
		super(e);
	}

	/**
	 * Return the owner of this Item
	 * @return
	 */
	public String getOwner() {
		return getKeys().getPublicKey().toString(16);
	}
	
	/**
	 * Define the owner of this Item (with User Object in parameter)
	 * @param owner
	 */
	public void setOwner(User owner){
		setKeys(owner.getKeys());
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
	 * @param friendLYNick
	 */
	public void setFriendlyNick(String friendLYNick){
		this.friendlyNick = friendLYNick;
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
		this.date = date==0 ? System.currentTimeMillis() : date;
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
	public boolean isActive(){
		if(lifeTime == 0)
			return true;
		if((date + lifeTime)>System.currentTimeMillis())
			return true;
		return false;
	}
	
	/**
	 * If the lifetime is exceeded or the ownerLastConnection is older than LifeTimeAfterDisconnected return false, true else
	 * @param ownerLastConnection
	 * @return
	 */
	public boolean isAlive(long ownerLastConnection){
		if(lifeTime == 0 &&
				ownerLastConnection + VARIABLES.LifeTimeAfterDisconnected > System.currentTimeMillis())
			return true;
		if((date + lifeTime)>System.currentTimeMillis() &&
				ownerLastConnection + VARIABLES.LifeTimeAfterDisconnected > System.currentTimeMillis())
			return true;
		return false;
	}
	
	/**
	 * Get an unique id for this item
	 * TODO have to change
	 * @return
	 */
	public String getItemKey(){
		return this.getId();
	}
	 
	@Override
	protected void setKeys() {
		this.addKey("friendNick", false, true);
		this.addKey("title", true, true);
		this.addKey("category",true, true);
		this.addKey("description",false, true);
		this.addKey("image",false, true);
		this.addKey("country",true, true);
		this.addKey("contact",false, true);
		this.addKey("date",true, false);
		this.addKey("lifeTime",false, true);
		this.addKey("type", true, true);
	}
	
	/**
	 * Used to add all keys
	 */
	@Override
	protected void putValues() {
		addValue("friendNick", this.getFriendNick());
		addValue("title", this.getTitle());
		addValue("category", category.getStringChoice());
		addValue("description", this.getDescription());
		addValue("image", this.getImage());
		addValue("country", this.getCountry());
		addValue("contact", this.getContact());
		addValue("date", String.valueOf(this.getDate()));
		addValue("lifeTime", String.valueOf(this.getLifeTime()));
		addValue("type", this.getType().toString());
	}

	@Override
	protected String getAdvertisementName() {
		return this.getClass().getName();
	}

	@Override
	protected boolean handleElement(org.jdom2.Element e) {
		String val = e.getText();
		switch(e.getName()){
		case "friendNick":
			setFriendlyNick(val);
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
			if(val.toLowerCase().equals(TYPE.OFFER.toString().toLowerCase()))
				setType(TYPE.OFFER);
			else if(val.toLowerCase().equals(TYPE.DEMAND.toString().toLowerCase()))
				setType(TYPE.DEMAND);
			else {
				return false;
			}
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * Give the good class to the constructor
	 */
	public static void register() {
		Item i = new Item();
		System.out.println(i.getAdvType());
		AdvertisementFactory.registerAdvertisementInstance(i.getAdvType(), new AdvertisementInstaciator(i));
	}
	/////////////////////////////////////////////////// OVERRIDE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	/**
	 * @return boolean 0 if both are identical, 1 else
	 */
	@Override
	public int compareTo(Item item) {
		if(this.exactlyEquals(item))
			return 0;
		if(!this.getOwner().equals(item.getOwner()))
			return this.getOwner().compareTo(item.getOwner());
		if(!this.getTitle().equals(item.getTitle()))
			return this.title.compareTo(item.getTitle());
		if(this.getDate() != item.getDate())
			return this.getDate() > item.getDate() ? 1 : -1;
		if(!this.getCategory().equals(item.getCategory()))
			return this.getCategory().getStringChoice().compareTo(item.getCategory().getStringChoice());
		if(!this.getCountry().equals(item.getCountry()))
			return this.getCountry().compareTo(item.getCountry());
		if(this.getLifeTime() != item.getLifeTime())
			return this.getLifeTime() > item.getLifeTime() ? 1 : -1;
		if(!this.getType().name().equals(item.getType().name()))
			return this.getType().name().compareTo(item.getType().name());
		if(!this.getDescription().equals(item.getDescription()))
			return this.getDescription().compareTo(item.getDescription());
		return 0;
	}
	
	public boolean exactlyEquals(Object i){
		if(!(i instanceof Item))
			return false;
		Item item = (Item) i;
		if(!this.getItemKey().equals(item.getItemKey()))
			return false;
		if(!this.getOwner().equals(item.getOwner()) ||
				!this.getFriendNick().equals(item.getFriendNick()) ||
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
			return false;
		return true;
	}
	
	@Override
	public boolean equals(Object i){
		if(!(i instanceof Item))
			return false;
		Item item = (Item) i;
		if(!this.getItemKey().equals(item.getItemKey()))
			return false;
		return true;
	}



	public static void main(String[] arg){
		User user = new User("nick", "passWord", "name", "firstName", "email", "phone");
		
		Item item1 = new Item(user, "title", new Category(CATEGORY.NA), "description", "image", "country", "contact", 0L, 0L, TYPE.OFFER);
		Item item2 = new Item(user, "title", new Category(CATEGORY.NA), "description", "image", "country", "contact", 0L, 0L, TYPE.OFFER);
		Item item1Copy = new Item(item1.toString());
		System.out.println(item1.getItemKey());
		System.out.println(item1Copy.getItemKey());
		System.out.println(item2.getItemKey());
		
		item1.sign(user.getKeys());
		System.out.println(item1.checkSignature(user.getKeys()));
	}

	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}
}
