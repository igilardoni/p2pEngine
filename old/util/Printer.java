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
package util;

public  class Printer {

	public static void printInfo(Object o, String method, String info){
		System.out.println("INFO : "+ o.getClass().getName()+ "."+method+" : "+info);
	}
	public static boolean printError(Object o, String method, String error){
		System.err.println("ERROR : "+ o.getClass().getName()+"."+method+" : "+error);
		return false;
	}
	
}
