package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import net.jxta.impl.util.Base64;

@SuppressWarnings("deprecation")
public class ImageBase64 {
	public static String encode(String path) {
		File file = new File(path);
		byte[] imageData = new byte[(int) file.length()];
		FileInputStream imageInFile;
		try {
			imageInFile = new FileInputStream(file);
			imageInFile.read(imageData);
			imageInFile.close();
		} catch (IOException e) {	
			e.printStackTrace();
		}
		
		return Base64.encodeBase64(imageData);
	}
	
	
	public static ImageIcon decode(String encoded) {
		try {
			return new ImageIcon(Base64.decodeBase64(encoded));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
