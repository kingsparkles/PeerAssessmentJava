package PeerAssessmentApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import PeerAssessmentApplication.Models.ModeratedMark;
import PeerAssessmentApplication.Models.RawScore;
import PeerAssessmentApplication.Models.StudentFeedbackLine;
import PeerAssessmentApplication.Models.StudentFormInput;

public class Calculator {
private HashMap<String, ModeratedMark> map;
	
	public Calculator() {
		map = new HashMap<String, ModeratedMark>();
	}
	
	public HashMap<String, ModeratedMark> mapAndCalculateInput(List<StudentFormInput> formInupts, List<RawScore> rawScores, boolean excludeSelfAwardedMarks) {
		
		for(RawScore rawScore : rawScores) {
			map.put(rawScore.getID(), new ModeratedMark(rawScore.getGrade()));
		}
		
		for(StudentFormInput formInput : formInupts) {
			for(StudentFeedbackLine line : formInput.getFeedbackLines()) {
				ModeratedMark studentMark = map.get(line.getStudentID());
				if(studentMark != null) {
					if(formInput.getAuthorID().equals(line.getStudentID())) {
						studentMark.setSelfAwarded(Float.parseFloat(line.getContribution()));
					} else {
						studentMark.addPeerMark(Float.parseFloat(line.getContribution()));
					}
				}
				map.put(line.getStudentID(), studentMark);
			}
		}
		
		for(Entry<String, ModeratedMark> entry : map.entrySet()) {
			ModeratedMark studentMark = entry.getValue();
			studentMark.calculateAverageMark(excludeSelfAwardedMarks);
			studentMark.calculateModeratedGrade();
			
			map.put(entry.getKey(), studentMark);
		}
		
		return map;
	}

}
