package model;

import java.io.IOException;
import java.util.Map;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;



public class PDFGenerator extends AbstractPDFGenerator {
	
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	
	public PDFGenerator(ObjetPDFModel model) throws IOException, DocumentException{

		this.model = model;
		this.objet = model.getHashMap();
		createPDF();
		
	}
	
	protected void addContent() throws DocumentException, IOException {
		for(Map.Entry<String,String> champ : objet.entrySet()){
			stamp.setField(champ.getKey(), champ.getValue());
		}
	}

/*	 
	private void addMetaData() {
	    document.addTitle("truc");
	    document.addSubject("truc");
	}
	
	private void addAdresseSource() throws DocumentException {
		Paragraph adresse = new Paragraph();
		
		adresse.add(new Paragraph("truc"+" "+"truc", smallBold));
	    adresse.add(new Paragraph("truc", smallBold));
	    adresse.add(new Paragraph("truc", smallBold));
	    adresse.add(new Paragraph("truc", smallBold));
	    addEmptyLine(adresse, 2);

	    document.add(adresse);	
	}
	
	private void addAdresseDest() throws DocumentException {
		Paragraph adresse = new Paragraph();
		
		adresse.add(new Paragraph("truc"+" "+"truc", smallBold));
	    adresse.add(new Paragraph("truc", smallBold));
	    adresse.add(new Paragraph("truc", smallBold));
	    adresse.add(new Paragraph("truc", smallBold));
	    addEmptyLine(adresse, 2);

	    document.add(adresse);	
	}
	
	private void addObjet() throws DocumentException {
	    Paragraph objetDoc = new Paragraph();
	   
	    objetDoc.add(new Paragraph(Messages.getString("AfficherPdf.objetAnnonce.text") + "truc", smallBold));
	    addEmptyLine(objetDoc, 2);
	    
	    document.add(objetDoc);   
	}
	
	private void addHTML() throws DocumentException, IOException {
		
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(".//test.pdf"));    
		XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
		 
		worker.parseXHtml(pdfWriter, document, new StringReader("truc"));
		
	}
*/
	
}
