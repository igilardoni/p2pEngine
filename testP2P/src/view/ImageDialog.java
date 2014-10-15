package view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class ImageDialog extends JFileChooser{

	public ImageDialog() {
		this.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				String name = f.getName();
				if(name.endsWith(".png")) return true;
				if(name.endsWith(".jpg")) return true;
				if(name.endsWith(".jpeg")) return true;
				if(name.endsWith(".bmp")) return true;
				if(name.endsWith(".gif")) return true;
				return false;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "Images(png, jpg, jpeg, bmp, gif)";
			}
			
		});
	}
	
}
