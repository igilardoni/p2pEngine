package util;

import java.security.MessageDigest;

public class Hasher {
	public static String SHA256(String string) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
 
        byte[] dataBytes = new byte[1024];
 
        byte[] bits = string.getBytes();
        for (int i = 0; i < bits.length; i++) {
			md.update(dataBytes, 0, bits[i]);
		}
        byte[] mdbytes = md.digest();
        
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<mdbytes.length;i++) {
    	  hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
    	}
    	
    	return hexString.toString();
    }
	
	public static void main(String[] args){
		String message = "Ceci est un test !";
		System.out.println(message);
		try {
			System.out.println(SHA256(message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
