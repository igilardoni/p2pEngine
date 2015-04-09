package view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Verifie le format de l'image mise dans l'annonce
 * @author
 *
 */

@SuppressWarnings("serial")
public class ImageDialog extends JFileChooser{

	public ImageDialog() {
		this.setFileFilter(new FileFilter() {

			public boolean accept(File f) {
				String name = f.getName();
				if(name.endsWith(".png")) return true;
				if(name.endsWith(".jpg")) return true;
				if(name.endsWith(".jpeg")) return true;
				if(name.endsWith(".bmp")) return true;
				if(name.endsWith(".gif")) return true;
				return false;
			}

			public String getDescription() {
				return Langues.getString("ImageDialog.formatImages.text");
			}
		});
	}	
}
