package model;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;



public class PDFGenerator extends AbstractPdfGenerator {

	
	public PDFGenerator(ObjetPdfModel model){

		this.model = model;
		this.texte = model.getTexteMap();
		this.image = model.getImageMap();
		this.bool = model.getBoolMap();
		
		try {createPDF();} 
		catch (DocumentException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		
	}
	
	protected void addContent(){
		
		try { addTexte();}
		catch (IOException e) {	e.printStackTrace();}
		catch (DocumentException e) { e.printStackTrace();}
		
		
		try {addImage();}
		catch (MalformedURLException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		catch (DocumentException e) {e.printStackTrace();}
		
		try {addCheckBox();} 
		catch (IOException e) {e.printStackTrace();} 
		catch (DocumentException e) {e.printStackTrace();}
		
	}	
}
