package PeerAssessmentApplication.Models;

import java.util.ArrayList;
import java.util.List;

public class StudentFormInput {
	private String authorID;
	private String fileName;
	private List<StudentFeedbackLine> feedbackLines;
	
	public StudentFormInput(String authorID, String fileName) {
		feedbackLines = new ArrayList<>();
		this.authorID = authorID;
		this.fileName = fileName;
	}
	
	public String getAuthorID() {
		return authorID;
	}
	
	public void setAuthorID(String newID) {
		authorID = newID;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public List<StudentFeedbackLine> getFeedbackLines(){
		return feedbackLines;
	}
	
	public void setFeedbackLines(List<StudentFeedbackLine> newLines) {
		feedbackLines = newLines;
	}
	
	public void addFeedbackLine(StudentFeedbackLine feedbackLine) {
		feedbackLines.add(feedbackLine);
	}
}
