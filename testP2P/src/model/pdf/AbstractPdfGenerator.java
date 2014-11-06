package model.pdf;

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
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


public abstract class AbstractPdfGenerator {	
	
	protected Document document;
	protected PdfReader pdfTemplate = null;
	protected FileOutputStream fileOutputStream = null;
	protected AcroFields stamp;
	protected PdfStamper stamper;
	protected ByteArrayOutputStream byteStream;
	protected PdfCopy copy = null;
	protected HashMap<String,String> texte = new HashMap<String,String>();
	protected HashMap<String,String> image = new HashMap<String,String>();
	protected HashMap<String,Boolean> bool = new HashMap<String,Boolean>();
	
	protected void createPdf(String fileOut, String modele){
			
		openPdfReader(modele);
		openFileOut(fileOut);
		openStamper();
		
		addContent();
		flattenPdf();
		
		closeStamper();
		closePdfReader();
		closeFileOut();
	}
	
	protected void mergePdf(List<String> modeles, String fileOut){
		
		document = new Document();
		
		try {copy = new PdfCopy(document, new FileOutputStream("modeles/"+fileOut+".pdf"));}
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (DocumentException e) {e.printStackTrace();}
		
		document.open();
		byteStream = new ByteArrayOutputStream();
		
		for(String modele : modeles){
			
			openPdfReader(modele);
			
			stamper = null;
			try {stamper = new PdfStamper(pdfTemplate, byteStream);} 
			catch (DocumentException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
			
	
			PdfContentByte cb = stamper.getUnderContent(1);
			try {stamper.close();} 
			catch (DocumentException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
			
			
			PdfReader outReader = null;
			try {outReader = new PdfReader(byteStream.toByteArray());} 
			catch (IOException e) {e.printStackTrace();}
			
			try {copy.addPage(copy.getImportedPage(outReader,1));} 
			catch (BadPdfFormatException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
			
			outReader.close();
			closePdfReader();
		}
		
		copy.close();
		try {byteStream.close();} 
		catch (IOException e) {e.printStackTrace();}
		
	//	createPdf(fileOut, fileOut);
	}
	
	protected void openPdfReader(String modele){
		
		try {pdfTemplate = new PdfReader("modeles/"+modele+".pdf");} 
		catch (IOException e) {e.printStackTrace();}
	}
	
	protected void openFileOut(String fileOut){
		
		try {fileOutputStream = new FileOutputStream("pdf/"+fileOut+".pdf");} 
		catch (FileNotFoundException e) {e.printStackTrace();}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	}
	
	protected void openStamper(){
		try {stamper = new PdfStamper(pdfTemplate, fileOutputStream);} 
		catch (DocumentException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		
		stamp = stamper.getAcroFields();
	}
	
	protected void flattenPdf(){
		
		stamper.setFormFlattening(true);
		stamp.setGenerateAppearances(true);
	}
	
	protected void closePdfReader(){
	
		pdfTemplate.close();
	}
	
	protected void closeStamper(){
		
		try {stamper.close();} 
		catch (DocumentException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		
	}
	protected void closeFileOut(){
		
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
			if(champ.getValue() != null){
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
