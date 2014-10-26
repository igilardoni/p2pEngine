package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import view.Messages;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;



@SuppressWarnings("deprecation")
public class PDFGenerator extends AbstractPDFGenerator {
	
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	Objet objet;
	
	public PDFGenerator(Objet objet) throws IOException, DocumentException{
		super();
		this.objet = objet;
		createPdf();
	}
	
	protected void createPdf() throws DocumentException, IOException{
	
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(objet.getTitre()+".pdf"));
		document.open();
		
		addMetaData(document);
		addAddress(document);
		addObjet(document);
		addDescription(document);	
		
		document.close();
	}
	
	private void addMetaData(Document document) {
	    document.addTitle(objet.getTitre());
	    document.addSubject(objet.getResume());
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
	    Paragraph objetDoc = new Paragraph();
	   
	    objetDoc.add(new Paragraph(Messages.getString("AfficherPdf.objetAnnonce.text") + objet.getTitre(), smallBold));
	    addEmptyLine(objetDoc, 2);
	    
	    document.add(objetDoc);   
	}
	
	private void addDescription(Document document) throws DocumentException, IOException {
		
		HTMLWorker description = new HTMLWorker(document);	   
		description.parse(new StringReader(objet.getResume()));    
	}

}
