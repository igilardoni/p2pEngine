package model;

import java.io.IOException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;



public class PDFGenerator extends AbstractPdfGenerator {
	
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	
	public PDFGenerator(ObjetPdfModel model) throws IOException, DocumentException{

		this.model = model;
		this.texte = model.getTexteMap();
		this.image = model.getImageMap();
		this.bool = model.getBoolMap();
		createPDF();
		
	}
	
	protected void addContent() throws DocumentException, IOException {
		
		addTexte();
		addImage();
		addCheckBox();
		
	}	
}
