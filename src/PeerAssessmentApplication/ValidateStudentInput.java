package PeerAssessmentApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import PeerAssessmentApplication.Models.Error;
import PeerAssessmentApplication.Models.StudentFeedbackLine;
import PeerAssessmentApplication.Models.StudentFormInput;

public class ValidateStudentInput {

	public List<Error> listErrors(List<StudentFormInput> studentInputs){
		
		String idRegex = "u?[0-9]{7}";
		Pattern pattern = Pattern.compile(idRegex);
		
		Matcher matcher;
		
		List<Error> errors = new ArrayList<>();
		for(StudentFormInput input : studentInputs) {
			float totalCoins = 0;
			matcher = pattern.matcher(input.getAuthorID());
			if(matcher.matches() == false) {
				errors.add(new Error(input.getFileName(), "ID has incorrect format"));
			}
			for(StudentFeedbackLine feedbackLine : input.getFeedbackLines()) {
				matcher = pattern.matcher(feedbackLine.getStudentID());
				if(matcher.matches() == false) {
					errors.add(new Error(input.getFileName(), "ID has incorrect format"));
				}
				try {
					totalCoins += Float.parseFloat(feedbackLine.getContribution());
				}catch(NumberFormatException e) {
					errors.add(new Error(input.getFileName(), "Contribution percentage is not a number"));
				}
			}
			if((int)totalCoins!=100) {
				errors.add(new Error(input.getFileName(), Float.toString(totalCoins)));
			}
		}
		return errors;
	}
}
