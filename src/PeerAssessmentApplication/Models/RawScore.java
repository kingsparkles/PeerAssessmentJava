package PeerAssessmentApplication.Models;

public class RawScore {
	private String studentID;
	private float grade;
	
	public RawScore(String studentID, double d) {
		this.studentID = studentID;
		this.grade = (float)d;
	}
	
	public String getID() {
		return studentID;
	}
	
	public float getGrade() {
		return grade;
	}
}
