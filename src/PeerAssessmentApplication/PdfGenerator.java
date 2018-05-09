package PeerAssessmentApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;

public class PdfGenerator {
	
	public void Generate(String destination) throws DocumentException, IOException {
		//https://developers.itextpdf.com/examples/itext5-building-blocks/page-size
		Document document = new Document(PageSize.A4, 36, 36, 36, 36);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(destination));
		document.open();
		
		//Fonts to be used in the different paragraphs and tables
		Font headingFont = new Font(FontFamily.COURIER, 28, Font.BOLD);
		Font subheadingFont = new Font(FontFamily.COURIER, 16, Font.NORMAL, BaseColor.BLUE);
		Font standardFont = new Font(FontFamily.COURIER, 12);
		
		//Paragraphs contain the non-editable text in the pdf
		Paragraph pageHeading = new Paragraph("Peer Assessment Form", headingFont);
		pageHeading.setSpacingAfter(18f);
		
		Paragraph disclaimerHeading = new Paragraph("Complete highlighted spaces below", subheadingFont);
		disclaimerHeading.setSpacingAfter(8f);
		
		Paragraph disclaimerContent = new Paragraph("Peer assessments which do not follow this template, and peer assessments which are incomplete will not be counted", standardFont);
		disclaimerContent.setSpacingAfter(10f);
		
		Paragraph idHeading = new Paragraph("Your details: ", subheadingFont);
		idHeading.setSpacingAfter(8f);
		
		Paragraph contributionHeading = new Paragraph("Group member contribution:", subheadingFont);
		contributionHeading.setSpacingBefore(8f);
		contributionHeading.setSpacingAfter(8f);
		
		Paragraph contributionContentp1 = new Paragraph("You must provide a comment that briefly lists or descrives each group member's contribution "+
				"to the Group Project, focusing on their contributions since the last peer assessment report (or the start of the semester, for the first report)."+
				" Peer assessments that do not include substantive comments will be considered incomplete and will not be counted.", standardFont);
		contributionContentp1.setSpacingAfter(8f);
		
		Paragraph contributionContentp2 = new Paragraph("The contribution to group work should reflect both the effort and the output of the group member towards the group work.", standardFont);
		contributionContentp2.setSpacingAfter(8f);
		
		Paragraph contributionFooter = new Paragraph("Note that the percentages must add to 100%, and remember to include yourself!", standardFont);
		contributionFooter.setSpacingAfter(8f);
		
		//Tables are non-editable but provide a nice grid to put editable fields inside
		PdfPTable idTable = new PdfPTable(2);
		idTable.setWidthPercentage(100);
		idTable.setWidths(new float[] {1,2});
		idTable.addCell(CreateCell("Your Student ID: ", standardFont));
		idTable.addCell(CreateCell("", standardFont));
		
		PdfPTable feedbackTable = new PdfPTable(3);
		feedbackTable.setWidthPercentage(100);
		feedbackTable.setWidths(new float[] {1, 1, 3});
		feedbackTable.addCell(CreateCell("Group Member Student ID", standardFont));
		feedbackTable.addCell(CreateCell("Contribution as a percentage", standardFont));
		feedbackTable.addCell(CreateCell("Comments", standardFont));
		
		//adding 12 cells for 3 columns for 4 students
		for(int i = 0; i < 12; i++) {
			feedbackTable.addCell(CreateCell("", standardFont, 30f));
		}
		
		document.add(pageHeading);
		document.add(disclaimerHeading);
		document.add(disclaimerContent);
		document.add(idHeading);
		document.add(idTable);
		document.add(contributionHeading);
		document.add(contributionContentp1);
		document.add(contributionContentp2);
		document.add(contributionFooter);
		document.add(feedbackTable);
		
		//creating a list of text fields so that they can be iterated over to add to the page
		List<TextField> textFields = new ArrayList<>();
		textFields.add(new TextField(writer, new Rectangle(211, 620, 558, 636), "authorID"));
		
		textFields.add(new TextField(writer, new Rectangle(37, 307, 140, 335), "student1ID"));
		textFields.add(new TextField(writer, new Rectangle(37, 277, 140, 305), "student2ID"));
		textFields.add(new TextField(writer, new Rectangle(37, 247, 140, 275), "student3ID"));
		textFields.add(new TextField(writer, new Rectangle(37, 217, 140, 245), "student4ID"));
		
		textFields.add(new TextField(writer, new Rectangle(141, 307, 244, 335), "student1Contribution"));
		textFields.add(new TextField(writer, new Rectangle(141, 277, 244, 305), "student2Contribution"));
		textFields.add(new TextField(writer, new Rectangle(141, 247, 244, 275), "student3Contribution"));
		textFields.add(new TextField(writer, new Rectangle(141, 217, 244, 245), "student4Contribution"));
		
		textFields.add(new TextField(writer, new Rectangle(246, 307, 557, 335), "student1Comments"));
		textFields.add(new TextField(writer, new Rectangle(246, 277, 557, 305), "student2Comments"));
		textFields.add(new TextField(writer, new Rectangle(246, 247, 557, 275), "student3Comments"));
		textFields.add(new TextField(writer, new Rectangle(246, 217, 557, 245), "student4Comments"));
		
		//adding the fields to the page as form fields
		Iterator<TextField> iterator = textFields.iterator();
		while(iterator.hasNext()) {
			PdfFormField field = iterator.next().getTextField();
			writer.addAnnotation(field);
		}
		
		document.close();
	}
	
	public PdfPCell CreateCell(String contents, Font font) {
		PdfPCell cell = new PdfPCell();
		cell.setPhrase(new Phrase(contents, font));
		return cell;
	}
	
	//override method if cells need to be a certain height
	public PdfPCell CreateCell(String contents, Font font, float height) {
		PdfPCell cell = new PdfPCell();
		cell.setPhrase(new Phrase(contents, font));
		cell.setMinimumHeight(height);
		return cell;
	}
}
