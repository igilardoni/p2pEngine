package model.data.contrat;

import org.jdom2.Element;

import model.advertisement.AbstractAdvertisement;

/**
 * @author Michael Dubuis
 *
 */
public class Clause extends AbstractAdvertisement {
	String title;
	String content;
	
	public Clause() {
		super();
	}
	public Clause(String title, String content){
		super();
		setTitle(title);
		setContent(content);
	}
	public Clause(String xml) {
		super(xml);
	}
	public Clause(Element e) {
		super(e);
	}
	
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String getSimpleName() {
		return this.getSimpleName();
	}
	@Override
	protected String getAdvertisementName() {
		return this.getClass().getName();
	}
	@Override
	protected void setKeys() {
		title = "";
		content = "";
		this.addKey("title", false, true);
		this.addKey("content", false, false);
	}
	@Override
	protected void putValues() {
		this.addValue("title", title);
		this.addValue("content", content);
	}
	@Override
	protected boolean handleElement(Element e) {
		String val = e.getText();
		switch(e.getName()){
		case "title" :
			this.title = val;
			return true;
		case "content" :
			this.content = val;
			return true;
		default :
			return false;			
		}
	}
}
