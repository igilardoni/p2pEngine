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
package view.interlocutors;

import javax.websocket.RemoteEndpoint.Async;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public abstract class AbstractInterlocutor {
	public static Async com;
	protected String content;

	public static void setCom(Async com){
		AbstractInterlocutor.com = com;
	}
	
	public void init(String content){
		this.content = content;
	}
	
	public void reset(){
		this.content = null;
	}
	
	public boolean isInitialized(){
		return this.content != null && AbstractInterlocutor.com != null;
	}
	
	public abstract void run();

	protected static JSONObject getJSON(String string){
		try {
			return new JSONObject(string);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
