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
