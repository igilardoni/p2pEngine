package model;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


public abstract class AbstractPdfGenerator {	
	
	protected Document document;
	protected ObjetPdfModel model;
	protected AcroFields stamp;
	protected PdfStamper stamper;
	protected HashMap<String,String> texte;
	protected HashMap<String,String> image;
	protected HashMap<String,Boolean> bool;
	
	protected void createPDF() throws DocumentException, IOException{
		
		
		PdfReader pdfTemplate = new PdfReader("modeles/"+texte.get("modele")+".pdf");
		FileOutputStream fileOutputStream = new FileOutputStream("pdf/"+texte.get("filename")+".pdf");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		stamper = new PdfStamper(pdfTemplate, fileOutputStream);
		
		stamp = stamper.getAcroFields();

		addContent();
		
		
		stamper.setFormFlattening(true);
		stamp.setGenerateAppearances(true);
		stamper.close();
		pdfTemplate.close();
		
		fileOutputStream.close();
		
		
	}
	
	protected abstract void addContent() throws DocumentException, IOException;
	
	
	
	protected void addTexte() throws IOException, DocumentException{
		for(Map.Entry<String,String> champ : texte.entrySet()){
			stamp.setField(champ.getKey(), champ.getValue());
		}
	}
	
	protected void addImage() throws MalformedURLException, IOException, DocumentException{
		for(Map.Entry<String,String> champ : image.entrySet()){
			
			Image img = Image.getInstance(String.format("%s", champ.getValue()));

			List<AcroFields.FieldPosition> positions = stamp.getFieldPositions(champ.getKey());
			Rectangle rect = positions.get(0).position;
		
			int page = positions.get(0).page;

			img.scaleAbsolute(rect.getWidth(), rect.getHeight());
			img.setAbsolutePosition(rect.getLeft(), rect.getBottom());

			stamper.getOverContent(page).addImage(img);
		}
	}
	
	protected void addCheckBox() throws IOException, DocumentException{
		for(Map.Entry<String,Boolean> champ : bool.entrySet()){
			if(champ.getValue())
				stamp.setField(champ.getKey(), "Yes");
		}
	}
}
