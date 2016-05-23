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
package util.secure.AVProtocol;

import java.util.ArrayList;

/**
 * set TTP
 * number n paticipant, k min, set participant 
 * @author sarah
 *
 */

public class TTP {

	private int n ;
	private int k ;
	private ArrayList<ParticipantEx> Participants = new ArrayList <ParticipantEx>();
	
	public TTP (int n, int k)
	{
		setN(n);
		setK(k);
	}
	
	public int getN() {
		return n;
	}
	
	public void setN(int n) {
		this.n = n;
	}
	
	public int getK() {
		return k;
	}
	
	public void setK(int k) {
		this.k = k;
	}
	
	public ArrayList<ParticipantEx> getParticipants() {
		return Participants;
	}
	
	public ParticipantEx getParticipant( int index) {
		return Participants.get(index);
	}
	
	public void setParticipants(ArrayList<ParticipantEx> Participants) {
		this.Participants = Participants;
	}
	
	public void addParticipant(ParticipantEx Participant) {
		Participants.add(Participant);
	}
	
}
