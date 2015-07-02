package util;

import model.data.manager.Manager;

public  class Printer {

	public static void printInfo(Object o, String method, String info){
		System.out.println("INFO : "+ o.getClass().getName()+ "."+method+" : "+info);
	}
	public static boolean printError(Object o, String method, String error){
		System.err.println("ERROR : "+ o.getClass().getName()+"."+method+" : "+error);
		return false;
	}
	
}
