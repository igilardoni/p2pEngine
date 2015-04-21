package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is used for hash a message
 * @author Michael
 *
 */
public class Hasher {	
	public static String SHA256(String string) {
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        byte[] mdbytes = md.digest();
        return bytesToHex(mdbytes);
    }
	
	public static String SHA256(String passWord, String salt){
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(salt.getBytes("UTF-8"));
			md.update(passWord.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        byte[] mdbytes = md.digest();
        return bytesToHex(mdbytes);
	}

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
