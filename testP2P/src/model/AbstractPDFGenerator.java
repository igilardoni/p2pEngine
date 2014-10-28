package model;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import model.ObjetPDFModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


public abstract class AbstractPDFGenerator {	
	
	protected Document document;
	protected ObjetPDFModel model;
	protected AcroFields stamp;
	protected HashMap<String,String> objet;
	
	
	protected void createPDF() throws DocumentException, IOException{
		
		
		PdfReader pdfTemplate = new PdfReader(objet.get("modele"));
		FileOutputStream fileOutputStream = new FileOutputStream(objet.get("filename"));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfStamper stamper = new PdfStamper(pdfTemplate, fileOutputStream);
		
		stamp = stamper.getAcroFields();

		addContent();
		
		
		stamper.setFormFlattening(true);
		stamp.setGenerateAppearances(true);
		stamper.close();
		pdfTemplate.close();
		
		fileOutputStream.close();
		
		
	}
	
	protected abstract void addContent() throws DocumentException, IOException;
	
	/*
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
	*/

}
