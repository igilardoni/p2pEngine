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
package view;

import org.codehaus.jettison.json.JSONObject;
import javax.websocket.RemoteEndpoint.Async;

public class Answer extends Thread {
	public static Async com = null;
	public JSONObject data = null;
	
	public static void setCom(Async com){
		Answer.com = com;
	}
	
	private Answer(JSONObject data) {
		this.data = data;
	}
	
	public static void reply(JSONObject data) throws Exception {
		if(com == null) throw new Exception("Answer not initialized");
		(new Answer(data)).start();
	}
	
	@Override
	public void run() {
		com.sendText(data.toString());
	}
}
