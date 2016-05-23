/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
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
