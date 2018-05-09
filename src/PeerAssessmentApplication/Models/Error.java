package PeerAssessmentApplication.Models;

public class Error {
	
	private String fileName;
	private String errorMessage;
	
	public Error(String fileName, String errorMessage ) {
		this.fileName = fileName;
		this.errorMessage = errorMessage;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
