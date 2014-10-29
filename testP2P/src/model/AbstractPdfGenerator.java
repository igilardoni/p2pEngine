package model;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BadElementException;
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
	
	protected void createPDF(){
		
		
		PdfReader pdfTemplate = null;
		try {pdfTemplate = new PdfReader("modeles/"+texte.get("modele")+".pdf");} 
		catch (IOException e) {e.printStackTrace();}
		
		FileOutputStream fileOutputStream = null;
		try {fileOutputStream = new FileOutputStream("pdf/"+texte.get("filename")+".pdf");} 
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {stamper = new PdfStamper(pdfTemplate, fileOutputStream);} 
		catch (DocumentException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		
		stamp = stamper.getAcroFields();

		addContent();
		
		
		stamper.setFormFlattening(true);
		stamp.setGenerateAppearances(true);
		try {stamper.close();} 
		catch (DocumentException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		
		pdfTemplate.close();
		
		try {fileOutputStream.close();} 
		catch (IOException e) {e.printStackTrace();}
		
		
	}
	
	protected abstract void addContent();
	
	
	
	protected void addTexte(){
		for(Map.Entry<String,String> champ : texte.entrySet()){
			try {stamp.setField(champ.getKey(), champ.getValue());} 
			catch (IOException e) {e.printStackTrace();} 
			catch (DocumentException e) {e.printStackTrace();}
		}
	}
	
	protected void addImage(){
		for(Map.Entry<String,String> champ : image.entrySet()){
			
			Image img = null;
			try {img = Image.getInstance(String.format("%s", champ.getValue()));} 
			catch (BadElementException e) {e.printStackTrace();} 
			catch (MalformedURLException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}

			List<AcroFields.FieldPosition> positions = stamp.getFieldPositions(champ.getKey());
			Rectangle rect = positions.get(0).position;
		
			int page = positions.get(0).page;

			img.scaleAbsolute(rect.getWidth(), rect.getHeight());
			img.setAbsolutePosition(rect.getLeft(), rect.getBottom());

			try {stamper.getOverContent(page).addImage(img);} 
			catch (DocumentException e) {e.printStackTrace();}
		}
	}
	
	protected void addCheckBox(){
		for(Map.Entry<String,Boolean> champ : bool.entrySet()){
			if(champ.getValue())
				try {stamp.setField(champ.getKey(), "Yes");} 
				catch (IOException e) {e.printStackTrace();} 
				catch (DocumentException e) {e.printStackTrace();}
		}
	}
}
