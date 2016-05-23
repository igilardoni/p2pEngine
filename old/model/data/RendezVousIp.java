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
package model.data;

import java.util.ArrayList;

import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import model.advertisement.AbstractAdvertisement;
import util.StringToElement;

/**
 * Keep Rendez vous' ips. Start Bootstrap. Maybe sharing ?
 * @author Julien Prudhomme
 *
 */
public class RendezVousIp extends AbstractAdvertisement {
	private Element ips;

	
	public RendezVousIp() {
		super();
	}
	
	public RendezVousIp(Element root) {
		super(root);
	}
	
	public RendezVousIp(String xml) {
		super(xml);
	}
	
	@SuppressWarnings("rawtypes")
	public RendezVousIp(net.jxta.document.Element root) {
		super(root);
	}
	
	public ArrayList<String> getIps() {
		ArrayList<String> iparray = new ArrayList<String>();
		for(Element e : ips.getChildren()) {
			iparray.add(e.getValue());
		}
		return iparray;
	}
	
	public void addIp(String ip) {
		Element e = new Element("ip");
		e.addContent(ip);
		ips.addContent(e);
	}
	
	public void removeIp(String ip) {
		for(Element e : ips.getChildren()) {
			if(e.getValue().equals(ip)) {
				e.detach();
			}
		}
	}
	
	
	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}

	@Override
	protected String getAdvertisementName() {
		return getClass().getName();
	}

	@Override
	protected void setKeys() {
		ips = new Element("allips");
		addKey("ips", false, true);
	}

	@Override
	protected void putValues() {
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		addValue("ips", out.outputString(ips));
	}

	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "ips": ips = StringToElement.getElementFromString(e.getValue()); return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		RendezVousIp rdvip = new RendezVousIp();
		rdvip.addIp("tcp://85.240.163.6:9800");
		rdvip.addIp("tcp://120.120.120.120:9800");
		rdvip.addIp("tcp://125.125.125.125:9800");
		rdvip.removeIp("tcp://120.120.120.120:9800");
		
		RendezVousIp rdv2 = (RendezVousIp) rdvip.clone();
		for(String s: rdv2.getIps()) {
			System.out.println(s);
		}
		
	}
}
