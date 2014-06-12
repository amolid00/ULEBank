package es.unileon.ulebank.assets.documents;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
//import org.jsoup.Connection;
//import org.jsoup.Connection.Method;
//import org.jsoup.Jsoup;
//import org.jsoup.select.Elements;
//
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
import es.unileon.ulebank.assets.strategy.loan.ScheduledPayment;
import es.unileon.ulebank.assets.strategy.loan.StrategyLoan;

import es.unileon.ulebank.client.Person;

public class GeneratePDFDocument {

	/**
	 * Instance of the pdf document
	 */
//	private Document mipdf;
	
	/**
	 * Contract ID
	 */
	private int idContract;
	
	/**
	 * Open loan date
	 */
	private Date start;
	
	/**
	 * Close loan date
	 */
	private Date finish;
	
	/**
	 * Loans payments
	 */
	private List<ScheduledPayment> payments;
	
	/**
	 * Loans owner
	 */
	private Person client;

	public GeneratePDFDocument(int idContract, Date start, Date finish, StrategyLoan sl, Person p){
		this.idContract = idContract;
		this.start = start;
		this.finish = finish;
		this.payments = sl.doCalculationOfPayments();
		this.client = p;
		this.generatePDFDocument();
	}

	/**
	 * Method used to create the contract document of a loan
	 */
	public void generatePDFDocument() {
//		mipdf = new Document();
//		try {
//			PdfWriter.getInstance(mipdf, new FileOutputStream("_contrato.pdf"));
//		} catch (FileNotFoundException e) {
//		} catch (DocumentException e) {
//		}
//		
//		mipdf.open();
//		mipdf.addTitle("Plan de amortizacion de prestamo bancario");
//		mipdf.addAuthor("ULE-BANK");
//		mipdf.addSubject("Amortizacion de prestamo");
//		addBase();
//		mipdf.close();
	}
	
	/**
	 * Add all data to the pdf
	 */
	private void addBase(){
		addTitle();
		separator();
		addAnnex(idContract);
		addDates(start,finish);
		separator();
		addClientInformation();
		separator();
		addSignatures();
		separator();
		addAmortization();
	}
	
	/**
	 * Add clients data
	 */
	private void addClientInformation(){
//		String name = "Nombre: " + client.getName() + " " + client.getSurnames();
//		String address = "Direccion: " + client.getAddress();
//		String civilState = "Estado civil: " + client.getCivilState();
//		String telephone = "Telefono: " + client.getPhoneNumber(0);
//		String proffesion = "Ocupacion: " + client.getProfession();
//		String birthDate = "Fecha nacimiento: " + client.getBirthDate();
//		
//		Font font = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
//		
//		try {
//			mipdf.add(new Paragraph(name, font));
//			mipdf.add(new Paragraph(address, font));
//			mipdf.add(new Paragraph(civilState, font));
//			mipdf.add(new Paragraph(telephone, font));
//			mipdf.add(new Paragraph(proffesion, font));
//			mipdf.add(new Paragraph(birthDate, font));
//		} catch (DocumentException e) {
//		}
	}
	
	/**
	 * Add amortization table
	 */
	private void addAmortization(){
//		try {
//			mipdf.add(generateAmortTable());
//		} catch (DocumentException e) {
//		}
	}

	/**
	 * Generate amortization table with payments
	 * @param amort
	 * @return
	 */
//	private PdfPTable generateAmortTable() {
//		
//		PdfPTable mitablacompleja = new PdfPTable(6);
//		mitablacompleja.setWidthPercentage(100);
//		PdfPCell celda = new PdfPCell(new Paragraph(
//				"VENCIMIENTO", FontFactory.getFont("Times-Roman",
//						12,
//						Font.BOLD,
//						BaseColor.BLACK)));
//		celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		celda.setColspan(2);
//		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
//		celda.setPadding(6.0f);
//		celda.setBackgroundColor(BaseColor.GRAY);
//		mitablacompleja.addCell(celda);
//
//		PdfPCell celda2 = new PdfPCell(new Paragraph(
//				"IMPORTE PLAZO", FontFactory.getFont("Times-Roman",
//						12,
//						Font.BOLD,
//						BaseColor.BLACK)));
//		celda2.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		celda2.setHorizontalAlignment(Element.ALIGN_CENTER);
//		celda2.setPadding(6.0f);
//		celda2.setBackgroundColor(BaseColor.GRAY);
//		mitablacompleja.addCell(celda2);
//		
//		PdfPCell celda3 = new PdfPCell(new Paragraph(
//				"AMORT. CAPITAL", FontFactory.getFont("Times-Roman",
//						12,
//						Font.BOLD,
//						BaseColor.BLACK)));
//		celda3.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		celda3.setHorizontalAlignment(Element.ALIGN_CENTER);
//		celda3.setPadding(6.0f);
//		celda3.setBackgroundColor(BaseColor.GRAY);
//		mitablacompleja.addCell(celda3);
//		
//		PdfPCell celda4 = new PdfPCell(new Paragraph(
//				"INTERESES", FontFactory.getFont("Times-Roman",
//						12,
//						Font.BOLD,
//						BaseColor.BLACK)));
//		celda4.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		celda4.setHorizontalAlignment(Element.ALIGN_CENTER);
//		celda4.setPadding(6.0f);
//		celda4.setBackgroundColor(BaseColor.GRAY);
//		mitablacompleja.addCell(celda4);
//		
//		PdfPCell celda5 = new PdfPCell(new Paragraph(
//				"CAPITAL PENDIENTE", FontFactory.getFont("Times-Roman",
//						12,
//						Font.BOLD,
//						BaseColor.BLACK)));
//		celda5.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		celda5.setHorizontalAlignment(Element.ALIGN_CENTER);
//		celda5.setPadding(6.0f);
//		celda5.setBackgroundColor(BaseColor.GRAY);
//		mitablacompleja.addCell(celda5);
//		
//		
//		//Fill in the table with payments
//		
//		for(int i=0; i<this.payments.size(); i++){
//			ScheduledPayment sp = payments.get(i);
//			
//			celda = new PdfPCell(new Paragraph("" + sp.getExpiration(), FontFactory.getFont("Times-Roman",
//					10,
//					Font.NORMAL,
//					BaseColor.BLACK)));
//			celda.setColspan(2);
//			celda.setBackgroundColor(BaseColor.GRAY);
//			mitablacompleja.addCell(celda);
//			
//			celda = new PdfPCell(new Paragraph("" + sp.getImportOfTerm() + "���", FontFactory.getFont("Times-Roman",
//					10,
//					Font.NORMAL,
//					BaseColor.BLACK)));
//			mitablacompleja.addCell(celda);
//			
//			celda = new PdfPCell(new Paragraph("" + sp.getAmortization() + "���", FontFactory.getFont("Times-Roman",
//					10,
//					Font.NORMAL,
//					BaseColor.BLACK)));
//			mitablacompleja.addCell(celda);
//			
//			celda = new PdfPCell(new Paragraph("" + sp.getInterests() + "���", FontFactory.getFont("Times-Roman",
//					10,
//					Font.NORMAL,
//					BaseColor.BLACK)));
//			mitablacompleja.addCell(celda);
//			
//			celda = new PdfPCell(new Paragraph("" + sp.getOutstandingCapital() + "���", FontFactory.getFont("Times-Roman",
//					10,
//					Font.NORMAL,
//					BaseColor.BLACK)));
//			mitablacompleja.addCell(celda);
//			
//		}
//		
//		return mitablacompleja;
//	}

