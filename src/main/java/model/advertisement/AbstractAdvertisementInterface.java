package model.advertisement;



public interface AbstractAdvertisementInterface {
	
	public String getValue(String key);
	public org.jdom2.Element getRootElement();
	public org.jdom2.Document getJdomDocument();
}
