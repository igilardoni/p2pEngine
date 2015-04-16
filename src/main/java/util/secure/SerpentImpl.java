package util.secure;

import gnu.crypto.cipher.CipherFactory;
import gnu.crypto.cipher.IBlockCipher;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

public class SerpentImpl {
	/*
	 * TODO A COMPRENDRE.
	 * La Librairie semble bien, mais la documentation est assez peu détaillée !
	 */
	public static void main(String[] args){
		byte[] key_bytes = {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1};
		String message = "Test Test Test Test Test Test Test Test Test Test Test Test ";
		byte[] messByte = message.getBytes();
		byte[] cryptByte = new byte[messByte.length+100];
		byte[] repo = new byte[messByte.length+100];
		
		IBlockCipher cipher = CipherFactory.getInstance("Serpent");
	     Map attributes = new HashMap();
	     attributes.put(IBlockCipher.CIPHER_BLOCK_SIZE, new Integer(16));
	     attributes.put(IBlockCipher.KEY_MATERIAL, key_bytes);
	     try {
			cipher.init(attributes);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     int bs = cipher.currentBlockSize();
	     
	     System.out.println(new String(messByte));
	     System.out.println();
	     for (int i = 0; i + bs < messByte.length; i += bs)
	        {
	           cipher.encryptBlock(messByte, i, cryptByte, i);
	        }
	     System.out.println(new String(cryptByte));
	     System.out.println();
	     for (int i = 0; i + bs < messByte.length; i += bs)
	        {
	           cipher.decryptBlock(cryptByte, i, repo, i);
	        }
	     System.out.println(new String(repo));
	}
	
	public static String toHex(byte[] t){
		String repo = "";
		for (int i = 0; i < t.length; i++) {
			repo += t[i];
		}
		return repo;
	}
}