	/**
	 * Add the physical and temporal location for signatures
	 */
	private void addSignatures() {
		String line1 = "\nEn prueba de conformidad, firman las partes el presente contrato en ..... hojas y tantos ejemplares como partes intervienen.";
		String line2 = "En "+ this.getGeolocalization() +" a " + Calendar.getInstance().getTime();
		String line3 = "El/Los Prestatario/s \t\t\t El/Los Fiador/es \t\t\t El Financiador \t\t\t El Fedatario\n\n\n\n\n\n";

//		Font font = FontFactory.getFont("Times-Roman", 12, Font.ITALIC);
//
//		try {
//			mipdf.add(new Paragraph(line1, font));
//			mipdf.add(new Paragraph(line2, font));
//			mipdf.add(new Paragraph(line3, font));
//		} catch (DocumentException e) {
//		}
	}

	/**
	 * Separator
	 */
	private void separator() {
		String separator = "\n----------------------------------------------------------------------------------------------------------------------------------";
//		try {
//			mipdf.add(new Paragraph(separator+ "\n"));
//		} catch (DocumentException e) {
//		}
	}

	/**
	 * Add open and finish loan dates
	 * @param start
	 * @param finish
	 */
	private void addDates(Date start, Date finish) {
		String titulo = "Celebrado entre: "+start +" y "+ finish;
		
//		Font font = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
//		
//		try {
//			mipdf.add(new Paragraph(titulo, font));
//		} catch (DocumentException e) {
//		}
	}

	/**
	 * Add annex
	 * @param num
	 */
	private void addAnnex(int num) {
		String titulo = "Anexo al Contrato de Financiacion a comprador de bienes inmuebles, impreso n�� "+num;
		
//		Font font = FontFactory.getFont("Times-Roman", 12, Font.UNDERLINE);
//		
//		try {
//			mipdf.add(new Paragraph(titulo, font));
//		} catch (DocumentException e) {
//		}
	}

	/**
	 * Add title
	 */
	private void addTitle() {
		String titulo = "PLAN DE AMORTIZACION DE PRESTAMO";

//        Font font = FontFactory.getFont("Times-Roman", 18, Font.BOLD);
//		
//		try {
//			mipdf.add(new Paragraph(titulo, font));
//		} catch (DocumentException e) {
//		}
	}
	
	/**
	 * Gets the location based on the IP address where the computer is running
	 * @return location
	 */
	private String getGeolocalization(){
		String localization = "";
//		org.jsoup.nodes.Document doc;
//		try {
//			// ISO-8859-15
//			Connection connectionTest = Jsoup.connect("http://www.geoiptool.com/es/")
//					.cookie("cookiereference", "cookievalue")
//					.method(Method.POST);
//			doc = Jsoup.parse(new String(
//					connectionTest.execute().bodyAsBytes(),"ISO-8859-15"));
//			
//			Elements query=doc.select("td[class=arial_bold]");
//			org.jsoup.nodes.Element interest = query.get(5);
//			char [] charsInterest=interest.text().toCharArray();
//
//			localization = String.copyValueOf(charsInterest,0,charsInterest.length);
//		} catch (IOException e) {
//		}
		return localization + " " + Locale.getDefault().getCountry();
	}

	/**
	 * Add text without any format
	 * @param contenido
	 */
	public void anyadirParrafo(String contenido){
//		try {
//			mipdf.add(new Paragraph(contenido));
//		} catch (DocumentException e) {
//		}
	}
	
}