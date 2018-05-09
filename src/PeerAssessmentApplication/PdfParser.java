package PeerAssessmentApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;

import PeerAssessmentApplication.Models.StudentFeedbackLine;
import PeerAssessmentApplication.Models.StudentFormInput;

public class PdfParser {

	private List<StudentFormInput> studentInputs;
	
	public List<StudentFormInput> parsePDFs(String folderLocation) {
		//list to store the names of files
		List<String> pdfFiles = new ArrayList<>();
		File folder = new File(folderLocation);
		//lists all files in the folder
		File[] files = folder.listFiles();
		//adds any pdf file paths to the string list
		for(int i = 0; i < files.length; i++) {
			if(files[i].getPath().indexOf(".pdf") >= 0) {
				pdfFiles.add(files[i].getPath());
			}
		}
		
		studentInputs = new ArrayList<>();
		
		//iterates over all the pdf file paths
		Iterator<String> iterator = pdfFiles.iterator();
		while(iterator.hasNext()) {
			parsePDF(iterator.next());
		}
		
		ValidateStudentInput validator = new ValidateStudentInput();
		validator.listErrors(studentInputs);
		
		return studentInputs;
	}
	
	public void parsePDF(String pdfFile) {
		try {
			PdfReader reader = new PdfReader(pdfFile);
			AcroFields fields = reader.getAcroFields();
			
			if(fields.getField("authorID") == null) {
				return;
			}
			
			StudentFormInput studentInput = new StudentFormInput(fields.getField("authorID").toLowerCase().trim(), pdfFile);
			
			//checks to see if an id is empty before adding a line as some groups may have less than 4 people
			if(fields.getField("student1ID").isEmpty() == false)
			studentInput.addFeedbackLine(new StudentFeedbackLine(fields.getField("student1ID"),
					fields.getField("student1Contribution"), fields.getField("student1Comments")));
			
			if(fields.getField("student2ID").isEmpty() == false)
			studentInput.addFeedbackLine(new StudentFeedbackLine(fields.getField("student2ID"),
					fields.getField("student2Contribution"), fields.getField("student2Comments")));
			
			if(fields.getField("student3ID").isEmpty() == false)
			studentInput.addFeedbackLine(new StudentFeedbackLine(fields.getField("student3ID"),
					fields.getField("student3Contribution"), fields.getField("student3Comments")));
			
			if(fields.getField("student4ID").isEmpty() == false)
			studentInput.addFeedbackLine(new StudentFeedbackLine(fields.getField("student4ID"),
					fields.getField("student4Contribution"), fields.getField("student4Comments")));
			
			studentInputs.add(studentInput);
			
		} catch (Exception e) {
			
		}
	}
}
