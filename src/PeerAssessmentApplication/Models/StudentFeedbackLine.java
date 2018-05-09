package PeerAssessmentApplication.Models;

public class StudentFeedbackLine {
	private String studentID;
	private String contributionPercentage;
	private String comments;
	
	public StudentFeedbackLine(String studentID, String contributionPercentage, String comments) {
		this.studentID = studentID;
		this.contributionPercentage = contributionPercentage;
		this.comments = comments;
	}
	
	public String getStudentID() {
		return studentID;
	}
	
	public void setStudentID(String newID) {
		studentID = newID;
	}
	
	public String getContribution() {
		return contributionPercentage;
	}
	
	public void setContribution(String newContribution) {
		contributionPercentage = newContribution;
	}
	
	public String getComments() {
		return comments;
	}
}
