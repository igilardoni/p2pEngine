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
package view.interlocutors.contrat;

import view.interlocutors.AbstractInterlocutor;

public class SignContrat extends AbstractInterlocutor {

	public SignContrat() {
	}

	@Override
	public void run() {
		if(!isInitialized())
			return;
		try {
			System.out.println("Trying to sign contract... Next release maybe ?");
			// TODO Interlocutor signature contract 
		} finally {
			this.reset();
		}
	}

}
