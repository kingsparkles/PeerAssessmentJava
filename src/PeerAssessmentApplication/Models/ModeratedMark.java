package PeerAssessmentApplication.Models;

import java.util.ArrayList;
import java.util.List;

public class ModeratedMark {
	private float selfAwardedMark;
	private List<Float> peerAwardedMarks;
	private float averageMark;
	private float rawGrade;
	private float moderatedGrade;
	
	public ModeratedMark(float rawGrade) {
		this.rawGrade = rawGrade;
		peerAwardedMarks = new ArrayList<>();
		averageMark = 0f;
		moderatedGrade = 0f;
		selfAwardedMark = 0f;
	}
	
	public void setSelfAwarded(float selfAwardedMark) {
		this.selfAwardedMark = selfAwardedMark;
	}
	
	public float getSelfAwarded() {
		return selfAwardedMark;
	}
	
	public void addPeerMark(float peerMark) {
		peerAwardedMarks.add(peerMark);
	}
	
	public List<Float> getPeerMarks(){
		return peerAwardedMarks;
	}
	
	public float getRawGrade() {
		return rawGrade;
	}
	
	public void calculateAverageMark(boolean excludeSelfAwardedMarks) {
		if(excludeSelfAwardedMarks) {
			float sum = 0;
			for(Float peerMark : peerAwardedMarks) {
				sum+= peerMark;
			}
			if(sum > 0) {
				averageMark = sum / peerAwardedMarks.size();
			}
		} else {
			float sum = selfAwardedMark;
			for(Float peerMark : peerAwardedMarks) {
				sum+= peerMark;
			}
			if(sum > 0) {
				averageMark = sum / (peerAwardedMarks.size()+1);
			}
		}
	}
	
	public float getAverageMark() {
		return averageMark;
	}
	
	public void calculateModeratedGrade() {
		int groupSize = peerAwardedMarks.size()+1;
		moderatedGrade = rawGrade * (averageMark/(100/groupSize));
		System.out.println("moderated " + rawGrade);
	}
	
	public float getModeratedGrade() {
		return moderatedGrade;
	}
}
