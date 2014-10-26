package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import view.Application;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;


public abstract class AbstractPDFGenerator {	
	
	User user;
	
	public AbstractPDFGenerator() throws IOException, DocumentException{
		this.user = Application.getInstance().getUsers().getConnectedUser();
	}
	
	protected abstract void createPdf() throws FileNotFoundException, DocumentException, IOException;
	
	
	protected void addEmptyLine(Paragraph paragraph, int number) {
	    for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
	}
	
	
	protected void createListString(Section section, ArrayList<String> aList) {
		List list = new List();  
		for(String l : aList)
			list.add(new ListItem(l));
		section.add(list);
	}

}
