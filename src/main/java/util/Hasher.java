package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is used for hash a message
 * @author michael
 *
 */
public class Hasher {
	public static String SHA256(String string) {
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
        md.update(string.getBytes());
        byte[] mdbytes = md.digest();
        
        return bytesToHex(mdbytes);
    }
	

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
