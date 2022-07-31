package com.subhan.onlinebank.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.subhan.onlinebank.entiry.Transaction;

public class PDFGenerator {

	private static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

	public static ByteArrayInputStream employeePDFReport(List<Transaction> employees) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfWriter.getInstance(document, out);
			document.open();

			// Add Text to PDF file -> 
			Font font1 = FontFactory.getFont(FontFactory.COURIER);
			Font font = FontFactory.getFont(FontFactory.COURIER,11);
			Paragraph para = new Paragraph("Bank Statement", font1);
			Font font_2 = FontFactory.getFont(FontFactory.COURIER,10);
			Paragraph para1 = new Paragraph("Name     : KMSubhan", font_2);
			Paragraph para2 = new Paragraph("Account  : Bank Statement", font_2);
			Paragraph para3 = new Paragraph("Address  : Bank Statement", font_2);
		//	para.setAlignment(Element.ALIGN_CENTER);
			document.add(para); 
			
			document.add(para1); document.add(para2); document.add(para3); 
			document.add(Chunk.NEWLINE);
			document.setPageSize(PageSize.LETTER);

			  float [] pointColumnWidths = {100F, 200F, 80F, 80F, 85F};  
			PdfPTable table = new PdfPTable(pointColumnWidths);
			 table.setWidthPercentage(95);
			// Add PDF Table Header ->
			Stream.of("DATE", "NARRATION", "CREDIT","DEBIT","BALANCE").forEach(headerTitle -> {
				PdfPCell header = new PdfPCell();
			//	Font headFont = FontFactory.getFont(FontFactory.HELVETICA,12);
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			//	header.setHorizontalAlignment(Element.ALIGN_CENTER);
				//header.setBorderWidth(1);
			//	header.setPhrase(new Phrase(headerTitle, headFont));
				header.setPhrase(new Phrase(headerTitle,font));
				table.addCell(header);
			});

			for (Transaction tx : employees) {
				
				PdfPCell dateCell = new PdfPCell(new Phrase(getStringDate(tx.getCreated()),font));
				
				//  dateCell.setPaddingLeft(4);
				  dateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				  dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				   table.addCell(dateCell);

				PdfPCell narrationCell = new PdfPCell(new Phrase(tx.getNarration(),font));
				
				
				 // narrationCell.setPaddingLeft(4);
				  narrationCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				  narrationCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 
				table.addCell(narrationCell);

				/*
				 * PdfPCell refCell = new PdfPCell(new Phrase("TX1567",font)); //
				 * creditedCell.setPaddingRight(4);
				 * refCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 * refCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				 * 
				 * table.addCell(refCell);
				 */
				
				PdfPCell creditedCell = new PdfPCell(new Phrase(tx.getCredit(),font));
				// creditedCell.setPaddingRight(4);
				  creditedCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				  creditedCell.setHorizontalAlignment(Element.ALIGN_RIGHT);			 
				 
				table.addCell(creditedCell);
				
				PdfPCell debitedCell = new PdfPCell(new Phrase(tx.getDebit(),font));
				
				//  debitedCell.setPaddingLeft(4);
				  debitedCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				  debitedCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				 
				table.addCell(debitedCell);

				PdfPCell balCell = new PdfPCell(new Phrase(""+tx.getUpdatedBal(),font));
				
				  balCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				  balCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//  balCell.setPaddingRight(4);
				 
				table.addCell(balCell);
			}
			document.add(table);

			document.close();
		} catch (DocumentException e) {
			logger.error(e.toString());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	private static String getStringDate(LocalDateTime now) {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
	        String formatDateTime_str = now.format(formatter);	
		return formatDateTime_str;
	}
}
