package by.training.lysiuk.project.datamodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Competition implements Serializable {
	
	private Date startDateSet;
	
	private Date endDateSet;

	public Date getStartDateSet() {
		return startDateSet;
	}

	public void setStartDateSet(Date startDateSet) {
		this.startDateSet = startDateSet;
	}

	public Date getEndDateSet() {
		return endDateSet;
	}

	public void setEndDateSet(Date endDateSet) {
		this.endDateSet = endDateSet;
	}

	private Faculty faculty;

	private Integer plan;

	private Long currentAmount;

	private List<Enrolee> enrolees;

	private double competition;

	private int passingScore;

	public double getCompetition() {
		return competition;
	}

	public void setCompetition(double competition) {
		this.competition = competition;
	}

	public int getPassingScore() {
		return passingScore;
	}

	public void setPassingScore(int passingScore) {
		this.passingScore = passingScore;
	}

	public List<Enrolee> getEnrolees() {
		return enrolees;
	}

	public void setEnrolees(List<Enrolee> enrolees) {
		this.enrolees = enrolees;
	}

	public Competition() {
		super();

	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Integer getPlan() {
		return plan;
	}

	public void setPlan(Integer plan) {
		this.plan = plan;
	}

	public Long getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(Long currentAmount) {
		this.currentAmount = currentAmount;
	}

}
