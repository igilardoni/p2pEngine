package controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import view.Application;
import view.Messages;
import model.User;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;



public class AfficherPdf {
	
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	AnnonceEditor annonce;
	User user;
	
	public AfficherPdf(AnnonceEditor annonce) throws IOException{
		this.annonce = annonce;
		this.user = Application.getInstance().getUsers().getConnectedUser();
		try { createPdf();} 
		catch (FileNotFoundException e) { e.printStackTrace();} 
		catch (DocumentException e) { e.printStackTrace();}
	}
	
	private void createPdf() throws DocumentException, IOException{
	
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(annonce.getTitle()+".pdf"));
		document.open();
		
		addMetaData(document);
		addAddress(document);
		addObjet(document);
		addDescription(document);
		
		
		document.close();
	}
	
	private void addMetaData(Document document) {
	    document.addTitle(annonce.getTitle());
	    document.addSubject(annonce.getResDescr());
	}
	
	private void addAddress(Document document) throws DocumentException {
		Paragraph adresse = new Paragraph();
		
		adresse.add(new Paragraph(user.getPrenom()+" "+user.getNom(), smallBold));
	    adresse.add(new Paragraph(user.getAdresse(), smallBold));
	    adresse.add(new Paragraph(user.getMail(), smallBold));
	    adresse.add(new Paragraph(user.getTel(), smallBold));
	    addEmptyLine(adresse, 2);

	    document.add(adresse);
		
	}
	
	private void addObjet(Document document) throws DocumentException {
	    Paragraph objet = new Paragraph();
	   
	    objet.add(new Paragraph(Messages.getString("AfficherPdf.objetAnnonce.text") + annonce.getTitle(), smallBold));
	    addEmptyLine(objet, 2);
	    
	    document.add(objet);
	    
	}
	
	@SuppressWarnings("deprecation")
	private void addDescription(Document document) throws DocumentException, IOException {
		
		HTMLWorker description = new HTMLWorker(document);	   
		description.parse(new StringReader(annonce.getDescrComp()));
		    
	}

	
	private static void addEmptyLine(Paragraph paragraph, int number) {
	    for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
	}

}
